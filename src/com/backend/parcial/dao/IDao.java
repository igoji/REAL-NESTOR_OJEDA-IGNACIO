package com.backend.parcial.dao;

public interface IDao<T> {

    T crear(T t);

    T buscarPorId(int id);

}
