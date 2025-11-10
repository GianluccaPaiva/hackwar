package br.ufjf.dcc.GerenciadoresDeJogo;

import br.ufjf.dcc.CalculosDoCombate.Calcular;
import br.ufjf.dcc.Replay.Replay;
import br.ufjf.dcc.Usuarios.Hacker;
import br.ufjf.dcc.MaoBaralho.Carta;
import java.util.List;

public class GerenciadorMultiplayer {
    public static int GerenciarJogoMultiplayer(Hacker h1, Hacker h2, List<Carta> deckH1Copia, List<Carta> deckH2Copia) {
        String turnoOrdem = h1.getNome() + " joga primeiro neste turno.";

        Replay.registrar("\n=== NOVA RODADA === ");
        Replay.registrar("--- Status Inicial ---");
        Replay.registrar(h1.getStatusReplay());
        Replay.registrar(h2.getStatusReplay());

        System.out.println("\n========= NOVA RODADA ==========");
        h1.exibirStatus();
        h2.exibirStatus();

        List<Carta> jogadaHacker1 = h1.escolherJogada();
        if (h1.getVida() <= 0) {
            Replay.registrar("Ação: " + h1.getNome() + " entregou o sistema/perdeu antes da jogada do oponente.");
            Replay.registrar(h2.getNome() + " VENCEU!");
            System.out.println("\n--- FIM DE JOGO ---");
            System.out.println(h2.getNome() + " venceu!");
            Replay.novoReplay();
            return -1;
        }

        List<Carta> jogadaHacker2 = h2.escolherJogada();
        if (h2.getVida() <= 0) {
            Replay.registrar("Ação: " + h2.getNome() + " entregou o sistema/perdeu antes da consolidação.");
            Replay.registrar(h1.getNome() + " VENCEU!");
            System.out.println("\n--- FIM DE JOGO ---");
            System.out.println(h2.getNome() + " venceu!");
            Replay.novoReplay();
            return -1;
        }

        Replay.registrar("--- Ações da Rodada ---");
        Replay.registrar("Ordem: " + turnoOrdem);

        Replay.registrar(h1.getNome() + " jogou " + jogadaHacker1.size() + " cartas:");
        for(Carta c : jogadaHacker1) {
            Replay.registrar("  H1: " + c.getReplayInfo());
        }

        Replay.registrar(h2.getNome() + " jogou " + jogadaHacker2.size() + " cartas:");
        for(Carta c : jogadaHacker2) {
            Replay.registrar("  H2: " + c.getReplayInfo());
        }

        Calcular.ResultadoTurno resultado = Calcular.calcularResultado(
                h1.getVida(), h1.getEnergia(), jogadaHacker1,
                h2.getVida(), h2.getEnergia(), jogadaHacker2
        );

        h1.setVida(resultado.vidaFinalJ1());
        h1.setEnergia(resultado.energiaFinalJ1());
        h2.setVida(resultado.vidaFinalJ2());
        h2.setEnergia(resultado.energiaFinalJ2());

        System.out.println("--- Consolidação da Rodada ---");
        System.out.println(h1.getNome() + " termina com " + resultado.vidaFinalJ1() + " de vida.");
        System.out.println(h2.getNome() + " termina com " + resultado.vidaFinalJ2() + " de vida.");
        Replay.registrar("--- Resultado da Consolidação ---");
        Replay.registrar(h1.getNome() + " Vida Final: " + resultado.vidaFinalJ1() + ", Energia Final: " + resultado.energiaFinalJ1());
        Replay.registrar(h2.getNome() + " Vida Final: " + resultado.vidaFinalJ2() + ", Energia Final: " + resultado.energiaFinalJ2());

        if (h1.getVida() <= 0) {
            Replay.registrar(h2.getNome() + " VENCEU!");
            System.out.println("\n--- FIM DE JOGO ---");
            System.out.println(h2.getNome() + " venceu!");
            Replay.novoReplay();
            return -1;
        } else if (h2.getVida() <= 0) {
            Replay.registrar(h1.getNome() + " VENCEU!");
            System.out.println("\n--- FIM DE JOGO ---");
            System.out.println(h1.getNome() + " venceu!");
            Replay.novoReplay();
            return -1;
        }

        if(h1.getMaoSize()== 0){
            h1.setMao(deckH1Copia);
            Replay.registrar(h1.getNome() + " recarregou o deck completo.");
        }
        else if(h2.getMaoSize()== 0) {
            h2.setMao(deckH2Copia);
            Replay.registrar(h2.getNome() + " recarregou o deck completo.");
        }
        return 0;
    }
}
