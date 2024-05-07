package br.com.consulta.fipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ConverteDados implements IConverteDados {

    //instanciar ObjectMapper
    private ObjectMapper mapper = new ObjectMapper();


    //conversão do json para a classe que for determinada com tratamento de exceção
    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try{
            return mapper.readValue(json, classe);
        }catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }

    }

    //aqui é implementado o método para a gerar uma lista de dados
    // com a classe que esta sendo apresentada utilizando CollectionType
    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) {
        CollectionType lista = mapper.getTypeFactory()
                .constructCollectionType(List.class, classe);
        try{
            return mapper.readValue(json, lista);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }


}
