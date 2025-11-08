package br.ufjf.dcc.mao_baralho;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mao {
    private List<Carta> mao;
    Baralho baralho;
    private static int LIMITE_CARTAS = 10;
    private List<Carta> ataques = new ArrayList<>();
    private List<Carta> defesas = new ArrayList<>();
    private List<Carta> suportes = new ArrayList<>();

    public Mao() {
        this.mao = new ArrayList<>(LIMITE_CARTAS);
        this.baralho = new Baralho();
        separaCartas();
    }

    private void separaCartas(){
        for(Carta carta : this.baralho.getCartas()) {
            switch (carta.tipo.toLowerCase()) {
                case "ataque" -> this.ataques.add(carta);
                case "defesa" -> this.defesas.add(carta);
                case "suporte" -> this.suportes.add(carta);
            }
        }
    }

    void adicionarCarta(String tipo, String[] indices) {
        int limite;
        if(tipo.equalsIgnoreCase("ataque") || tipo.equalsIgnoreCase("defesa")){
            limite = 4;
        }
        else{
            limite = 2;
        }
        for(String indice : indices){
            if(limite == 0){
                break;
            }
            int idxs = Integer.parseInt(indice);
            if(tipo.equalsIgnoreCase("ataque")){
                this.mao.add(this.ataques.get(idxs));
            }
            else if(tipo.equalsIgnoreCase("defesa")){
                this.mao.add(this.defesas.get(idxs));
            }
            else if(tipo.equalsIgnoreCase("suporte")){
                this.mao.add(this.suportes.get(idxs));
            }
            limite--;
        }
    }

    public void escolherCartas(){
        Scanner teclado = new Scanner(System.in);
        int cont = 0;
        while(cont<LIMITE_CARTAS){
            if(cont<3){
                System.out.println("Cartas de ATAQUE: ");
                for(Carta carta: this.ataques){
                    carta.imprimirCarta();
                }
                System.out.println("Insira a cartas de ataque desejadas(indices e com espaçamento): ");
                String indice = teclado.nextLine();
                String[] listaIdx = indice.split(" ");
                adicionarCarta("ataque", listaIdx);
                cont = 3;
            } else if (cont>=3 && cont<7) {
                System.out.println("Cartas de DEFESA: ");
                for(Carta carta: this.defesas){
                    carta.imprimirCarta();
                }
                System.out.println("Insira a cartas de defesa desejadas(indices e com espaçamento):");
                String indice = teclado.nextLine();
                String[] listaIdx = indice.split(" ");
                adicionarCarta("defesa", listaIdx);
                cont = 7;
            }
            else{
                System.out.println("Cartas de SUPORTE: ");
                for(Carta carta: this.suportes){
                    carta.imprimirCarta();
                }
                System.out.println("Insira a cartas de suporte desejadas(indices e com espaçamento):");
                String indice = teclado.nextLine();
                String[] listaIdx = indice.split(" ");
                adicionarCarta("suporte", listaIdx);
                cont = LIMITE_CARTAS;
            }
        }
    }

    public void exibirMao() {
        System.out.println("Cartas da Mão (" + this.mao.size() + "/" + LIMITE_CARTAS + "):");
        for (Carta carta : this.mao) {
            System.out.println(" - " + carta.nome + " [" + carta.tipo + "]");
        }
    }

}