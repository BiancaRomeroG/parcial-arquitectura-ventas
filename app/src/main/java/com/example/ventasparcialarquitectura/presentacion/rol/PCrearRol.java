package com.example.ventasparcialarquitectura.presentacion.rol;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.datos.DRol;
import com.example.ventasparcialarquitectura.negocio.NRol;

public class PCrearRol extends AppCompatActivity {

    private EditText etNombreRol;
    private Button btnGuardarRol;
    private NRol nRol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_rol);

        etNombreRol = findViewById(R.id.etNombreRol);
        btnGuardarRol = findViewById(R.id.btnGuardarRol);
        nRol = new NRol();

        btnGuardarRol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarRol();
            }
        });
    }

    private void guardarRol() {
        String nombre = etNombreRol.getText().toString().trim();
        if (!nombre.isEmpty()) {
            DRol rol = new DRol();
            rol.setNombre(nombre);
            nRol.guardar(rol);
            finish();
        } else {
            etNombreRol.setError("Ingrese el nombre del rol");
        }
    }
}
