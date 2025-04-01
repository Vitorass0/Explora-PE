import java.util.List;

class Rota {
    List<Cidade> caminho;
    int distanciaTotal;

    public Rota(List<Cidade> caminho, int distanciaTotal) {
        this.caminho = caminho;
        this.distanciaTotal = distanciaTotal;
    }

    @Override
    public String toString() {
        return "Caminho: " + caminho + " | Distância: " + distanciaTotal + " km";
    }
}
