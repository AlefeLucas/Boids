
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Rebanho {

    private static final int MAXIMO_DE_BOIDS = 200; //CUIDADO, se por muito seu CPU frita.
    private List<Boid> boids;
    private BoidLider lider;
    private List<Vetor> rastro;
    private boolean drawRastro;
    private boolean drawHUD;

    public void setDrawRastro(boolean drawRastro) {
        this.drawRastro = drawRastro;
        if (!drawRastro) {
            rastro.clear();
        }
    }

    public BoidLider getLider() {
        return lider;
    }

    public void add10(double w, double h) {
        for (int i = 0; i < 10 && boids.size() < MAXIMO_DE_BOIDS; i++) {
            Boid boid = new Boid(lider.localizaçao.x, lider.localizaçao.y);
            boid.setLider(this.getLider());
            this.addBoid(boid);
        }
    }

    public void remove10() {
        int remove = (10 < (boids.size() - 1) ? 10 : boids.size() - 1);
        Random r = new Random();
        int index = r.nextInt(boids.size());
        while (remove > 0) {
            if (!(boids.get(index) instanceof BoidLider)) {
                boids.remove(index);
                remove--;
            }
            index = r.nextInt(boids.size());
        }
    }

    Rebanho(double w, double h) {
        boids = new ArrayList<>();
        this.setLider(new BoidLider(w, h));
        this.rastro = new LinkedList<>();
    }

    void run(Graphics2D g, int w, int h, Camera camera) {

        Vetor centroide = calculaCentroide();
        Vetor ancora = calculaAncora(camera, w, h, centroide);
        if (drawRastro) {
            calculaRastro(centroide);
            drawRastro(g, ancora);
        }

        for (Boid b : boids) {
            b.run(g, boids, w, h, ancora);
        }

        if (drawHUD) {
            drawHUD(g, camera, centroide);
        }

    }

    public boolean isDrawHUD() {
        return drawHUD;
    }

    public void setDrawHUD(boolean drawHUD) {
        this.drawHUD = drawHUD;
    }

    public boolean isDrawRastro() {
        return drawRastro;
    }

    private Vetor calculaAncora(Camera camera, int w, int h, Vetor centroide) {
        Vetor ancora;
        switch (camera) {
            case LIDER:
                ancora = new Vetor(lider.localizaçao.x - (w / 2), lider.localizaçao.y - (h / 2));

                break;
            case REBANHO:

                ancora = new Vetor(centroide.x - (w / 2), centroide.y - (h / 2));
                break;
            case MEIO_REBANHO_LIDER:
                ancora = new Vetor(lider.localizaçao.x - (w / 2), lider.localizaçao.y - (h / 2));
                Vetor a = new Vetor(centroide.x - (w / 2), centroide.y - (h / 2));
                ancora.somar(a);
                ancora.dividir(2);
                break;
            case CENTRO:
            default:
                double x = 0,
                 y = 0;

                ancora = new Vetor(x, y);

        }
        return ancora;
    }

    private void drawHUD(Graphics2D g, Camera camera, Vetor centroide) {
        g.setColor(BoidsTeste.getContrastColor(g.getBackground()));
        g.setFont(Font.decode("Arial-BOLD-15"));
        g.drawString("Câmera: " + camera.toString().toLowerCase().replaceAll("_", " "), 15, 25);
        g.drawString("Líder: " + lider.localizaçao, 15, 50);
        g.drawString("Centróide: " + centroide, 15, 75);
        g.drawString("Rebanho: " + boids.size(), 15, 100);
        g.drawString(String.format("Velocidade do Líder: %.2f", lider.velocidade.modulo()), 15, 125);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        g.drawString("Teclas:", 15, screenSize.height - 250);
        g.drawString("ESC  -  sair", 30, screenSize.height - 225);
        g.drawString("H  -  mostra/esconde HUD", 30, screenSize.height - 200);
        g.drawString("B  -  muda cor de fundo", 30, screenSize.height - 175);
        g.drawString("Setas  -  mover líder", 30, screenSize.height - 150);
        g.drawString("V  -  câmera", 30, screenSize.height - 125);
        g.drawString("R  -  rastro do rebanho", 30, screenSize.height - 100);
        g.drawString("S  -  parar líder", 30, screenSize.height - 75);
        g.drawString("+  -  adiciona 10 boids", 30, screenSize.height - 50);
        g.drawString("\"-\"  -  retira 10 boids", 30, screenSize.height - 25);
    }

    private void drawRastro(Graphics2D g, Vetor ancora) {
        Color base = BoidsTeste.getContrastColor(g.getBackground());

        for (int i = 0; i < rastro.size(); i++) {
            Vetor vetor = rastro.get(i);
            Color cor = new Color(base.getRed(), base.getGreen(), base.getBlue(), (int) (((rastro.size() - i) / ((double) rastro.size())) * 255.0));
            g.setColor(cor);
            g.fillOval((int) ((vetor.x - 2) - ancora.x), (int) ((vetor.y - 2) - ancora.y), 4, 4);
        }
    }

    private void calculaRastro(Vetor centroide) {
        if (rastro.size() < 1200) {
            rastro.add(0, centroide);
        } else {
            rastro.remove(rastro.size() - 1);
            rastro.add(0, centroide);
        }
    }

    private Vetor calculaCentroide() {
        Vetor centroide = new Vetor(lider.localizaçao.x, lider.localizaçao.y);
        for (Boid b : boids) {
            if (!(b instanceof BoidLider)) {
                centroide.somar(b.localizaçao);
            }
        }
        centroide.subtrair(lider.localizaçao);
        centroide.dividir(boids.size() - 1);
        return centroide;
    }

    boolean saiuDoCenario(int w, int h) {
        int count = 0;
        for (Boid b : boids) {
            if (b.localizaçao.x + Boid.TAMANHO > w || b.localizaçao.x < 0 || b.localizaçao.y < 0 || b.localizaçao.y + Boid.TAMANHO > h) {
                count++;
            }
        }
        return boids.size() == count;
    }

    void addBoid(Boid b) {
        boids.add(b);
    }

    static Rebanho spawn(double w, double h, int numBoids) {
        Rebanho rebanho = new Rebanho(w, h);

        for (int i = 0; i < numBoids; i++) {
            Boid boid = new Boid(w, h);
            boid.setLider(rebanho.getLider());
            rebanho.addBoid(boid);
        }

        return rebanho;
    }

    private void setLider(BoidLider lider) {
        this.addBoid(lider);
        this.lider = lider;
    }
}
