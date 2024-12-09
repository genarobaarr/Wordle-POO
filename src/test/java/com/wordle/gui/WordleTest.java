package com.wordle.gui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas para la clase Wordle")
class WordleTest {

    @Test
    @DisplayName("Debería inicializar correctamente la palabra objetivo y los intentos")
    void testInitialization() {
        // Verifica que el juego se inicialice correctamente con 6 intentos
        // y que la palabra objetivo se guarde en mayúsculas.
        Wordle wordle = new Wordle("amigo");
        assertEquals(6, wordle.getRemainingAttempts());
        assertEquals("AMIGO", wordle.getTargetWord());
    }

    @Test
    @DisplayName("Debería devolver 'Longitud inválida!' si el intento tiene una longitud incorrecta")
    void testInvalidLengthGuess() {
        // Comprueba que se devuelva un mensaje de error si el intento
        // tiene una longitud diferente a la palabra objetivo.
        Wordle wordle = new Wordle("amigos");
        assertEquals("Longitud inválida!", wordle.checkGuess("enemigo"));
    }

    @Test
    @DisplayName("Debería manejar un intento completamente correcto")
    void testCorrectGuess() {
        // Verifica que el juego detecte un intento completamente correcto,
        // devolviendo ✔ por cada letra acertada.
        Wordle wordle = new Wordle("amigo");
        assertEquals("✔✔✔✔✔", wordle.checkGuess("amigo"));
        assertTrue(wordle.isCorrectGuess("amigo"));
    }

    @Test
    @DisplayName("Debería manejar un intento completamente incorrecto")
    void testCompletelyIncorrectGuess() {
        // Comprueba que el juego devuelva ✗ por cada letra
        // cuando el intento no contiene letras correctas.
        Wordle wordle = new Wordle("amigo");
        assertEquals("✗✗✗✗✗", wordle.checkGuess("breve"));
    }

    @Test
    @DisplayName("Debería manejar letras correctas en posiciones incorrectas")
    void testPartialCorrectGuess() {
        // Verifica que el juego devuelva _ por cada letra que es correcta
        // pero está en una posición incorrecta.
        Wordle wordle = new Wordle("amigo");
        assertEquals("_____", wordle.checkGuess("oamig"));
    }

    @Test
    @DisplayName("Debería reducir los intentos restantes después de cada intento")
    void testAttemptsDecrease() {
        // Asegura que los intentos restantes disminuyan en 1 por cada intento,
        // incluso si el intento es incorrecto.
        Wordle wordle = new Wordle("amigo");
        wordle.checkGuess("amiga");
        assertEquals(5, wordle.getRemainingAttempts());
        wordle.checkGuess("malos");
        assertEquals(4, wordle.getRemainingAttempts());
    }

    @Test
    @DisplayName("Debería identificar correctamente si el juego ha terminado")
    void testGameOver() {
        // Comprueba que el juego detecte correctamente cuando se han agotado
        // los intentos disponibles, indicando que el juego ha terminado.
        Wordle wordle = new Wordle("amigo");
        for (int i = 0; i < 6; i++) {
            wordle.checkGuess("malos");
        }
        assertTrue(wordle.isGameOver());
        assertEquals(0, wordle.getRemainingAttempts());
    }

    @Test
    @DisplayName("Debería devolver verdadero cuando el intento es correcto, independientemente de mayúsculas o minúsculas")
    void testCaseInsensitiveCorrectGuess() {
        // Verifica que el juego trate los intentos como insensibles a mayúsculas,
        // permitiendo adivinanzas correctas en cualquier combinación de caso.
        Wordle wordle = new Wordle("amigo");
        assertTrue(wordle.isCorrectGuess("AMIGO"));
        assertTrue(wordle.isCorrectGuess("amigo"));
    }
}
