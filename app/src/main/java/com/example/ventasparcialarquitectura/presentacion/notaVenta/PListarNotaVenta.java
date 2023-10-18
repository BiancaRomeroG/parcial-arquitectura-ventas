package com.example.ventasparcialarquitectura.presentacion.notaVenta;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.MenuItem;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NNotaVenta;

import java.util.List;

public class PListarNotaVenta extends AppCompatActivity {
    NNotaVenta nNotaVenta = new NNotaVenta();
    ListView listViewNotaVenta;
    Button btnAddNotaVenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_nota_venta);

        btnAddNotaVenta = findViewById(R.id.btnAddNotaVenta);

        btnAddNotaVenta.setOnClickListener(view -> {
            Intent intent = new Intent(PListarNotaVenta.this, PCrearNotaVenta.class);
            startActivity(intent);
        });

        listViewNotaVenta = findViewById(R.id.listViewNotaVenta);

        obtenerListaNotaVenta();

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
        obtenerListaNotaVenta();
    }

    private void obtenerListaNotaVenta() {
        new GetListaNotaVentaTask().execute();
    }

    private class GetListaNotaVentaTask extends AsyncTask<Void, Void, List<Object[]>> {
        @Override
        protected List<Object[]> doInBackground(Void... voids) {
            return nNotaVenta.getLista();
        }

        @Override
        protected void onPostExecute(List<Object[]> notaVentas) {
            NotaVentaAdapter adapter = new NotaVentaAdapter(PListarNotaVenta.this, notaVentas, nNotaVenta);
            listViewNotaVenta.setAdapter(adapter);
        }
    }

}