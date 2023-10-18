package com.example.ventasparcialarquitectura.negocio;

import android.os.AsyncTask;

import com.example.ventasparcialarquitectura.datos.DCategoria;
import com.example.ventasparcialarquitectura.datos.DComision;
import com.example.ventasparcialarquitectura.datos.DNotaVenta;
import com.example.ventasparcialarquitectura.datos.DProducto;
import com.example.ventasparcialarquitectura.datos.DVendedor;
import com.example.ventasparcialarquitectura.datos.DVentaDetalle;

import java.util.ArrayList;
import java.util.List;

public class NNotaVenta {

    private DNotaVenta dNotaVenta = new DNotaVenta();
    private DVentaDetalle dVentaDetalle = new DVentaDetalle();
    private DProducto dProducto = new DProducto();
    private DVendedor dVendedor = new DVendedor();
    private NComision nComision = new NComision();

    public List<Object[]> getLista() {
        List<Object[]> lista = new ArrayList<>();
        for (DNotaVenta notaVenta : dNotaVenta.getLista()) {
            lista.add(new Object[]{
                    notaVenta.getId(), notaVenta.getUsuarioId(),
                    notaVenta.getCliente(), notaVenta.getNit(),
                    notaVenta.getFecha(), notaVenta.getTotal()});
        }
        return lista;
    }

    public Object[] getPorId(int id) {
        DNotaVenta notaVenta = dNotaVenta.getPorId(id);
        return new Object[]{
                notaVenta.getId(), notaVenta.getUsuarioId(),
                notaVenta.getCliente(), notaVenta.getNit(),
                notaVenta.getFecha(), notaVenta.getTotal()};
    }

    public void guardar(int usuarioId, String cliente, String nit, String fecha, double total) {
        dNotaVenta.guardar(new DNotaVenta(0, usuarioId, cliente, nit, fecha, total));
    }

    public void eliminar(int id) {
        dNotaVenta.eliminar(id);
    }

    public void registrarVentaConDetalle(int usuarioId, String cliente, String nit, String fecha, double total, List<Object[]> listaDetalle) {
        DNotaVenta nuevaVenta = new DNotaVenta(0, usuarioId, cliente, nit, fecha, total);
        dNotaVenta.guardar(nuevaVenta);
        int notaVentaId = nuevaVenta.getId();
        System.out.println("notaVentaId: " + notaVentaId);
        for (Object[] detalle : listaDetalle) {
            dVentaDetalle.guardar(new DVentaDetalle(0, notaVentaId, (int) detalle[0], (double) detalle[1], (int) detalle[2], (double) detalle[3]));
            total += (double) detalle[3];
        }
        nComision.registrarComision(usuarioId, notaVentaId);
    }

    public Object[] getPorIdConDetalle(int id) {
        DNotaVenta notaVenta = dNotaVenta.getPorId(id);
        List<DVentaDetalle> listaDetalle = dVentaDetalle.getLista();
        List<Object[]> listaDetalleFiltrada = new ArrayList<>();
        DVendedor vendedor = dVendedor.getPorId(notaVenta.getUsuarioId());
        for (DVentaDetalle detalle : listaDetalle) {
            if (detalle.getNota_venta_id() == id) {
                DProducto producto = dProducto.getPorId(detalle.getProducto_id());
                Object[] detalleFiltrado = new Object[]{
                        producto.getNombre(), detalle.getCantidad(),
                        detalle.getPrecio_unitario(), detalle.getTotal()};
                listaDetalleFiltrada.add(detalleFiltrado);
            }
        }

        return new Object[]{
                notaVenta.getCliente(), notaVenta.getNit(),
                notaVenta.getFecha(), vendedor.getNombre(),
                listaDetalleFiltrada, notaVenta.getTotal()};
    }
}
