import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Grafo grafo;
        try {
            grafo = LeitorArquivo.lerArquivo("C:\\Users\\nicol\\IdeaProjects\\Explora-PE\\Dados"); // Ajuste o caminho conforme necessário
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nESCOLHA UMA OPÇÃO:");
            System.out.println("1: Explorar PE");
            System.out.println("2: Pesquisar cidade");
            System.out.println("3: Exibir grafo completo");
            System.out.println("4: Sair");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    System.out.print("Cidade de origem (ou ponto turístico): ");
                    String origemNome = scanner.nextLine();
                    System.out.print("Cidade de destino (ou ponto turístico): ");
                    String destinoNome = scanner.nextLine();

                    Cidade origem = encontrarCidadePorNomeOuPonto(grafo, origemNome);
                    Cidade destino = encontrarCidadePorNomeOuPonto(grafo, destinoNome);

                    if (origem == null || destino == null) {
                        System.out.println("Cidade ou ponto turístico não encontrado.");
                    } else {
                        Rota resultado = Dijkstra.encontrarMenorCaminho(grafo, origem, destino);
                        System.out.println("\nMenor rota encontrada: " + resultado);

                        System.out.println("\nPontos turísticos ao longo do caminho:");
                        for (Cidade cidade : resultado.getCaminho()) {
                            if (!cidade.getPontosTuristicos().isEmpty()) {
                                System.out.println("Em " + cidade.getNome() + ":");
                                for (PontoTuristico ponto : cidade.getPontosTuristicos()) {
                                    System.out.println(" - " + ponto);
                                }
                            }
                        }

                        System.out.println("\nDeseja ver o mapa da rota?");
                        System.out.println("1: Sim");
                        System.out.println("2: Não");
                        System.out.print("Opção: ");
                        int opcaoMapa = scanner.nextInt();
                        scanner.nextLine();

                        if (opcaoMapa == 1) {
                            MontarGrafoRota.exibirGrafoRota(grafo, resultado.getCaminho());
                        }
                    }
                    break;

                case 2:
                    System.out.print("Digite o nome da cidade: ");
                    String nomeCidade = scanner.nextLine();
                    Cidade cidade = encontrarCidadePorNomeOuPonto(grafo, nomeCidade); // Ajustado!

                    if (cidade == null) {
                        System.out.println("Cidade não encontrada.");
                        break;
                    }

                    List<Aresta> conexoes = grafo.getAdjacencias().get(cidade);
                    System.out.println("\nCidades mais próximas por rodovias:");
                    for (Aresta aresta : conexoes) {
                        System.out.println(" - " + aresta.destino.getNome() + " (" + aresta.distancia + " km)");
                    }

                    System.out.println("\nPontos turísticos:");
                    for (PontoTuristico pt : cidade.getPontosTuristicos()) {
                        System.out.println(" - " + pt.getNome());
                    }

                    System.out.println("\nDeseja exibir o mapa da cidade?");
                    System.out.println("1: Sim");
                    System.out.println("2: Não");
                    System.out.print("Opção: ");
                    int escolhaCidade = scanner.nextInt();
                    scanner.nextLine();

                    if (escolhaCidade == 1) {
                        MapaCidade.exibirMapaCidade(grafo, cidade);
                    }
                    break;

                case 3:
                    MontarGrafo.exibirGrafo(grafo);
                    break;

                case 4:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
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
