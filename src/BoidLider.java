
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import static java.lang.Math.PI;
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
public class BoidLider extends Boid {

    public BoidLider(double x, double y) {
        super(x, y);
        velocidade = new Vetor(2, 0);
        localizaçao = new Vetor(x, y);
        velocidadeMaxima = 4.0;
        forçaMaxima = 0.1;
    }

    //cor diferente
    void draw(Graphics2D g, Vetor ancora) {
        AffineTransform save = g.getTransform();

        g.translate(localizaçao.x - ancora.x, localizaçao.y - ancora.y);
        g.rotate(velocidade.direçao() + PI / 2);
        g.setColor(Color.black);
        g.fill(FORMA_DO_BOID);
        g.setColor(Color.yellow);
        g.draw(FORMA_DO_BOID);

        g.setTransform(save);
    }

    void leTeclado() {

    }

    //nao tera comportamento de rebanho, sera controlado
    @Override
    void run(Graphics2D g, List<Boid> boids, int w, int h, Vetor ancora) {
        leTeclado();
        //System.out.println("Aceleração: " + aceleraçao);
        update(); //atualiza vetores conforme forças calculadas
        draw(g, ancora); //desenha
    }
}
