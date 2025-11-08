package br.ufjf.dcc.Replay;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import  java.io.FileWriter;
public class Replay {
    private static int num_replay = 1;
    private static String arqJogo = "replay_jogo.txt";
    private static String arqAtual = arqJogo;

    public static void registrar(String jogada){
        try(FileWriter writer = new FileWriter(arqAtual, true)){
            writer.write(LocalDateTime.now() + ": "+ jogada + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao registrar jogada no replay: " + e.getMessage());
        }

    }

    public static void  proxReplay() {
        num_replay++;
        String prefixo = "replay_jogo";
        arqAtual = prefixo + "_" + num_replay + ".txt";
        System.out.println("📁 Novo arquivo de replay definido: " + arqAtual);
    }
}
