package com.backend.parcial.dao.impl;

import com.backend.parcial.dao.H2Connection;
import com.backend.parcial.dao.IDao;
import org.apache.log4j.Logger;

import java.sql.*;

public class DaoH2 implements IDao<> {

    private final Logger LOGGER = Logger.getLogger(DaoH2.class);


    //trycatch de la interfaz a implementar

   Connection connection = null;

   try {
       connection = H2Connection.getConnection();
       connection.setAutoCommit(false);

       PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ?");
       preparedStatement.setString(1, "TABLE");
       preparedStatement.execute();


       connection.commit();

       Logger.info("query mostrado");

    } catch (Exception e){

        LOGGER.error(e.getMessage());
        e.printStackTrace();
        if (connection != null) {
            try {
                connection.rollback();
                LOGGER.info("Tuvimos un problema");
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            } catch (SQLException exception) {
                LOGGER.error(exception.getMessage());
                exception.printStackTrace();
            }
        }
    } finally {
        try {
            connection.close();
        } catch (Exception ex) {
            LOGGER.error("No se pudo cerrar la conexion: " + ex.getMessage());
        }
    }


}
