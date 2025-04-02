import java.util.ArrayList;
import java.util.List;

class Cidade {
    private String nome;
    private List<PontoTuristico> pontosTuristicos;

    public Cidade(String nome) {
        this.nome = nome;
        this.pontosTuristicos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<PontoTuristico> getPontosTuristicos() {
        return pontosTuristicos;
    }

    public void adicionarPontoTuristico(PontoTuristico pontoTuristico) {
        pontosTuristicos.add(pontoTuristico);
    }

    @Override
    public String toString() {
        return nome;
    }
}