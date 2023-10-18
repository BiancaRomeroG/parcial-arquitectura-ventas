package com.example.ventasparcialarquitectura.datos;

import java.sql.Connection;
import java.sql.DriverManager;

public class Db {
    private Connection conexion;
    private final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://10.0.2.2:3306/ventas";
    private static final String user = "android";
    private static final String pass = "android";

    public Connection CreateConnection() throws Exception {
        if (conexion != null && !conexion.isClosed()) {
            return conexion;
        }

        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return conexion;
    }



}
