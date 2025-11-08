package br.ufjf.dcc.CalculosDoCombate;
import br.ufjf.dcc.mao_baralho.Carta;

import java.util.List;

public class Calcular {

    private static int arredondarVida(int vida) {
        if (vida < 0) {
            return 0;
        }
        int resto = vida%10;
        if(resto>=5){
            return vida + (10 - resto);
        } else {
            return vida - resto;
        }
    }

    public static int calcularDano(int ataque, int defesa, String tipoAtk, String tipoDef) {
        if (tipoDef.equalsIgnoreCase("defesa") && tipoAtk.equalsIgnoreCase("defesa")) {
            return 0;
        }
        if (tipoAtk.equalsIgnoreCase("ataque") && tipoDef.equalsIgnoreCase("ataque")) {
            return Math.max(ataque - defesa, 0);
        }
        return Math.max(ataque - defesa, 0);
    }

    public static int aplicaSuporteAtaque(int atkBase, List<Carta> suportes, List<Carta> ataques) {
        double bonus = 0.0;
        double reducao = 0.0;
        for (Carta suporte : suportes) {
            if (suporte.getTipo().equalsIgnoreCase("AUMENTA_ATAQUE")) {
                bonus += suporte.getPoder();
            } else if (suporte.getTipo().equalsIgnoreCase("DIMINUI_ATAQUE")) {
                reducao += suporte.getPoder();
            }
        }
        if (ataques.size() <= 1) {
            int total = (int) Math.round(atkBase * (1 + bonus));
            return (int) Math.max(total - (total * reducao), 0);
        } else {
            ataques.sort((a, b) -> Double.compare(b.getPoder(), a.getPoder()));
            int atkMaior = (int) ataques.getFirst().getPoder();
            int atkMenor = 0;
            for (int i = 1; i < ataques.size(); i++) {
                atkMenor += (int) ataques.get(i).getPoder();
            }
            int total = (int) Math.round(atkMaior * (1 + bonus)) + atkMenor;
            return (int) Math.max(total - (total * reducao), 0);
        }
    }

    public static int aplicaSuporteVida(int vidaAtual, List<Carta> suportes) {
        int novaVida = vidaAtual;
        for (Carta suporte : suportes) {
            if (suporte.getTipo().equalsIgnoreCase("AUMENTA_VIDA")) {
                novaVida += (int) suporte.getPoder();
            }
        }
        return arredondarVida(novaVida);
    }

    public static int calcularEnergiaFinal(int energiaInicial, List<Carta> cartasUsadas) {
        int energiaUsada = 0;
        for (Carta c : cartasUsadas) {
            energiaUsada += c.getCusto();
        }
        int energiaFinal = 1 + (energiaInicial - energiaUsada);
        if(energiaFinal < 0) {
            energiaFinal = 0;
        }
        if(energiaFinal > 10) {
            energiaFinal = 10;
        }
        return energiaFinal;
    }
}
