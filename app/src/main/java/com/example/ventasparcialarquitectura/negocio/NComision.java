package com.example.ventasparcialarquitectura.negocio;

import com.example.ventasparcialarquitectura.datos.DComision;
import com.example.ventasparcialarquitectura.datos.DNivel;
import com.example.ventasparcialarquitectura.datos.DNotaVenta;
import com.example.ventasparcialarquitectura.datos.DVendedor;

import java.util.ArrayList;
import java.util.List;

public class NComision {
    private DComision dComision = new DComision();
    private DVendedor dVendedor = new DVendedor();
    private DNotaVenta dNotaVenta = new DNotaVenta();
    private DNivel dNivel = new DNivel();

    void registrarComision(int usuarioId, int notaVentaId) {
        DNotaVenta notaVenta = dNotaVenta.getPorId(notaVentaId);
        int referenciadoId = usuarioId;
        for (int i = 1; i <= 3; i++) {
            DVendedor vendedor = dVendedor.getPorId(referenciadoId);
            DNivel nivel = dNivel.getPorId(i);
            dComision.guardar(new DComision(0, referenciadoId, notaVentaId, i, nivel.getPorcentaje_comision() * notaVenta.getTotal()));
            referenciadoId = vendedor.getReferenciadorId();
        }
    }

    public List<Object[]> getLista() {
        List<Object[]> lista = new ArrayList<>();
        for (DComision comision : dComision.getLista()) {
            DVendedor vendedor = dVendedor.getPorId(comision.getUsuario_id());

            lista.add(new Object[]{comision.getId(), vendedor.getNombre(),
                    comision.getNota_venta_id(), comision.getNivel_id(),
                    comision.getMonto()});
        }
        return lista;
    }
}
