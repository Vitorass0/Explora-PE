import java.util.*;

class Dijkstra {
    public static Rota encontrarMenorCaminho(Grafo grafo, Cidade origem, Cidade destino) {
        Map<Cidade, Integer> distancias = new HashMap<>();
        Map<Cidade, Cidade> predecessores = new HashMap<>();
        PriorityQueue<Caminho> filaPrioridade = new PriorityQueue<>(Comparator.comparingInt(c -> c.distancia));
        Set<Cidade> visitados = new HashSet<>();

        for (Cidade cidade : grafo.getAdjacencias().keySet()) {
            distancias.put(cidade, Integer.MAX_VALUE);
        }
        distancias.put(origem, 0);
        filaPrioridade.add(new Caminho(origem, 0));

        while (!filaPrioridade.isEmpty()) {
            Cidade atual = filaPrioridade.poll().cidade;
            if (!visitados.add(atual)) continue;

            for (Aresta aresta : grafo.getAdjacencias().get(atual)) {
                Cidade vizinho = aresta.destino;
                int novaDistancia = distancias.get(atual) + aresta.distancia;

                if (novaDistancia < distancias.get(vizinho)) {
                    distancias.put(vizinho, novaDistancia);
                    predecessores.put(vizinho, atual);
                    filaPrioridade.add(new Caminho(vizinho, novaDistancia));
                }
            }
        }
        List<Cidade> caminho = reconstruirCaminho(predecessores, origem, destino);
        return new Rota(caminho, distancias.get(destino));
    }

    private static List<Cidade> reconstruirCaminho(Map<Cidade, Cidade> predecessores, Cidade origem, Cidade destino) {
        List<Cidade> caminho = new ArrayList<>();
        for (Cidade at = destino; at != null; at = predecessores.get(at)) {
            caminho.add(at);
        }
        Collections.reverse(caminho);
        return caminho;
    }
}
