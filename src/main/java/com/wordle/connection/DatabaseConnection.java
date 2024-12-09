package com.wordle.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Instancia única para implementar el patrón Singleton
    private static DatabaseConnection instance;
    private Connection connection; // Objeto connection para manejar la conexión a la base de datos

    // Constantes con los detalles de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/wordle";
    private static final String USER = "root";
    private static final String PASSWORD = "my$Q.L1P4sS$w0Rd";

    // Constructor privado para evitar instanciación externa
    private DatabaseConnection() throws SQLException {
        try {
            // Carga el driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establece la conexión con la base de datos
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {

            // Lanza una excepción en caso de no encontrar el driver
            throw new RuntimeException(e);
        }
    }

    // Mét0do para obtener la instancia única de la clase (Singleton)
    public static DatabaseConnection getInstance() throws SQLException {

        // Crea una nueva instancia si aún no existe o si la conexión está cerrada
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Devuelve el objeto Connection
    public Connection getConnection() {

        return connection;
    }
}
