package com.example.ventasparcialarquitectura.presentacion.rol;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.negocio.NRol;

import java.util.List;

public class PListarRoles extends AppCompatActivity {
    NRol nRol = new NRol();
    ListView listViewRoles;
    Button btnAddRol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_rol);

        btnAddRol = findViewById(R.id.btnAddRol);

        btnAddRol.setOnClickListener(view -> {
            Intent intent = new Intent(PListarRoles.this, PCrearRol.class);
            startActivity(intent);
        });

        listViewRoles = findViewById(R.id.listViewRoles);

        nRol.setOnRolGuardadoListener(() -> {
            cargarListaRoles();
        });

        cargarListaRoles();

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
        cargarListaRoles();
    }

    private void cargarListaRoles() {
        new GetLista().execute();
    }

    private class GetLista extends AsyncTask<Void, Void, List<Object[]>> {
        @Override
        protected List<Object[]> doInBackground(Void... voids) {
            return nRol.getLista();
        }

        @Override
        protected void onPostExecute(List<Object[]> roles) {
            RolAdapter adapter = new RolAdapter(PListarRoles.this, roles, nRol);

            adapter.setOnRolEliminadoListener(() -> {
                cargarListaRoles();
            });

            listViewRoles.setAdapter(adapter);
        }
    }
}
