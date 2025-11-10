package br.ufjf.dcc.GerenciadoresDeJogo;

import br.ufjf.dcc.CalculosDoCombate.Calcular;
import br.ufjf.dcc.Replay.Replay;
import br.ufjf.dcc.Usuarios.Bot;
import br.ufjf.dcc.Usuarios.Hacker;
import br.ufjf.dcc.MaoBaralho.Carta;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorSinglePlayer {
    public static int GerenciarSinglePlayer(Hacker h, Bot b, List<Carta> deckHCopia, List<Carta> deckBCopia, int val){

        int roundNumber = val + 1;
        String turnoOrdem = "";

        Replay.registrar("\n=== ROUND " + roundNumber + " ===");
        Replay.registrar("--- Status Inicial ---");
        Replay.registrar(h.getStatusReplay());
        Replay.registrar(b.getStatusReplay());

        System.out.println("\n========= NOVA RODADA ==========");
        h.exibirStatus();
        b.exibirStatus();

        List<Carta> vazia = new ArrayList<>();
        List<Carta> jogadaBot;
        List<Carta> jogadaHacker;

        if(val%2 == 0){
            turnoOrdem = h.getNome() + " joga primeiro.";
            jogadaHacker = h.escolherJogada();
            if (h.getVida() <= 0) {
                System.out.println("\n--- FIM DE JOGO ---");
                System.out.println(b.getNome() + " venceu!");
                Replay.novoReplay();
                return -1;
            }

            turnoOrdem += " " + b.getNome() + " reage.";
            jogadaBot = b.escolherJogada(jogadaHacker);
            if (b.getVida() <= 0) {
                System.out.println("\n--- FIM DE JOGO ---");
                System.out.println(b.getNome() + " venceu!");
                Replay.novoReplay();
                return -1;
            }
        }
        else{
            turnoOrdem = b.getNome() + " joga primeiro.";
            jogadaBot = b.escolherJogada(vazia);
            if (b.getVida() <= 0) {
                Replay.registrar("Ação: " + b.getNome() + " perdeu a vida durante a escolha.");
                Replay.registrar(h.getNome() + " VENCEU!");
                System.out.println("\n--- FIM DE JOGO ---");
                System.out.println(b.getNome() + " venceu!");
                Replay.novoReplay();
                return -1;
            }

            turnoOrdem += " " + h.getNome() + " reage.";
            jogadaHacker = h.escolherJogada();
            if (h.getVida() <= 0) {
                Replay.registrar("Ação: " + h.getNome() + " entregou o sistema ou teve a vida zerada durante a escolha.");
                Replay.registrar(b.getNome() + " VENCEU!");
                System.out.println("\n--- FIM DE JOGO ---");
                System.out.println(b.getNome() + " venceu!");
                Replay.novoReplay();
                return -1;
            }
        }

        Replay.registrar("--- Ações da Rodada ---");
        Replay.registrar("Ordem: " + turnoOrdem);

        Replay.registrar(h.getNome() + " jogou " + jogadaHacker.size() + " cartas:");
        for(Carta c : jogadaHacker) {
            Replay.registrar("  Hacker: " + c.getReplayInfo());
        }

        Replay.registrar(b.getNome() + " jogou " + jogadaBot.size() + " cartas:");
        for(Carta c : jogadaBot) {
            Replay.registrar("  Bot: " + c.getReplayInfo());
        }

        Calcular.ResultadoTurno resultado = Calcular.calcularResultado(
                h.getVida(), h.getEnergia(), jogadaHacker,
                b.getVida(), b.getEnergia(), jogadaBot
        );

        h.setVida(resultado.vidaFinalJ1());
        h.setEnergia(resultado.energiaFinalJ1());
        b.setVida(resultado.vidaFinalJ2());
        b.setEnergia(resultado.energiaFinalJ2());

        System.out.println("--- Consolidação da Rodada ---");
        System.out.println(h.getNome() + " termina com " + resultado.vidaFinalJ1() + " de vida.");
        System.out.println(b.getNome() + " termina com " + resultado.vidaFinalJ2() + " de vida.");
        Replay.registrar("--- Resultado da Consolidação ---");
        Replay.registrar(h.getNome() + " Vida Final: " + resultado.vidaFinalJ1() + ", Energia Final: " + resultado.energiaFinalJ1());
        Replay.registrar(b.getNome() + " Vida Final: " + resultado.vidaFinalJ2() + ", Energia Final: " + resultado.energiaFinalJ2());

        if (h.getVida() <= 0) {
            Replay.registrar(b.getNome() + " VENCEU!");
            System.out.println("\n--- FIM DE JOGO ---");
            System.out.println(b.getNome() + " venceu!");
            Replay.novoReplay();
            return -1;
        } else if (b.getVida() <= 0) {
            Replay.registrar(h.getNome() + " VENCEU!");
            System.out.println("\n--- FIM DE JOGO ---");
            System.out.println(h.getNome() + " venceu!");
            Replay.novoReplay();
            return -1;
        }

        if(h.getMaoSize()== 0){
            h.setMao(deckHCopia);
            Replay.registrar(h.getNome() + " recarregou o deck completo.");
        }
        else if(b.getMaoSize()== 0) {
            b.setMao(deckBCopia);
            Replay.registrar(b.getNome() + " recarregou o deck completo.");
        }
        return 0;
    }
}
