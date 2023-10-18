package com.example.ventasparcialarquitectura.presentacion.notaVenta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NNotaVenta;
import com.example.ventasparcialarquitectura.negocio.NProducto;
import com.example.ventasparcialarquitectura.negocio.NVendedor;

import java.util.ArrayList;
import java.util.List;

public class PVerNotaVenta extends AppCompatActivity {

    private EditText etDate, editTextCliente, editTextNit;
    private TextView tvTotal;
    private Spinner spVendedor;
    private Button btnGuardarNotaVenta, btnAgregarProducto;
    private TableLayout tableProductos;
    private NNotaVenta nNotaVenta = new NNotaVenta();
    private NVendedor nVendedor = new NVendedor();
    private NProducto nProducto = new NProducto();

    int productoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_nota_venta);

        editTextCliente = findViewById(R.id.editTextCliente);
        editTextNit = findViewById(R.id.editTextNit);
        etDate = findViewById(R.id.et_date);
        spVendedor = findViewById(R.id.spVendedor);
        tableProductos = findViewById(R.id.tableProductos);
        tvTotal = findViewById(R.id.tvTotal);

        productoId = getIntent().getIntExtra("notaVentaId", -1);

        obtenerNotaVenta();

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

    void obtenerNotaVenta() {
        new GetNotaVentaTask().execute();
    }

    private class GetNotaVentaTask extends AsyncTask<Void, Void, Object[]> {
        @Override
        protected Object[] doInBackground(Void... voids) {
            return nNotaVenta.getPorIdConDetalle(productoId);
        }

        @Override
        protected void onPostExecute(Object[] notaVenta) {
            editTextCliente.setText(notaVenta[0].toString());
            editTextNit.setText(notaVenta[1].toString());
            etDate.setText(notaVenta[2].toString());
            List<String> vendedorList = new ArrayList<>();
            vendedorList.add(notaVenta[3].toString());
            spVendedor.setAdapter(new ArrayAdapter<>(PVerNotaVenta.this, android.R.layout.simple_spinner_dropdown_item, vendedorList));

            for (Object[] detalle : (List<Object[]>) notaVenta[4]) {
                TableRow row = new TableRow(PVerNotaVenta.this);
                TextView tvNombreProducto = new TextView(PVerNotaVenta.this);
                TextView tvCantidad = new TextView(PVerNotaVenta.this);
                TextView tvPrecio = new TextView(PVerNotaVenta.this);
                TextView tvTotalProd = new TextView(PVerNotaVenta.this);

                int margin = 45;
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                layoutParams.setMargins(margin, margin, margin, margin);
                tvNombreProducto.setText(detalle[0].toString());
                tvCantidad.setText(detalle[1].toString());
                tvPrecio.setText(detalle[2].toString());
                tvTotalProd.setText(detalle[3].toString());
                // add to row
                row.addView(tvNombreProducto, layoutParams);
                row.addView(tvCantidad, layoutParams);
                row.addView(tvPrecio, layoutParams);
                row.addView(tvTotalProd, layoutParams);

                tableProductos.addView(row);
            }

            tvTotal.setText(notaVenta[5].toString());
        }
    }
}