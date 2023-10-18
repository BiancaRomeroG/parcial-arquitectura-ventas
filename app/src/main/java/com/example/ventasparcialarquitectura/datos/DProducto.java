package com.example.ventasparcialarquitectura.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DProducto {
    private int id;
    private int categoriaId;
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;

    private final String nombreTabla = "producto";
    private Db dbConnection = new Db();

    public DProducto() {
    }

    public DProducto(int id, int categoria_id, String codigo, String nombre, String descripcion, double precio) {
        this.id = id;
        this.categoriaId = categoria_id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }


    public List<DProducto> getLista() {
        List<DProducto> productos = new ArrayList<>();
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nombreTabla);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                productos.add(new DProducto(
                        rs.getInt("id"),
                        rs.getInt("categoria_id"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio")
                ));
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productos;
    }

    public void guardar(DProducto producto) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dbConnection.CreateConnection();
            if (conn == null) {
                throw new Exception("La conexión a la base de datos es nula.");
            }

            ps = conn.prepareStatement("INSERT INTO " + nombreTabla + " (categoria_id, codigo, nombre, descripcion, precio) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, producto.categoriaId);
            ps.setString(2, producto.codigo);
            ps.setString(3, producto.nombre);
            ps.setString(4, producto.descripcion);
            ps.setDouble(5, producto.precio);
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

    public void actualizar(DProducto producto) {
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE " + nombreTabla + " SET categoria_id = ?, codigo = ?, nombre = ?, descripcion = ?, precio = ? WHERE id = ?");
            ps.setInt(1, producto.categoriaId);
            ps.setString(2, producto.codigo);
            ps.setString(3, producto.nombre);
            ps.setString(4, producto.descripcion);
            ps.setDouble(5, producto.precio);
            ps.setInt(6, producto.id);
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

    public DProducto getPorId(int id) {
        DProducto producto = null;
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nombreTabla + " WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                producto = new DProducto(
                        rs.getInt("id"),
                        rs.getInt("categoria_id"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio")
                );
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return producto;
    }
}
