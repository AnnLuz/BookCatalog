package com.aluracursos.BookCatalog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.util.List;

public class ConvierteDatos  implements IConvierteDatos{

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> obtenerLista(String json, Class<T> clase) {
        CollectionType lista = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, clase);

        try {
            return objectMapper.readValue(json, lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public String extraerObjetoJson(String json, String objeto){
        try {
            JsonNode jsonCompleto = objectMapper.readTree(json);
            JsonNode jsonLibro = jsonCompleto.path("resultado");
            return jsonLibro.toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
