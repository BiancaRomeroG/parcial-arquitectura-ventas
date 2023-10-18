package com.example.ventasparcialarquitectura.presentacion.categoria;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NCategoria;

public class PEditarCategoria extends AppCompatActivity {
    int categoriaId;
    NCategoria nCategoria = new NCategoria();

    EditText etCategoriaNombre;
    Button btnActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_categoria);

        etCategoriaNombre = findViewById(R.id.etCategoriaNombre);
        btnActualizar = findViewById(R.id.btnActualizar);

        categoriaId = getIntent().getIntExtra("categoriaId", -1);
        obtenerCategoria();

        btnActualizar.setOnClickListener(view -> {
            actualizarCategoria();
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

    private void obtenerCategoria() {
        new GetCategoriaTask().execute(categoriaId);
    }

    private void actualizarCategoria() {
        String nombre = etCategoriaNombre.getText().toString().trim();

        if (!nombre.isEmpty()) {
            Object[] nuevaCategoria = new Object[]{categoriaId, nombre};
            new ActualizarCategoriaTask().execute(nuevaCategoria);
        }
    }

    private class GetCategoriaTask extends AsyncTask<Integer, Void, Object[]> {
        @Override
        protected Object[] doInBackground(Integer... ids) {
            return nCategoria.getPorId(ids[0]);
        }

        @Override
        protected void onPostExecute(Object[] result) {
            etCategoriaNombre.setText((String) result[1]);
        }
    }

    private class ActualizarCategoriaTask extends AsyncTask<Object[], Void, Void> {
        @Override
        protected Void doInBackground(Object[]... objects) {
            nCategoria.actualizar((int) objects[0][0], (String) objects[0][1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
