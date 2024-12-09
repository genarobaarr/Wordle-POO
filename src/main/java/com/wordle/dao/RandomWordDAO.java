package com.wordle.dao;

import java.sql.SQLException;

public interface RandomWordDAO {

    // Mét0do para obtener una palabra aleatoria de la base de datos.
    public String getRandomWord() throws SQLException;
}
