package com.example.ventasparcialarquitectura.negocio;

import com.example.ventasparcialarquitectura.datos.DCategoria;
import com.example.ventasparcialarquitectura.datos.DProducto;

import java.util.ArrayList;
import java.util.List;

public class NProducto {
    private DProducto dProducto = new DProducto();

    public List<Object[]> getLista() {
        List<Object[]> lista = new ArrayList<>();
        for (DProducto producto : dProducto.getLista()) {
            lista.add(new Object[]{
                    producto.getId(), producto.getCategoriaId(),
                    producto.getCodigo(), producto.getNombre(),
                    producto.getDescripcion(), producto.getPrecio()});
        }
        return lista;
    }

    public Object[] getPorId(int id) {
        DProducto producto = dProducto.getPorId(id);
        return new Object[]{
                producto.getId(), producto.getCategoriaId(),
                producto.getCodigo(), producto.getNombre(),
                producto.getDescripcion(), producto.getPrecio()};
    }

    public void guardar(int categoriaId, String codigo, String nombre, String descripcion, double precio) {
        dProducto.guardar(new DProducto(0, categoriaId, codigo, nombre, descripcion, precio));
    }

    public void eliminar(int id) {
        dProducto.eliminar(id);
    }

    public void actualizar(int id, int categoriaId, String codigo, String nombre, String descripcion, double precio) {
        DProducto producto = new DProducto(id, categoriaId, codigo, nombre, descripcion, precio);
        dProducto.actualizar(producto);
    }
}
