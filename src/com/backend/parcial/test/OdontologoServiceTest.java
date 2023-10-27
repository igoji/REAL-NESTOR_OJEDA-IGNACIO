package com.backend.parcial.test;

import com.backend.parcial.dao.impl.OdontologoDaoH2;
import com.backend.parcial.dao.impl.OdontologoDaoMemoria;
import com.backend.parcial.model.Odontologo;
import com.backend.parcial.service.OdontologoService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

    public class OdontologoServiceTest {

        private OdontologoService odontologoServiceMemoria = new OdontologoService(new OdontologoDaoMemoria(new ArrayList<>()));

        private OdontologoService getOdontologoServiceH2 = new OdontologoService(new OdontologoDaoH2());

        @BeforeAll
        static void doBefore() {
            Connection connection = null;
            try {
                Class.forName("org.h2.Driver");
                connection = DriverManager.getConnection("jdbc:h2:~/parcial;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        @Test
        public void deberiaListarTodosLosOdontologosH2() throws ClassNotFoundException, SQLException {

            assertFalse(getOdontologoServiceH2.listarOdontologos().isEmpty());

        }


        @Test
        public void deberiaRegistrarUnOdontologoEnMemoria() {

            Odontologo odontologo = new Odontologo(5, "Samuel", "Velasco");

            Odontologo odontologoRegistrado = odontologoServiceMemoria.registrarOdontologo(odontologo);

            assertTrue(odontologoRegistrado.getId() != 0);


        }

        @Test
        public void deberiaListarLosOdontologosEnMemoria() {

            Odontologo odontologo1 = new Odontologo(6, "Bastian", "Alvarado");
            Odontologo odontologo2 = new Odontologo(7, "Eduardo", "Troncoso");

            odontologoServiceMemoria.registrarOdontologo(odontologo1);
            odontologoServiceMemoria.registrarOdontologo(odontologo2);


            List<Odontologo> listaOdontologos = odontologoServiceMemoria.listarOdontologos();

            assertFalse(listaOdontologos.isEmpty());

        }



    }
