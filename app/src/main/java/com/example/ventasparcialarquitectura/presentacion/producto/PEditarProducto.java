package com.example.ventasparcialarquitectura.presentacion.producto;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NCategoria;
import com.example.ventasparcialarquitectura.negocio.NProducto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PEditarProducto extends AppCompatActivity {
    int productoId;
    NProducto nProducto = new NProducto();
    NCategoria nCategoria = new NCategoria();
    int categoriaId = -1;

    EditText etCodigo, etNombreProducto, etDescripcion, etPrecio;
    Spinner spCategoria;
    Button btnActualizarProducto;

    private Map<Integer, String> categoriaMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        etCodigo = findViewById(R.id.etCodigo);
        etNombreProducto = findViewById(R.id.etNombreProducto);
        etDescripcion = findViewById(R.id.etDescripcion);
        etPrecio = findViewById(R.id.etPrecio);
        spCategoria = findViewById(R.id.spCategoria);
        btnActualizarProducto = findViewById(R.id.btnEditarProducto);

        productoId = getIntent().getIntExtra("productoId", -1);

        obtenerCategorias();
        obtenerProducto();

        btnActualizarProducto.setOnClickListener(view -> {
            actualizarProducto();
            finish();
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }



    private void obtenerProducto() {
        new GetProductoTask().execute(productoId);
    }

    private class GetProductoTask extends AsyncTask<Integer, Void, Object[]> {
        @Override
        protected Object[] doInBackground(Integer... ids) {
            return nProducto.getPorId(ids[0]);
        }

        @Override
        protected void onPostExecute(Object[] result) {
            etCodigo.setText((String) result[2]);
            etNombreProducto.setText((String) result[3]);
            etDescripcion.setText((String) result[4]);
            etPrecio.setText(String.valueOf(result[5]));
            spCategoria.setSelection(new ArrayList<>(categoriaMap.keySet()).indexOf(result[1]));
            categoriaId = (int) result[1];
        }
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
            ArrayAdapter<String> adapter = new ArrayAdapter<>(PEditarProducto.this, android.R.layout.simple_spinner_item, categoriasList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCategoria.setAdapter(adapter);
        }
    }

    private void actualizarProducto() {
        String codigo = etCodigo.getText().toString().trim();
        String nombre = etNombreProducto.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        String precio = etPrecio.getText().toString().trim();
        String nombreCategoria = spCategoria.getSelectedItem().toString();
        int categoriaId = new ArrayList<>(categoriaMap.keySet())
                .get(new ArrayList<>(categoriaMap.values()).indexOf(nombreCategoria));

        if (!codigo.isEmpty() && !nombre.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty() && categoriaId != -1) {
            Object[] producto = new Object[] {productoId, categoriaId, codigo, nombre, descripcion, Double.parseDouble(precio) };
            new ActualizarProductoTask().execute(producto);
            finish();
        }
    }

    private class ActualizarProductoTask extends AsyncTask<Object[], Void, Void> {
        @Override
        protected Void doInBackground(Object[]... objects) {
            nProducto.actualizar((int) objects[0][0], (int) objects[0][1],
                    (String) objects[0][2], (String) objects[0][3],
                    (String) objects[0][4], (double) objects[0][5]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
