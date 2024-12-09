package com.wordle.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.wordle.connection.DatabaseConnection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Pruebas para la clase RandomWordDAOImp")
class RandomWordDAOImpTest {

    private RandomWordDAOImp randomWordDAOImp;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        // Inicializa los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);
        // Crea una instancia de la clase bajo prueba
        randomWordDAOImp = new RandomWordDAOImp();
    }

    @Test
    @DisplayName("Debería obtener una palabra aleatoria de la base de datos")
    void testGetRandomWord() throws SQLException {
        // Usa MockedStatic para simular métodos estáticos de DatabaseConnection
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {

            // Configura el mock para el Singleton de DatabaseConnection
            DatabaseConnection mockDatabaseConnection = Mockito.mock(DatabaseConnection.class);
            when(DatabaseConnection.getInstance()).thenReturn(mockDatabaseConnection);
            when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);

            // Configura el mock para el mét0do prepareStatement y ResultSet
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            // Simula que el ResultSet contiene una fila con una palabra
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getString("word")).thenReturn("exampleWord");

            // Invoca el mét0do bajo prueba
            String randomWord = randomWordDAOImp.getRandomWord();

            // Verifica que el resultado sea la palabra esperada
            assertEquals("exampleWord", randomWord, "La palabra obtenida debería ser 'exampleWord'");

            // Verifica que los métodos correctos fueron llamados en el orden esperado
            verify(mockConnection).prepareStatement("SELECT word FROM words ORDER BY RAND() LIMIT 1");
            verify(mockPreparedStatement).executeQuery();
            verify(mockResultSet).next();
            verify(mockResultSet).getString("word");
        }
    }

    @Test
    @DisplayName("Debería regresar null sino encuentra una palabra")
    void testGetRandomWordNoResult() throws SQLException {
        // Usa MockedStatic para simular métodos estáticos de DatabaseConnection
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {

            // Configura el mock para el Singleton de DatabaseConnection
            DatabaseConnection mockDatabaseConnection = Mockito.mock(DatabaseConnection.class);
            when(DatabaseConnection.getInstance()).thenReturn(mockDatabaseConnection);
            when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);

            // Configura el mock para el mét0do prepareStatement y ResultSet
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            // Simula que el ResultSet no contiene filas
            when(mockResultSet.next()).thenReturn(false);

            // Invoca el mét0do bajo prueba
            String randomWord = randomWordDAOImp.getRandomWord();

            // Verifica que el resultado sea null cuando no hay palabras disponibles
            assertNull(randomWord, "El resultado debería ser null si no se encuentra ninguna palabra");

            // Verifica que los métodos correctos fueron llamados
            verify(mockConnection).prepareStatement("SELECT word FROM words ORDER BY RAND() LIMIT 1");
            verify(mockPreparedStatement).executeQuery();
            verify(mockResultSet).next();
        }
    }
}
