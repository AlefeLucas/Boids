
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.*;
import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.Timer;

public class BoidsTeste extends JPanel {

    Rebanho rebanho;
    final int w, h;

    public BoidsTeste() {
        w = 800;
        h = 600;

        setPreferredSize(new Dimension(w, h));
        setBackground(Color.black);

        spawnRebanho();

        new Timer(17, (ActionEvent e) -> {
            if (rebanho.saiuDoCenario(w, h)) {
                spawnRebanho();
            }
            repaint();
        }).start();
        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                double força = 0.5;
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    BoidLider lider = rebanho.getLider();
                    lider.aceleraçao.somar(new Vetor(-força, 0));
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    BoidLider lider = rebanho.getLider();
                    lider.aceleraçao.somar(new Vetor(força, 0));
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    BoidLider lider = rebanho.getLider();
                    lider.aceleraçao.somar(new Vetor(0, -força));
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    BoidLider lider = rebanho.getLider();
                    lider.aceleraçao.somar(new Vetor(0, força));
                } else if (e.getKeyCode() == KeyEvent.VK_PLUS) {
                    
                } else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                    
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void spawnRebanho() {
        rebanho = Rebanho.spawn(w * 0.5, h * 0.5, 20);
    }

    //roda a cada frame, responsavel por desenhar
    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rebanho.run(g, w, h);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Boids");
            f.setResizable(false);
            f.add(new BoidsTeste(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
