package com.example.ventasparcialarquitectura.presentacion.producto;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.datos.DProducto;
import com.example.ventasparcialarquitectura.negocio.NCategoria;
import com.example.ventasparcialarquitectura.negocio.NProducto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PCrearProducto extends AppCompatActivity {

    private EditText etCodigo, etNombreProducto, etDescripcion, etPrecio;
    private Spinner spCategoria;
    private Button btnGuardarProducto;
    private NProducto nProducto = new NProducto();
    private NCategoria nCategoria = new NCategoria();

    int categoriaId = -1;
    private Map<Integer, String> categoriaMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);

        etCodigo = findViewById(R.id.etCodigo);
        etNombreProducto = findViewById(R.id.etNombreProducto);
        etDescripcion = findViewById(R.id.etDescripcion);
        etPrecio = findViewById(R.id.etPrecio);
        spCategoria = findViewById(R.id.spCategoria);
        btnGuardarProducto = findViewById(R.id.btnGuardarProducto);

        obtenerCategorias();

        btnGuardarProducto.setOnClickListener(view -> {
            guardarProducto();
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

    private void obtenerCategorias() {
        new GetListaCategoriasTask().execute();
    }

    private class GetListaCategoriasTask extends AsyncTask<Void, Void, List<Object[]>> {
        @Override
        protected List<Object[]> doInBackground(Void... voids) {
            return nCategoria.getLista();
        }

        @Override
        protected void onPostExecute(List<Object[]> categorias) {
            for (Object[] categoria : categorias) {
                categoriaMap.put((Integer) categoria[0], (String) categoria[1]);
            }

            List<String> categoriasList = new ArrayList<>(categoriaMap.values());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(PCrearProducto.this, android.R.layout.simple_spinner_item, categoriasList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCategoria.setAdapter(adapter);
        }
    }

    private void guardarProducto() {
        String codigo = etCodigo.getText().toString().trim();
        String nombre = etNombreProducto.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        String precio = etPrecio.getText().toString().trim();
        String nombreCategoria = spCategoria.getSelectedItem().toString();

        for (Map.Entry<Integer, String> entry : categoriaMap.entrySet()) {
            if (entry.getValue().equals(nombreCategoria)) {
                categoriaId = entry.getKey();
            }
        }

        if (!codigo.isEmpty() && !nombre.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty() && categoriaId != -1) {
            Object[] producto = new Object[]{categoriaId, codigo, nombre, descripcion, Double.parseDouble(precio)};
            new GuardarProductoTask().execute(producto);
            finish();
        }
    }

    private class GuardarProductoTask extends AsyncTask<Object[], Void, Void> {
        @Override
        protected Void doInBackground(Object[]... objects) {
            nProducto.guardar((int) objects[0][0], (String) objects[0][1],
                    (String) objects[0][2], (String) objects[0][3],
                    (double) objects[0][4]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
