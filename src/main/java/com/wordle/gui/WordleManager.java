package com.wordle.gui;

import com.wordle.connection.DatabaseConnection;
import com.wordle.dao.RandomWordDAO;
import com.wordle.dao.RandomWordDAOImp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WordleManager extends JFrame {  // Define la clase WordleManager, que extiende JFrame para manejar la interfaz gráfica.

    private Wordle game;  // Declara una variable para manejar el juego Wordle.
    protected JTextField guessField;  // Declara un campo de texto para que el jugador ingrese su adivinanza.
    protected JTextArea feedbackArea;  // Declara un área de texto para mostrar la retroalimentación del juego.
    protected JLabel attemptsLabel;  // Declara una etiqueta para mostrar los intentos restantes.
    private String loggedUser;  // Declara una variable para almacenar el nombre del usuario logueado.

    public static void main(String[] args) {  // Mét0do principal para ejecutar la aplicación.
        SwingUtilities.invokeLater(() -> {  // Ejecuta la interfaz gráfica en el hilo de eventos de Swing.
            WordleManager wordleManager = new WordleManager("admin");  // Crea una instancia de WordleManager con el usuario "admin".
            wordleManager.setVisible(true);  // Muestra la ventana de la aplicación.
        });
    }

    public WordleManager(String loggedUser) {  // Constructor de la clase WordleManager, recibe el usuario logueado.
        try {
            this.loggedUser = loggedUser;  // Asigna el usuario logueado a la variable.
            RandomWordDAO randomWordDAO = new RandomWordDAOImp();  // Crea una instancia del DAO que obtiene palabras aleatorias.

            setTitle("Wordle - Adivina la Palabra");  // Establece el título de la ventana.
            setSize(500, 500);  // Establece el tamaño de la ventana.
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Establece que el programa se cierre al cerrar la ventana.
            setLocationRelativeTo(null);  // Centra la ventana en la pantalla.

            JPanel mainPanel = new JPanel();  // Crea un panel principal para organizar los componentes de la interfaz.
            mainPanel.setLayout(new BorderLayout());  // Establece un diseño de bordes en el panel principal.
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // Añade un borde vacío alrededor del panel.
            mainPanel.setBackground(new Color(240, 248, 255));  // Establece el color de fondo del panel.

            JLabel titleLabel = new JLabel("¡Bienvenido, " + loggedUser + "!", SwingConstants.CENTER);  // Crea una etiqueta de bienvenida con el nombre del usuario.
            titleLabel.setFont(new Font("Verdana", Font.BOLD, 24));  // Establece la fuente y el tamaño del texto.
            titleLabel.setForeground(new Color(0, 102, 204));  // Establece el color del texto.
            mainPanel.add(titleLabel, BorderLayout.NORTH);  // Añade la etiqueta al panel principal en la parte superior.

            String randomWord = randomWordDAO.getRandomWord();  // Obtiene una palabra aleatoria del DAO.
            if (randomWord == null) {  // Si no se pudo obtener una palabra.
                JOptionPane.showMessageDialog(this, "No se pudo cargar una palabra de la base de datos.");  // Muestra un mensaje de error.
                System.exit(1);  // Finaliza la aplicación.
            }
            game = new Wordle(randomWord);  // Crea una nueva instancia del juego con la palabra aleatoria obtenida.

            JPanel topPanel = new JPanel(new BorderLayout());  // Crea un panel para la parte superior de la interfaz con un diseño de bordes.
            topPanel.setOpaque(false);  // Hace que el panel superior sea transparente.

            attemptsLabel = new JLabel("Intentos restantes: " + game.getRemainingAttempts() + "  ");  // Crea una etiqueta para mostrar los intentos restantes.
            attemptsLabel.setName("attemptsLabel");         // Establece nombre a la etiqueta intentos
            attemptsLabel.setFont(new Font("Verdana", Font.PLAIN, 16));  // Establece la fuente de la etiqueta.
            topPanel.add(attemptsLabel, BorderLayout.WEST);  // Añade la etiqueta al panel superior.

            guessField = new JTextField();  // Crea un campo de texto para que el jugador ingrese su adivinanza.
            guessField.setName("guessField");               // Establece nombre al campo adivinanza
            guessField.setFont(new Font("Consolas", Font.PLAIN, 18));  // Establece la fuente del campo de texto.
            topPanel.add(guessField, BorderLayout.CENTER);  // Añade el campo de texto al panel superior.

            JButton guessButton = new JButton("   OK   ");  // Crea un botón para enviar la adivinanza.
            guessButton.setFont(new Font("Verdana", Font.BOLD, 16));  // Establece la fuente del botón.
            guessButton.setBackground(new Color(0, 153, 51));  // Establece el color de fondo del botón.
            guessButton.setForeground(Color.WHITE);  // Establece el color del texto del botón.
            guessButton.addActionListener(new GuessListener());  // Añade un ActionListener para manejar el evento de clic.
            topPanel.add(guessButton, BorderLayout.EAST);  // Añade el botón al panel superior.

            mainPanel.add(topPanel, BorderLayout.CENTER);  // Añade el panel superior al panel principal.

            feedbackArea = new JTextArea();  // Crea un área de texto para mostrar la retroalimentación del juego.
            feedbackArea.setName("feedbackArea");       // Establece nombre al área de retroalimentación.
            feedbackArea.setEditable(false);  // Hace que el área de texto no sea editable.
            feedbackArea.setFont(new Font("Monospaced", Font.PLAIN, 14));  // Establece la fuente del área de texto.
            feedbackArea.setBackground(new Color(245, 245, 245));  // Establece el color de fondo del área de texto.
            feedbackArea.setForeground(Color.DARK_GRAY);  // Establece el color del texto.
            feedbackArea.setLineWrap(true);  // Activa el ajuste de línea en el área de texto.
            feedbackArea.setWrapStyleWord(true);  // Establece el ajuste de palabras en el área de texto.

            JScrollPane scrollPane = new JScrollPane(feedbackArea);  // Crea un JScrollPane para hacer desplazable el área de texto.
            scrollPane.setBorder(BorderFactory.createTitledBorder("Retroalimentación"));  // Establece el borde del JScrollPane.
            mainPanel.add(scrollPane, BorderLayout.SOUTH);  // Añade el JScrollPane al panel principal en la parte inferior.

            add(mainPanel);  // Añade el panel principal a la ventana.
            setVisible(true);  // Hace visible la ventana.
        } catch (SQLException d) {  // Captura excepciones relacionadas con la base de datos.
            throw new RuntimeException(d);  // Lanza una excepción de tiempo de ejecución si ocurre un error.
        }
    }

    protected class GuessListener implements ActionListener { // Define una clase interna que implementa ActionListener para manejar los eventos del botón de adivinanza.
        @Override
        public void actionPerformed(ActionEvent e) {  // Sobrescribe el mét0do actionPerformed, que se ejecuta cuando se hace clic en el botón.
            String guess = guessField.getText().trim();  // Obtiene el texto ingresado en el campo de adivinanza y elimina los espacios en blanco al principio y al final.

            if (game.isCorrectGuess(guess)) {  // Si la adivinanza es correcta según el juego.
                feedbackArea.append("🎉 ¡Correcto! La palabra era: " + guess + "\n");  // Añade un mensaje al área de retroalimentación indicando que la adivinanza fue correcta.
                guessField.setEnabled(false);  // Desactiva el campo de texto de la adivinanza para evitar más intentos.
                updateResults(true);  // Llama al mét0do updateResults para actualizar las estadísticas del usuario con una victoria.
                showResults();  // Muestra los resultados en una ventana.
            } else {  // Si la adivinanza no es correcta.
                String feedback = game.checkGuess(guess);  // Obtiene la retroalimentación del juego sobre la adivinanza.
                feedbackArea.append("❌ Adivinaste: " + guess + " - " + feedback + "\n");  // Añade un mensaje al área de retroalimentación indicando que la adivinanza fue incorrecta.
                attemptsLabel.setText("Intentos restantes: " + game.getRemainingAttempts());  // Actualiza la etiqueta de intentos restantes.

                if (game.isGameOver()) {  // Si el juego ha terminado.
                    feedbackArea.append("💔 ¡Juego terminado! La palabra era: " + game.getTargetWord() + "\n");  // Muestra un mensaje indicando que el juego ha terminado y revela la palabra.
                    guessField.setEnabled(false);  // Desactiva el campo de texto de la adivinanza.
                    updateResults(false);  // Llama al mét0do updateResults para actualizar las estadísticas del usuario con una derrota.
                    showResults();  // Muestra los resultados en una ventana.
                }
            }
            guessField.setText("");  // Limpia el campo de texto de la adivinanza para el siguiente intento.
        }
    }

    protected void updateResults(boolean won) { // Mét0do para actualizar los resultados del usuario en la base de datos.
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();  // Obtiene la conexión a la base de datos.
            String query = won
                    ? "UPDATE users SET victories = victories + 1 WHERE username = ?"  // Si el usuario ganó, incrementa el contador de victorias.
                    : "UPDATE users SET defeats = defeats + 1 WHERE username = ?";  // Si el usuario perdió, incrementa el contador de derrotas.
            PreparedStatement ps = conn.prepareStatement(query);  // Prepara la consulta SQL.

            ps.setString(1, loggedUser);  // Establece el nombre de usuario en la consulta.
            ps.executeUpdate();  // Ejecuta la consulta para actualizar los resultados en la base de datos.
        } catch (SQLException e) {  // Si ocurre un error de SQL.
            throw new RuntimeException(e);  // Lanza una excepción de tiempo de ejecución.
        }
    }

    protected void showResults() { // Mét0do para mostrar los resultados del juego en una ventana.
        ImageIcon icon = new ImageIcon("C:/Users/genae/Pictures/LogoW.png");  // Carga una imagen para el icono.
        Image img = icon.getImage();  // Obtiene la imagen del icono.
        Image scaledImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);  // Redimensiona la imagen.
        icon = new ImageIcon(scaledImg);  // Crea un nuevo ImageIcon con la imagen redimensionada.

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();  // Obtiene la conexión a la base de datos.
            String query = "SELECT victories, defeats FROM users WHERE username = ?";  // Consulta para obtener las victorias y derrotas del usuario.
            PreparedStatement ps = conn.prepareStatement(query);  // Prepara la consulta SQL.

            ps.setString(1, loggedUser);  // Establece el nombre de usuario en la consulta.
            ResultSet rs = ps.executeQuery();  // Ejecuta la consulta y obtiene el resultado.

            if (rs.next()) {  // Si se encontró un resultado en la base de datos.
                int victories = rs.getInt("victories");  // Obtiene el número de victorias.
                int defeats = rs.getInt("defeats");  // Obtiene el número de derrotas.

                JPanel panel = new JPanel(new BorderLayout());  // Crea un panel para mostrar los resultados.
                panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Añade un borde vacío alrededor del panel.

                JLabel messageLabel = new JLabel("<html>"
                        + "Usuario: <strong>" + loggedUser + "</strong><br>"
                        + "Victorias: " + victories + "<br>"
                        + "Derrotas: " + defeats
                        + "</html>");  // Crea una etiqueta con el mensaje de los resultados.
                messageLabel.setIcon(icon);  // Establece el icono de la etiqueta.
                messageLabel.setHorizontalTextPosition(SwingConstants.RIGHT);  // Establece la posición horizontal del texto en la etiqueta.
                messageLabel.setVerticalTextPosition(SwingConstants.TOP);  // Establece la posición vertical del texto en la etiqueta.

                JButton restartButton = new JButton("Reiniciar juego");  // Crea un botón para reiniciar el juego.
                JButton closeButton = new JButton("Cerrar juego");  // Crea un botón para cerrar el juego.

                restartButton.addActionListener(e -> {  // Añade un ActionListener para reiniciar el juego cuando se haga clic en el botón.
                    dispose();  // Cierra la ventana actual.
                    new WordleManager(loggedUser);  // Crea una nueva instancia de WordleManager para reiniciar el juego.
                });

                closeButton.addActionListener(e -> {  // Añade un ActionListener para cerrar el juego cuando se haga clic en el botón.
                    System.exit(0);  // Finaliza la aplicación.
                });

                JPanel buttonPanel = new JPanel();  // Crea un panel para los botones.
                buttonPanel.add(restartButton);  // Añade el botón de reiniciar al panel.
                buttonPanel.add(closeButton);  // Añade el botón de cerrar al panel.

                panel.add(messageLabel, BorderLayout.CENTER);  // Añade la etiqueta de resultados al centro del panel.
                panel.add(buttonPanel, BorderLayout.SOUTH);  // Añade los botones al sur del panel.

                JOptionPane.showMessageDialog(this, panel, "Historial de Resultados", JOptionPane.PLAIN_MESSAGE);  // Muestra los resultados en una ventana emergente.
            }

            dispose();  // Cierra la ventana actual después de mostrar los resultados.
        } catch (SQLException e) {  // Si ocurre un error de SQL.
            throw new RuntimeException(e);  // Lanza una excepción de tiempo de ejecución.
        }
    }
}
