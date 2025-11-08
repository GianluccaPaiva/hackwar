package br.ufjf.dcc.mao_baralho;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Mao {
    private List<Carta> mao;
    Baralho baralho;
    private static int LIMITE_CARTAS = 10;
    private Random rand = new Random();
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
        List<Carta> listaDeOrigem;

        if (tipo.equalsIgnoreCase("ataque")) {
            limite = 4;
            listaDeOrigem = this.ataques;
        } else if (tipo.equalsIgnoreCase("defesa")) {
            limite = 4;
            listaDeOrigem = this.defesas;
        } else {
            limite = 2;
            listaDeOrigem = this.suportes;
        }

        int cartasAdicionadas = 0;

        if (indices.length > 0 && !indices[0].isEmpty()) {
            for (String indiceStr : indices) {
                if (cartasAdicionadas >= limite) {
                    break;
                }

                try {
                    int idxs = Integer.parseInt(indiceStr) - 1;

                    if (idxs >= 0 && idxs < listaDeOrigem.size()) {
                        this.mao.add(listaDeOrigem.get(idxs));
                        cartasAdicionadas++;
                    } else {
                        System.out.println("Aviso: Indice " + (idxs + 1) + " e invalido e sera ignorado.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Aviso: Entrada '" + indiceStr + "' nao e um numero e sera ignorada.");
                }
            }
        }
        if (cartasAdicionadas < limite) {
            int cartasFaltando = limite - cartasAdicionadas;
            System.out.println("Voce escolheu " + cartasAdicionadas + " cartas. Adicionando " + cartasFaltando +
                    " cartas aleatorias de " + tipo + "...");

            for (int i = 0; i < cartasFaltando; i++) {
                int indiceAleatorio = rand.nextInt(listaDeOrigem.size());
                this.mao.add(listaDeOrigem.get(indiceAleatorio));
            }
        }
    }

    public void escolherCartas(){
        Scanner teclado = new Scanner(System.in);
        int cont = 0, id = 1;
        while(cont<LIMITE_CARTAS){
            if(cont<3){
                System.out.println("CARTAS DE ATAQUE:");
                System.out.println("-----------------------------------------------------------------------------------"
                        + "-----------------------------------------------------------");
                for(Carta carta: this.ataques){
                    System.out.print("ID: " + id + " - ");
                    carta.imprimirCarta();
                    id++;
                }
                System.out.println("Insira a cartas de ataque desejadas(indices e com espaçamento): ");
                String indice = teclado.nextLine();
                String[] listaIdx = indice.split(" ");
                adicionarCarta("ataque", listaIdx);
                id = 1;
                cont = 3;
            } else if (cont>=3 && cont<7) {
                System.out.println("CARTAS DE DEFESA:");
                System.out.println("-----------------------------------------------------------------------------------"
                        + "-----------------------------------------------------------");
                for(Carta carta: this.defesas){
                    System.out.print("ID: " + id + " - ");
                    carta.imprimirCarta();
                    id++;
                }
                System.out.println("Insira a cartas de defesa desejadas(indices e com espaçamento):");
                String indice = teclado.nextLine();
                String[] listaIdx = indice.split(" ");
                adicionarCarta("defesa", listaIdx);
                id = 1;
                cont = 7;
            }
            else{
                System.out.println("CARTAS DE SUPORTE:");
                System.out.println("-----------------------------------------------------------------------------------"
                        + "-----------------------------------------------------------");
                for(Carta carta: this.suportes){
                    System.out.print("ID: " + id + " - ");
                    carta.imprimirCarta();
                    id++;
                }
                System.out.println("Insira a cartas de suporte desejadas(indices e com espaçamento):");
                String indice = teclado.nextLine();
                String[] listaIdx = indice.split(" ");
                adicionarCarta("suporte", listaIdx);
                id = 1;
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