package com.wordle.dao;

import com.wordle.connection.DatabaseConnection;
import com.wordle.model.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginFormDAOImp implements LoginFormDAO{

    private String tableName; // Nombre de la tabla en la base de datos

    // Constructor que asigna el nombre de la tabla
    public LoginFormDAOImp(){
        tableName = "users";
    }

    @Override
    public void create(Login login) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection(); // Obtiene la conexión
        String insertQuery = "INSERT INTO " + tableName + " (username, password) VALUES (?, ?)"; // Consulta SQL
        PreparedStatement ps = conn.prepareStatement(insertQuery); // Prepara la consulta
        ps.setString(1, login.getUsername()); // Asigna el nombre de usuario
        ps.setString(2, login.getPassword()); // Asigna la contraseña
        ps.execute(); // Ejecuta la consulta
    }

    @Override
    public List<Login> readAll() throws SQLException {
        List<Login> users = new ArrayList(); // Lista para almacenar los usuarios
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT id, username, password FROM " + tableName;
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery(); // Ejecuta la consulta

        while (rs.next()) { // Recorre los resultados
            users.add(new Login(
                    rs.getInt(1),       // Asigna el id
                    rs.getString(2),    // Asigna el username
                    rs.getString(3)     // Asigna la password
            ));
        }

        return users; // Devuelve la lista de usuarios
    }
}
