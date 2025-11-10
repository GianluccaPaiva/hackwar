package br.ufjf.dcc.CalculosDoCombate;
import br.ufjf.dcc.MaoBaralho.Carta;
import java.util.ArrayList;
import java.util.List;

public class Calcular {

    public record ResultadoTurno(
            int vidaFinalJ1,
            int energiaFinalJ1,
            int vidaFinalJ2,
            int energiaFinalJ2
    ) {}

    public static ResultadoTurno calcularResultado(
            int vidaAtualJ1, int energiaAtualJ1, List<Carta> cartasJ1,
            int vidaAtualJ2, int energiaAtualJ2, List<Carta> cartasJ2) {

        List<Carta> ataquesJ1 = new ArrayList<>();
        List<Carta> defesasJ1 = new ArrayList<>();
        List<Carta> suportesJ1 = new ArrayList<>();
        separarCartas(cartasJ1, ataquesJ1, defesasJ1, suportesJ1);

        List<Carta> ataquesJ2 = new ArrayList<>();
        List<Carta> defesasJ2 = new ArrayList<>();
        List<Carta> suportesJ2 = new ArrayList<>();
        separarCartas(cartasJ2, ataquesJ2, defesasJ2, suportesJ2);

        int atkBaseJ1 = calcularPoderBase(ataquesJ1);
        int defBaseJ1 = calcularPoderBase(defesasJ1);
        int atkBaseJ2 = calcularPoderBase(ataquesJ2);
        int defBaseJ2 = calcularPoderBase(defesasJ2);

        int vidaTempJ1 = aplicaSuporteVida(vidaAtualJ1, suportesJ1);
        int vidaTempJ2 = aplicaSuporteVida(vidaAtualJ2, suportesJ2);

        int atkTotalJ1 = aplicaSuporteAtaque(atkBaseJ1, suportesJ1, suportesJ2, ataquesJ1);
        int atkTotalJ2 = aplicaSuporteAtaque(atkBaseJ2, suportesJ2, suportesJ1, ataquesJ2);

        boolean j1Atacou = !ataquesJ1.isEmpty();
        boolean j2Atacou = !ataquesJ2.isEmpty();

        if (j1Atacou && j2Atacou) {
            vidaTempJ1 -= atkTotalJ2;
            vidaTempJ2 -= atkTotalJ1;
        } else if (j1Atacou && !j2Atacou) {
            int dano = calcularDanoSimples(atkTotalJ1, defBaseJ2);
            vidaTempJ2 -= dano;
        } else if (!j1Atacou && j2Atacou) {
            int dano = calcularDanoSimples(atkTotalJ2, defBaseJ1);
            vidaTempJ1 -= dano;
        }

        int vidaFinalJ1 = arredondarVida(vidaTempJ1);
        int vidaFinalJ2 = arredondarVida(vidaTempJ2);

        int energiaFinalJ1 = calcularEnergiaFinal(energiaAtualJ1, cartasJ1);
        int energiaFinalJ2 = calcularEnergiaFinal(energiaAtualJ2, cartasJ2);

        return new ResultadoTurno(vidaFinalJ1, energiaFinalJ1, vidaFinalJ2, energiaFinalJ2);
    }

    private static int calcularDanoSimples(int ataqueTotal, int defesaTotal) {
        return Math.max(ataqueTotal - defesaTotal, 0);
    }

    private static int arredondarVida(int vida) {
        if (vida <= 0) return 0;
        int resto = vida % 10;
        int vidaArredondada = (resto >= 5) ? (vida + (10 - resto)) : (vida - resto);
        return Math.min(vidaArredondada, 100);
    }

    private static int aplicaSuporteAtaque(int atkBase, List<Carta> seuSuportes, List<Carta> iniSuportes,  List<Carta> ataques) {
        double bonus = 0.0, reducao = 0.0;
        for (Carta suporte : seuSuportes) {
            if (suporte.getEfeito().equalsIgnoreCase("AUMENTA_ATAQUE")) bonus += suporte.getPoder() / 100.0;
        }
        for (Carta suporte : iniSuportes) {
            if (suporte.getEfeito().equalsIgnoreCase("REDUZ_ATAQUE")) reducao += suporte.getPoder() / 100.0;
        }

        int total;
        if (ataques.isEmpty()) total = 0;
        else if (ataques.size() == 1) total = (int)Math.round(atkBase * (1 + bonus));
        else {
            ataques.sort((a, b) -> Double.compare(b.getPoder(), a.getPoder()));
            double atkMaior = ataques.getFirst().getPoder();
            int atkOutras = 0;
            for (int i = 1; i < ataques.size(); i++){
                atkOutras += ataques.get(i).getPoder();
            }
            total = (int)Math.round((atkMaior * (1 + bonus)) + atkOutras);
        }
        return (int)Math.max(total - (total * reducao), 0);
    }

    private static int aplicaSuporteVida(int vidaAtual, List<Carta> suportes) {
        int novaVida = vidaAtual;
        for (Carta suporte : suportes) {
            if (suporte.getEfeito().equalsIgnoreCase("AUMENTA_VIDA")) novaVida += (int)suporte.getPoder();
        }
        return novaVida;
    }

    private static int calcularEnergiaFinal(int energiaInicial, List<Carta> cartasUsadas) {
        int energiaUsada = 0;
        for (Carta carta : cartasUsadas){
            energiaUsada += carta.getCusto();
        }
        int energiaFinal = 1 + (energiaInicial - energiaUsada);
        if (energiaFinal > 10) return 10;
        if (energiaFinal < 0) return 0;
        return energiaFinal;
    }

    private static int calcularPoderBase(List<Carta> cartas) {
        int poderTotal = 0;
        for (Carta carta : cartas) poderTotal += (int)carta.getPoder();
        return poderTotal;
    }

    private static void separarCartas(List<Carta> cartasJogadas, List<Carta> ataques, List<Carta> defesas, List<Carta> suportes) {
        for (Carta carta : cartasJogadas) {
            if (carta.getTipo().equalsIgnoreCase("Ataque")) ataques.add(carta);
            else if (carta.getTipo().equalsIgnoreCase("Defesa")) defesas.add(carta);
            else if (carta.getTipo().equalsIgnoreCase("Suporte")) suportes.add(carta);
        }
    }
}