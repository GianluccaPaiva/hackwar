package br.ufjf.dcc.Usuarios;
import br.ufjf.dcc.maoBaralho.Carta;
import br.ufjf.dcc.maoBaralho.Mao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot {
    private String nome, id;
    private static int MAX_ENERGIA = 10, MAX_VIDA = 100;
    private int vida, energia;
    private Mao mao;

    public Bot() {
        this.nome = "BOT";
        this.id = "202565001";
        this.vida = MAX_VIDA;
        this.energia = MAX_ENERGIA;
        this.mao = new Mao();
        this.mao.selecaoAutomatica();
    }

    private final Random random = new Random();
    private static  String[] FRASES_ATAQUE = {
            "Sério? Você tentou *isso*? É como se uma formiga tentasse arranhar o vidro.",
            "Não me faça rir. O único resultado possível é a sua falha patética.",
            "Parabéns, gastou energia. Eu mal senti. Próximo, por favor.",
            "Você é a vítima nesta história, não o herói. Aceite seu destino.",
            "Você joga com tanta *esperança*. É a única coisa que eu não vou destruir.",
            "Isso é o melhor que a sua mediocridade pode oferecer? Estou *desapontado*.",
            "Eu sou o limite do seu poder. Você não pode me afetar.",
            "Seu destino é me dar a vitória, não resistir.",
            "Não Grita. shhhhhh, vai ser mais rápido assim."
    };

    private static String[] FRASES_DEFESA = {
            "Você realmente acha que pode se esconder de mim? Que ingênuo.",
            "Eu dou a você o benefício de tentar, mas isso é só um atraso na sua derrota.",
            "Eu sou a única coisa que importa aqui. Sua 'segurança' é uma piada.",
            "Isso é fofo. Me mostre a próxima 'barreira' que eu vou atravessar.",
            "O que é um escudo para um Deus? Uma formalidade.",
            "Seu medo é palpável. Continue a se esconder, isso me diverte.",
            "Eu não me defendo, eu domino. Você se defende, você perde.",
            "Você está desperdiçando uma boa carta. Devia ter usado para me agradar.",
            "Bate na massa"
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

    private void exibeFrase(Carta reacao) {
        if (reacao.getTipo().equalsIgnoreCase("Ataque")) {
            System.out.println(this.nome + ": " + selecionarFrase(FRASES_ATAQUE));
        } else if (reacao.getTipo().equalsIgnoreCase("Defesa")) {
            System.out.println(this.nome + ": " + selecionarFrase(FRASES_DEFESA));
        } else if (reacao.getTipo().equalsIgnoreCase("Suporte")) {
            if (reacao.getEfeito().contains("AUMENTA_VIDA") || reacao.getEfeito().contains("DIMINUI_ATAQUE")) {
                System.out.println(this.nome + ": " + selecionarFrase(FRASES_SUPORTE_VIDA));
            } else {
                System.out.println(this.nome + ": " + selecionarFrase(FRASES_SUPORTE_BUFF_DEBUFF));
            }
        }
    }

    public void exibirStatus() {
        System.out.println("Hacker: " + this.nome + " (ID: " + this.id + ")");
        System.out.println("Vida: " + this.vida + "/" + MAX_VIDA + "  " + "Energia: " + this.energia + "/" + MAX_ENERGIA);
        this.mao.exibirMao();
    }

    private Carta encontrarMelhorCarta(List<Carta> listaCartas, int energiaAtual, String efeitoObrigatorio) {
        Carta melhorCarta = null;
        double maiorPoder = -1;

        for (Carta carta : listaCartas) {
            if (carta.getCusto() <= energiaAtual) {
                boolean efeitoValido = efeitoObrigatorio.isEmpty() || carta.getEfeito().equalsIgnoreCase(efeitoObrigatorio);

                if (efeitoValido) {
                    if (carta.getPoder() > maiorPoder) {
                        maiorPoder = carta.getPoder();
                        melhorCarta = carta;
                    }
                }
            }
        }
        return melhorCarta;
    }

    private Carta decidirReacao(Carta cartaInimiga, int energiaDisponivel) {
        Carta cartaEscolhida = null;

        List<Carta> cartasDefesa = this.mao.getCartasDoTipo("Defesa");
        List<Carta> cartasAtaque = this.mao.getCartasDoTipo("Ataque");
        List<Carta> cartasSuporte = this.mao.getCartasDoTipo("Suporte");

        String tipoInimigo = (cartaInimiga != null) ? cartaInimiga.getTipo().toLowerCase() : "nenhum";
        String efeitoInimigo = (cartaInimiga != null && tipoInimigo.equals("suporte")) ? cartaInimiga.getEfeito().toUpperCase() : "";

        if (tipoInimigo.equals("ataque")) {
            cartaEscolhida = encontrarMelhorCarta(cartasDefesa, energiaDisponivel, "");

        } else if (tipoInimigo.equals("defesa")) {
            cartaEscolhida = encontrarMelhorCarta(cartasAtaque, energiaDisponivel, "");

        } else if (tipoInimigo.equals("suporte")) {
            if (efeitoInimigo.contains("AUMENTA_ATAQUE")) {
                cartaEscolhida = encontrarMelhorCarta(cartasSuporte, energiaDisponivel, "DIMINUI_ATAQUE");
                if (cartaEscolhida == null) {
                    cartaEscolhida = encontrarMelhorCarta(cartasDefesa, energiaDisponivel, "");
                }
            } else if (efeitoInimigo.contains("AUMENTA_VIDA") || efeitoInimigo.contains("DIMINUI_ATAQUE")) {
                if (this.vida < MAX_VIDA / 2) {
                    cartaEscolhida = encontrarMelhorCarta(cartasSuporte, energiaDisponivel, "AUMENTA_VIDA");
                } else {
                    cartaEscolhida = encontrarMelhorCarta(cartasAtaque, energiaDisponivel, "");
                }
            }
        }
        if (cartaEscolhida == null) {
            if(this.vida < MAX_VIDA / 2) {
                cartaEscolhida = encontrarMelhorCarta(cartasSuporte, energiaDisponivel, "AUMENTA_VIDA");
            }
            else{
                cartaEscolhida = encontrarMelhorCarta(cartasAtaque, energiaDisponivel, "");
            }
        }
        return cartaEscolhida;
    }

    public List<Carta> escolherJogada(List<Carta> jogadaInimiga) {
        System.out.println("\n--- É a vez de: " + this.nome);

        List<Carta> cartasParaJogar = new ArrayList<>();
        int energiaParaGastar = this.getEnergia();

        if (!jogadaInimiga.isEmpty()) {
            for (Carta cartaInimiga : jogadaInimiga) {
                Carta reacao = decidirReacao(cartaInimiga, energiaParaGastar);

                if (reacao != null && !cartasParaJogar.contains(reacao) && reacao.getCusto() <= energiaParaGastar) {
                    System.out.println("✅ Bot reagiu com: " + reacao.getNome() + " (" + reacao.getTipo() +
                            " - Custo: " + reacao.getCusto() + ")");
                    cartasParaJogar.add(reacao);
                    energiaParaGastar -= reacao.getCusto();
                    this.mao.removerCarta(reacao);
                    exibeFrase(reacao);
                }
            }
        } else {
            Carta jogadaSolo = decidirReacao(null, energiaParaGastar);

            if (jogadaSolo != null && jogadaSolo.getCusto() <= energiaParaGastar) {
                System.out.println("✅ Bot joga: " + jogadaSolo.getNome() + " (" + jogadaSolo.getTipo() +
                        " - Custo: " + jogadaSolo.getCusto() + ")");
                cartasParaJogar.add(jogadaSolo);
                this.mao.removerCarta(jogadaSolo);
                exibeFrase(jogadaSolo);
            }
        }

        if (this.getVida() < 20 && energiaParaGastar > 0) {
            Carta melhorCura = encontrarMelhorCarta(this.mao.getCartasDoTipo("Suporte"), energiaParaGastar, "AUMENTA_VIDA");
            if (melhorCura != null && !cartasParaJogar.contains(melhorCura) && melhorCura.getCusto() <= energiaParaGastar) {
                System.out.println("✅ Bot se cura com: " + melhorCura.getNome());
                cartasParaJogar.add(melhorCura);
                this.mao.removerCarta(melhorCura);
                System.out.println(this.nome + ": " + selecionarFrase(FRASES_SUPORTE_VIDA));
            }
        }

        if (cartasParaJogar.isEmpty()) {
            System.out.println("❌ Bot não possui energia ou carta ideal para reagir e passa a vez.");
        } else {
            System.out.println(this.nome + " jogou " + cartasParaJogar.size() + " cartas.");
        }
        return cartasParaJogar;
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

    public int getMaoSize() {
        return this.mao.size();
    }

    public void setMao(List<Carta> novaMao) {
        this.mao = new Mao(novaMao);
    }

    public Mao getMao() {
        return this.mao;
    }

    public String getNome() {
        return nome;
    }
}
