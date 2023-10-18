package com.example.ventasparcialarquitectura.presentacion.categoria;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NCategoria;

import java.util.List;

public class PListarCategorias extends AppCompatActivity {
    NCategoria nCategoria = new NCategoria();
    ListView listViewCategorias;
    Button btnAddCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_categorias);

        btnAddCategoria = findViewById(R.id.btnAddCategoria);

        btnAddCategoria.setOnClickListener(view -> {
            Intent intent = new Intent(PListarCategorias.this, PCrearCategoria.class);
            startActivity(intent);
        });

        listViewCategorias = findViewById(R.id.listViewCategorias);

        obtenerListaCategorias();

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
        obtenerListaCategorias();
    }

    private void obtenerListaCategorias() {
        new GetListaCategoriasTask().execute();
    }

    private class GetListaCategoriasTask extends AsyncTask<Void, Void, List<Object[]>> {
        @Override
        protected List<Object[]> doInBackground(Void... voids) {
            return nCategoria.getLista();
        }

        @Override
        protected void onPostExecute(List<Object[]> categorias) {
            CategoriaAdapter adapter = new CategoriaAdapter(PListarCategorias.this, categorias, nCategoria);
            listViewCategorias.setAdapter(adapter);
        }
    }
}
