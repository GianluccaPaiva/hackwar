package br.ufjf.dcc.mao_baralho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mao {
    private List<Carta> mao;
    private static int LIMITE_CARTAS = 10;

    public Mao(Baralho baralho) {
        this.mao = new ArrayList<>();
        Random random = new Random();

        List<Carta> ataques = new ArrayList<>();
        List<Carta> defesas = new ArrayList<>();
        List<Carta> suportes = new ArrayList<>();

        for (Carta carta : baralho.getCartas()) {
            switch (carta.tipo.toLowerCase()) {
                case "ataque" -> ataques.add(carta);
                case "defesa" -> defesas.add(carta);
                case "suporte" -> suportes.add(carta);
            }
        }

        Collections.shuffle(ataques, random);
        Collections.shuffle(defesas, random);
        Collections.shuffle(suportes, random);

        adicionarCartas(ataques, 4);
        adicionarCartas(defesas, 4);
        adicionarCartas(suportes, 2);

        if (this.mao.size() > LIMITE_CARTAS) {
            this.mao = this.mao.subList(0, LIMITE_CARTAS);
        }
    }

    private void adicionarCartas(List<Carta> origem, int quantidade) {
        int limite = Math.min(quantidade, origem.size());
        for (int i = 0; i < limite; i++) {
            if (this.mao.size() < LIMITE_CARTAS) {
                this.mao.add(origem.get(i));
            }
        }
    }

    public void exibirMao() {
        System.out.println("Cartas da Mão (" + this.mao.size() + "/" + LIMITE_CARTAS + "):");
        for (Carta carta : this.mao) {
            System.out.println(" - " + carta.nome + " [" + carta.tipo + "]");
        }
    }

    public void exibirCartaDetalhada(int indice) {
        if (indice < 0 || indice >= this.mao.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        Carta carta = this.mao.get(indice);
        carta.imprimirCarta();
    }
}