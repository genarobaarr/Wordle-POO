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
import java.util.List;
import com.wordle.connection.DatabaseConnection;
import com.wordle.model.Login;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Pruebas para la clase LoginFormDAOImp")
class LoginFormDAOImpTest {

    private LoginFormDAOImp loginFormDAOImp;

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
        loginFormDAOImp = new LoginFormDAOImp();
    }

    @Test
    @DisplayName("Debería crear un nuevo usuario en la base de datos")
    void testCreate() throws SQLException {
        // Usa MockedStatic para simular métodos estáticos de DatabaseConnection
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {

            // Configura el mock para el Singleton de DatabaseConnection
            DatabaseConnection mockDatabaseConnection = Mockito.mock(DatabaseConnection.class);
            when(DatabaseConnection.getInstance()).thenReturn(mockDatabaseConnection);
            when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);

            // Configura el mock para el mét0do prepareStatement
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            // Crea un objeto Login para la prueba
            Login login = new Login(1, "testUser", "testPassword");

            // Invoca el mét0do create para insertar un usuario en la base de datos
            loginFormDAOImp.create(login);

            // Verifica que se usaron correctamente los métodos del mock
            verify(mockConnection).prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            verify(mockPreparedStatement).setString(1, "testUser");
            verify(mockPreparedStatement).setString(2, "testPassword");
            verify(mockPreparedStatement).execute();
        }
    }

    @Test
    @DisplayName("Debería leer a todos los usuarios de la base de datos")
    void testReadAll() throws SQLException {
        // Usa MockedStatic para simular métodos estáticos de DatabaseConnection
        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {

            // Configura el mock para el Singleton de DatabaseConnection
            DatabaseConnection mockDatabaseConnection = Mockito.mock(DatabaseConnection.class);
            when(DatabaseConnection.getInstance()).thenReturn(mockDatabaseConnection);
            when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);

            // Configura el mock para el mét0do prepareStatement y ResultSet
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            // Configura el ResultSet para simular resultados de la base de datos
            when(mockResultSet.next()).thenReturn(true, false); // Primera llamada retorna true, la segunda false
            when(mockResultSet.getInt(1)).thenReturn(1);        // Simula el ID del usuario
            when(mockResultSet.getString(2)).thenReturn("testUser"); // Simula el nombre de usuario
            when(mockResultSet.getString(3)).thenReturn("testPassword"); // Simula la contraseña

            // Invoca el mét0do readAll para obtener todos los usuarios
            List<Login> users = loginFormDAOImp.readAll();

            // Verifica que los datos obtenidos coinciden con los valores simulados
            assertEquals(1, users.size(), "Debería haber un usuario en la lista");
            Login user = users.get(0);
            assertEquals(1, user.getId(), "El ID del usuario debería ser 1");
            assertEquals("testUser", user.getUsername(), "El nombre de usuario debería ser 'testUser'");
            assertEquals("testPassword", user.getPassword(), "La contraseña debería ser 'testPassword'");
        }
    }
}
