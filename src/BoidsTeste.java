
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
    Camera camera;

    public BoidsTeste() {
        w = 1155;
        h = 650;

        camera = Camera.CENTRO;
        setPreferredSize(new Dimension(w, h));
        setBackground(Color.black);

        spawnRebanho();

        new Timer(17, (ActionEvent e) -> {
            repaint();
        }).start();
        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    rebanho.getLider().esquerda = true;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rebanho.getLider().direita = true;
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    rebanho.getLider().cima = true;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    rebanho.getLider().baixo = true;
                } else if (e.getKeyCode() == KeyEvent.VK_ADD) {
                    System.out.println("+");
                    rebanho.add10(w * 0.5, h * 0.5);
                } else if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
                    rebanho.remove10();
                    System.out.println("-");
                } else if (e.getKeyCode() == KeyEvent.VK_V) {

                    switch (camera) {
                        case REBANHO:
                            camera = Camera.LIDER;
                            System.out.println("LIDER");
                            break;
                        case LIDER:
                            camera = Camera.CENTRO;
                            System.out.println("CENTRO");
                            break;
                        case CENTRO:
                            camera = Camera.REBANHO;
                            System.out.println("REBANHO");
                            break;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    rebanho.getLider().esquerda = false;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rebanho.getLider().direita = false;
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    rebanho.getLider().cima = false;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    rebanho.getLider().baixo = false;
                }
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

        rebanho.run(g, w, h, camera);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Boids");
            f.setResizable(true);
            f.add(new BoidsTeste(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
