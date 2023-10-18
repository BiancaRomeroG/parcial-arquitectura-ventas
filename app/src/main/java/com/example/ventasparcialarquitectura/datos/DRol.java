package com.example.ventasparcialarquitectura.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DRol {
    private int id;
    private String nombre;

    private final String nombreTabla = "rol";
    private Db dbConnection = new Db();

    public DRol() {
    }

    public DRol(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public List<DRol> getLista() {
        List<DRol> roles = new ArrayList<>();
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nombreTabla);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                roles.add(new DRol(rs.getInt("id"), rs.getString("nombre")));
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return roles;
    }

    public void guardar(DRol rol) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbConnection.CreateConnection();

            if (conn == null) {
                throw new Exception("La conexión a la base de datos es nula.");
            }

            ps = conn.prepareStatement("INSERT INTO " + nombreTabla + " (nombre) VALUES (?)");
            ps.setString(1, rol.getNombre());
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

    public void actualizar(DRol rol) {
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE " + nombreTabla + " SET nombre = ? WHERE id = ?");
            ps.setString(1, rol.getNombre());
            ps.setInt(2, rol.getId());
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

    public DRol getPorId(int id) {
        DRol rol = null;
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nombreTabla + " WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rol = new DRol(rs.getInt("id"), rs.getString("nombre"));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rol;
    }
}
