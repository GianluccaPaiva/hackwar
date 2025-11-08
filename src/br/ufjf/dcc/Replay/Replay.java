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

        java.util.function.UnaryOperator<String> clean = s -> {
            if (s == null) return "";
            s = s.replace("\uFEFF", "");         // remove BOM
            s = s.trim();                        // remove espaços nas pontas
            s = s.replaceAll("^\"+|\"+$", "");   // remove aspas iniciais/finais sobrando
            s = s.replace("\\", "/");            // normaliza barras
            return s;
        };

        String raw = arqAtual;
        String cleaned = clean.apply(raw);

        java.util.LinkedHashSet<String> candidatos = new java.util.LinkedHashSet<>();
        if (!cleaned.isBlank()) candidatos.add(cleaned);
        if (!raw.equals(cleaned)) candidatos.add(raw);

        int slash = cleaned.lastIndexOf('/');
        if (slash != -1) {
            String somenteNome = cleaned.substring(slash + 1);
            candidatos.add(somenteNome);
            candidatos.add("src/" + somenteNome);
            candidatos.add("src/br/ufjf/dcc/Replay/" + somenteNome);
        } else {
            candidatos.add("src/" + cleaned);
            candidatos.add("src/br/ufjf/dcc/Replay/" + cleaned);
        }

        StringBuilder resultado = new StringBuilder();
        IOException ultimoErro = null;
        for (String cand : candidatos) {
            if (cand == null || cand.isBlank()) continue;
            System.out.println("▶ Tentando abrir: [" + cand + "]");
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(cand))) {
                String linha;
                int cont = 1;
                resultado.append("----- 🎮 Início do Replay (").append(cand).append(") -----\n");
                while ((linha = br.readLine()) != null) {
                    resultado.append(cont++).append("️⃣  ").append(linha).append("\n");
                }
                resultado.append("------ 🏁 Fim do Replay ------\n");

                // 👇 imprime o conteúdo acumulado aqui
                System.out.println(resultado.toString());
                System.out.println("✅ Replay lido com sucesso: " + cand);
                return;
            } catch (IOException e) {
                ultimoErro = e;
                System.out.println("   ❌ Falha ao abrir: " + e.getMessage());
            }
        }

        System.out.println("❌ Não foi possível abrir nenhum dos caminhos testados.");
        System.out.println("Caminhos tentados:");
        for (String c : candidatos) System.out.println("  - " + c);
        if (ultimoErro != null) System.out.println("Último erro: " + ultimoErro.getMessage());
        System.out.println("Dica: verifique se o arquivo existe, se o JSON não contém aspas extras/BOM,");
        System.out.println("e se chamou Replay.carregarConfig() antes de mostrar o replay.");
    }

}