package com.backend.parcial.dao.impl;

import com.backend.parcial.dao.IDao;
import com.backend.parcial.model.Odontologo;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoMemoria implements IDao<Odontologo> {

    private final Logger LOGGER = Logger.getLogger(OdontologoDaoMemoria.class);
    private List<Odontologo> listaOdontologos = new ArrayList<>();

    public OdontologoDaoMemoria(List<Odontologo> listaOdontologos) {
        this.listaOdontologos = listaOdontologos;
    }

    @Override
    public Odontologo registrar(Odontologo odontologo) {
        int id = listaOdontologos.size() + 1;
        listaOdontologos.add(odontologo);
        Odontologo odontologoGuardado = new Odontologo(id, odontologo.getNumero_matricula(), odontologo.getNombre(), odontologo.getApellido());
        LOGGER.info("Odontologo guardado: " + odontologoGuardado);

        return odontologoGuardado;
    }

    @Override
    public List<Odontologo> listarTodos() {
        LOGGER.info("Lista de odontologos: " + listaOdontologos);
        return listaOdontologos;
    }
}
