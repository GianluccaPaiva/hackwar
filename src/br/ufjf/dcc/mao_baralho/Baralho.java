package br.ufjf.dcc.mao_baralho;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Baralho {
    private List<Carta> cartas;

    public Baralho(){
        cartas = new ArrayList<>();
        inicializarCarta();
    }

    private String tipoArquivo(String caminhoCSV){
        if(caminhoCSV.contains("ataque")){
            return "Ataque";
        } else if (caminhoCSV.contains("defesa")){
            return "Defesa";
        } else if (caminhoCSV.contains("suporte")){
            return "Suporte";
        } else {
            return "Desconhecido";
        }

    }
    public void carregarCarta(String caminhoCSV){
        try(BufferedReader br = new BufferedReader(new FileReader(caminhoCSV))) {
            String linha, tipoArquivo = tipoArquivo(caminhoCSV);
            boolean primeiraLinha = true;
            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                String[] valores = linha.split(",");
                String nome, tipo, efeito, descricao;
                int custo;
                double poder;
                if (tipoArquivo.equals("Suporte")) {
                    if (valores.length < 6) continue;
                    nome = valores[0].trim();
                    tipo = valores[1].trim();
                    poder = Double.parseDouble(valores[2].trim());
                    custo = Integer.parseInt(valores[3].trim());
                    efeito = valores[4].trim();
                    descricao = valores[5].trim();

                } else {
                    if (valores.length < 5) continue;
                    nome = valores[0].trim();
                    tipo = valores[1].trim();
                    poder = Double.parseDouble(valores[2].trim());
                    custo = Integer.parseInt(valores[3].trim());
                    descricao = valores[4].trim();
                    efeito = "Não tem efeito";
                }
                Carta carta = new Carta(nome, tipo, descricao, poder, custo, efeito);
                cartas.add(carta);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    private void inicializarCarta(){
        carregarCarta("src/br/ufjf/dcc/ArquivoCarta/ataque.csv");
        carregarCarta("src/br/ufjf/dcc/ArquivoCarta/defesa.csv");
        carregarCarta("src/br/ufjf/dcc/ArquivoCarta/suporte.csv");
    }
}