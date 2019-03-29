
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import static java.lang.Math.PI;
import java.util.List;
import java.util.Random;
/**
 * A luana é gay
 * @author alefe
 */
public class Boid {

    static final Random RANDOM = new Random();
    static final int TAMANHO = 6;
    static final Path2D FORMA_DO_BOID = new Path2D.Double();

    //Define o formato do Boid
    static {

        FORMA_DO_BOID.moveTo(0, -TAMANHO * 2);
        FORMA_DO_BOID.lineTo(-TAMANHO, TAMANHO * (3f / 2));
        FORMA_DO_BOID.lineTo(-TAMANHO * (2f / 3), TAMANHO * (3f / 2) * (2f / 3));
        FORMA_DO_BOID.lineTo(TAMANHO * (2f / 3), TAMANHO * (3f / 2) * (2f / 3));
        FORMA_DO_BOID.lineTo(TAMANHO, TAMANHO * (3f / 2));
        FORMA_DO_BOID.closePath();

        FORMA_DO_BOID.closePath();
    }

    double forçaMaxima, velocidadeMaxima;
    Vetor localizaçao;
    BoidLider lider;

    Vetor velocidade, aceleraçao;
    private boolean avistado = true;

    Boid(double x, double y) {
        aceleraçao = new Vetor();
        velocidade = new Vetor(RANDOM.nextDouble() * 10 - 5, RANDOM.nextDouble() * 10 - 5);
        localizaçao = new Vetor(x, y);
        velocidadeMaxima = 3;
        forçaMaxima = 0.05;
    }

    public void setLider(BoidLider lider) {
        this.lider = lider;
    }

    void update() {
        velocidade.somar(aceleraçao);
        velocidade.limitar(velocidadeMaxima);
        localizaçao.somar(velocidade);
        aceleraçao.multiplicar(0); //reseta aceleração, é calculada no rebanho()
    }

    void aplicarForça(Vetor força) {
        aceleraçao.somar(força);
    }

    //nao entendi exatamente
    Vetor seek(Vetor alvo) {
        Vetor rumo = Vetor.subtrair(alvo, localizaçao);
        rumo.normalizar();
        rumo.multiplicar(velocidadeMaxima);
        rumo.subtrair(velocidade);
        rumo.limitar(forçaMaxima);
        return rumo;
    }

    /**
     * calcula o movimento de rebanho, baseado no que este boid vê
     */
    void rebanho(Graphics2D g, List<Boid> boids) {
        visao(g, boids);

        Vetor rule1 = separacao(boids); //para que eles não andem um em cima do outro
        Vetor rule2 = alinhamento(boids);
        Vetor rule3 = coesao(boids);
        Vetor rule4 = seguirLider();
        //pesos
        rule1.multiplicar(2.5);
        rule2.multiplicar(1.5);
        rule3.multiplicar(1.3);
        rule4.multiplicar(1);

        aplicarForça(rule1);
        aplicarForça(rule2);
        aplicarForça(rule3);

        aplicarForça(rule4);

    }

    private Vetor seguirLider() {
        Vetor v = new Vetor(0, 0);
        v.somar(lider.localizaçao);
        v.subtrair(this.localizaçao);
        v.dividir(1 / (Vetor.dist(this.localizaçao, lider.localizaçao) * 0.000001));
        return v;
    }

    //aparentemente, lista quais outros boids este boid está enxergando
    void visao(Graphics2D g, List<Boid> boids) {
        double raioDeVisao = TAMANHO * (100 / 3);
        double anguloPeriferico = PI * 0.85;

        for (Boid b : boids) {
            b.avistado = false;

            if (b == this) { //ignora a si proprio
                continue;
            }

            double d = Vetor.dist(localizaçao, b.localizaçao); //distancia entre este boid e o boid avistado

            if (!(b instanceof BoidLider) && d <= 0 || d > raioDeVisao) { //Ignora boids fora do raio de visao
                continue;
            }

            Vetor lineOfSight = Vetor.subtrair(b.localizaçao, localizaçao);  //vetor na direção do boid avistado

            double angle = Vetor.anguloEntre(lineOfSight, velocidade); //angulo desse vetor
            if (angle < anguloPeriferico) { //se esse angulo ta é menor que o angulo periferico, quer dizer que está dentro do campo de visão
                b.avistado = true;
            }
        }
    }

