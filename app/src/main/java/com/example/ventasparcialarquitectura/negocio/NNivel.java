package com.example.ventasparcialarquitectura.negocio;

import com.example.ventasparcialarquitectura.datos.DNivel;

import java.util.ArrayList;
import java.util.List;

public class NNivel {

    public DNivel dNivel = new DNivel();

    public List<Object[]> getLista() {
        List<Object[]> lista = new ArrayList<>();
        for (DNivel nivel : dNivel.getLista()) {
            lista.add(new Object[]{nivel.getId(), nivel.getNombre(), nivel.getPorcentaje_comision()});
        }
        return lista;
    }

    public Object[] getPorId(int id) {
        DNivel nivel = dNivel.getPorId(id);
        return new Object[]{nivel.getId(), nivel.getNombre(), nivel.getPorcentaje_comision()};
    }

    public void guardar(String nombre, double porcentaje_comision) {
        DNivel nivel = new DNivel(0, nombre, porcentaje_comision);
        dNivel.guardar(nivel);
    }

    public void eliminar(int id) {
        dNivel.eliminar(id);
    }

    public void actualizar(int id, String nombre, double porcentaje_comision) {
        DNivel nivel = new DNivel(id, nombre, porcentaje_comision);
        dNivel.actualizar(nivel);
    }

}
