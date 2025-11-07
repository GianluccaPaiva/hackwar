package br.ufjf.dcc.mao_baralho;

public class Carta {
    String nome, tipo, descricao, efeito;
    double poder;
    int custo;

    public Carta(String nome, String tipo, String descricao, double poder, int custo, String efeito) {
        this.nome = nome;
        this.tipo = tipo;
        this.descricao = descricao;
        this.poder = poder;
        this.custo = custo;
        this.efeito = efeito;
    }

    public void imprimirCarta() {
        System.out.println("Nome: " + this.nome);
        System.out.println("Tipo: " + this.tipo);
        System.out.println("Poder: " + this.poder);
        System.out.println("Custo: " + this.custo);
        if(!this.efeito.equals( "Não tem efeito") ) {
            System.out.println("Efeito: " + this.efeito);
        }
        System.out.println("Descricao: " + this.descricao);
    }

    public String getTipo() {
        return this.tipo;
    }

    public double getPoder() {
        return this.poder;
    }

    public int getCusto() {
        return this.custo;
    }
}
