package com.wordle.gui;

import com.wordle.dao.LoginFormDAO;
import com.wordle.model.Login;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.assertj.swing.timing.Pause.pause;
import static org.mockito.Mockito.*;

@DisplayName("Pruebas para la clase RegisterForm")
class RegisterFormTest {
    private FrameFixture frameFixture;

    @Mock
    private LoginFormDAO mockLoginFormDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear instancia de la clase bajo prueba
        RegisterForm registerForm = GuiActionRunner.execute(() -> new RegisterForm(mockLoginFormDAO));

        // Inicializar AssertJ Swing Fixture
        frameFixture = new FrameFixture(registerForm);
        frameFixture.show(); // Mostrar la ventana para pruebas
    }

    @AfterEach
    void tearDown() {
        frameFixture.cleanUp();
    }

    @Test
    @DisplayName("Debería mostrar un mensaje de error cuando los campos están vacíos")
    void testEmptyFieldsError() {
        // Simular clic en el botón de registro
        frameFixture.button("registerButton").click();

        // Esperar que Swing actualice los cambios
        pause(100);

        // Verificar que el mensaje de error sea mostrado
        frameFixture.label("messageLabel").requireText("Todos los campos son obligatorios.");
    }

    @Test
    @DisplayName("Debería mostrar un mensaje de éxito cuando un usuario se registra")
    void testSuccessfulRegistration() throws SQLException {
        // Ingresar datos válidos en los campos de texto
        frameFixture.textBox("usernameField").setText("testUser");
        frameFixture.textBox("passwordField").setText("testPassword");

        // Simular comportamiento del DAO
        doNothing().when(mockLoginFormDAO).create(any(Login.class));

        // Simular clic en el botón de registro
        frameFixture.button("registerButton").click();

        // Verificar que se muestra un mensaje de éxito
        verify(mockLoginFormDAO).create(argThat(login ->
                login.getUsername().equals("testUser") && login.getPassword().equals("testPassword")
        ));
        JOptionPaneFixture optionPane = frameFixture.optionPane();
        optionPane.requireMessage("Usuario registrado con éxito.");
    }

    @Test
    @DisplayName("Debería arrojar un mensaje de error cual el DAO lanza una SQLException")
    void testSQLExceptionError() throws SQLException {
        // Ingresar datos válidos en los campos de texto
        frameFixture.textBox("usernameField").setText("testUser");
        frameFixture.textBox("passwordField").setText("testPassword");

        // Simular excepción al llamar al DAO
        doThrow(new SQLException("Error en la base de datos")).when(mockLoginFormDAO).create(any(Login.class));

        // Simular clic en el botón de registro
        frameFixture.button("registerButton").click();

        // Esperar brevemente para que Swing actualice el texto de la etiqueta
        pause(100);

        // Verificar que se muestra un mensaje de error en el messageLabel
        frameFixture.label("messageLabel").requireText("Error al registrar el usuario.");
    }
}