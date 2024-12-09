package com.wordle.model;

import java.io.Serializable;

public class Login implements Serializable {
    private int id;              // Identificador del usuario
    private String username;     // Nombre de usuario
    private String password;     // Contraseña del usuario

    // Constructor sin parámetros
    public Login() {
    }

    // Constructor con parámetros
    public Login(int id, String username, String password) {
        this.id = id;
        this.password = password;
        this.username = username;
    }

    // Getter para el id
    public int getId() {
        return id;
    }

    // Setter para el id
    public void setId(int id) {
        this.id = id;
    }

    // Getter para la password
    public String getPassword() {
        return password;
    }

    // Setter para la password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter para el username
    public String getUsername() {
        return username;
    }

    // Setter para el username
    public void setUsername(String username) {
        this.username = username;
    }
}