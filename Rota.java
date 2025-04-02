import java.util.List;

class Rota {
    List<Cidade> caminho;
    int distanciaTotal;

    public Rota(List<Cidade> caminho, int distanciaTotal) {
        this.caminho = caminho;
        this.distanciaTotal = distanciaTotal;
    }

    public List<Cidade> getCaminho() {
        return caminho;
    }

    public void setCaminho(List<Cidade> caminho) {
        this.caminho = caminho;
    }

    public int getDistanciaTotal() {
        return distanciaTotal;
    }

    public void setDistanciaTotal(int distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }

    @Override
    public String toString() {
        return "Caminho: " + caminho + " | Distância: " + distanciaTotal + " km";
    }
}
