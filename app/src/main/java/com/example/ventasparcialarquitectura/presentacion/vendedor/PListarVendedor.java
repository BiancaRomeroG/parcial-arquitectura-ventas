package com.example.ventasparcialarquitectura.presentacion.vendedor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NVendedor;

import java.util.List;

public class PListarVendedor extends AppCompatActivity {
    NVendedor nVendedor = new NVendedor();
    ListView listViewVendedores;
    Button btnAddVendedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_vendedor);

        btnAddVendedor = findViewById(R.id.btnAddVendedor);

        btnAddVendedor.setOnClickListener(view -> {
            Intent intent = new Intent(PListarVendedor.this, PCrearVendedor.class);
            startActivity(intent);
        });

        listViewVendedores = findViewById(R.id.listViewVendedor);

        obtenerListaVendedores();

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
        obtenerListaVendedores();
    }

    private void obtenerListaVendedores() {
        new GetListaVendedoresTask().execute();
    }

    private class GetListaVendedoresTask extends AsyncTask<Void, Void, List<Object[]>> {

        @Override
        protected List<Object[]> doInBackground(Void... voids) {
            return nVendedor.getLista();
        }

        @Override
        protected void onPostExecute(List<Object[]> vendedores) {
            VendedorAdapter adapter = new VendedorAdapter(PListarVendedor.this, vendedores, nVendedor);
            listViewVendedores.setAdapter(adapter);
        }
    }
}
