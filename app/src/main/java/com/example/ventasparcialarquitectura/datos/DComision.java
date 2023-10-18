package com.example.ventasparcialarquitectura.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DComision {

    private int id;
    private int usuario_id;
    private int nota_venta_id;
    private int nivel_id;
    private double monto;

    private final String nombreTabla = "comision";
    private Db dbConnection = new Db();

    public DComision() {
    }

    public DComision(int id, int usuario_id, int nota_venta_id, int nivel_id, double monto) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.nota_venta_id = nota_venta_id;
        this.nivel_id = nivel_id;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getNota_venta_id() {
        return nota_venta_id;
    }

    public void setNota_venta_id(int nota_venta_id) {
        this.nota_venta_id = nota_venta_id;
    }

    public int getNivel_id() {
        return nivel_id;
    }

    public void setNivel_id(int nivel_id) {
        this.nivel_id = nivel_id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }


    public List<DComision> getLista() {
        List<DComision> comisiones = new ArrayList<>();
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nombreTabla);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                comisiones.add(new DComision(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("nota_venta_id"),
                        rs.getInt("nivel_id"),
                        rs.getDouble("monto")
                ));
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return comisiones;
    }

    public void guardar(DComision comision) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbConnection.CreateConnection();
            if (conn == null) {
                throw new Exception("La conexión a la base de datos es nula.");
            }

            ps = conn.prepareStatement("INSERT INTO " + nombreTabla + " (usuario_id, nota_venta_id, nivel_id, monto) VALUES (?, ?, ?, ?)");
            ps.setInt(1, comision.getUsuario_id());
            ps.setInt(2, comision.getNota_venta_id());
            ps.setInt(3, comision.getNivel_id());
            ps.setDouble(4, comision.getMonto());
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

    public DComision getPorId(int id) {
        DComision comision = null;
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nombreTabla + " WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                comision = new DComision(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("nota_venta_id"),
                        rs.getInt("nivel_id"),
                        rs.getDouble("monto")
                );
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comision;
    }
}
