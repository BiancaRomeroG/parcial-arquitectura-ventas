package com.example.ventasparcialarquitectura.presentacion.vendedor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NVendedor;
import com.example.ventasparcialarquitectura.presentacion.producto.PEditarProducto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PEditarVendedor extends AppCompatActivity {

    int vendedorId;
    NVendedor nVendedor = new NVendedor();
    int referenciadorId = -1;

    EditText etNombreVendedor, etEmailVendedor, etCiVendedor, etTelefonoVendedor, etPasswordVendedor;
    Spinner spReferenciador;
    Button btnActualizarVendedor;

    private Map<Integer, String> referenciadorMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_vendedor);

        etNombreVendedor = findViewById(R.id.etNombreVendedor);
        etEmailVendedor = findViewById(R.id.etEmailVendedor);
        etCiVendedor = findViewById(R.id.etCiVendedor);
        etTelefonoVendedor = findViewById(R.id.etTelefonoVendedor);
        etPasswordVendedor = findViewById(R.id.etPasswordVendedor);
        spReferenciador = findViewById(R.id.spReferenciadorId);
        btnActualizarVendedor = findViewById(R.id.btnEditarVendedor);

        vendedorId = getIntent().getIntExtra("vendedorId", -1);

        obtenerReferenciadores();
        obtenerVendedor();

        btnActualizarVendedor.setOnClickListener(view -> {
            actualizarVendedor();
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

    private void obtenerVendedor() {
        new GetVendedorTask().execute(vendedorId);
    }

    private class GetVendedorTask extends AsyncTask<Integer, Void, Object[]> {

        @Override
        protected Object[] doInBackground(Integer... integers) {
            return nVendedor.getPorId(integers[0]);
        }

        @Override
        protected void onPostExecute(Object[] result) {
            System.out.println((String) result[3]);
            etNombreVendedor.setText((String) result[3]);
            etEmailVendedor.setText((String) result[4]);
            etPasswordVendedor.setText((String) result[5]);
            etCiVendedor.setText((String) result[6]);
            etTelefonoVendedor.setText((String) result[7]);

            spReferenciador.setSelection(new ArrayList<>(referenciadorMap.keySet()).indexOf((int) result[1]));
            referenciadorId = (int) result[1];
        }
    }

    private void obtenerReferenciadores() {
        new GetListaReferenciadores().execute();
    }

    private class GetListaReferenciadores extends AsyncTask<Void, Void, List<Object[]>> {
        @Override
        protected List<Object[]> doInBackground(Void... voids) {
            return nVendedor.getListaReferenciadores(vendedorId);
        }

        @Override
        protected void onPostExecute(List<Object[]> vendedores) {
            for (Object[] vendedor : vendedores) {
                referenciadorMap.put((int) vendedor[0], (String) vendedor[3]);
            }

            List<String> vendedoresList = new ArrayList<>(referenciadorMap.values());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(PEditarVendedor.this, android.R.layout.simple_spinner_item, vendedoresList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spReferenciador.setAdapter(adapter);
        }
    }

    private void actualizarVendedor() {
        String nombre = etNombreVendedor.getText().toString();
        String email = etEmailVendedor.getText().toString();
        String ci = etCiVendedor.getText().toString();
        String telefono = etTelefonoVendedor.getText().toString();
        String password = etPasswordVendedor.getText().toString();
        String nombreReferenciador = spReferenciador.getSelectedItem().toString();
        int referenciadorId = new ArrayList<>(referenciadorMap.keySet())
                .get(new ArrayList<>(referenciadorMap.values()).indexOf(nombreReferenciador));

        Object[] vendedor = new Object[]{vendedorId, referenciadorId, nombre, email, password, ci, telefono};
        new ActualizarVendedorTask().execute(vendedor);
        finish();
    }

    private class ActualizarVendedorTask extends AsyncTask<Object[], Void, Void> {
        @Override
        protected Void doInBackground(Object[]... objects) {
            nVendedor.actualizar((int) objects[0][0], (int) objects[0][1], (String) objects[0][2], (String) objects[0][3], (String) objects[0][4], (String) objects[0][5], (String) objects[0][6]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }


}
