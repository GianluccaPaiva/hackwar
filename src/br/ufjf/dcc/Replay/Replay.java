package br.ufjf.dcc.Replay;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replay {

    // Classe interna estática privada para gerenciar a configuração e o índice do replay
    private static class ReplayConfig {
        private static final String CONFIG_PATH = "src/br/ufjf/dcc/Replay/arqJogo.json";
        private String currentFileName; // e.g., "replay_jogo_1.txt"
        private int index;           // e.g., 1

        public ReplayConfig() {
            this.currentFileName = "replay_jogo_1.txt";
            this.index = 1;
            carregarConfig(); // Carrega a config ao inicializar
        }

        // Extrai o número do índice de um nome de arquivo (e.g., replay_jogo_1.txt -> 1)
        private int extractIndex(String nome) {
            // Procura por _[número].
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

        // Carrega o caminho do arquivo de replay do JSON
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
                // Os valores padrão já definidos são usados se houver erro
            }
        }

        // Escreve o novo caminho do arquivo de replay no JSON
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
                // Mantém a parte base e substitui o número do índice
                String prefixo = currentFileName.substring(0, sub + 1);
                String sufixo = currentFileName.substring(ponto);
                novoNomeArquivo = prefixo + this.index + sufixo;
            } else {
                // Se o nome for simples, anexa o índice (caso de falha na estrutura inicial)
                novoNomeArquivo = currentFileName.substring(0, ponto) + "_" + this.index + currentFileName.substring(ponto);
            }

            this.currentFileName = novoNomeArquivo;
            System.out.println("🔁 Novo replay configurado: " + this.currentFileName);
            atualizarConfig(this.currentFileName);
        }

        // Renomeado de volta para manter compatibilidade com o método deletarTodosReplay
        public void resetConfigToInitial() {
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

        // Sanitização de Caminho (simplificada)
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

        // Cria o diretório se não existir
        if (dir != null && !dir.exists()) {
            boolean ok = dir.mkdirs();
            if (!ok) {
                System.out.println("⚠️ Não foi possível criar diretório: " + dir.getAbsolutePath());
            }
        }

        // Validação de nome de arquivo
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