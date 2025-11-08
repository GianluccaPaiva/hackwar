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

    private static  int selecionarReplay(Scanner teclado) {
        System.out.println("Carregar Replay:");
        System.out.print("Digite o nome do arquivo de replay: ");
        String nomeArquivo = teclado.next();
        System.out.println("Carregando replay do arquivo: " + nomeArquivo);
        Replay.reproduzirReplay(nomeArquivo);
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("Menu Principal");
        System.out.println("1. Iniciar Jogo");
        System.out.println("2. Carregar Replay");
        System.out.println("3. Sair");
        Scanner teclado = new Scanner(System.in);
        System.out.print("Escolha uma opcao: ");
        int opcao = teclado.nextInt();
        switch (opcao) {
            case 1:
                selecionarTipoJogo(teclado);
                break;
            case 2:
                selecionarReplay(teclado);
                break;
            case 3:
                System.out.println("Saindo do jogo. Ate mais!");
                break;
            default:
                System.out.println("Opcao invalida. Tente novamente.");
        }
    }
}
