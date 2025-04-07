import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MontarGrafo extends JPanel {
    private final Grafo grafo;
    private final Map<Cidade, Point> coordenadas;

    public MontarGrafo(Grafo grafo) {
        this.grafo = grafo;
        this.coordenadas = gerarCoordenadas();
    }

    private Map<Cidade, Point> gerarCoordenadas() {
        Map<Cidade, Point> coordenadas = new java.util.HashMap<>();
        Random random = new Random();
        int largura = 1400; // Ajuste da largura
        int altura = 800;  // Ajuste da altura

        for (Cidade cidade : grafo.getAdjacencias().keySet()) {
            int x = random.nextInt(largura - 100) + 50;
            int y = random.nextInt(altura - 100) + 50;
            coordenadas.put(cidade, new Point(x, y));
        }
        return coordenadas;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.GRAY);
        for (Map.Entry<Cidade, List<Aresta>> entry : grafo.getAdjacencias().entrySet()) {
            Cidade origem = entry.getKey();
            Point p1 = coordenadas.get(origem);

            for (Aresta aresta : entry.getValue()) {
                Cidade destino = aresta.destino;
                Point p2 = coordenadas.get(destino);

                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        g2d.setColor(Color.BLUE);
        for (Map.Entry<Cidade, Point> entry : coordenadas.entrySet()) {
            Cidade cidade = entry.getKey();
            Point p = entry.getValue();

            g2d.fillOval(p.x - 10, p.y - 10, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawString(cidade.getNome(), p.x + 12, p.y);
            g2d.setColor(Color.BLUE);
        }
    }

    public static void exibirGrafo(Grafo grafo) {
        JFrame frame = new JFrame("Visualização do Grafo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.add(new MontarGrafo(grafo));
        frame.setVisible(true);
    }
}
