package br.ufjf.dcc.Usuarios;
import br.ufjf.dcc.mao_baralho.Mao;

public class Hacker {
    private String nome, id;
    private int vida, energia;
    private Mao mao;

    public Hacker(String nome, String id) {
        this.nome = nome;
        this.id = id;
        this.vida = 100;
        this.energia = 10;
        this.mao = new Mao();
        this.mao.escolherCartas();
    }

    public void exibirStatus() {
        System.out.println("Hacker: " + this.nome + " (ID: " + this.id + ")");
        System.out.println("Vida: " + this.vida + " " + "Energia: " + this.energia);
        this.mao.exibirMao();
    }
}
