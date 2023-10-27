package com.backend.parcial.dao.impl;

import com.backend.parcial.dao.H2Connection;
import com.backend.parcial.dao.IDao;
import com.backend.parcial.model.Odontologo;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {

    private final Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);


    @Override
    public Odontologo registrar(Odontologo odontologo) {

        Connection connection = null;
        Odontologo odontologoRegistrado = null;

        try {

            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);


            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ODONTOLOGOS (NUMERO_MATRICULA, NOMBRE, APELLIDO) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, odontologo.getNumero_matricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            odontologoRegistrado = new Odontologo(odontologo.getNumero_matricula(), odontologo.getNombre(), odontologo.getApellido());

            while (resultSet.next()) {
                odontologoRegistrado.setId(resultSet.getInt("id"));
            }

            connection.commit();
            LOGGER.info("Se registro un odontologo con los siguientes datos: " + odontologoRegistrado);


        } catch (Exception e) {
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


        return odontologoRegistrado;
    }


    @Override
    public List<Odontologo> listarTodos() {

        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();

        try {

            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ODONTOLOGOS");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Odontologo odontologo = crearObjetoOdontologo(resultSet);
                odontologos.add(odontologo);

            }
            LOGGER.info("Listado de todos los odontologos: " + odontologos);



        }catch (Exception e) {
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






        return odontologos;
    }


    private Odontologo crearObjetoOdontologo (ResultSet resultSet) throws SQLException{

        return new Odontologo(resultSet.getInt("id"), resultSet.getInt("numero_matricula"), resultSet.getString("nombre"), resultSet.getString("apellido"));

    }
}
