package com.wordle.gui;

import com.wordle.connection.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.mockito.*;
import javax.swing.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Pruebas para la clase WordleManager")
class WordleManagerTest {

    private WordleManager wordleManager;
    private DatabaseConnection mockDatabaseConnection;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks necesarios para las pruebas
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        mockDatabaseConnection = mock(DatabaseConnection.class);

        // Configura el comportamiento del mét0do estático de DatabaseConnection
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getInstance).thenReturn(mockDatabaseConnection);
            when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);
        } catch (RuntimeException e) {
            fail("Error al configurar los mocks de la base de datos: " + e.getMessage());
        }

        // Crea una instancia de WordleManager con un usuario de prueba
        wordleManager = new WordleManager("testUser");
    }

    @Test
    @DisplayName("Actualizar resultados: incrementar victorias correctamente")
    void testUpdateResultsIncrementsVictories() {
        // Simula el mét0do estático DatabaseConnection y configura los mocks
        try (MockedStatic<DatabaseConnection> mockedDatabase = mockStatic(DatabaseConnection.class)) {
            mockedDatabase.when(DatabaseConnection::getInstance).thenReturn(mockDatabaseConnection);
            when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Invoca el mét0do updateResults con el parámetro true (incrementa victorias)
            wordleManager.updateResults(true);

            // Verifica que se hayan llamado los métodos correctos
            verify(mockPreparedStatement, times(1)).setString(1, "testUser");
            verify(mockPreparedStatement, times(1)).executeUpdate();
        } catch (SQLException e) {
            fail("Excepción inesperada durante la prueba de actualización de victorias.");
        }
    }

    @Test
    @DisplayName("Actualizar resultados: incrementar derrotas correctamente")
    void testUpdateResultsIncrementsDefeats() {
        // Similar a la prueba anterior, pero con derrotas
        try (MockedStatic<DatabaseConnection> mockedDatabase = mockStatic(DatabaseConnection.class)) {
            mockedDatabase.when(DatabaseConnection::getInstance).thenReturn(mockDatabaseConnection);
            when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Invoca el mét0do updateResults con el parámetro false (incrementa derrotas)
            wordleManager.updateResults(false);

            // Verifica que se hayan llamado los métodos correctos
            verify(mockPreparedStatement, times(1)).setString(1, "testUser");
            verify(mockPreparedStatement, times(1)).executeUpdate();
        } catch (SQLException e) {
            fail("Excepción inesperada durante la prueba de actualización de derrotas.");
        }
    }

    @Test
    @DisplayName("Mostrar resultados: historial mostrado correctamente")
    void testShowResultsDisplaysCorrectly() throws SQLException {
        // Configura el mock del PreparedStatement y ResultSet
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Simula un ResultSet con valores de victorias y derrotas
        when(mockResultSet.next()).thenReturn(true, false); // Primera fila existe, luego no hay más
        when(mockResultSet.getInt("victories")).thenReturn(5);
        when(mockResultSet.getInt("defeats")).thenReturn(3);

        // Invoca el mét0do showResults
        wordleManager.showResults();

        // Verificación adicional de la lógica interna puede ser necesaria dependiendo de la implementación
    }

    @Test
    @DisplayName("Componentes de la GUI inicializados correctamente")
    void testComponentsInitialized() {
        // Verifica que los componentes de la interfaz gráfica no sean nulos
        assertNotNull(wordleManager.attemptsLabel, "La etiqueta de intentos no debería ser nula.");
        assertNotNull(wordleManager.guessField, "El campo de texto para adivinanzas no debería ser nulo.");
        assertNotNull(wordleManager.feedbackArea, "El área de retroalimentación no debería ser nula.");
    }
}
