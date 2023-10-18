package com.example.ventasparcialarquitectura.presentacion.nivel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NNivel;

import java.util.List;


public class PListarNivel extends AppCompatActivity {
    NNivel nNivel = new NNivel();
    ListView listViewNiveles;
    Button btnAddNivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_nivel);

        btnAddNivel = findViewById(R.id.btnAddNivel);

        btnAddNivel.setOnClickListener(view -> {
            Intent intent = new Intent(PListarNivel.this, PCrearNivel.class);
            startActivity(intent);
        });

        listViewNiveles = findViewById(R.id.listViewNiveles);

        obtenerListaNiveles();

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

    @Override
    protected void onResume() {
        super.onResume();
        obtenerListaNiveles();
    }

    private void obtenerListaNiveles() {
        new GetListaNivelesTask().execute();
    }

    private class GetListaNivelesTask extends AsyncTask<Void, Void, List<Object[]>> {

        @Override
        protected List<Object[]> doInBackground(Void... voids) {
            return nNivel.getLista();
        }

        @Override
        protected void onPostExecute(List<Object[]> listaNiveles) {
            NivelAdapter adapter = new NivelAdapter(PListarNivel.this, listaNiveles, nNivel);
            listViewNiveles.setAdapter(adapter);
        }
    }
}
