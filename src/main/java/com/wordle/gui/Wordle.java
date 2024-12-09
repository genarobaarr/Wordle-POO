package com.wordle.gui;

public class Wordle {  // Define la clase Wordle, que maneja la lógica del juego.
    private final String targetWord;  // Declara una variable privada para almacenar la palabra objetivo que el jugador debe adivinar.
    private int attempts;  // Declara una variable privada para almacenar los intentos restantes del jugador.

    public Wordle(String word) {  // Constructor de la clase Wordle que recibe la palabra a adivinar.
        this.targetWord = word.toUpperCase();  // Asigna la palabra objetivo convirtiéndola a mayúsculas.
        this.attempts = 6;  // Inicializa los intentos disponibles en 6.
    }

    public String checkGuess(String guess) {  // Mét0do para verificar la adivinanza del jugador.
        guess = guess.toUpperCase();  // Convierte la adivinanza del jugador a mayúsculas para la comparación.

        if (guess.length() != targetWord.length()) {  // Verifica si la longitud de la adivinanza es correcta.
            return "Longitud inválida!";  // Si la longitud es incorrecta, devuelve un mensaje de error.
        }

        StringBuilder feedback = new StringBuilder();  // Crea un StringBuilder para construir la retroalimentación.
        boolean[] targetUsed = new boolean[targetWord.length()];  // Array para marcar las letras ya utilizadas de la palabra objetivo.
        boolean[] guessUsed = new boolean[targetWord.length()];  // Array para marcar las letras ya utilizadas de la adivinanza.

        // Primera pasada: marca las letras correctas (✔)
        for (int i = 0; i < targetWord.length(); i++) {  // Recorre cada letra de la palabra.
            if (guess.charAt(i) == targetWord.charAt(i)) {  // Si la letra en la adivinanza coincide con la de la palabra objetivo.
                feedback.append("✔");  // Añade el símbolo de correcto (✔) a la retroalimentación.
                targetUsed[i] = true;  // Marca esta letra como utilizada en la palabra objetivo.
                guessUsed[i] = true;  // Marca esta letra como utilizada en la adivinanza.
            } else {
                feedback.append(" ");  // Si la letra no es correcta, agrega un espacio en la retroalimentación.
            }
        }

        // Segunda pasada: marca las letras incorrectas en posición pero presentes (_)
        for (int i = 0; i < targetWord.length(); i++) {  // Vuelve a recorrer las letras de la palabra.
            if (!guessUsed[i]) {  // Solo procesa letras que no han sido marcadas como correctas.
                boolean found = false;  // Variable para verificar si la letra se encuentra en la palabra objetivo en otra posición.
                for (int j = 0; j < targetWord.length(); j++) {  // Recorre las letras de la palabra objetivo.
                    if (!targetUsed[j] && guess.charAt(i) == targetWord.charAt(j)) {  // Verifica si la letra en la adivinanza está en la palabra en una posición diferente.
                        feedback.setCharAt(i, '_');  // Reemplaza el espacio por una marca vacía (para evitar sobreescribir incorrectamente).
                        targetUsed[j] = true;  // Marca la letra como utilizada en la palabra objetivo.
                        found = true;  // Indica que la letra fue encontrada.
                        break;  // Sale del bucle una vez que encuentra la letra.
                    }
                }
                if (!found) {  // Si la letra no fue encontrada en la palabra objetivo.
                    feedback.setCharAt(i, '✗');  // Marca la letra como incorrecta con un símbolo de error (✗).
                }
            }
        }

        attempts--;  // Decrementa el número de intentos restantes.
        return feedback.toString();  // Devuelve la retroalimentación construida como una cadena.
    }

    public boolean isGameOver() {  // Mét0do para verificar si el juego ha terminado.
        return attempts <= 0;  // Si los intentos son 0 o menos, el juego ha terminado.
    }

    public boolean isCorrectGuess(String guess) {  // Mét0do para verificar si la adivinanza es correcta.
        return targetWord.equalsIgnoreCase(guess);  // Compara la adivinanza con la palabra objetivo sin importar mayúsculas/minúsculas.
    }

    public int getRemainingAttempts() {  // Mét0do para obtener los intentos restantes.
        return attempts;  // Devuelve el número de intentos restantes.
    }

    public String getTargetWord() {  // Mét0do para obtener la palabra objetivo.
        return targetWord;  // Devuelve la palabra objetivo.
    }
}
