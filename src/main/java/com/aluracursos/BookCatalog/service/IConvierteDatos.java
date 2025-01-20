package com.aluracursos.BookCatalog.service;

public interface IConvierteDatos {

    <T> T obtenerDatos(String json, Class<T> clase);

}
