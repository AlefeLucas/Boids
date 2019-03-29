
import static java.lang.Math.acos;
import static java.lang.Math.atan2;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class Vetor {

    double x, y;

    Vetor() {
    }

    Vetor(double x, double y) {
        this.x = x;
        this.y = y;
    }

    void somar(Vetor v) {
        x += v.x;
        y += v.y;
    }

    void subtrair(Vetor v) {
        x -= v.x;
        y -= v.y;
    }

    void dividir(double val) {
        x /= val;
        y /= val;
    }

    void multiplicar(double val) {
        x *= val;
        y *= val;
    }

    double modulo() {
        return sqrt(pow(x, 2) + pow(y, 2));
    }

    double dot(Vetor v) {
        return x * v.x + y * v.y;
    }

    
    void normalizar() {
        double modulo = modulo();
        if (modulo != 0) {
            x /= modulo;
            y /= modulo;
        }
    }

    void limitar(double lim) {
        double mag = modulo();
        if (mag != 0 && mag > lim) {
            x *= lim / mag;
            y *= lim / mag;
        }
    }

    double direçao() {
        return atan2(y, x);
    }

    static Vetor subtrair(Vetor v, Vetor v2) {
        return new Vetor(v.x - v2.x, v.y - v2.y);
    }

    static double dist(Vetor v, Vetor v2) {
        return sqrt(pow(v.x - v2.x, 2) + pow(v.y - v2.y, 2));
    }

    static double anguloEntre(Vetor v, Vetor v2) {
        return acos(v.dot(v2) / (v.modulo() * v2.modulo()));
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }
    
    
}