import java.util.*;

class Grafo {
    private Map<Cidade, List<Aresta>> adjacencias;

    public Grafo() {
        this.adjacencias = new HashMap<>();
    }

    public void adicionarCidade(Cidade cidade) {
        adjacencias.putIfAbsent(cidade, new ArrayList<>());
    }

    public void adicionarRota(Cidade origem, Cidade destino, int distancia) {
        adjacencias.get(origem).add(new Aresta(destino, distancia));
        adjacencias.get(destino).add(new Aresta(origem, distancia)); // Grafo não-direcionado
    }

    public Map<Cidade, List<Aresta>> getAdjacencias() {
        return adjacencias;
    }
}
