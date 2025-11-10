package br.ufjf.dcc.GerenciadoresDeJogo;

import br.ufjf.dcc.CalculosDoCombate.Calcular;
import br.ufjf.dcc.Usuarios.Bot;
import br.ufjf.dcc.Usuarios.Hacker;
import br.ufjf.dcc.mao_baralho.Carta;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorSinglePlayer {
    public static int GerenciadorSinglePlayer(Hacker h, Bot b, List<Carta> deckHCopia, List<Carta> deckBCopia, int val){
        System.out.println("\n========= NOVA RODADA ==========");
        h.exibirStatus();
        b.exibirStatus();

        List<Carta> vazia = new ArrayList<>();
        List<Carta> jogadaBot;
        List<Carta> jogadaHacker;

        if(val%2 == 0){
            jogadaHacker = h.escolherJogada();
            if (h.getVida() <= 0) {
                System.out.println("\n--- FIM DE JOGO ---");
                System.out.println(b.getNome() + " venceu!");
                return -1;
            }

            jogadaBot = b.escolherJogada(jogadaHacker);
            if (b.getVida() <= 0) {
                System.out.println("\n--- FIM DE JOGO ---");
                System.out.println(b.getNome() + " venceu!");
                return -1;
            }
        }
        else{
            jogadaBot = b.escolherJogada(vazia);
            if (b.getVida() <= 0) {
                System.out.println("\n--- FIM DE JOGO ---");
                System.out.println(b.getNome() + " venceu!");
                return -1;
            }

            jogadaHacker = h.escolherJogada();
            if (h.getVida() <= 0) {
                System.out.println("\n--- FIM DE JOGO ---");
                System.out.println(b.getNome() + " venceu!");
                return -1;
            }
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

        if (h.getVida() <= 0) {
            System.out.println("\n--- FIM DE JOGO ---");
            System.out.println(b.getNome() + " venceu!");
            return -1;
        } else if (b.getVida() <= 0) {
            System.out.println("\n--- FIM DE JOGO ---");
            System.out.println(h.getNome() + " venceu!");
            return -1;
        }

        if(h.getMaoSize()== 0){
            h.setMao(deckHCopia);
        }
        else if(b.getMaoSize()== 0) {
            b.setMao(deckBCopia);
        }
        return 0;
    }
}
