import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Grafo grafo;
        try {
            grafo = LeitorArquivo.lerArquivo("C:\\Users\\vitor\\Grafo\\Dados");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a cidade de origem:");
        String origemNome = scanner.nextLine();
        System.out.println("Digite a cidade de destino:");
        String destinoNome = scanner.nextLine();

        Cidade origem = encontrarCidadePorNome(grafo, origemNome);
        Cidade destino = encontrarCidadePorNome(grafo, destinoNome);

        if (origem == null || destino == null) {
            System.out.println("Cidade de origem ou destino não encontrada.");
        } else {
            Rota resultado = Dijkstra.encontrarMenorCaminho(grafo, origem, destino);

            System.out.println("Menor rota encontrada:");
            System.out.println(resultado);

            System.out.println("Pontos turísticos ao longo do caminho:");
            for (Cidade cidade : resultado.getCaminho()) {
                System.out.println("Em " + cidade.getNome() + ":");
                for (PontoTuristico ponto : cidade.getPontosTuristicos()) {
                    System.out.println(" - " + ponto);
                }
            }
        }
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