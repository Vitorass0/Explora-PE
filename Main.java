import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo();
        Scanner scanner = new Scanner(System.in);
        // Criando cidades
        Cidade a = new Cidade("São Paulo");
        Cidade b = new Cidade("Rio de Janeiro");
        Cidade c = new Cidade("Belo Horizonte");
        Cidade d = new Cidade("Curitiba");

        // Adicionando cidades ao grafo
        grafo.adicionarCidade(a);
        grafo.adicionarCidade(b);
        grafo.adicionarCidade(c);
        grafo.adicionarCidade(d);

        // Adicionando rotas entre cidades
        grafo.adicionarRota(a, b, 430); // SP -> RJ
        grafo.adicionarRota(a, c, 580); // SP -> BH
        grafo.adicionarRota(b, c, 340); // RJ -> BH
        grafo.adicionarRota(a, d, 408); // SP -> Curitiba
        grafo.adicionarRota(d, c, 1000); // Curitiba -> BH

        // Testando menor rota de São Paulo para Belo Horizonte
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

            // Exibir resultado
            System.out.println("Menor rota encontrada:");
            System.out.println(resultado);
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