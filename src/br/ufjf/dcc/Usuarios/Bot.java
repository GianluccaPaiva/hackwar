package br.ufjf.dcc.Usuarios;
import br.ufjf.dcc.mao_baralho.Mao;

public class Bot {
    private String nome, id;
    private static int MAX_ENERGIA = 10, MAX_VIDA = 100;
    private int vida, energia;
    private Mao mao;

    public Bot() {
        this.nome = "Bot";
        this.id = "202565001";
        this.vida = MAX_VIDA;
        this.energia = MAX_ENERGIA;
        this.mao = new Mao();
        this.mao.selecaoAutomatica();
    }

    public void exibirStaus() {
        System.out.println("Hacker: " + this.nome + " (ID: " + this.id + ")");
        System.out.println("Vida: " + this.vida + "/" + MAX_VIDA + "  " + "Energia: " + this.energia + "/" + MAX_ENERGIA);
        this.mao.exibirMao();
    }

    public int getVida() {
        return vida;
    }
    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getEnergia() {
        return energia;
    }
    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public Mao getMao() {
        return mao;
    }
    public void setMao(Mao mao) {
        this.mao = mao;
    }
}
