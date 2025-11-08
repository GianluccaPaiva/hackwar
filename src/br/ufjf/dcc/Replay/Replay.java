package br.ufjf.dcc.Replay;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import  java.io.FileWriter;


public class Replay {
    private static int indice;
    private static String arqBase;
    private static String arqAtual;

    private static int extraiNumero(String nome) {
        try {
            int sub = nome.lastIndexOf("_");
            int ponto = nome.lastIndexOf(".");
            if (sub != -1 && ponto != -1) {
                String numStr = nome.substring(sub + 1, ponto);
                return Integer.parseInt(numStr);
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao extrair número do nome do arquivo: " + e.getMessage());
        }
        return -1;
    }


    public static void carregarConfig() {
        String caminhoJson = "src/br/ufjf/dcc/Replay/arqJogo.json";
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoJson))) {
            String linha = br.readLine();
            if (linha != null && linha.contains(":")) {
                int inicio = linha.indexOf(":") + 2;
                int fim = linha.lastIndexOf("\"");
                arqBase = linha.substring(inicio, fim).replace("\\", "/");
                arqAtual = arqBase;
                indice = extraiNumero(arqBase);
                if (indice == -1) indice = 1;
                System.out.println("✅ Caminho base carregado: " + arqBase);
            } else {
                throw new IOException("Formato de JSON inválido");
            }
        } catch (IOException e) {
            System.out.println("❌ Erro ao ler config: " + e.getMessage());
            arqBase = "src/replay_jogo_1.txt";
            arqAtual = arqBase;
            indice = 1;
        }
    }


    public static void registrar(String conteudo) {
        try {
            if (arqAtual == null) {
                System.out.println("❌ arqAtual é null — chame carregarConfig() antes.");
                return;
            }


            String caminho = arqAtual.replace("\uFEFF", "")
                    .trim()
                    .replaceAll("^\"+|\"+$", "")
                    .replace("\\", "/");


            if (caminho.isBlank()) {
                System.out.println("❌ Caminho resultante vazio após sanitização: '" + arqAtual + "'");
                return;
            }


            System.out.println("▶ Tentando gravar em: [" + caminho + "]");


            java.io.File arquivo = new java.io.File(caminho);
            java.io.File dir = arquivo.getParentFile();
            if (dir != null && !dir.exists()) {
                boolean ok = dir.mkdirs();
                if (!ok) {
                    System.out.println("⚠️ Não foi possível criar diretório: " + dir.getAbsolutePath());

                }
            }


            String nomeArquivo = arquivo.getName();
            if (nomeArquivo.contains("\"") || nomeArquivo.contains(":\"") || nomeArquivo.startsWith("\"")) {
                System.out.println("❌ Nome do arquivo contém caracteres inválidos: " + nomeArquivo);
                return;
            }

            try (java.io.FileWriter writer = new java.io.FileWriter(arquivo, true)) {
                writer.write(conteudo + System.lineSeparator());
                System.out.println("📝 Registrado em: " + arquivo.getAbsolutePath());
            }

        } catch (java.io.IOException e) {
            System.out.println("❌ Erro ao registrar replay: " + e.getMessage());
        }
    }



    public static void novoReplay() {
        indice++;
        int sub = arqBase.lastIndexOf("_");
        int ponto = arqBase.lastIndexOf(".");
        if (sub != -1 && ponto != -1) {
            arqAtual = arqBase.substring(0, sub + 1) + indice + arqBase.substring(ponto);
        } else {
            arqAtual = arqBase + indice;
        }

        System.out.println("🔁 Novo replay criado: " + arqAtual);

        // Atualiza o JSON corretamente
        String caminhoJson = "src/br/ufjf/dcc/Replay/arqJogo.json";
        try (FileWriter fw = new FileWriter(caminhoJson)) {
            String json = "{\"nomeArqReplay\": \"" + arqAtual + "\"}";
            fw.write(json);
            fw.flush();
            System.out.println("💾 JSON atualizado: " + caminhoJson);
        } catch (IOException e) {
            System.out.println("❌ Erro ao atualizar JSON: " + e.getMessage());
        }
    }

    public static void reiniciarListaReplay(){
        indice = 1;
        arqAtual = arqBase;
        System.out.println("🔄 Replay reiniciado para: " + arqAtual);
    }

    public static void mostrarReplay() {
        System.out.println("📂 Replay atual (raw): " + arqAtual);

        if (arqAtual == null || arqAtual.isBlank()) {
            System.out.println("⚠️ arqAtual vazio. Chame Replay.carregarConfig() antes.");
            return;
        }

        // Normaliza o caminho
        String caminho = arqAtual
                .replace("\uFEFF", "")   // remove BOM
                .trim()                  // tira espaços
                .replaceAll("^\"+|\"+$", "") // remove aspas sobressalentes
                .replace("\\", "/");     // normaliza barras

        System.out.println("📁 Caminho final: " + caminho);

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(caminho))) {
            String linha;
            int contador = 1;
            System.out.println("----- 🎮 Início do Replay -----");

            while ((linha = br.readLine()) != null) {
                System.out.println(contador++ + "️⃣  " + linha);
            }

            System.out.println("------ 🏁 Fim do Replay ------");
            System.out.println("✅ Replay lido com sucesso: " + caminho);
        } catch (java.io.IOException e) {
            System.out.println("❌ Erro ao ler replay: " + e.getMessage());
            System.out.println("⚠️ Dica: verifique se o arquivo existe em relação ao diretório do projeto.");
        }
    }

}