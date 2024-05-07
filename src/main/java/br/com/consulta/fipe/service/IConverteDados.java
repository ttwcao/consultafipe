package br.com.consulta.fipe.service;

import java.util.List;

public interface IConverteDados {
    //método para obter uma entidade, ou seja, representação de uma classe
    <T> T obterDados(String json, Class<T> classe);

    //novo metodo para criar uma lista
    <T> List <T> obterLista(String json, Class<T> classe);
}
