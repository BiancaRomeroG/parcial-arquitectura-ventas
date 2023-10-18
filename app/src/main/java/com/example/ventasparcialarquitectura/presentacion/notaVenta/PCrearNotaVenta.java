package com.example.ventasparcialarquitectura.presentacion.notaVenta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NNotaVenta;
import com.example.ventasparcialarquitectura.negocio.NVendedor;
import com.example.ventasparcialarquitectura.negocio.NProducto;
import com.example.ventasparcialarquitectura.presentacion.producto.PCrearProducto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PCrearNotaVenta extends AppCompatActivity {

    private EditText etDate, editTextCliente, editTextNit, editTextCantidad;
    private TextView tvTotal;
    private Spinner spVendedor;
    private Spinner spProducto;
    private Button btnGuardarNotaVenta, btnAgregarProducto;
    private TableLayout tableProductos;
    private NNotaVenta nNotaVenta = new NNotaVenta();
    private NVendedor nVendedor = new NVendedor();
    private NProducto nProducto = new NProducto();

    int productoId = -1;
    int vendedorId = -1;
    double total = 0;

    private Map<Integer, String> vendedorMap = new HashMap<>();
    private List<Object[]> listaProductos = new ArrayList<>();
    private List<Object[]> productosSeleccionados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nota_venta);

        etDate = findViewById(R.id.et_date);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        editTextCliente = findViewById(R.id.editTextCliente);
        editTextNit = findViewById(R.id.editTextNit);
        editTextCantidad = findViewById(R.id.editTextCantidad);
        tvTotal = findViewById(R.id.tvTotal);
        spVendedor = findViewById(R.id.spVendedor);
        spProducto = findViewById(R.id.spProducto);
        tableProductos = findViewById(R.id.tableProductos);
        btnGuardarNotaVenta = findViewById(R.id.btnGuardarNotaVenta);
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto);

        obtenerVendedores();
        obtenerProductos();

        btnAgregarProducto.setOnClickListener(view -> {
            agregarProducto();
        });

        btnGuardarNotaVenta.setOnClickListener(view -> {
            guardarNotaVenta();
            finish();
        });


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void obtenerVendedores() {
        new GetListaVendedoresTask().execute();
    }

    private void obtenerProductos() {
        new GetListaProductosTask().execute();
    }

    private void agregarProducto() {
        String nombreProducto = spProducto.getSelectedItem().toString();
        int cantidad = Integer.valueOf(editTextCantidad.getText().toString().trim());

        int idProducto = -1;
        double precio = 0;

        for (Object[] producto : listaProductos) {
            if (producto[3].equals(nombreProducto)) {
                idProducto = (int) producto[0];
                precio = (double) producto[5];
            }
        }

        productosSeleccionados.add(new Object[]{idProducto, precio, cantidad, precio * cantidad});

        // add to table
        TableRow row = new TableRow(this);
        TextView tvNombreProducto = new TextView(this);
        TextView tvCantidad = new TextView(this);
        TextView tvPrecio = new TextView(this);
        TextView tvTotal = new TextView(this);

        int margin = 45;
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
        layoutParams.setMargins(margin, margin, margin, margin);

        // set values
        tvNombreProducto.setText(nombreProducto);
        tvCantidad.setText(String.valueOf(cantidad));
        tvPrecio.setText(String.valueOf(precio));
        tvTotal.setText(String.valueOf(precio * cantidad));
        // add to row
        row.addView(tvNombreProducto, layoutParams);
        row.addView(tvCantidad, layoutParams);
        row.addView(tvPrecio, layoutParams);
        row.addView(tvTotal, layoutParams);
        // add to table
        tableProductos.addView(row);
        editTextCantidad.setText("");
        total += precio * cantidad;
        this.tvTotal.setText("Total: " + total);
    }


    private class GetListaVendedoresTask extends AsyncTask<Void, Void, List<Object[]>> {
        @Override
        protected List<Object[]> doInBackground(Void... voids) {
            return nVendedor.getLista();
        }

        @Override
        protected void onPostExecute(List<Object[]> vendedores) {
            for (Object[] vendedor : vendedores) {
                vendedorMap.put((int) vendedor[0], (String) vendedor[3]);
            }

            List<String> vendedoresList = new ArrayList<>(vendedorMap.values());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(PCrearNotaVenta.this, android.R.layout.simple_spinner_item, vendedoresList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spVendedor.setAdapter(adapter);
        }
    }

    private class GetListaProductosTask extends AsyncTask<Void, Void, List<Object[]>> {
        @Override
        protected List<Object[]> doInBackground(Void... voids) {
            return nProducto.getLista();
        }

        @Override
        protected void onPostExecute(List<Object[]> productos) {
            listaProductos = productos;

            List<String> nombresProductos = new ArrayList<>();

            for (Object[] producto : productos) {
                nombresProductos.add((String) producto[3]);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(PCrearNotaVenta.this, android.R.layout.simple_spinner_item, nombresProductos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spProducto.setAdapter(adapter);
        }
    }


    private void showDatePickerDialog() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                etDate.setText(selectedDate);
            }
        });
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void guardarNotaVenta() {
        String cliente = editTextCliente.getText().toString().trim();
        String nit = editTextNit.getText().toString().trim();
        String fecha = etDate.getText().toString().trim();
        String cantidad = editTextCantidad.getText().toString().trim();
        String nombreVendedor = spVendedor.getSelectedItem().toString();


        for (Map.Entry<Integer, String> entry : vendedorMap.entrySet()) {
            if (entry.getValue().equals(nombreVendedor)) {
                vendedorId = entry.getKey();
            }
        }

        Object[] venta = new Object[]{vendedorId, cliente, nit, fecha, total};
        new GuardarVentaTask().execute(venta);
    }

    private class GuardarVentaTask extends AsyncTask<Object[], Void, Void> {
        @Override
        protected Void doInBackground(Object[]... objects) {
            nNotaVenta.registrarVentaConDetalle((int) objects[0][0], (String) objects[0][1],
                    (String) objects[0][2], (String) objects[0][3], (double) objects[0][4], productosSeleccionados);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
