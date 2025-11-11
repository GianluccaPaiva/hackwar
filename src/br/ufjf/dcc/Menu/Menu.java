package br.ufjf.dcc.Menu;
import java.util.Scanner;
import br.ufjf.dcc.Replay.Replay;
import br.ufjf.dcc.Usuarios.Bot;

public class Menu {
    private static int selecionarTipoJogo(Scanner teclado) {
        final String GREEN = "\u001B[32m";
        final String RESET = "\u001B[0m";
        System.out.println(GREEN);
        System.out.println("Selecione o tipo de jogo:");
        System.out.println("1. Jogo Multiplayer");
        System.out.println("2. Jogo Com Bot");
        System.out.print("Escolha uma opcao: ");
        int tipoJogo = teclado.nextInt();
        if (tipoJogo == 1) {
            System.out.println("Iniciando Jogo Multiplayer...");
            System.out.println(RESET);
            return 1;
        } else if (tipoJogo == 2) {
            System.out.println("Iniciando Jogo Com Bot...");
            System.out.println(RESET);
            return 2;
        } else {
            System.out.println("Opção inválida. Retornando ao menu principal.");
            System.out.println(RESET);
            Menu.Menu();
            return 0;
        }
    }

    private static void selecionarReplay(Scanner teclado) {
        final String GREEN = "\u001B[32m";
        final String RESET = "\u001B[0m";
        System.out.println(GREEN);
        System.out.println("Digite o número do replay que deseja carregar: " + "(Quantidade de replays atuais -> " + Replay.getIndice() + ")");
        int numeroReplay = teclado.nextInt();
        if (numeroReplay <= 0) {
            System.out.println("Número inválido. Retornando ao menu principal.");
            Menu.Menu();
        }
        String caminhoReplay = "replay_jogo_" + numeroReplay + ".txt";
        System.out.println("Carregando replay do arquivo: " + caminhoReplay);
        System.out.println(RESET);
        Replay.reproduzirReplay(caminhoReplay);
    }

    private static int retornaInteiroOpcao(String opcao) {
        if (opcao == null || opcao.isEmpty() || !opcao.matches("\\d+")) {
            return -1;
        } else {
            return Integer.parseInt(opcao);
        }
    }

