
package br.ufjf.dcc.Main;
import br.ufjf.dcc.GerenciadoresDeJogo.GerenciadorMultiplayer;
import br.ufjf.dcc.GerenciadoresDeJogo.GerenciadorSinglePlayer;
import br.ufjf.dcc.Menu.Menu;
import br.ufjf.dcc.Usuarios.Bot;
import br.ufjf.dcc.Usuarios.Hacker;
import br.ufjf.dcc.MaoBaralho.Carta;
import br.ufjf.dcc.MaoBaralho.Mao;
import java.util.ArrayList;
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
            deckHacker1Copia.addAll(deckHacker1.getMao());
            deckHacker2Copia.addAll(deckHacker2.getMao());

            System.out.println("Decks montados. A batalha começa!");

            int i = 0;
            while (true) {
                if (i%2 == 0) {
                    int estado = GerenciadorMultiplayer.GerenciarJogoMultiplayer(hacker1, hacker2,
                            deckHacker1Copia, deckHacker2Copia);
                    if(estado == -1){
                        break;
                    }
                }
                else if(i%2 != 0){
                    int estado = GerenciadorMultiplayer.GerenciarJogoMultiplayer(hacker2, hacker1,
                            deckHacker2Copia, deckHacker1Copia);
                    if(estado == -1){
                        break;
                    }
                }
                i++;
            }

        } else if (opcao == 2) {
            System.out.println("Jogador:");
            System.out.print("Digite seu nome de Hacker: ");
            String nome1 = teclado.nextLine();
            System.out.print("Digite seu id: ");
            String id1 = teclado.nextLine();
            System.out.println("Deseja selecionar suas cartas manualmente? (sim/nao): ");
            String selecao1 = teclado.nextLine();
            Hacker hacker1 = new Hacker(nome1, id1, selecao1);

            Bot bot = new Bot();

            List<Carta> deckHackerCopia = new ArrayList<>();
            List<Carta> deckBotCopia = new ArrayList<>();
            Mao deckHacker = hacker1.getMao();
            Mao deckBot = bot.getMao();
            deckHackerCopia.addAll(deckHacker.getMao());
            deckBotCopia.addAll(deckBot.getMao());

            System.out.println("Decks montados. A batalha começa!");

            int i = 0;
            while (true) {
                int estado = GerenciadorSinglePlayer.GerenciarSinglePlayer(hacker1, bot,
                        deckHackerCopia, deckBotCopia, i);
                if(estado == -1){
                    break;
                }
                i++;
            }
        }
    }
}