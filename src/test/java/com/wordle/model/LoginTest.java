package com.wordle.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas para la clase Login")
class LoginTest {

    @Test
    @DisplayName("El constructor por defecto debe inicializar los campos con los valores predeterminados")
    void testDefaultConstructor() {
        // Verifica que el constructor por defecto inicialice:
        // - El ID como 0.
        // - El username como null.
        // - El password como null.
        Login login = new Login();

        assertEquals(0, login.getId(), "El ID debería ser 0 por defecto.");
        assertNull(login.getUsername(), "El username debería ser null por defecto.");
        assertNull(login.getPassword(), "El password debería ser null por defecto.");
    }

    @Test
    @DisplayName("El constructor con parámetros debería iniciar correctamente los campos")
    void testParameterizedConstructor() {
        // Verifica que el constructor con parámetros inicialice correctamente
        // el ID, username y password con los valores proporcionados.
        int id = 1;
        String username = "testUser";
        String password = "testPassword";

        Login login = new Login(id, username, password);

        assertEquals(id, login.getId(), "El ID debería inicializarse con el valor proporcionado.");
        assertEquals(username, login.getUsername(), "El username debería inicializarse con el valor proporcionado.");
        assertEquals(password, login.getPassword(), "El password debería inicializarse con el valor proporcionado.");
    }

    @Test
    @DisplayName("Getters y setters deberían funcionar correctamente")
    void testGettersAndSetters() {
        // Verifica que los métodos getter y setter funcionen correctamente para:
        // - Establecer y recuperar el ID.
        // - Establecer y recuperar el username.
        // - Establecer y recuperar el password.
        int id = 5;
        String username = "newUser";
        String password = "newPassword";

        Login login = new Login();

        login.setId(id);
        login.setUsername(username);
        login.setPassword(password);

        assertEquals(id, login.getId(), "El getter y setter de ID deberían funcionar correctamente.");
        assertEquals(username, login.getUsername(), "El getter y setter de username deberían funcionar correctamente.");
        assertEquals(password, login.getPassword(), "El getter y setter de password deberían funcionar correctamente.");
    }
}

