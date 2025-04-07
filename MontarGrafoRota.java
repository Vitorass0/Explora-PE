import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MontarGrafoRota extends JPanel {
    private final List<Cidade> rota;
    private final Grafo grafo;
    private final Map<Cidade, Point> coordenadas;
    private Cidade cidadeSelecionada = null;

    public MontarGrafoRota(Grafo grafo, List<Cidade> rota) {
        this.grafo = grafo;
        this.rota = rota;
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

        for (Cidade cidade : rota) {
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
        for (int i = 0; i < rota.size() - 1; i++) {
            Cidade origem = rota.get(i);
            Cidade destino = rota.get(i + 1);
            Point p1 = coordenadas.get(origem);
            Point p2 = coordenadas.get(destino);

            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);

            for (Aresta aresta : grafo.getAdjacencias().get(origem)) {
                if (aresta.destino.equals(destino)) {
                    int mx = (p1.x + p2.x) / 2;
                    int my = (p1.y + p2.y) / 2;
                    g2d.drawString(aresta.distancia + " km", mx, my);
                    break;
                }
            }
        }

        for (Cidade cidade : rota) {
            Point p = coordenadas.get(cidade);
            if (cidade.equals(rota.get(0))) {
                g2d.setColor(Color.GREEN); // Origem
            } else if (cidade.equals(rota.get(rota.size() - 1))) {
                g2d.setColor(Color.RED); // Destino
            } else {
                g2d.setColor(Color.BLUE);
            }

            g2d.fillOval(p.x - 10, p.y - 10, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawString(cidade.getNome(), p.x + 12, p.y);

            // Destacar a cidade selecionada
            if (cidade.equals(cidadeSelecionada)) {
                g2d.setColor(Color.ORANGE);
                g2d.drawOval(p.x - 12, p.y - 12, 24, 24);
            }
        }
    }

    private void mostrarPontosTuristicos(Cidade cidade) {
        if (cidade.getPontosTuristicos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum ponto turístico cadastrado em " + cidade.getNome(), "Pontos Turísticos", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder mensagem = new StringBuilder("Pontos turísticos em " + cidade.getNome() + ":\n");
            for (PontoTuristico ponto : cidade.getPontosTuristicos()) {
                mensagem.append(" - ").append(ponto.getNome()).append("\n");
            }
            JOptionPane.showMessageDialog(this, mensagem.toString(), "Pontos Turísticos", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void exibirGrafoRota(Grafo grafo, List<Cidade> rota) {
        JFrame frame = new JFrame("Grafo da Rota");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.add(new MontarGrafoRota(grafo, rota));
        frame.setVisible(true);
    }
}
