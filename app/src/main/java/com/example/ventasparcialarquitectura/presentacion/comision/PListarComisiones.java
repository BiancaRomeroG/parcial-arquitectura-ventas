package com.example.ventasparcialarquitectura.presentacion.comision;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NCategoria;
import com.example.ventasparcialarquitectura.negocio.NComision;
import com.example.ventasparcialarquitectura.presentacion.categoria.CategoriaAdapter;
import com.example.ventasparcialarquitectura.presentacion.categoria.PListarCategorias;

import java.util.List;

public class PListarComisiones extends AppCompatActivity {

    NComision nComision = new NComision();
    ListView listViewCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_comisiones);

        listViewCategorias = findViewById(R.id.listViewComisiones);

        obtenerListaComisiones();

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
        obtenerListaComisiones();
    }

    private void obtenerListaComisiones() {
        new GetListaComisionesTask().execute();
    }

    private class GetListaComisionesTask extends AsyncTask<Void, Void, List<Object[]>> {
        @Override
        protected List<Object[]> doInBackground(Void... voids) {
            return nComision.getLista();
        }

        @Override
        protected void onPostExecute(List<Object[]> comisiones) {
            ComisionAdapter adapter = new ComisionAdapter(PListarComisiones.this, comisiones);
            listViewCategorias.setAdapter(adapter);
        }
    }
}