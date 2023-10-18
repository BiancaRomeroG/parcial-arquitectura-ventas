package com.example.ventasparcialarquitectura.presentacion;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.example.ventasparcialarquitectura.R;
import com.example.ventasparcialarquitectura.presentacion.categoria.PListarCategorias;
import com.example.ventasparcialarquitectura.presentacion.comision.PListarComisiones;
import com.example.ventasparcialarquitectura.presentacion.nivel.PListarNivel; // Asegúrate de importar esta clase
//import com.example.ventasparcialarquitectura.presentacion.notaVenta.PListarNotaVenta;
import com.example.ventasparcialarquitectura.presentacion.notaVenta.PListarNotaVenta;
import com.example.ventasparcialarquitectura.presentacion.producto.PListarProductos;
import com.example.ventasparcialarquitectura.presentacion.rol.PListarRoles;
import com.example.ventasparcialarquitectura.presentacion.vendedor.PListarVendedor;

public class NavigationMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_menu);

        Button btnCategoria = findViewById(R.id.btnCategoria);
        btnCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationMenuActivity.this, PListarCategorias.class);
                startActivity(intent);
            }
        });

        Button btnNiveles = findViewById(R.id.btnNiveles);
        btnNiveles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationMenuActivity.this, PListarNivel.class);
                startActivity(intent);
            }
        });

        Button btnProductos = findViewById(R.id.btnProductos);
        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationMenuActivity.this, PListarProductos.class);
                startActivity(intent);
            }
        });

        Button btnRoles = findViewById(R.id.btnRol);
        btnRoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationMenuActivity.this, PListarRoles.class);
                startActivity(intent);
            }
        });

        Button btnVendedores = findViewById(R.id.btnVendedores);
        btnVendedores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationMenuActivity.this, PListarVendedor.class);
                startActivity(intent);
            }
        });

        Button btnNotaVenta = findViewById(R.id.btnNotaVenta);
        btnNotaVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationMenuActivity.this, PListarNotaVenta.class);
                startActivity(intent);
            }
        });

        Button btnComision = findViewById(R.id.btnComisiones);
        btnComision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationMenuActivity.this, PListarComisiones.class);
                startActivity(intent);
            }
        });

    }
}