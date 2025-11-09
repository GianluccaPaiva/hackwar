
package br.ufjf.dcc.Main;
import br.ufjf.dcc.Menu.Menu;
import br.ufjf.dcc.Usuarios.Bot;
import br.ufjf.dcc.Usuarios.Hacker;
import br.ufjf.dcc.mao_baralho.Carta;
import br.ufjf.dcc.mao_baralho.Mao;
import br.ufjf.dcc.CalculosDoCombate.Calcular;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int opcao = Menu.Menu();
        if (opcao == 1) {
            System.out.println("Jogador 1:");
            System.out.print("Digite seu nome de Hacker: ");
            String nome1 = teclado.nextLine();
            System.out.print("Digite seu id: ");
            String id1 = teclado.nextLine();
            System.out.println("Deseja selecionar suas cartas manualmente? (sim/nao): ");
            String selecao1 = teclado.nextLine();
            Hacker hacker1 = new Hacker(nome1, id1, selecao1);

            System.out.println("Jogador 2:");
            System.out.print("Digite seu nome de Hacker: ");
            String nome2 = teclado.nextLine();
            System.out.print("Digite seu id: ");
            String id2 = teclado.nextLine();
            System.out.println("Deseja selecionar suas cartas manualmente? (sim/nao): ");
            String selecao2 = teclado.nextLine();
            Hacker hacker2 = new Hacker(nome2, id2, selecao2);

            List<Carta> deckHacker1Copia = new ArrayList<>();
            List<Carta> deckHacker2Copia = new ArrayList<>();
            Mao deckHacker1 = hacker1.getMao();
            Mao deckHacker2 = hacker2.getMao();

            for(Carta carta : deckHacker1.getMao()){
                deckHacker1Copia.add(carta);
            }
            for(Carta carta : deckHacker2.getMao()){
                deckHacker2Copia.add(carta);
            }

            System.out.println("Decks montados. A batalha começa!");

            Random quemComeca = new Random();
            int primeiro = 0;

            while (true) {
                if (primeiro == 0) {
                    System.out.println("\n========= NOVA RODADA ==========");
                    hacker1.exibirStatus();
                    hacker2.exibirStatus();

                    List<Carta> jogadaHacker1 = hacker1.escolherJogada();
                    if (hacker1.getVida() <= 0) {
                        System.out.println("\n--- FIM DE JOGO ---");
                        System.out.println(hacker2.getNome() + " venceu!");
                        break;
                    }

                    List<Carta> jogadaHacker2 = hacker2.escolherJogada();
                    if (hacker2.getVida() <= 0) {
                        System.out.println("\n--- FIM DE JOGO ---");
                        System.out.println(hacker2.getNome() + " venceu!");
                        break;
                    }

                    Calcular.ResultadoTurno resultado = Calcular.calcularResultado(
                            hacker1.getVida(), hacker1.getEnergia(), jogadaHacker1,
                            hacker2.getVida(), hacker2.getEnergia(), jogadaHacker2
                    );

                    hacker1.setVida(resultado.vidaFinalJ1());
                    hacker1.setEnergia(resultado.energiaFinalJ1());
                    hacker2.setVida(resultado.vidaFinalJ2());
                    hacker2.setEnergia(resultado.energiaFinalJ2());

                    System.out.println("--- Consolidação da Rodada ---");
                    System.out.println(hacker1.getNome() + " termina com " + resultado.vidaFinalJ1() + " de vida.");
                    System.out.println(hacker2.getNome() + " termina com " + resultado.vidaFinalJ2() + " de vida.");

                    if (hacker1.getVida() <= 0) {
                        System.out.println("\n--- FIM DE JOGO ---");
                        System.out.println(hacker2.getNome() + " venceu!");
                        break;
                    } else if (hacker2.getVida() <= 0) {
                        System.out.println("\n--- FIM DE JOGO ---");
                        System.out.println(hacker1.getNome() + " venceu!");
                        break;
                    }

                    if(hacker1.getMaoSize()== 0){
                        hacker1.setMao(deckHacker1Copia);
                    }
                    else if(hacker2.getMaoSize()== 0) {
                        hacker2.setMao(deckHacker2Copia);
                    }
                }
            }

        } else if (opcao == 2) {

        } else {
            return;
        }

    }
}