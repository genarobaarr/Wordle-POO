package com.wordle.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import com.wordle.dao.LoginFormDAO;
import com.wordle.model.Login;

public class RegisterForm extends JFrame {
    private JTextField usernameField;               // Campo para el nombre de usuario
    private JPasswordField passwordField;           // Compo para la contraseña
    private JLabel messageLabel;                    //Etiqueta para mensajes al usuario.

    private final LoginFormDAO loginFormDAO;        // DAO para interactuar con los datos de usuario.

    public RegisterForm(LoginFormDAO dao) {
        this.loginFormDAO = dao;                // Inicializa el DAO con el pasado como argumento.

        setTitle("Registro de Usuario");         // Título de la ventana de registro.
        setSize(300, 200);          // Tamaño de la ventana.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);      // Cierra solo esta ventana al cerrar.
        setLocationRelativeTo(null);             // Centra la ventana en la pantalla.

        initComponents();                       // Inicializa los componentes.
    }

    private void initComponents() {
        JPanel panel = new JPanel();                 // Crea un panel principal.
        panel.setLayout(new GridLayout(4, 1, 10, 10));      // Layout de 4 filas y 1 columna.

        usernameField = new JTextField();                           // Campo de texto para el nombre de usuario.
        usernameField.setName("usernameField");                     //Establece nombre al campo username
        passwordField = new JPasswordField();                       // Campo de texto para la contraseña.
        passwordField.setName("passwordField");                     //Establece nombre al campo password
        messageLabel = new JLabel("", SwingConstants.CENTER);           // Etiqueta para mensajes de error.
        messageLabel.setName("messageLabel");           //Establece nombre a la etiqueta mensaje
        messageLabel.setForeground(Color.RED);          // Texto en color rojo.

        JButton registerButton = new JButton("Registrar");      // Botón para registrar al usuario.
        registerButton.setName("registerButton");                      //Establece nombre al botón registrar
        registerButton.addActionListener(e -> registerUser());           // Agrega el evento para registrar.

        panel.add(new JLabel("Usuario:", SwingConstants.CENTER));           // Etiqueta de usuario.
        panel.add(usernameField);                                               // Agrega el campo de usuario.
        panel.add(new JLabel("Contraseña:", SwingConstants.CENTER));        // Etiqueta de contraseña.
        panel.add(passwordField);                                               // Agrega el campo de contraseña.

        add(panel, BorderLayout.CENTER);                        // Agrega el panel principal.
        add(registerButton, BorderLayout.SOUTH);                // Agrega el botón en la parte inferior.
        add(messageLabel, BorderLayout.NORTH);                     // Agrega la etiqueta de mensajes arriba.
    }

    private void registerUser() {
        String username = usernameField.getText();                           // Obtiene el nombre de usuario.
        String password = new String(passwordField.getPassword());              // Obtiene la contraseña.

        try {
            if (username.isEmpty() || password.isEmpty()) {                 // Verifica si los campos están vacíos.
                messageLabel.setText("Todos los campos son obligatorios.");
                return;
            }

            loginFormDAO.create(new Login(1, username, password));          // Crea un nuevo usuario en la base de datos.
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.");
            dispose();                          // Cierra la ventana de registro.

        } catch (SQLException e) {
            messageLabel.setText("Error al registrar el usuario.");                 // Mensaje de error si hay problemas.
        }
    }
}
