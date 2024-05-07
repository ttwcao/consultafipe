package br.com.consulta.fipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//criação de uma classe com uma lista de modelos de carro
@JsonIgnoreProperties (ignoreUnknown = true)
public record Modelos(List<Dados> modelos) {
}
