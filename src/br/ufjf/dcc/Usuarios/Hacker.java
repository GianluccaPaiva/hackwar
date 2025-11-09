package br.ufjf.dcc.Usuarios;
import br.ufjf.dcc.mao_baralho.Carta;
import br.ufjf.dcc.mao_baralho.Mao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hacker {
    private String nome, id;
    private static int MAX_ENERGIA = 10, MAX_VIDA = 100;
    private int vida, energia;
    private Mao mao;

    public Hacker(String nome, String id, String tipoSelecao) {
        this.nome = nome;
        this.id = id;
        this.vida = MAX_VIDA;
        this.energia = MAX_ENERGIA;
        this.mao = new Mao();
        if(tipoSelecao.equalsIgnoreCase("sim")){
            this.mao.escolherCartas();
        }
        else{
            this.mao.selecaoAutomatica();
        }
    }

    public void exibirStatus() {
        System.out.println("Hacker: " + this.nome + " (ID: " + this.id + ")");
        System.out.println("Vida: " + this.vida + "/" + MAX_VIDA + "  " + "Energia: " + this.energia + "/" + MAX_ENERGIA);
        this.mao.exibirMao();
    }

    public List<Carta> escolherJogada() {
        Scanner scanner = new Scanner(System.in);
        List<Carta> cartasParaJogar = new ArrayList<>();
        int energiaDisponivel = this.getEnergia();
        int energiaGasta = 0;

        while (true) {
            if(energiaDisponivel == 0){
                System.out.println("Energia esgotada. Passou a vez!.");
                break;
            }
            System.out.println("\nEnergia Restante: " + (energiaDisponivel - energiaGasta)  + "/" + MAX_ENERGIA);
            this.mao.exibirMao();
            System.out.println("Caso queira entregar o sistema, digite -1.");
            System.out.print("Digite o ID da carta para jogar (ou 0 para 'Confirmar e Passar a Vez'): ");

            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
                continue;
            }

            if(id == -1){
                System.out.println(this.nome + " entregou o sistema.");
                this.vida = 0;
                break;
            }

            if (id == 0) {
                if (cartasParaJogar.isEmpty()) {
                    System.out.println(this.nome + " passou a vez.");
                } else {
                    System.out.println(this.nome + " confirmou a jogada.");
                }
                break;
            }

            Carta carta = this.mao.getCarta(id - 1);

            if (carta == null) {
                System.out.println("ID inválido. Tente novamente.");
            } else if (cartasParaJogar.contains(carta)) {
                System.out.println("Você já escolheu essa carta. Tente outra.");
            } else if ((energiaGasta + carta.getCusto()) > energiaDisponivel) {
                System.out.println("Energia insuficiente para jogar " + carta.getNome());
            } else {
                cartasParaJogar.add(carta);
                this.mao.removerCarta(carta);
                energiaGasta += carta.getCusto();
                System.out.println("-->> Adicionado: " + carta.getNome());
            }
        }

        return cartasParaJogar;
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

    public String getNome() {
        return nome;
    }
    public String getId() {
        return id;
    }
}
