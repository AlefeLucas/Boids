
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alefe
 */
public class Rebanho {

    private List<Boid> boids;
    private BoidLider lider;

    public BoidLider getLider() {
        return lider;
    }

    public void add10(double w, double h) {
        for (int i = 0; i < 10; i++) {
            Boid boid = new Boid(w, h);
            boid.setLider(this.getLider());
            this.addBoid(boid);
        }
    }

    public void remove10() {
        int remove = (10 < (boids.size() - 1) ? 10 : boids.size() - 1);
        int index = 0;
        while (remove > 0) {
            if (!(boids.get(index) instanceof BoidLider)) {
                boids.remove(index);
                remove--;
            } else {
                index++;
            }
        }
    }

    Rebanho(double w, double h) {
        boids = new ArrayList<>();
        this.setLider(new BoidLider(w, h));
    }

    void run(Graphics2D g, int w, int h, Camera camera) {
        
        Vetor ancora;
        switch (camera) {
            case LIDER:
                ancora = new Vetor(lider.localizaçao.x - (w / 2), lider.localizaçao.y - (h / 2));
                break;
            case REBANHO: 
                Vetor centroide = calculaCentroide();
                ancora = new Vetor(centroide.x - (w / 2), centroide.y - (h / 2));
                break;
            case CENTRO:
            default:
                ancora = new Vetor(0, 0);

        }

        for (Boid b : boids) {
            b.run(g, boids, w, h, ancora);
        }

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
