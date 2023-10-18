package com.example.ventasparcialarquitectura.presentacion.nivel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NNivel;

public class PCrearNivel extends AppCompatActivity {

    private EditText etNombreNivel, etPorcentajeComision;
    private Button btnGuardarNivel;
    private NNivel nNivel = new NNivel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nivel);

        etNombreNivel = findViewById(R.id.etNombreNivel);
        etPorcentajeComision = findViewById(R.id.etPorcentajeComision);
        btnGuardarNivel = findViewById(R.id.btnGuardarNivel);

        btnGuardarNivel.setOnClickListener(view -> {
            guardarNivel();
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

    private void guardarNivel() {
        String nombre = etNombreNivel.getText().toString().trim();
        double porcentaje_comision = Double.parseDouble(etPorcentajeComision.getText().toString().trim());

        if(!nombre.isEmpty() && porcentaje_comision > 0) {
            new GuardarNivelTask().execute(new Object[]{nombre, porcentaje_comision});
        }
    }

    private class GuardarNivelTask extends AsyncTask<Object[], Void, Void> {
        @Override
        protected Void doInBackground(Object[]... niveles) {
            nNivel.guardar((String) niveles[0][0], (double) niveles[0][1]);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }

}
