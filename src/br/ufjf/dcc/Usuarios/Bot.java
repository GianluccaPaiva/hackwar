package br.ufjf.dcc.Usuarios;
import br.ufjf.dcc.mao_baralho.Mao;

public class Bot {
    private String nome, id;
    private int vida, energia;
    private Mao mao;

    public Bot() {
        this.nome = "Bot";
        this.id = "202565001";
        this.vida = 100;
        this.energia = 10;
        this.mao = new Mao();
        this.mao.selecaoAutomatica();
    }

    public void exibirStaus() {
        System.out.println("Hacker: " + this.nome + " (ID: " + this.id + ")");
        System.out.println("Vida: " + this.vida + " " + "Energia: " + this.energia);
        this.mao.exibirMao();
    }
}
