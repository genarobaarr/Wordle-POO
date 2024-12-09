package com.wordle.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import com.wordle.dao.LoginFormDAO;
import com.wordle.dao.LoginFormDAOImp;
import com.wordle.model.Login;

public class LoginForm extends JFrame {

    protected JTextField usernameField;           // Campo para ingresar el nombre de usuario.
    protected JPasswordField passwordField;       // Campo para ingresar la contraseña.
    protected JLabel messageLabel;                // Etiqueta para mostrar mensajes al usuario.

    LoginFormDAO loginFormDAO = new LoginFormDAOImp(); // DAO para interactuar con los datos de usuario.

    public LoginForm() {
        setTitle("Inicio de Sesión");           // Configura el título de la ventana.
        setSize(300, 200);                      // Configura el tamaño de la ventana.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al cerrar la ventana.
        setLocationRelativeTo(null);            // Centra la ventana en la pantalla.

        initComponents();                       // Inicializa los componentes de la interfaz.
    }

    private void initComponents () {
        JPanel panel = new JPanel();                    // Crea un panel principal.
        panel.setLayout(new GridLayout(4, 1, 10, 10));  // Layout con 4 filas y 1 columna, con espacio entre componentes.

        usernameField = new JTextField();               // Campo para el nombre de usuario.
        usernameField.setName("usernameField");         // Establece nombre al campo username
        passwordField = new JPasswordField();           // Campo para la contraseña.
        passwordField.setName("passwordField");         // Establece nombre al campo password
        messageLabel = new JLabel("", SwingConstants.CENTER); // Etiqueta para mostrar mensajes de error.
        messageLabel.setForeground(Color.RED);          // Configura el color de texto a rojo.
        messageLabel.setName("messageLabel");           // Establece nombre a la etiqueta mensaje

        JButton loginButton = new JButton("Iniciar Sesión"); // Botón para iniciar sesión.
        loginButton.addActionListener(new LoginAction());    // Asigna un ActionListener para manejar el login.

        JButton registerButton = new JButton("Registrarse"); // Botón para registrarse.
        registerButton.addActionListener(e -> {              // Abre el formulario de registro al hacer clic.
            new RegisterForm(loginFormDAO).setVisible(true);
        });

        panel.add(new JLabel("Usuario:", SwingConstants.CENTER)); // Etiqueta para el campo de usuario.
        panel.add(usernameField);                                 // Agrega el campo de usuario.
        panel.add(new JLabel("Contraseña:", SwingConstants.CENTER)); // Etiqueta para el campo de contraseña.
        panel.add(passwordField);                                 // Agrega el campo de contraseña.

        JPanel buttonPanel = new JPanel();                        // Panel para los botones.
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));      // Layout con 1 fila y 2 columnas.
        buttonPanel.add(loginButton);                             // Agrega el botón de login.
        buttonPanel.add(registerButton);                          // Agrega el botón de registro.

        add(panel, BorderLayout.CENTER);                          // Agrega el panel principal al centro.
        add(buttonPanel, BorderLayout.SOUTH);                     // Agrega el panel de botones en la parte inferior.
        add(messageLabel, BorderLayout.NORTH);                    // Agrega la etiqueta de mensajes en la parte superior.
    }

    protected class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();                    // Obtiene el texto del campo de usuario.
            String password = new String(passwordField.getPassword());    // Obtiene el texto del campo de contraseña.

            if (authenticate(username, password)) {                       // Verifica si la autenticación es correcta.
                messageLabel.setText("Inicio de sesión exitoso!");        // Muestra mensaje de éxito.
                JOptionPane.showMessageDialog(LoginForm.this, "Bienvenido " + username + "!");
                dispose();                                                // Cierra la ventana de login.
                new WordleManager(username);                              // Abre la ventana del juego Wordle.
            } else {
                messageLabel.setText("Usuario o contraseña incorrectos..."); // Muestra mensaje de error.
            }
        }

        protected boolean authenticate(String username, String password) {
            try {
                List<Login> users = loginFormDAO.readAll();    // Lee todos los usuarios desde la base de datos.

                for (Login user : users) {                     // Itera sobre cada usuario.
                    if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                        return true;                           // Devuelve true si encuentra una coincidencia.
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);                 // Lanza una excepción si hay un error SQL.
            }
            return false;                                      // Devuelve false si no se encontró coincidencia.
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();     // Crea una instancia del formulario de login.
            loginForm.setVisible(true);                // Muestra el formulario.
        });
    }
}
