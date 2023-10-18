package com.example.ventasparcialarquitectura.presentacion.vendedor;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NVendedor;
import com.example.ventasparcialarquitectura.presentacion.producto.PCrearProducto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.widget.Spinner;

public class PCrearVendedor extends AppCompatActivity {

    private EditText etNombreVendedor, etEmailVendedor, etCiVendedor, etTelefonoVendedor, etPasswordVendedor;
    private Spinner spinnerReferenciadorId;
    private Button btnGuardarVendedor;
    private NVendedor nVendedor = new NVendedor();

    int referenciadorId = -1;
    private Map<Integer, String> referenciadorMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_vendedor);

        etNombreVendedor = findViewById(R.id.etNombreVendedor);
        etEmailVendedor = findViewById(R.id.etEmailVendedor);
        etCiVendedor = findViewById(R.id.etCiVendedor);
        etTelefonoVendedor = findViewById(R.id.etTelefonoVendedor);
        etPasswordVendedor = findViewById(R.id.etPasswordVendedor);
        spinnerReferenciadorId = findViewById(R.id.acReferenciadorId);
        btnGuardarVendedor = findViewById(R.id.btnGuardarVendedor);

        obtenerReferenciadores();

        btnGuardarVendedor.setOnClickListener(view -> {
            guardarVendedor();
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

    private void obtenerReferenciadores() {
        new GetListaReferenciadoresTask().execute();
    }

    private class GetListaReferenciadoresTask extends AsyncTask<Void, Void, List<Object[]>> {
        @Override
        protected List<Object[]> doInBackground(Void... voids) {
            return nVendedor.getLista();
        }

        @Override
        protected void onPostExecute(List<Object[]> referenciadores) {
            for (Object[] referenciador : referenciadores) {
                referenciadorMap.put((int) referenciador[0], (String) referenciador[3]);
            }
            List<String> referenciadorList = new ArrayList<>(referenciadorMap.values());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(PCrearVendedor.this, android.R.layout.simple_spinner_item, referenciadorList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerReferenciadorId.setAdapter(adapter);
        }
    }

    private void guardarVendedor() {
        String nombreVendedor = etNombreVendedor.getText().toString();
        String emailVendedor = etEmailVendedor.getText().toString();
        String ciVendedor = etCiVendedor.getText().toString();
        String telefonoVendedor = etTelefonoVendedor.getText().toString();
        String passwordVendedor = etPasswordVendedor.getText().toString();
        String nombreReferenciador = spinnerReferenciadorId.getSelectedItem().toString();

        for (Map.Entry<Integer, String> entry : referenciadorMap.entrySet()) {
            if (entry.getValue().equals(nombreReferenciador)) {
                referenciadorId = entry.getKey();
            }
        }

        Object[] vendedor = new Object[]{referenciadorId, nombreVendedor, emailVendedor, passwordVendedor, ciVendedor, telefonoVendedor};
        new GuardarVendedorTask().execute(vendedor);
        finish();
    }

    private class GuardarVendedorTask extends AsyncTask<Object[], Void, Void> {
        @Override
        protected Void doInBackground(Object[]... objects) {
            nVendedor.guardar((int) objects[0][0], (String) objects[0][1], (String) objects[0][2], (String) objects[0][3], (String) objects[0][4], (String) objects[0][5]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
