package com.example.ventasparcialarquitectura.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DVendedor {
    private int id;
    private int referenciadorId;
    private int rolId;
    private String nombre;
    private String email;
    private String password;
    private String ci;
    private String telefono;

    private final String nombreTabla = "usuario";
    private Db dbConnection = new Db();

    public DVendedor() {
    }

    public DVendedor(int id, int referenciador_id, int rol_id, String nombre, String email, String password, String ci, String telefono) {
        this.id = id;
        this.referenciadorId = referenciador_id;
        this.rolId = rol_id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.ci = ci;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReferenciadorId() {
        return referenciadorId;
    }

    public void setReferenciadorId(int referenciadorId) {
        this.referenciadorId = referenciadorId;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public List<DVendedor> getLista() {
        List<DVendedor> vendedores = new ArrayList<>();
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nombreTabla + " WHERE rol_id = 2");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vendedores.add(new DVendedor(
                        rs.getInt("id"),
                        rs.getInt("referenciador_id"),
                        rs.getInt("rol_id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("ci"),
                        rs.getString("telefono")
                ));
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return vendedores;
    }


    public void guardar(DVendedor vendedor) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbConnection.CreateConnection();
            if (conn == null) {
                throw new Exception("La conexión a la base de datos es nula.");
            }

            ps = conn.prepareStatement("INSERT INTO " + nombreTabla + " (referenciador_id, rol_id, nombre, email, password, ci, telefono) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, vendedor.getReferenciadorId());
            ps.setInt(2, vendedor.getRolId());
            ps.setString(3, vendedor.getNombre());
            ps.setString(4, vendedor.getEmail());
            ps.setString(5, vendedor.getPassword());
            ps.setString(6, vendedor.getCi());
            ps.setString(7, vendedor.getTelefono());
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


    public void actualizar(DVendedor vendedor) {
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE " + nombreTabla + " SET referenciador_id = ?, rol_id =?, nombre = ?, email = ?, password = ?, ci = ?, telefono = ? WHERE id = ?");
            ps.setInt(1, vendedor.getReferenciadorId());
            ps.setInt(2, vendedor.getRolId());
            ps.setString(3, vendedor.getNombre());
            ps.setString(4, vendedor.getEmail());
            ps.setString(5, vendedor.getPassword());
            ps.setString(6, vendedor.getCi());
            ps.setString(7, vendedor.getTelefono());
            ps.setInt(8, vendedor.id);
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

    public DVendedor getPorId(int id) {
        DVendedor vendedor = null;
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nombreTabla + " WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                vendedor = new DVendedor(
                        rs.getInt("id"),
                        rs.getInt("referenciador_id"),
                        rs.getInt("rol_id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("ci"),
                        rs.getString("telefono")
                );
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return vendedor;
    }
}
