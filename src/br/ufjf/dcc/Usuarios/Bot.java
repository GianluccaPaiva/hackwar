package br.ufjf.dcc.Usuarios;
import br.ufjf.dcc.mao_baralho.Carta;
import br.ufjf.dcc.mao_baralho.Mao;

import java.util.List;

public class Bot {
    private String nome, id;
    private static int MAX_ENERGIA = 10, MAX_VIDA = 100;
    private int vida, energia;
    private Mao mao;

    public Bot() {
        this.nome = "Bot";
        this.id = "202565001";
        this.vida = MAX_VIDA;
        this.energia = MAX_ENERGIA;
        this.mao = new Mao();
        this.mao.selecaoAutomatica();
    }
    public Carta escolherCartaReacao(Carta cartaInimiga) {
        Carta cartaEscolhida = null;
        // Filtra as cartas disponíveis do Bot
        List<Carta> cartasDefesa = this.mao.getCartasDoTipo("Defesa");
        List<Carta> cartasAtaque = this.mao.getCartasDoTipo("Ataque");
        List<Carta> cartasSuporte = this.mao.getCartasDoTipo("Suporte");

        String tipoInimigo = cartaInimiga.getTipo().toLowerCase();

        System.out.println("--- 🤖 Bot Analisando Reação à carta: " + cartaInimiga.getNome() + " (" + cartaInimiga.getTipo() + ") ---");

        // Lógica de reação:

        // 1. Reação a Ataque
        if (tipoInimigo.equals("ataque")) {
            // Prioriza a Defesa de maior poder que possa pagar
            cartaEscolhida = cartasDefesa.stream()
                    .filter(c -> c.getCusto() <= this.energia)
                    .max((c1, c2) -> Double.compare(c1.getPoder(), c2.getPoder()))
                    .orElse(null);

            // 2. Reação a Defesa
        } else if (tipoInimigo.equals("defesa")) {
            // Prioriza o Ataque de maior poder que possa pagar
            cartaEscolhida = cartasAtaque.stream()
                    .filter(c -> c.getCusto() <= this.energia)
                    .max((c1, c2) -> Double.compare(c1.getPoder(), c2.getPoder()))
                    .orElse(null);

            // 3. Reação a Suporte
        } else if (tipoInimigo.equals("suporte")) {
            String efeitoInimigo = cartaInimiga.getEfeito().toUpperCase();

            if (efeitoInimigo.contains("AUMENTA_ATAQUE")) {
                // Tenta neutralizar com DIMINUI_ATAQUE ou usa a Defesa mais forte
                cartaEscolhida = cartasSuporte.stream()
                        .filter(c -> c.getEfeito().toUpperCase().contains("DIMINUI_ATAQUE") && c.getCusto() <= this.energia)
                        .max((c1, c2) -> Double.compare(c1.getPoder(), c2.getPoder()))
                        .orElseGet(() -> cartasDefesa.stream()
                                .filter(c -> c.getCusto() <= this.energia)
                                .max((c1, c2) -> Double.compare(c1.getPoder(), c2.getPoder()))
                                .orElse(null));

            } else if (efeitoInimigo.contains("AUMENTA_VIDA") || efeitoInimigo.contains("DIMINUI_ATAQUE")) {
                if(this.vida< MAX_VIDA / 2) {
                    // Se a vida está baixa, tenta usar Suporte de cura
                    cartaEscolhida = cartasSuporte.stream()
                            .filter(c -> c.getEfeito().toUpperCase().contains("CURA") && c.getCusto() <= this.energia)
                            .max((c1, c2) -> Double.compare(c1.getPoder(), c2.getPoder()))
                            .orElse(null);
                }else {
                    // Reage a cura ou debuff de ataque com Ataque para manter pressão
                    cartaEscolhida = cartasAtaque.stream()
                            .filter(c -> c.getCusto() <= this.energia)
                            .max((c1, c2) -> Double.compare(c1.getPoder(), c2.getPoder()))
                            .orElse(null);
                }
            }
        }

        if (cartaEscolhida == null) {
            cartaEscolhida = this.mao.getMao().stream()
                    .filter(c -> c.getCusto() <= this.energia)
                    .max((c1, c2) -> Double.compare(c1.getPoder(), c2.getPoder()))
                    .orElse(null);
        }

        // Consolidação da jogada:
        if (cartaEscolhida != null) {
            System.out.println("✅ Bot reagiu com: " + cartaEscolhida.getNome() + " (" + cartaEscolhida.getTipo() + " - Custo: " + cartaEscolhida.getCusto() + ")");
            this.mao.removerCarta(cartaEscolhida);
            this.energia -= cartaEscolhida.getCusto();
        } else {
            System.out.println("❌ Bot não possui energia ou carta ideal para reação e passa a vez.");
        }

        return cartaEscolhida;
    }

    public void exibirStatus() {
        System.out.println("Hacker: " + this.nome + " (ID: " + this.id + ")");
        System.out.println("Vida: " + this.vida + "/" + MAX_VIDA + "  " + "Energia: " + this.energia + "/" + MAX_ENERGIA);
        this.mao.exibirMao();
    }

    public int getVida() {
        return vida;
    }
    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getEnergia() {
        return energia;
    }
    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public Mao getMao() {
        return mao;
    }
    public void setMao(Mao mao) {
        this.mao = mao;
    }
}
