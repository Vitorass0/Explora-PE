import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MapaCidade extends JPanel {
    private final Cidade cidade;
    private final Grafo grafo;
    private final Map<Cidade, Point> coordenadas;
    private Cidade cidadeSelecionada = null;

    public MapaCidade(Grafo grafo, Cidade cidade) {
        this.grafo = grafo;
        this.cidade = cidade;
        this.coordenadas = gerarCoordenadas();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (Map.Entry<Cidade, Point> entry : coordenadas.entrySet()) {
                    Point p = entry.getValue();
                    int raio = 10;
                    if (e.getX() >= p.x - raio && e.getX() <= p.x + raio &&
                            e.getY() >= p.y - raio && e.getY() <= p.y + raio) {
                        cidadeSelecionada = entry.getKey();
                        mostrarPontosTuristicos(cidadeSelecionada);
                        repaint();
                        break;
                    }
                }
            }
        });
    }

    private Map<Cidade, Point> gerarCoordenadas() {
        Map<Cidade, Point> coordenadas = new HashMap<>();
        Random random = new Random();
        int largura = 800;
        int altura = 600;

        coordenadas.put(cidade, new Point(largura / 2, altura / 2)); // Cidade principal no centro
        for (Aresta aresta : grafo.getAdjacencias().get(cidade)) {
            int x = random.nextInt(largura - 100) + 50;
            int y = random.nextInt(altura - 100) + 50;
            coordenadas.put(aresta.destino, new Point(x, y));
        }
        return coordenadas;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.GRAY);
        for (Aresta aresta : grafo.getAdjacencias().get(cidade)) {
            Cidade destino = aresta.destino;
            Point p1 = coordenadas.get(cidade);
            Point p2 = coordenadas.get(destino);

            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            g2d.drawString(aresta.distancia + " km", (p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        }

        for (Cidade c : coordenadas.keySet()) {
            Point p = coordenadas.get(c);
            g2d.setColor(c.equals(cidade) ? Color.RED : Color.BLUE);
            g2d.fillOval(p.x - 10, p.y - 10, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawString(c.getNome(), p.x + 12, p.y);
        }
    }

    private void mostrarPontosTuristicos(Cidade cidade) {
        if (cidade.getPontosTuristicos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum ponto turístico encontrado em " + cidade.getNome(), "Pontos Turísticos", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder mensagem = new StringBuilder("Pontos turísticos em " + cidade.getNome() + ":\n");
            for (PontoTuristico ponto : cidade.getPontosTuristicos()) {
                mensagem.append(" - ").append(ponto.getNome()).append("\n");
            }
            JOptionPane.showMessageDialog(this, mensagem.toString(), "Pontos Turísticos", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void exibirMapaCidade(Grafo grafo, Cidade cidade) {
        JFrame frame = new JFrame("Grafo da Cidade: " + cidade.getNome());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.add(new MapaCidade(grafo, cidade));
        frame.setVisible(true);
    }
}
