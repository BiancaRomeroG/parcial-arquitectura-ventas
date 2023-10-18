package com.example.ventasparcialarquitectura.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DNivel {
    private int id;
    private String nombre;
    private double porcentaje_comision;

    private final String nombreTabla = "nivel";
    private Db dbConnection = new Db();

    public DNivel() {
    }

    public DNivel(int id, String nombre, double porcentaje_comision) {
        this.id = id;
        this.nombre = nombre;
        this.porcentaje_comision = porcentaje_comision;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPorcentaje_comision() {
        return porcentaje_comision;
    }

    public void setPorcentaje_comision(double porcentaje_comision) {
        this.porcentaje_comision = porcentaje_comision;
    }

    public List<DNivel> getLista() {
        List<DNivel> niveles = new ArrayList<>();
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nombreTabla);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                niveles.add(new DNivel(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("porcentaje_comision")));
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return niveles;
    }

    public void guardar(DNivel nivel) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbConnection.CreateConnection();
            if (conn == null) {
                throw new Exception("La conexión a la base de datos es nula.");
            }

            ps = conn.prepareStatement("INSERT INTO " + nombreTabla + " (nombre, porcentaje_comision) VALUES (?, ?)");
            ps.setString(1, nivel.getNombre());
            ps.setDouble(2, nivel.getPorcentaje_comision());
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

    public void actualizar(DNivel nivel) {
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE " + nombreTabla + " SET nombre = ?, porcentaje_comision = ? WHERE id = ?");
            ps.setString(1, nivel.getNombre());
            ps.setDouble(2, nivel.getPorcentaje_comision());
            ps.setInt(3, nivel.getId());
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
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

    public DNivel getPorId(int id) {
        DNivel nivel = null;
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nombreTabla + " WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nivel = new DNivel(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("porcentaje_comision"));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nivel;
    }
}
