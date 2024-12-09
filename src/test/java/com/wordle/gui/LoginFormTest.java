package com.wordle.gui;

import com.wordle.dao.LoginFormDAO;
import com.wordle.model.Login;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Pruebas para la clase LoginForm")
class LoginFormTest {
    private LoginForm.LoginAction loginAction;
    private LoginFormDAO mockDAO;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    @BeforeEach
    void setUp() {
        mockDAO = Mockito.mock(LoginFormDAO.class);
        LoginForm loginForm = new LoginForm();
        loginForm.loginFormDAO = mockDAO;

        // Preparar los componentes necesarios para las pruebas
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        messageLabel = new JLabel();

        loginForm.usernameField = usernameField;
        loginForm.passwordField = passwordField;
        loginForm.messageLabel = messageLabel;

        loginAction = loginForm.new LoginAction();
    }

    @Test
    @DisplayName("Autenticación exitosa con usuario válido")
    void testAuthenticate_Successful() throws SQLException {
        // Configurar datos de prueba
        String validUsername = "usuario1";
        String validPassword = "contraseña1";
        List<Login> mockUsers = Collections.singletonList(
                new Login(1, validUsername, validPassword)
        );
        when(mockDAO.readAll()).thenReturn(mockUsers);

        // Simular la entrada del usuario
        usernameField.setText(validUsername);
        passwordField.setText(validPassword);

        // Verificar autenticación
        boolean isAuthenticated = loginAction.authenticate(validUsername, validPassword);
        assertTrue(isAuthenticated, "El usuario y la contraseña deberían ser válidos");
    }

    @Test
    @DisplayName("Autenticación fallida con usuario inválido")
    void testAuthenticate_InvalidUser() throws SQLException {
        // Configurar datos de prueba
        List<Login> mockUsers = Arrays.asList(
                new Login(1, "usuario1", "contraseña1"),
                new Login(2, "usuario2", "contraseña2")
        );
        when(mockDAO.readAll()).thenReturn(mockUsers);

        // Simular la entrada del usuario
        String invalidUsername = "usuarioInvalido";
        String invalidPassword = "contraseñaInvalida";
        usernameField.setText(invalidUsername);
        passwordField.setText(invalidPassword);

        // Verificar autenticación
        boolean isAuthenticated = loginAction.authenticate(invalidUsername, invalidPassword);
        assertFalse(isAuthenticated, "El usuario y la contraseña no deberían ser válidos");
    }

    @Test
    @DisplayName("Mostrar mensaje de error cuando la autenticación falla")
    void testErrorMessageOnFailedAuthentication() throws SQLException {
        // Configurar datos de prueba
        when(mockDAO.readAll()).thenReturn(Collections.emptyList());

        // Simular la entrada del usuario
        usernameField.setText("usuario");
        passwordField.setText("contraseña");

        // Ejecutar acción de inicio de sesión
        loginAction.actionPerformed(null);

        // Verificar el mensaje mostrado
        assertEquals("Usuario o contraseña incorrectos...", messageLabel.getText(),
                "El mensaje de error debería mostrarse correctamente");
    }

    @Test
    @DisplayName("Manejar excepción de SQL durante la autenticación")
    void testSQLExceptionHandling() throws SQLException {
        // Configurar el DAO para lanzar una excepción
        when(mockDAO.readAll()).thenThrow(new SQLException("Error al acceder a la base de datos"));

        // Simular la entrada del usuario
        usernameField.setText("usuario");
        passwordField.setText("contraseña");

        // Verificar que la excepción es manejada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loginAction.authenticate("usuario", "contraseña");
        });
        assertEquals("java.sql.SQLException: Error al acceder a la base de datos", exception.getMessage(),
                "La excepción debería ser encapsulada en un RuntimeException");
    }

    @Test
    @DisplayName("Autenticación fallida con lista vacía de usuarios")
    void testAuthenticate_EmptyUserList() throws SQLException {
        // Configurar datos de prueba: lista vacía de usuarios
        when(mockDAO.readAll()).thenReturn(Collections.emptyList());

        // Simular la entrada del usuario
        usernameField.setText("usuario");
        passwordField.setText("contraseña");

        // Verificar autenticación
        boolean isAuthenticated = loginAction.authenticate("usuario", "contraseña");
        assertFalse(isAuthenticated, "La autenticación debería fallar con una lista vacía de usuarios");
    }

}