    private static void deletarTodosReplay(Scanner teclado) {
        final String GREEN = "\u001B[32m";
        final String RESET = "\u001B[0m";
        System.out.println(GREEN);
        System.out.println("Tem certeza que deseja deletar todos os replays? (s/n)");
        String resposta = teclado.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            System.out.println("Todos os replays foram deletados.");
            Replay.deletarTodosReplay();
        } else {
            System.out.println("Operação cancelada. Retornando ao menu principal.");
        }
        System.out.println(RESET);
    }

    private static void exibirTitulo() {
        final String GREEN = "\u001B[32m";
        final String RESET = "\u001B[0m";

        System.out.println(GREEN);
        System.out.println("===================================================");
        System.out.println("                                                   ");
        System.out.println("                  ################                 ");
        System.out.println("              ########################             ");
        System.out.println("            ############################           ");
        System.out.println("          ################################         ");
        System.out.println("          ################################         ");
        System.out.println("        ####################################       ");
        System.out.println("        ####################################       ");
        System.out.println("        ######################################     ");
        System.out.println("      ########################################     ");
        System.out.println("      ########################################     ");
        System.out.println("      ########      ++##########      ########     ");
        System.out.println("      MM####          ########          ######     ");
        System.out.println("      @@####            @@##..          @@####     ");
        System.out.println("      --@@##            ######          MM####     ");
        System.out.println("        ####          ########          ####       ");
        System.out.println("        ####################################       ");
        System.out.println("        ################    ################       ");
        System.out.println("      --##############        ################     ");
        System.out.println("      @@##############        ################     ");
        System.out.println("        ##############  ::MM  ##############       ");
        System.out.println("                ####################               ");
        System.out.println("                @@##################               ");
        System.out.println("                  ##  ####++##  ####               ");
        System.out.println("                ::##  ####++##  ####               ");
        System.out.println("                                                   ");
        System.out.println("===================================================");
        System.out.println("          H A C K W A R   I N I C I A D O          ");
        System.out.println("===================================================");
        System.out.println(RESET);
    }

    private static void exibirAnimacaoEntrada() {
        final String GREEN = "\u001B[32m";
        final String AMARELO = "\u001B[33m";
        final String RESET = "\u001B[0m";
        String[] carregamento = {"\\", "|", "/", "-"};
        String[] modulos = {
                "[>] Módulo de Kernel Carregado... OK",
                "[>] Dependências de I/O Carregadas... OK",
                "[>] Conexão com Servidor de Cartas... OK",
                "[>] Otimizando Algoritmos de Combate...",
                "[>] Iniciando Interface do Usuário...",
                "[>] Varredura de Segurança Concluída... ACESSO PERMITIDO"
        };
        System.out.println(GREEN);

        try {
            System.out.println("---------------------------------------------------");
            System.out.println("        INICIANDO O SISTEMA HACKWAR V1.0");
            System.out.println("---------------------------------------------------");


            for (int i = 0; i < 40; i++) {
                String indicador = carregamento[i % carregamento.length];
                System.out.print("\r[BOOT] Status: Processando... " + indicador + " | " + i + "%");
                Thread.sleep(30);
            }
            System.out.println("\r[BOOT] Status: Processando... | CONCLUÍDO (100%)");
            Thread.sleep(400);


            int delayPasso = 100;
            int delayFinal = 1000;

            System.out.println("\n[SCAN] Verificação de Integridade:");
            Thread.sleep(delayFinal);

            for (String modulo : modulos) {
                System.out.print("   ");
                for (char c : modulo.toCharArray()) {
                    System.out.print(c);
                    Thread.sleep(delayPasso / 20);
                }
                System.out.println();
                Thread.sleep(delayFinal);
                delayFinal = 500;
            }

            System.out.println("\n>>> PRESSIONE ENTER PARA ACESSAR O TERMINAL <<<");
            new java.util.Scanner(System.in).nextLine();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(RESET);
    }

    private static void limparTerminal() {
        for (int i = 0; i < 50; ++i) {
            System.out.println();
        }
    }

    public static int Menu() {
        final String GREEN = "\u001B[32m";
        final String RESET = "\u001B[0m";
        final String AMARELO = "\u001B[33m";
        final String CIANO = "\u001B[36m";
        exibirAnimacaoEntrada();
        limparTerminal();
        exibirTitulo();
        System.out.println(GREEN);
        System.out.println("Menu Principal");
        System.out.println("1. Iniciar Jogo");
        System.out.println("2. Carregar Replay");
        System.out.println("3. Deletar Todos Replay");
        System.out.println("4. Não sei jogar");
        System.out.println("5. Criadores");
        System.out.println("6. Sair");
        Scanner teclado = new Scanner(System.in);
        System.out.print("Escolha uma opcao: ");
        String opcLine = teclado.nextLine();
        int opcao = retornaInteiroOpcao(opcLine);
        if (opcao == -1) {
            System.out.println("Opcao invalida. Tente novamente.");
            Menu.Menu();
        } else if (opcao == 1) {
            return selecionarTipoJogo(teclado);

        } else if (opcao == 2) {
            selecionarReplay(teclado);
            return 0;
        } else if (opcao == 3) {
            Menu.deletarTodosReplay(teclado);
            return 0;

        } else if (opcao == 6) {
            System.out.println("Saindo do jogo. Ate mais!");
            System.exit(0);

        } else if (opcao==4) {
            Bot bot = new Bot();
            bot.instruirComoJogar();
            System.out.println("Deseja retornar ao Menu principal ou ir no Fight comigo?(Se tiver coragem) (M/F)");
            String resposta = teclado.nextLine();
            if(resposta.equalsIgnoreCase("M")){
                System.out.println("Saiba escolha! Retornando ao Menu Principal...");
                Menu();
            } else if(resposta.equalsIgnoreCase("F")) {
                System.out.println("Muito bem! Prepare-se para chorar!");
                return 2;
            }
        } else if(opcao == 5){
            System.out.print(AMARELO);
            System.out.println("Criado por NexusTeam - Gabriel Lineker && Gianlucca Paiva");
            System.out.print(RESET);
            System.out.print(CIANO);
            System.out.println("Número das matrículas respectivamente: " + 202476025 + " && " + 202476026);
            System.out.print(RESET);
            System.out.println("Precione enter para voltar ao menu principal");
            teclado.nextLine();
            Menu();
        }
        else {
            System.out.println("Opcao invalida. Tente novamente.");
            Menu();
            return 0;
        }
        System.out.println(RESET);
        return 0;
    }
}

