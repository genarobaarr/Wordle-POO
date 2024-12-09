package com.wordle.dao;

import com.wordle.connection.DatabaseConnection;

import java.sql.*;

public class RandomWordDAOImp implements RandomWordDAO {

    @Override
    public String getRandomWord() throws SQLException {
        // Obtiene una conexión a la base de datos.
        Connection conn = DatabaseConnection.getInstance().getConnection();

        // Consulta SQL para seleccionar una palabra aleatoria de la tabla "words".
        String query = "SELECT word FROM words ORDER BY RAND() LIMIT 1";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        // Si hay un resultado, devuelve la palabra.
        if (rs.next()) {
            return rs.getString("word");
        }

        return null; // Devuelve null si no hay palabras en la tabla.
    }
}
