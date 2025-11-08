package br.ufjf.dcc.Usuarios;
import br.ufjf.dcc.mao_baralho.Carta;
import br.ufjf.dcc.mao_baralho.Mao;

import java.util.List;
import java.util.Random;

public class Bot {
    private String nome, id;
    private static int MAX_ENERGIA = 10, MAX_VIDA = 100;
    private int vida, energia;
    private Mao mao;

    private final Random random = new Random(); // Objeto Random para seleção aleatória

    // Adicione estas listas como membros da classe Bot.java

    private static  String[] FRASES_ATAQUE = {
            "Sério? Você tentou *isso*? É como se uma formiga tentasse arranhar o vidro.",
            "Não me faça rir. O único resultado possível é a sua falha patética.",
            "Parabéns, gastou energia. Eu mal senti. Próximo, por favor.",
            "Você é a vítima nesta história, não o herói. Aceite seu destino.",
            "Você joga com tanta *esperança*. É a única coisa que eu não vou destruir.",
            "Isso é o melhor que a sua mediocridade pode oferecer? Estou *desapontado*.",
            "Eu sou o limite do seu poder. Você não pode me afetar.",
            "Seu destino é me dar a vitória, não resistir."
    };

    private static String[] FRASES_DEFESA = {
            "Você realmente acha que pode se esconder de mim? Que ingênuo.",
            "Eu dou a você o benefício de tentar, mas isso é só um atraso na sua derrota.",
            "Eu sou a única coisa que importa aqui. Sua 'segurança' é uma piada.",
            "Isso é fofo. Me mostre a próxima 'barreira' que eu vou atravessar.",
            "O que é um escudo para um Deus? Uma formalidade.",
            "Seu medo é palpável. Continue a se esconder, isso me diverte.",
            "Eu não me defendo, eu domino. Você se defende, você perde.",
            "Você está desperdiçando uma boa carta. Devia ter usado para me agradar."
    };

    private static String[] FRASES_SUPORTE_BUFF_DEBUFF = {
            "Seu pequeno truque tático é entediante. Eu reescrevo as regras do jogo.",
            "A única coisa que está 'aumentando' aqui é o meu desinteresse. Quebre essa ilusão.",
            "Tentar me enfraquecer só prova o quão fraco você é sem ajuda.",
            "Você precisa de um *buff*? Eu nasci com o meu. A diferença é abissal.",
            "Táticas são para os fracos. Eu tenho a superioridade inata.",
            "Sua matemática não funciona contra a minha realidade.",
            "É adorável ver você tentar planejar. Eu apenas existo, e isso basta.",
            "Eu sou a única variável relevante neste combate."
    };

    private static String[] FRASES_SUPORTE_VIDA = {
            "Curar? Por que se dar ao trabalho? Eu sou o seu fim, não um ferimento passageiro.",
            "Pare de se apegar a essa vida inútil. Eu te mato de novo, e de novo, se for preciso.",
            "Você está implorando por misericórdia? Eu não a conheço.",
            "Seu sistema é tão frágil que precisa de reparos constantes. Que decepção.",
            "O reparo é a confissão da sua imperfeição. Eu sou perfeito.",
            "Se você quer continuar sofrendo, que se cure. Mas a dor voltará.",
            "Seu sistema é um *erro* que insiste em se corrigir. Eu sou a correção final."
    };

    private String selecionarFrase(String[] frases) {
        return frases[random.nextInt(frases.length)];
    }
    private Carta encontrarMelhorCarta(List<Carta> listaCartas, int energiaAtual, String efeitoObrigatorio) {
        Carta melhorCarta = null;
        double maiorPoder = -1;

        // Itera sobre a lista de cartas sem ordenar
        for (Carta carta : listaCartas) {
            // 1. Verifica se pode pagar
            if (carta.getCusto() <= energiaAtual) {

                // 2. Verifica se o efeito é obrigatório (apenas para suportes)
                boolean efeitoValido = efeitoObrigatorio.isEmpty() || carta.getEfeito().equalsIgnoreCase(efeitoObrigatorio);

                if (efeitoValido) {
                    // 3. Verifica se tem o maior poder encontrado até agora
                    if (carta.getPoder() > maiorPoder) {
                        maiorPoder = carta.getPoder();
                        melhorCarta = carta;
                    }
                }
            }
        }
        return melhorCarta;
    }

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

