package com.example.ventasparcialarquitectura.presentacion.nivel;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.datos.DNivel;
import com.example.ventasparcialarquitectura.negocio.NNivel;

public class PEditarNivel extends AppCompatActivity {
    int nivelId;
    NNivel nNivel = new NNivel();

    EditText etNivelNombre, etPorcentajeComision;
    Button btnActualizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_nivel);

        etNivelNombre = findViewById(R.id.etNombreNivel);
        etPorcentajeComision = findViewById(R.id.etPorcentajeComision);
        btnActualizar = findViewById(R.id.btnActualizar);

        nivelId = getIntent().getIntExtra("nivelId", -1);
        obtenerNivel();

        btnActualizar.setOnClickListener(view -> {
            actualizarNivel();
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

    private void obtenerNivel() {
        new GetNivelTask().execute(nivelId);
    }

    private void actualizarNivel() {
        String nombre = etNivelNombre.getText().toString().trim();
        double porcentaje_comision = Double.parseDouble(etPorcentajeComision.getText().toString().trim());

        if (!nombre.isEmpty()) {
            Object[] nuevoNivel = new Object[]{nivelId, nombre, porcentaje_comision};
            new ActualizarNivelTask().execute(nuevoNivel);
        }
    }

    private class GetNivelTask extends android.os.AsyncTask<Integer, Void, Object[]> {
        @Override
        protected Object[] doInBackground(Integer... integers) {
            return nNivel.getPorId(integers[0]);
        }

        @Override
        protected void onPostExecute(Object[] result) {
            etNivelNombre.setText((String) result[1]);
            etPorcentajeComision.setText(String.valueOf((double) result[2]));
        }

    }

    private class ActualizarNivelTask extends android.os.AsyncTask<Object[], Void, Void> {
        @Override
        protected Void doInBackground(Object[]... niveles) {
            nNivel.actualizar((int) niveles[0][0], (String) niveles[0][1], (double) niveles[0][2]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
