package com.wordle.dao;

import com.wordle.model.Login;

import java.sql.SQLException;
import java.util.List;

public interface LoginFormDAO {

    // Crea un nuevo registro de usuario
    public void create(Login login) throws SQLException;

    // Devuelve una lista con todos los usuarios registrados
    public List<Login> readAll() throws SQLException;

}
