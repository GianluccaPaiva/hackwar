
import br.ufjf.dcc.Usuarios.Bot;
import br.ufjf.dcc.mao_baralho.Carta;
// src/br/ufjf/dcc/Main/Main.java

import br.ufjf.dcc.mao_baralho.Carta;
import br.ufjf.dcc.mao_baralho.Mao;
import br.ufjf.dcc.mao_baralho.Baralho;
import br.ufjf.dcc.Replay.Replay;
import br.ufjf.dcc.Usuarios.Bot; // Import necessário

// Adicione este método utilitário dentro da classe Main
public static Carta criarCartaMock(String nome, String tipo, double poder, int custo, String efeito) {
    String descricao = "Carta de Teste: " + nome;
    return new Carta(nome, tipo, descricao, poder, custo, efeito);
}

        void main() {
            System.out.println("--- INICIANDO TESTE DE REAÇÃO DO BOT ---");

            // -----------------------------------------------------------------------------------
            // PREPARAÇÃO: O Bot é inicializado com a mão aleatória padrão (4 ATK, 4 DEF, 2 SUP)
            // -----------------------------------------------------------------------------------

            // O Bot original (usado para exibição do status inicial)
            Bot botBase = new Bot();
            botBase.setEnergia(10); // Garante energia máxima para testar a melhor escolha

            System.out.println("\nSTATUS INICIAL DO BOT (Usado para referência de mão):");
            botBase.exibirStatus();
            System.out.println("-----------------------------------------------------------------------------------");


            // ---------------------- CENÁRIO 1: Oponente joga ATAQUE ----------------------
            System.out.println("\n>>> CENÁRIO 1: Oponente joga ATAQUE (Poder 50, Custo 5)");
            // A reação esperada é a DEFESA de MAIOR poder que o Bot possa pagar.
            Carta ataqueInimigo = criarCartaMock("Ataque Quântico", "ATAQUE", 50, 5, "Não tem efeito");

            // Novo Bot para o cenário 1 (para garantir mão e energia intactas)
            Bot botCenario1 = new Bot();
            botCenario1.setEnergia(10);

            Carta reacao1 = botCenario1.escolherCartaReacao(ataqueInimigo);

            if (reacao1 != null) {
                System.out.println("RESULTADO ESPERADO: DEFESA de MAIOR poder.");
                System.out.println("CARTA JOGADA PELO BOT: " + reacao1.getNome() + " (" + reacao1.getTipo() + ") - Poder: " + reacao1.getPoder());
            } else {
                System.out.println("CARTA JOGADA PELO BOT: Nenhuma. (Falha: o Bot deve ter Defesas na mão)");
            }

            // ---------------------- CENÁRIO 2: Oponente joga DEFESA ----------------------
            System.out.println("\n>>> CENÁRIO 2: Oponente joga DEFESA (Poder 50, Custo 5)");
            // A reação esperada é o ATAQUE de MAIOR poder que o Bot possa pagar.
            Carta defesaInimiga = criarCartaMock("Escudo Quântico", "DEFESA", 50, 5, "Não tem efeito");

            Bot botCenario2 = new Bot();
            botCenario2.setEnergia(10);

            Carta reacao2 = botCenario2.escolherCartaReacao(defesaInimiga);

            if (reacao2 != null) {
                System.out.println("RESULTADO ESPERADO: ATAQUE de MAIOR poder.");
                System.out.println("CARTA JOGADA PELO BOT: " + reacao2.getNome()+ " (" + reacao2.getTipo() + ") - Poder: " + reacao2.getPoder());
            } else {
                System.out.println("CARTA JOGADA PELO BOT: Nenhuma. (Falha: o Bot deve ter Ataques na mão)");
            }

            // ---------------------- CENÁRIO 3: Oponente joga SUPORTE (AUMENTA_ATAQUE) ----------------------
            System.out.println("\n>>> CENÁRIO 3: Oponente joga SUPORTE (AUMENTA_ATAQUE)");
            // A reação esperada é SUPORTE DIMINUI_ATAQUE ou a melhor DEFESA.
            Carta suporteAtkInimigo = criarCartaMock("Ataque Coordenado", "SUPORTE", 0.3, 3, "AUMENTA_ATAQUE");

            Bot botCenario3 = new Bot();
            botCenario3.setEnergia(10);

            Carta reacao3 = botCenario3.escolherCartaReacao(suporteAtkInimigo);

            if (reacao3 != null) {
                System.out.println("RESULTADO ESPERADO: SUPORTE DIMINUI_ATAQUE de maior poder ou DEFESA de maior poder.");
                System.out.println("CARTA JOGADA PELO BOT: " + reacao3.getNome() + " (" + reacao3.getTipo() + ") - Efeito: " + reacao3.getEfeito());
            } else {
                System.out.println("CARTA JOGADA PELO BOT: Nenhuma. (Falha: o Bot deve ter uma Defesa ou Suporte DIMINUI_ATAQUE)");
            }

            // ---------------------- CENÁRIO 4: Oponente joga SUPORTE (AUMENTA_VIDA) ----------------------
            System.out.println("\n>>> CENÁRIO 4: Oponente joga SUPORTE (AUMENTA_VIDA)");
            // A reação esperada é ATAQUE de MAIOR poder para punir a cura.
            Carta suporteVidaInimigo = criarCartaMock("Restauração de Backup", "SUPORTE", 30, 3, "AUMENTA_VIDA");

            Bot botCenario4 = new Bot();
            botCenario4.setEnergia(10);

            Carta reacao4 = botCenario4.escolherCartaReacao(suporteVidaInimigo);

            if (reacao4 != null) {
                System.out.println("RESULTADO ESPERADO: ATAQUE de MAIOR poder.");
                System.out.println("CARTA JOGADA PELO BOT: " + reacao4.getNome() + " (" + reacao4.getTipo() + ") - Poder: " + reacao4.getPoder());
            } else {
                System.out.println("CARTA JOGADA PELO BOT: Nenhuma. (Falha: o Bot deve ter Ataques na mão)");
            }
            System.out.println("\n--- TESTE DE REAÇÃO DO BOT CONCLUÍDO ---");
        }