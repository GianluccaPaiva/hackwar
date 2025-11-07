package br.ufjf.dcc.mao_baralho;

public class Carta {
    private String nome, tipo, descricao;
    private int poder, custo;

    public Carta(String nome, String tipo, String descricao, String poder, String custo) {
        this.nome = nome;
        this.tipo = tipo;
        this.descricao = descricao;
        this.poder = Integer.parseInt(poder);
        this.custo = Integer.parseInt(custo);

    }
    public String getNome() {
        return this.nome;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public int getPoder() {
        return this.poder;
    }

    public int getCusto() {
        return this.custo;
    }

    public void imprimirCarta() {
        System.out.println("Nome: " + this.nome);
        System.out.println("Tipo: " + this.tipo);
        System.out.println("Poder: " + this.poder);
        System.out.println("Custo: " + this.custo);
        System.out.println("Descricao: " + this.descricao);
    }
}
