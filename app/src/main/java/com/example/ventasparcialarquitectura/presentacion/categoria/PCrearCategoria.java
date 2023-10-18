package com.example.ventasparcialarquitectura.presentacion.categoria;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NCategoria;

public class PCrearCategoria extends AppCompatActivity {

    private EditText etNombreCategoria;
    private Button btnGuardarCategoria;
    private NCategoria nCategoria = new NCategoria();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_categoria);

        etNombreCategoria = findViewById(R.id.etNombreCategoria);
        btnGuardarCategoria = findViewById(R.id.btnGuardarCategoria);

        btnGuardarCategoria.setOnClickListener(view -> {
            guardarCategoria();
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

    private void guardarCategoria() {
        String nombre = etNombreCategoria.getText().toString().trim();

        if(!nombre.isEmpty()) {
            new GuardarCategoriaTask().execute(new Object[]{nombre});
        }
    }

    private class GuardarCategoriaTask extends AsyncTask<Object[], Void, Void> {
        @Override
        protected Void doInBackground(Object[]... categorias) {
            nCategoria.guardar((String) categorias[0][0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
