package com.example.ventasparcialarquitectura.negocio;

import com.example.ventasparcialarquitectura.datos.DCategoria;

import java.util.ArrayList;
import java.util.List;

public class NCategoria {
    private DCategoria dCategoria = new DCategoria();

    public List<Object[]> getLista() {
        List<Object[]> lista = new ArrayList<>();
        for (DCategoria categoria : dCategoria.getLista()) {
            lista.add(new Object[]{categoria.getId(), categoria.getNombre()});
        }
        return lista;
    }

    public Object[] getPorId(int id) {
        DCategoria categoria = dCategoria.getPorId(id);
        return new Object[]{categoria.getId(), categoria.getNombre()};
    }

    public void guardar(String nombre) {
        dCategoria.guardar(new DCategoria(0, nombre));
    }

    public void eliminar(int id) {
        dCategoria.eliminar(id);
    }

    public void actualizar(int id, String nombre) {
        DCategoria categoria = new DCategoria(id, nombre);
        dCategoria.actualizar(categoria);
    }
}
