package com.example.ventasparcialarquitectura.negocio;

import com.example.ventasparcialarquitectura.datos.DVendedor;

import java.util.ArrayList;
import java.util.List;

public class NVendedor {

    private DVendedor dVendedor = new DVendedor();

    public List<Object[]> getLista() {
        List<Object[]> lista = new ArrayList<>();
        for (DVendedor vendedor : dVendedor.getLista()) {
            lista.add(new Object[]{vendedor.getId(), vendedor.getReferenciadorId(), vendedor.getRolId(), vendedor.getNombre(), vendedor.getEmail(), vendedor.getCi(), vendedor.getTelefono()});
        }
        return lista;
    }

    public List<Object[]> getListaReferenciadores(int id) {
        List<Object[]> lista = new ArrayList<>();
        for (DVendedor vendedor : dVendedor.getLista()) {
            if (vendedor.getId() != id) {
                lista.add(new Object[]{vendedor.getId(), vendedor.getReferenciadorId(), vendedor.getRolId(), vendedor.getNombre(), vendedor.getEmail(), vendedor.getCi(), vendedor.getTelefono()});
            }
        }
        return lista;
    }

    public Object[] getPorId(int id) {
        DVendedor vendedor = dVendedor.getPorId(id);
        return new Object[]{vendedor.getId(), vendedor.getReferenciadorId(), vendedor.getRolId(), vendedor.getNombre(), vendedor.getEmail(), vendedor.getPassword(), vendedor.getCi(), vendedor.getTelefono()};
    }


    public void guardar(int referenciador_id, String nombre, String email, String password, String ci, String telefono) {
        dVendedor.guardar(new DVendedor(0, referenciador_id, 2, nombre, email, password, ci, telefono));
    }

    public void eliminar(int id) {
        dVendedor.eliminar(id);
    }

    public void actualizar(int id, int referenciador_id, String nombre, String email, String password, String ci, String telefono) {
        DVendedor vendedor = new DVendedor(id, referenciador_id, 2, nombre, email, password, ci, telefono);
        dVendedor.actualizar(vendedor);
    }
}
