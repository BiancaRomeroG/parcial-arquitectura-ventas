package com.example.ventasparcialarquitectura.presentacion.rol;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.datos.DRol;
import com.example.ventasparcialarquitectura.negocio.NRol;

public class PEditarRol extends AppCompatActivity {
    EditText etRolNombre;
    Button btnActualizar;
    int rolId;
    NRol nRol = new NRol();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_rol);

        etRolNombre = findViewById(R.id.etRolNombre);
        btnActualizar = findViewById(R.id.btnActualizar);

        rolId = getIntent().getIntExtra("rolId", -1);

        nRol.setOnRolObtenidoListener(new NRol.OnRolObtenidoListener() {
            @Override
            public void onRolObtenido(DRol rol) {
                etRolNombre.setText(rol.getNombre());
            }
        });

        if(rolId != -1) {
            nRol.obtenerRolPorId(rolId);
        } else {
            finish();
            return;
        }

        btnActualizar.setOnClickListener(view -> {
            DRol rol = new DRol();
            rol.setId(rolId);
            rol.setNombre(etRolNombre.getText().toString());

            nRol.setOnRolGuardadoListener(new NRol.OnRolGuardadoListener() {
                @Override
                public void onRolGuardado() {
                    finish();
                }
            });

            nRol.actualizar(rol);
        });
    }
}