    //para que eles não andem um em cima do outro
    Vetor separacao(List<Boid> boids) {
        double desiredSeparation = TAMANHO * (20 / 3); //quando maior, mais distantes uns dos outros

        Vetor rumo = new Vetor(0, 0); //vai armazenar a resultante para q este boid se afaste dos demais
        int count = 0;
        for (Boid b : boids) {
            if (!b.avistado) { //se o boid b não foi visto por este
                continue;
            }

            double d = Vetor.dist(localizaçao, b.localizaçao);
            if ((d > 0) && (d < desiredSeparation)) {
                Vetor diff = Vetor.subtrair(localizaçao, b.localizaçao); //vetor no sentido contrário ao do boid q está mto proximo, pois se pretende afastar-se dele
                diff.normalizar();
                diff.dividir(d);        // pesado pela distancia, pois são vários boids q estão proximos e deve-se afastar com mais força do q está mais proximo
                rumo.somar(diff);
                count++;
            }
        }
        if (count > 0) {
            rumo.dividir(count);
        }

        if (rumo.modulo() > 0) {
            rumo.normalizar();
            rumo.multiplicar(velocidadeMaxima);
            rumo.subtrair(velocidade);
            rumo.limitar(forçaMaxima);
            return rumo;
        } else {
            return new Vetor(0, 0);
        }
    }

    //nao compreendi exatamente
    Vetor alinhamento(List<Boid> boids) {
        double preferredDist = TAMANHO * (50 / 3);

        Vetor rumo = new Vetor(0, 0);
        int count = 0;

        for (Boid b : boids) {
            if (!b.avistado) { //se o boid b não foi visto por este
                continue;
            }

            double d = Vetor.dist(localizaçao, b.localizaçao);
            if ((d > 0) && (d < preferredDist)) {
                rumo.somar(b.velocidade);
                count++;
            }
        }

        if (count > 0) {
            rumo.dividir(count);
            rumo.normalizar();
            rumo.multiplicar(velocidadeMaxima);
            rumo.subtrair(velocidade);
            rumo.limitar(forçaMaxima);
        }
        return rumo;
    }

    //nao compreendi exatamente
    Vetor coesao(List<Boid> boids) {
        double preferredDist = TAMANHO * (50 / 3);

        Vetor alvo = new Vetor(0, 0);
        int count = 0;

        for (Boid b : boids) {
            if (!b.avistado) { //se o boid b não foi visto por este
                continue;
            }

            double d = Vetor.dist(localizaçao, b.localizaçao);
            if ((d > 0) && (d < preferredDist)) {
                alvo.somar(b.localizaçao);
                count++;
            }
        }
        if (count > 0) {
            alvo.dividir(count);
            return seek(alvo);
        }
        return alvo;
    }

    //desenha o boid
    void draw(Graphics2D g, Vetor ancora) {
        AffineTransform save = g.getTransform();

        g.translate(localizaçao.x - ancora.x, localizaçao.y - ancora.y);
        g.rotate(velocidade.direçao() + PI / 2);
        g.setColor(Color.red);
        g.fill(FORMA_DO_BOID);
        g.setColor(Color.red);
        g.draw(FORMA_DO_BOID);

        g.setTransform(save);
    }

    void run(Graphics2D g, List<Boid> boids, int w, int h, Vetor ancora) {
        rebanho(g, boids); //calcula as forças a partir do rebanho
        update(); //atualiza vetores conforme forças calculadas
        draw(g, ancora); //desenha
    }
}
