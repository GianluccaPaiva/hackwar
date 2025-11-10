package br.ufjf.dcc.GerenciadoresDeJogo;

import br.ufjf.dcc.CalculosDoCombate.Calcular;
import br.ufjf.dcc.Usuarios.Hacker;
import br.ufjf.dcc.mao_baralho.Carta;
import java.util.List;

public class GerenciadorMultiplayer {
    public static int GerenciarJogoMultiplayer(Hacker h1, Hacker h2, List<Carta> deckH1Copia, List<Carta> deckH2Copia) {
        System.out.println("\n========= NOVA RODADA ==========");
        h1.exibirStatus();
        h2.exibirStatus();

        List<Carta> jogadaHacker1 = h1.escolherJogada();
        if (h1.getVida() <= 0) {
            System.out.println("\n--- FIM DE JOGO ---");
            System.out.println(h2.getNome() + " venceu!");
            return -1;
        }

        List<Carta> jogadaHacker2 = h2.escolherJogada();
        if (h2.getVida() <= 0) {
            System.out.println("\n--- FIM DE JOGO ---");
            System.out.println(h2.getNome() + " venceu!");
            return -1;
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

        if (h1.getVida() <= 0) {
            System.out.println("\n--- FIM DE JOGO ---");
            System.out.println(h2.getNome() + " venceu!");
            return -1;
        } else if (h2.getVida() <= 0) {
            System.out.println("\n--- FIM DE JOGO ---");
            System.out.println(h1.getNome() + " venceu!");
            return -1;
        }

        if(h1.getMaoSize()== 0){
            h1.setMao(deckH1Copia);
        }
        else if(h2.getMaoSize()== 0) {
            h2.setMao(deckH2Copia);
        }
        return 0;
    }
}
