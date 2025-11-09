
package br.ufjf.dcc.Main;
import br.ufjf.dcc.Usuarios.Bot;
import br.ufjf.dcc.Usuarios.Hacker;
import br.ufjf.dcc.mao_baralho.Carta;
import br.ufjf.dcc.mao_baralho.Mao;
import br.ufjf.dcc.CalculosDoCombate.Calcular;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- CYBER DUEL: GUERRA DE HACKERS ---");
        System.out.print("Digite seu nome de Hacker: ");
        String nome = scanner.nextLine();
        System.out.print("Digite seu id: ");
        String id = scanner.nextLine();
        System.out.println("Deseja selecionar suas cartas manualmente? (sim/nao): ");
        String selecao = scanner.nextLine();

        Hacker hacker = new Hacker(nome, id, selecao);
        Bot bot = new Bot();
        Mao deckBot = bot.getMao();
        Mao deckHacker = hacker.getMao();
        System.out.println("Decks montados. A batalha começa!");

        while (true) {
            System.out.println("\n========= NOVA RODADA ==========");
            hacker.exibirStatus();
            bot.exibirStatus();

            List<Carta> jogadaHacker = hacker.escolherJogada();
            if (hacker.getVida() <= 0) {
                System.out.println("\n--- FIM DE JOGO ---");
                System.out.println(bot.getNome() + " venceu!");
                break;
            }

            List<Carta> jogadaBot = bot.escolherJogada(jogadaHacker);

            Calcular.ResultadoTurno res = Calcular.calcularResultado(
                    hacker.getVida(), hacker.getEnergia(), jogadaHacker,
                    bot.getVida(), bot.getEnergia(), jogadaBot
            );

            hacker.setVida(res.vidaFinalJ1());
            hacker.setEnergia(res.energiaFinalJ1());
            bot.setVida(res.vidaFinalJ2());
            bot.setEnergia(res.energiaFinalJ2());

            System.out.println("--- Consolidação da Rodada ---");
            System.out.println(hacker.getNome() + " termina com " + res.vidaFinalJ1() + " de vida.");
            System.out.println(bot.getNome() + " termina com " + res.vidaFinalJ2() + " de vida.");

            if (hacker.getVida() <= 0) {
                System.out.println("\n--- FIM DE JOGO ---");
                System.out.println(bot.getNome() + " venceu!");
                break;
            } else if (bot.getVida() <= 0) {
                System.out.println("\n--- FIM DE JOGO ---");
                System.out.println(hacker.getNome() + " venceu!");
                break;
            }

        }

        System.out.println("Obrigado por jogar!");
        scanner.close();
    }
}