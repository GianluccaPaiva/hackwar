
package br.ufjf.dcc.Main;
import br.ufjf.dcc.Usuarios.Bot;
import br.ufjf.dcc.Usuarios.Hacker;
import br.ufjf.dcc.mao_baralho.Carta;
import br.ufjf.dcc.CalculosDoCombate.Calcular;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- CYBER DUEL: GUERRA DE HACKERS ---");
        System.out.print("Digite seu nome de Hacker: ");
        String nome = scanner.nextLine();

        // --- 1. PREPARAÇÃO (Regras da Preparação) ---
        // (Isso chama o construtor do Hacker, que chama mao.escolherCartas())
        Hacker hacker = new Hacker(nome, "2025123");

        // (Isso chama o construtor do Bot, que chama mao.selecaoAutomatica())
        Bot bot = new Bot();
        System.out.println("Decks montados. A batalha começa!");

        // (Você precisará de uma classe Replay para logar essas ações)
        // Replay replay = new Replay();
        // replay.logar("Hacker " + hacker.getNome() + " e Bot " + bot.getNome() + " entraram na arena.");

        // --- 2. O "TURNO" (O Jogo todo) ---
        boolean jogoAtivo = true;

        while (jogoAtivo) {

            System.out.println("\n========= NOVA RODADA ==========");
            hacker.exibirStatus();
            bot.exibirStatus();
            // replay.logarStatus(hacker, bot);

            // --- 3. AÇÃO DO HACKER (Retorna um "Vetor" / Lista) ---
            // (Chama o novo método 'escolherJogada' que criamos para o Hacker)
            List<Carta> jogadaHacker = hacker.escolherJogada();

            // --- 4. REAÇÃO DO BOT (Retorna um "Vetor" / Lista) ---
            // (Chama o novo método 'escolherJogada' que criamos para o Bot,
            // passando a jogada do Hacker para a "Essência" da IA)
            List<Carta> jogadaBot = bot.escolherJogada(jogadaHacker);

            // --- 5. CONSOLIDAÇÃO (Chama o "Juiz" Opção A) ---
            // O "Juiz" (Calcular.java) recebe as duas LISTAS e
            // aplica TODAS as regras do PDF (N-vs-N, Bônus, Energia, Arredondamento)
            Calcular.ResultadoTurno res = Calcular.calcularResultado(
                    hacker.getVida(), hacker.getEnergia(), jogadaHacker,
                    bot.getVida(), bot.getEnergia(), jogadaBot
            );

            // --- 6. APLICAR RESULTADOS ---
            hacker.setVida(res.vidaFinalJ1());
            hacker.setEnergia(res.energiaFinalJ1());
            bot.setVida(res.vidaFinalJ2());
            bot.setEnergia(res.energiaFinalJ2());

            System.out.println("--- Consolidação da Rodada ---");
            System.out.println(hacker.getNome() + " termina com " + res.vidaFinalJ1() + " de vida.");
            System.out.println(bot.getNome() + " termina com " + res.vidaFinalJ2() + " de vida.");

            // --- 7. VERIFICA FIM DE JOGO (Regra da Finalização) ---
            if (hacker.getVida() <= 0) {
                System.out.println("\n--- FIM DE JOGO ---");
                System.out.println(bot.getNome() + " venceu!");
                // replay.logarVencedor(bot);
                jogoAtivo = false;
            } else if (bot.getVida() <= 0) {
                System.out.println("\n--- FIM DE JOGO ---");
                System.out.println(hacker.getNome() + " venceu!");
                // replay.logarVencedor(hacker);
                jogoAtivo = false;
            }

            // --- 8. RECARREGAR MÃO (Regra do PDF) ---
            // (Se a mão ficar vazia, ela é recarregada.
            // Você precisa implementar essa lógica em 'Mao.java')
            // if (hacker.getMao().getMao().isEmpty()) {
            //     hacker.getMao().recarregarMao();
            //     System.out.println(hacker.getNome() + " recarregou a mão!");
            // }
            // if (bot.getMao().getMao().isEmpty()) {
            //     bot.getMao().recarregarMao();
            // }
        }

        System.out.println("Obrigado por jogar!");
        // replay.salvarArquivo("partida.txt");
        scanner.close();
    }
}