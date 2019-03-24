
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
    
    public void add10(){
        spawn(0, 0, 10);
    }
    
    public void remove10(){
        
    }

    Rebanho() {
        boids = new ArrayList<>();
        this.setLider(new BoidLider(0, 0));
        
    }

    void run(Graphics2D g, int w, int h) {
        for (Boid b : boids) {
            b.run(g, boids, w, h);
        }
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
        Rebanho rebanho = new Rebanho();
       
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
