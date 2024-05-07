package br.com.consulta.fipe.principal;
import br.com.consulta.fipe.model.Dados;
import br.com.consulta.fipe.model.Modelos;
import br.com.consulta.fipe.model.Veiculo;
import br.com.consulta.fipe.service.ConsumoApi;
import br.com.consulta.fipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados converte = new ConverteDados();

    public void exibeMenu(){
        var menu = "" +
                "\n ========== **** OPÇÕES **** ========== \n" +
                "Carro, moto ou caminhão \n" +
                "\n Digite uma das opções para consultar:";

        System.out.println(menu);
        var opcao = leitura.nextLine();

        //lógica para montar o endereço de consulta na API conforme usuário
        String endereco;
        if(opcao.toLowerCase().contains("carr")){
            endereco = URL_BASE + "carros/marcas";
        }else if (opcao.toLowerCase().contains("mot")){
            endereco = URL_BASE + "motos/marcas";
        } else {
            endereco = URL_BASE + "caminhoes/marcas";
        }

        //variável que vai fazer o consumo na API e obter a resposta
        var json = consumo.obterDados(endereco);


        var marcas = converte.obterLista(json, Dados.class);
        System.out.println(json);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Digite o código da marca desejada: ");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modeloLista = converte.obterDados(json, Modelos.class);

        System.out.println("Modelos dessa marca: \n");

        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\n Digite um trecho do nome do carro para pesqquisa: ");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m-> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\n Modelos filtrados: ");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o código do modelo para buscar os valores de avaliação: ");
        var codigoModelo = leitura.nextLine();

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = converte.obterLista(json, Dados.class);

        List<Veiculo> veiculos = new ArrayList<>();
        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = converte.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\n Todos os veículos encontrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);

    }
}
