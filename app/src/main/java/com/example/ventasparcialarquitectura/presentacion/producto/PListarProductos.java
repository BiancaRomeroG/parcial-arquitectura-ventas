package com.example.ventasparcialarquitectura.presentacion.producto;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NProducto;

import java.util.List;

public class PListarProductos extends AppCompatActivity {
    NProducto nProducto = new NProducto();
    ListView listViewProductos;
    Button btnAddProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_producto);

        btnAddProducto = findViewById(R.id.btnAddProducto);

        btnAddProducto.setOnClickListener(view -> {
            Intent intent = new Intent(PListarProductos.this, PCrearProducto.class);
            startActivity(intent);
        });

        listViewProductos = findViewById(R.id.listViewProductos);

        obtenerListaProductos();

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
        obtenerListaProductos();
    }

    private void obtenerListaProductos() {
        new GetListaProductosTask().execute();
    }

    private class GetListaProductosTask extends AsyncTask<Void, Void, List<Object[]>> {
        @Override
        protected List<Object[]> doInBackground(Void... voids) {
            return nProducto.getLista();
        }

        @Override
        protected void onPostExecute(List<Object[]> productos) {
            ProductoAdapter adapter = new ProductoAdapter(PListarProductos.this, productos, nProducto);
            listViewProductos.setAdapter(adapter);
        }
    }
}
