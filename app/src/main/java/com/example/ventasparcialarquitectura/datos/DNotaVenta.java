package com.example.ventasparcialarquitectura.datos;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DNotaVenta {
    private int id;
    private int usuarioId;
    private String cliente;
    private String nit;
    private String fecha;
    private double total;

    private final String nombreTabla = "nota_venta";
    private Db dbConnection = new Db();

    public DNotaVenta() {
    }

    public DNotaVenta(int id, int usuario_id, String cliente, String nit, String fecha, double total) {
        this.id = id;
        this.usuarioId = usuario_id;
        this.cliente = cliente;
        this.nit = nit;
        this.fecha = fecha;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DNotaVenta> getLista() {
        List<DNotaVenta> notasVenta = new ArrayList<>();
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nombreTabla);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                notasVenta.add(new DNotaVenta(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("cliente"),
                        rs.getString("nit"),
                        rs.getString("fecha"),
                        rs.getDouble("total")
                ));
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return notasVenta;
    }

    public void guardar(DNotaVenta notaVenta) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = dbConnection.CreateConnection();
            if (conn == null) {
                throw new Exception("La conexión a la base de datos es nula.");
            }

            String query = "INSERT INTO " + nombreTabla + " (usuario_id, cliente, nit, fecha, total) VALUES (?, ?, ?, STR_TO_DATE(?, '%d/%m/%Y'), ?)";
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, notaVenta.getUsuarioId());
            ps.setString(2, notaVenta.getCliente());
            ps.setString(3, notaVenta.getNit());
            ps.setString(4, notaVenta.getFecha());
            ps.setDouble(5, notaVenta.getTotal());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys(); // Aquí obtenemos las claves generadas
            if (rs.next()) {
                notaVenta.setId(rs.getInt(1)); // Aquí asignamos el ID autoincrementado a la nota de venta
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
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


    public  DNotaVenta getPorId(int id) {
        DNotaVenta notaVenta = null;
        try {
            Connection conn = dbConnection.CreateConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + nombreTabla + " WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                notaVenta = new DNotaVenta(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("cliente"),
                        rs.getString("nit"),
                        rs.getString("fecha"),
                        rs.getDouble("total")
                );
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notaVenta;
    }
}
