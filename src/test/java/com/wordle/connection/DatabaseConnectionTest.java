package com.wordle.connection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas para conexión a base de datos")
class DatabaseConnectionTest {

    @Test
    @DisplayName("Instancia Singleton debería ser no nula y consistente")
    void testSingletonInstance() throws SQLException {
        // Obtiene dos instancias del Singleton
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();

        // Verifica que la primera instancia no sea nula
        assertNotNull(instance1, "La instancia debería ser no nula");
        // Verifica que ambas referencias apunten al mismo objeto (Singleton)
        assertSame(instance1, instance2, "Ambas instancias deberían ser iguales");
    }

    @Test
    @DisplayName("La conexión no debería ser nula y estar abierta")
    void testConnectionIsValid() throws SQLException {
        // Obtiene la instancia del Singleton
        DatabaseConnection instance = DatabaseConnection.getInstance();
        // Obtiene la conexión de la instancia
        Connection connection = instance.getConnection();

        // Verifica que la conexión no sea nula
        assertNotNull(connection, "La conexión debería ser no nula");
        // Verifica que la conexión esté abierta
        assertFalse(connection.isClosed(), "La conexión debería estar abierta");
    }

    @Test
    @DisplayName("Debería recrear instancia si la conexión está cerrada")
    void testRecreateInstanceIfConnectionClosed() throws SQLException {
        // Obtiene la primera instancia del Singleton y la conexión asociada
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        Connection connection = instance1.getConnection();
        // Cierra la conexión para simular un escenario donde la conexión no está disponible
        connection.close();

        // Verifica que la conexión esté cerrada
        assertTrue(connection.isClosed(), "La conexión debería estar cerrada");

        // Obtiene una nueva instancia del Singleton
        DatabaseConnection instance2 = DatabaseConnection.getInstance();
        // Verifica que la nueva instancia no sea la misma que la anterior (recreación)
        assertNotSame(instance1, instance2, "Una nueva instancia debería ser creada si la conexión está cerrada");
        // Verifica que la nueva conexión esté abierta
        assertFalse(instance2.getConnection().isClosed(), "La nueva conexión debería estar abierta");
    }
}