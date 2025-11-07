package br.ufjf.dcc.mao_baralho;

public class Carta {
    String nome, tipo, descricao;
    int poder, custo;

    public Carta(String nome, String tipo, String descricao, int poder, int custo) {
        this.nome = nome;
        this.tipo = tipo;
        this.descricao = descricao;
        this.poder = poder;
        this.custo = custo;

    }

    public void imprimirCarta() {
        System.out.println("Nome: " + this.nome);
        System.out.println("Tipo: " + this.tipo);
        System.out.println("Poder: " + this.poder);
        System.out.println("Custo: " + this.custo);
        System.out.println("Descricao: " + this.descricao);
    }
}
