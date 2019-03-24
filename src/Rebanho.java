
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rebanho {

    private static final int MAXIMO_DE_BOIDS = 200; //CUIDADO, se por muito seu CPU frita.
    private List<Boid> boids;
    private BoidLider lider;

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
    }

    void run(Graphics2D g, int w, int h, Camera camera) {

        Vetor ancora;
        Vetor centroide = calculaCentroide();
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

        for (Boid b : boids) {
            b.run(g, boids, w, h, ancora);
        }

        g.setColor(BoidsTeste.getContrastColor(g.getBackground()));
        g.setFont(Font.decode("Arial-BOLD-15"));
        g.drawString("Câmera: " + camera.toString().toLowerCase().replaceAll("_", " "), 15, 25);
        g.drawString("Líder: " + lider.localizaçao, 15, 50);
        g.drawString("Centróide: " + centroide, 15, 75);
        g.drawString("Rebanho: " + boids.size(), 15, 100);
        g.drawString(String.format("Velocidade do Líder: %.2f", lider.velocidade.modulo()), 15, 125);

    }

    private Vetor calculaCentroide() {
        Vetor centroide = new Vetor(lider.localizaçao.x, lider.localizaçao.y);
        for (Boid b : boids) {
            if (!(b instanceof BoidLider)) {
                centroide.somar(b.localizaçao);
            }
        }
        centroide.dividir(boids.size());
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