package com.example.ventasparcialarquitectura.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DVentaDetalle {

    private int id;
    private int nota_venta_id;
    private int producto_id;
    private double precio_unitario;
    private int cantidad;
    private double total;

    private final String nombreTabla = "venta_detalle";
    private Db dbConnection = new Db();

    public DVentaDetalle() {
    }

    public DVentaDetalle(int id, int nota_venta_id, int producto_id, double precio_unitario, int cantidad, double total) {
        this.id = id;
        this.nota_venta_id = nota_venta_id;
        this.producto_id = producto_id;
        this.precio_unitario = precio_unitario;
        this.cantidad = cantidad;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNota_venta_id() {
        return nota_venta_id;
    }

    public void setNota_venta_id(int nota_venta_id) {
        this.nota_venta_id = nota_venta_id;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DVentaDetalle> getLista() {
        List<DVentaDetalle> detalles = new ArrayList<>();
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nombreTabla);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                detalles.add(new DVentaDetalle(
                        rs.getInt("id"),
                        rs.getInt("nota_venta_id"),
                        rs.getInt("producto_id"),
                        rs.getDouble("precio_unitario"),
                        rs.getInt("cantidad"),
                        rs.getDouble("total")
                ));
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return detalles;
    }

    public void guardar(DVentaDetalle detalle) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbConnection.CreateConnection();
            if (conn == null) {
                throw new Exception("La conexión a la base de datos es nula.");
            }

            ps = conn.prepareStatement("INSERT INTO " + nombreTabla + " (nota_venta_id, producto_id, precio_unitario, cantidad, total) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, detalle.getNota_venta_id());
            ps.setInt(2, detalle.getProducto_id());
            ps.setDouble(3, detalle.getPrecio_unitario());
            ps.setInt(4, detalle.getCantidad());
            ps.setDouble(5, detalle.getTotal());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void eliminar(int id) {
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + nombreTabla + " WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DVentaDetalle getPorId(int id) {
        DVentaDetalle detalle = null;
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nombreTabla + " WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                detalle = new DVentaDetalle(
                        rs.getInt("id"),
                        rs.getInt("nota_venta_id"),
                        rs.getInt("producto_id"),
                        rs.getDouble("precio_unitario"),
                        rs.getInt("cantidad"),
                        rs.getDouble("total")
                );
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detalle;
    }
}
