package br.ufjf.dcc.mao_baralho;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Baralho {
    private List<Carta> cartas;

    public Baralho(){
        cartas = new ArrayList<>();
        inicializarCarta();
    }

    public void carrregarCarta(String caminhoCSV){
        try(BufferedReader br = new BufferedReader(new FileReader(caminhoCSV))){
            String linha;
            boolean primeiraLinha = true;
            while((linha = br.readLine()) != null ) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                String[] valores = linha.split(",");
                if (valores.length < 5) continue;
                String nome, tipo, descricao, poder, custo;
                nome = valores[0].trim();
                tipo = valores[1].trim();
                poder = valores[2].trim();
                custo = valores[3].trim();
                descricao = valores[4].trim();

                try {
                    int poderInt = Integer.parseInt(poder);
                    int custoInt = Integer.parseInt(custo);
                    Carta carta = new Carta(nome, tipo, descricao, poderInt, custoInt);
                    cartas.add(carta);
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Linha ignorada (valores inválidos): " + linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo " + caminhoCSV + ": " + e.getMessage());
        }
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    private void inicializarCarta(){
        carrregarCarta("src/br/ufjf/dcc/ArquivoCarta/ataque.csv");
        carrregarCarta("src/br/ufjf/dcc/ArquivoCarta/defesa.csv");
        carrregarCarta("src/br/ufjf/dcc/ArquivoCarta/suporte.csv");
    }
}