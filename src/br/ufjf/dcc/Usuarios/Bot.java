package br.ufjf.dcc.Usuarios;

public class Bot {
    private String nome, id;

    public Bot() {
        this.nome = "Bot";
        this.id = "202565001";
    }

    public void exibirInfo() {
        System.out.println("Nome: " + this.nome);
        System.out.println("ID: " + this.id);
    }
}
