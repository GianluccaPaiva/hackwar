package br.ufjf.dcc.Replay;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replay {

    private static class ReplayConfig {
        private static final String CONFIG_PATH = "src/br/ufjf/dcc/Replay/arqJogo.json";
        private String currentFileName;
        private int index;

        public ReplayConfig() {
            this.currentFileName = "replay_jogo_1.txt";
            this.index = 1;
            carregarConfig();
        }

        private void clearReplayFile(String caminho) {
            String caminhoSanitizado = caminho
                    .replace("\uFEFF", "")
                    .trim()
                    .replaceAll("^\"+|\"+$", "")
                    .replace("\\", "/");

            File arquivo = new File(caminhoSanitizado);

            try {
                // Se o arquivo não existir, tenta criar
                if (!arquivo.exists()) {
                    File dir = arquivo.getParentFile();
                    if (dir != null && !dir.exists()) {
                        dir.mkdirs();
                    }
                    if (arquivo.createNewFile()) {
                        System.out.println("✅ Arquivo " + caminhoSanitizado + " criado.");
                    }
                }

                // Sobrescreve o arquivo com uma string vazia (segundo argumento 'false' do FileWriter)
                try (FileWriter writer = new FileWriter(arquivo, false)) {
                    writer.write("");
                    System.out.println("✅ Conteúdo do arquivo " + caminhoSanitizado + " apagado.");
                }

            } catch (IOException e) {
                System.out.println("❌ Erro ao tentar limpar/criar arquivo de replay: " + e.getMessage());
            }
        }

        private int extractIndex(String nome) {

            Pattern pattern = Pattern.compile("(_)(\\d+)(\\.)");
            Matcher matcher = pattern.matcher(nome);
            if (matcher.find()) {
                try {
                    return Integer.parseInt(matcher.group(2));
                } catch (NumberFormatException e) {
                    System.out.println("❌ Erro ao converter número do índice: " + nome);
                }
            }
            return 1; // Índice padrão
        }


        private void carregarConfig() {
            try (BufferedReader br = new BufferedReader(new FileReader(CONFIG_PATH))) {
                String linha = br.readLine();
                if (linha != null && linha.contains(":")) {
                    int inicio = linha.indexOf(":") + 2;
                    int fim = linha.lastIndexOf("\"");
                    String fileFromJson = linha.substring(inicio, fim).replace("\\", "/").trim();
                    this.currentFileName = fileFromJson;
                    this.index = extractIndex(fileFromJson);
                    System.out.println("✅ Caminho base carregado: " + this.currentFileName);
                } else {
                    throw new IOException("Formato de JSON inválido: " + linha);
                }
            } catch (IOException e) {
                System.out.println("❌ Erro ao ler config: " + e.getMessage() + ". Usando padrão: " + this.currentFileName);

            }
        }


        private void atualizarConfig(String novoNomeArquivo) {
            try (FileWriter fw = new FileWriter(CONFIG_PATH)) {
                String json = "{\"nomeArqReplay\": \"" + novoNomeArquivo + "\"}";
                fw.write(json);
                fw.flush();
                System.out.println("💾 JSON atualizado: " + CONFIG_PATH);
            } catch (IOException e) {
                System.out.println("❌ Erro ao atualizar JSON: " + e.getMessage());
            }
        }

        public String getCurrentFilePath() {
            return currentFileName;
        }

        public int getCurrentIndex() {
            return index;
        }

        // Cria o caminho para o próximo arquivo de replay (incrementa o índice)
        public void nextReplay() {
            this.index++;
            int sub = currentFileName.lastIndexOf("_");
            int ponto = currentFileName.lastIndexOf(".");
            String novoNomeArquivo;

            if (sub != -1 && ponto != -1) {

                String prefixo = currentFileName.substring(0, sub + 1);
                String sufixo = currentFileName.substring(ponto);
                novoNomeArquivo = prefixo + this.index + sufixo;
            } else {

                novoNomeArquivo = currentFileName.substring(0, ponto) + "_" + this.index + currentFileName.substring(ponto);
            }

            this.currentFileName = novoNomeArquivo;
            System.out.println("🔁 Novo replay configurado: " + this.currentFileName);
            atualizarConfig(this.currentFileName);
        }


        public void resetConfigToInitial() {
            // CORREÇÃO: Chama o método de limpeza de arquivo antes de resetar a configuração.
            clearReplayFile("replay_jogo_1.txt");

            this.currentFileName = "replay_jogo_1.txt";
            this.index = 1;
            System.out.println("⚙️ Configuração de replay resetada para: " + this.currentFileName);
            atualizarConfig(this.currentFileName);
        }
    }

    private static ReplayConfig config = new ReplayConfig();

    public static void reproduzirReplay(String caminhoReplay){
        mostrarReplay(caminhoReplay);
    }

    public static void registrar(String conteudo) {
        String caminho = config.getCurrentFilePath();


        caminho = caminho
                .replace("\uFEFF", "")
                .trim()
                .replaceAll("^\"+|\"+$", "")
                .replace("\\", "/");

        if (caminho.isBlank()) {
            System.out.println("❌ Caminho do arquivo de replay está vazio.");
            return;
        }

        System.out.println("▶ Tentando gravar em: [" + caminho + "]");
        File arquivo = new File(caminho);
        File dir = arquivo.getParentFile();


        if (dir != null && !dir.exists()) {
            boolean ok = dir.mkdirs();
            if (!ok) {
                System.out.println("⚠️ Não foi possível criar diretório: " + dir.getAbsolutePath());
            }
        }


        String nomeArquivo = arquivo.getName();
        if (nomeArquivo.contains("\"") || nomeArquivo.contains(":\"")) {
            System.out.println("❌ Nome do arquivo contém caracteres inválidos: " + nomeArquivo);
            return;
        }

        try (FileWriter writer = new FileWriter(arquivo, true)) {
            writer.write(conteudo + System.lineSeparator());
            System.out.println("📝 Registrado em: " + arquivo.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("❌ Erro ao registrar replay: " + e.getMessage());
        }
    }

    public static void novoReplay() {
        config.nextReplay();
    }


    public static void deletarTodosReplay() {
        config.resetConfigToInitial();
    }

    public static void mostrarReplay(String caminhoReplay) {
        String caminho = caminhoReplay
                .replace("\uFEFF", "")
                .trim()
                .replaceAll("^\"+|\"+$", "")
                .replace("\\", "/");

        System.out.println("📁 Caminho final para leitura: " + caminho);

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int contador = 1;
            System.out.println("----- 🎮 Início do Replay -----");

            while ((linha = br.readLine()) != null) {
                System.out.println(contador++ + "️⃣  " + linha);
            }

            System.out.println("------ 🏁 Fim do Replay ------");
            System.out.println("✅ Replay lido com sucesso: " + caminho);
        } catch (IOException e) {
            System.out.println("❌ Erro ao ler replay: " + e.getMessage());
            System.out.println("⚠️ Dica: verifique se o arquivo existe em relação ao diretório do projeto.");
        }
    }

    public static int getIndice(){
        return config.getCurrentIndex();
    }
}