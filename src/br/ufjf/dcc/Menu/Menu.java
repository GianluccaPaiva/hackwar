package br.ufjf.dcc.Menu;
import java.util.Scanner;
import br.ufjf.dcc.Replay.Replay;

public class Menu {
    private static int selecionarTipoJogo(Scanner teclado) {
        System.out.println("Selecione o tipo de jogo:");
        System.out.println("1. Jogo Multiplayer");
        System.out.println("2. Jogo Com Bot");
        System.out.print("Escolha uma opcao: ");
        int tipoJogo = teclado.nextInt();
        if (tipoJogo == 1) {
            System.out.println("Iniciando Jogo Multiplayer...");
            return 1;
        } else if (tipoJogo == 2) {
            System.out.println("Iniciando Jogo Com Bot...");
            return 2;
        } else {
            System.out.println("Opção inválida. Retornando ao menu principal.");
            return -1;
        }
    }

    private static void selecionarReplay(Scanner teclado) {
        System.out.println("Digite o número do replay que deseja carregar: " + "(Quantidade de replays atuais -> " + Replay.getIndice() + ")");
        int numeroReplay = teclado.nextInt();
        if (numeroReplay <= 0) {
            System.out.println("Número inválido. Retornando ao menu principal.");
            Menu.Menu();
        }
        String caminhoReplay = "replay_jogo_" + numeroReplay + ".txt";
        System.out.println("Carregando replay do arquivo: " + caminhoReplay);
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
        System.out.println("Tem certeza que deseja deletar todos os replays? (s/n)");
        String resposta = teclado.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            Replay.deletarTodosReplay();
            System.out.println("Todos os replays foram deletados.");
        } else {
            System.out.println("Operação cancelada. Retornando ao menu principal.");
        }
    }

    public static int Menu() {
        System.out.println("Menu Principal");
        System.out.println("1. Iniciar Jogo");
        System.out.println("2. Carregar Replay");
        System.out.println("3. Deletar Todos Replay");
        System.out.println("4. Sair");
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

        } else if (opcao == 4) {
            System.out.println("Saindo do jogo. Ate mais!");
            System.exit(0);

        } else {
            System.out.println("Opcao invalida. Tente novamente.");
            Menu();
            return 0;
        }
        return 0;
    }
}

