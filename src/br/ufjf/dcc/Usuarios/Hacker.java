package br.ufjf.dcc.Usuarios;

public class Hacker {
    private String nome, id;
    private int vida, energia;

    public Hacker(String nome, String id) {
        this.nome = nome;
        this.id = id;
        this.vida = 100;
        this.energia = 10;
    }

    public void exibirStatus() {
        System.out.println("Hacker: " + this.nome + " (ID: " + this.id + ")");
        System.out.println("Vida: " + this.vida);
        System.out.println("Energia: " + this.energia);
    }
}