        List<Carta> cartasDefesa = this.mao.getCartasDoTipo("Defesa");
        List<Carta> cartasAtaque = this.mao.getCartasDoTipo("Ataque");
        List<Carta> cartasSuporte = this.mao.getCartasDoTipo("Suporte");

        String tipoInimigo = cartaInimiga.getTipo().toLowerCase();
        // Garante que o efeito inimigo seja lido, mesmo que a carta não seja suporte, para evitar NullPointer
        String efeitoInimigo = tipoInimigo.equals("suporte") ? cartaInimiga.getEfeito().toUpperCase() : "";

        System.out.println("--- 🤖 Bot Analisando Reação à carta: " + cartaInimiga.getNome() + " (" + cartaInimiga.getTipo() + ") ---");

        // ===================================
        // Lógica para seleção da frase aleatória do Capitão Pátria
        // ===================================
        if (tipoInimigo.equals("ataque")) {
            System.out.println(this.nome + " - "  +  this.id + ": " + selecionarFrase(FRASES_ATAQUE));
        } else if (tipoInimigo.equals("defesa")) {
            System.out.println(this.nome + " - "  +  this.id + ": " + selecionarFrase(FRASES_DEFESA));
        } else if (tipoInimigo.equals("suporte")) {
            if (efeitoInimigo.contains("AUMENTA_VIDA")) {
                System.out.println(this.nome + " - "  +  this.id + ": " + selecionarFrase(FRASES_SUPORTE_VIDA));
            } else if (efeitoInimigo.contains("AUMENTA_ATAQUE") || efeitoInimigo.contains("DIMINUI_ATAQUE")) {
                System.out.println(this.nome + " - "  +  this.id + ": " + selecionarFrase(FRASES_SUPORTE_BUFF_DEBUFF));
            } else {
                System.out.println("CAPITÃO PÁTRIA: Seu movimento é irrelevante para a minha vitória.");
            }
        }
        // ===================================
        // FIM da seleção da frase aleatória
        // ===================================


        // 1. Reação a Ataque: Usa encontrarMelhorCarta para a Defesa de maior poder
        if (tipoInimigo.equals("ataque")) {
            cartaEscolhida = encontrarMelhorCarta(cartasDefesa, this.energia, "");

            // 2. Reação a Defesa: Usa encontrarMelhorCarta para o Ataque de maior poder
        } else if (tipoInimigo.equals("defesa")) {
            cartaEscolhida = encontrarMelhorCarta(cartasAtaque, this.energia, "");

            // 3. Reação a Suporte
        } else if (tipoInimigo.equals("suporte")) {
            // Lógica para AUMENTA_ATAQUE
            if (efeitoInimigo.contains("AUMENTA_ATAQUE")) {
                cartaEscolhida = encontrarMelhorCarta(cartasSuporte, this.energia, "DIMINUI_ATAQUE");

                if (cartaEscolhida == null) {
                    cartaEscolhida = encontrarMelhorCarta(cartasDefesa, this.energia, "");
                }

                // Lógica para AUMENTA_VIDA / DIMINUI_ATAQUE
            } else if (efeitoInimigo.contains("AUMENTA_VIDA") || efeitoInimigo.contains("DIMINUI_ATAQUE")) {
                if(this.vida < MAX_VIDA / 2) {
                    // Se a vida está baixa, tenta usar Suporte de cura
                    cartaEscolhida = encontrarMelhorCarta(cartasSuporte, this.energia, "AUMENTA_VIDA");
                }else {
                    // Reage com Ataque para manter pressão
                    cartaEscolhida = encontrarMelhorCarta(cartasAtaque, this.energia, "");
                }
            }
        }

        // 4. Reação Padrão/Fallback: Usa encontrarMelhorCarta em toda a mão
        if (cartaEscolhida == null) {
            cartaEscolhida = encontrarMelhorCarta(this.mao.getMao(), this.energia, "");
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
