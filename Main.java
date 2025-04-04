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
        while (true) {
            System.out.println("\n\n");
            System.out.println("1. Explorar  PE");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            if (opcao == 1) {
                System.out.println("Digite o ponto de partida (ponto turístico ou cidade):");
                String origemNome = scanner.nextLine();
                System.out.println("Digite o ponto de destino (ponto turístico ou cidade):");
                String destinoNome = scanner.nextLine();

                Cidade origem = encontrarCidadePorNomeOuPonto(grafo, origemNome);
                Cidade destino = encontrarCidadePorNomeOuPonto(grafo, destinoNome);

                if (origem == null || destino == null) {
                    System.out.println("Cidade ou ponto turístico de origem ou destino não encontrado.");
                } else {
                    Rota resultado = Dijkstra.encontrarMenorCaminho(grafo, origem, destino);

                    System.out.println("Menor rota encontrada:");
                    System.out.println(resultado);

                    System.out.println("Pontos turísticos ao longo do caminho:");
                    for (Cidade cidade : resultado.getCaminho()) {
                        if (!cidade.getPontosTuristicos().isEmpty()) {
                            System.out.println("Em " + cidade.getNome() + ":");
                            for (PontoTuristico ponto : cidade.getPontosTuristicos()) {
                                System.out.println(" - " + ponto);
                            }
                        }
                    }
                }
            } else if (opcao == 2) {
                System.out.println("Saindo...");
                break;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static Cidade encontrarCidadePorNomeOuPonto(Grafo grafo, String nome) {
        Cidade cidade = encontrarCidadePorNome(grafo, nome);
        if (cidade != null) {
            return cidade;
        }
        return encontrarCidadePorPontoTuristico(grafo, nome);
    }

    private static Cidade encontrarCidadePorNome(Grafo grafo, String nome) {
        for (Cidade cidade : grafo.getAdjacencias().keySet()) {
            if (cidade.getNome().equalsIgnoreCase(nome)) {
                return cidade;
            }
        }
        return null;
    }

    private static Cidade encontrarCidadePorPontoTuristico(Grafo grafo, String nome) {
        for (Cidade cidade : grafo.getAdjacencias().keySet()) {
            for (PontoTuristico ponto : cidade.getPontosTuristicos()) {
                if (ponto.getNome().equalsIgnoreCase(nome)) {
                    return cidade;
                }
            }
        }
        return null;
    }
}