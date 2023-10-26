package com.backend.parcial;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

public class Application {
    private static final Logger LOGGER = Logger.getLogger(Application.class);
    public static void main(String[] args) {

        Connection connection = null;

        try{
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/parcial;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");

            LOGGER.info("Se ha creado la tabla en la base de datos parcial.");

        } catch (Exception e) {
            LOGGER.error("No se pudo crear la tabla: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception ex){
                LOGGER.error("No se pudo cerrar la conexion: " + ex.getMessage());
            }
        }
    }
}
