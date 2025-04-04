import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LeitorArquivo {
    public static Grafo lerArquivo(String caminhoArquivo) throws IOException {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            throw new IOException("Arquivo não encontrado: " + caminhoArquivo);
        }

        Grafo grafo = new Grafo();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                String tipo = partes[0];
                if (tipo.equals("cidade")) {
                    Cidade cidade = new Cidade(partes[1]);
                    grafo.adicionarCidade(cidade);
                } else if (tipo.equals("ponto")) {
                    Cidade cidade = encontrarCidadePorNome(grafo, partes[1]);
                    if (cidade != null) {
                        PontoTuristico ponto = new PontoTuristico(partes[2]);
                        cidade.adicionarPontoTuristico(ponto);
                    }
                } else if (tipo.equals("rota")) {
                    Cidade origem = encontrarCidadePorNome(grafo, partes[1]);
                    Cidade destino = encontrarCidadePorNome(grafo, partes[2]);
                    int distancia = Integer.parseInt(partes[3]);
                    if (origem != null && destino != null) {
                        grafo.adicionarRota(origem, destino, distancia);
                    }
                }
            }
        }
        return grafo;
    }

    private static Cidade encontrarCidadePorNome(Grafo grafo, String nome) {
        for (Cidade cidade : grafo.getAdjacencias().keySet()) {
            if (cidade.getNome().equalsIgnoreCase(nome)) {
                return cidade;
            }
        }
        return null;
    }
}