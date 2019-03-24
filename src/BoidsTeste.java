
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

    private static Color BACKGROUND_COLOR = Color.black;
    Rebanho rebanho;
    final int w, h;
    Camera camera;
    JFrame f;

    public BoidsTeste(JFrame f) {

        this.f = f;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        w = screenSize.width;
        h = screenSize.height;

        camera = Camera.CENTRO;
        setPreferredSize(new Dimension(w, h));
        setBackground(BACKGROUND_COLOR);

        spawnRebanho();

        new Timer(17, (ActionEvent e) -> {
            repaint();
        }).start();
        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    f.dispose();
                    System.exit(0);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    rebanho.getLider().esquerda = true;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rebanho.getLider().direita = true;
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    rebanho.getLider().cima = true;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    rebanho.getLider().baixo = true;
                } else if (e.getKeyCode() == KeyEvent.VK_ADD) {
                    rebanho.add10(w * 0.5, h * 0.5);
                } else if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
                    rebanho.remove10();
                } else if (e.getKeyCode() == KeyEvent.VK_H) {
                    rebanho.setDrawHUD(!rebanho.isDrawHUD());
                } else if (e.getKeyCode() == KeyEvent.VK_R) {
                    rebanho.setDrawRastro(!rebanho.isDrawRastro());
                }else if (e.getKeyCode() == KeyEvent.VK_S) {
                    rebanho.getLider().velocidade.x = 0;
                    rebanho.getLider().velocidade.y = 0;
                } else if (e.getKeyCode() == KeyEvent.VK_B) {
                    if(BACKGROUND_COLOR == Color.black){
                        BACKGROUND_COLOR = Color.white;
                    } else {
                        BACKGROUND_COLOR = Color.black;
                    }
                    setBackground(BACKGROUND_COLOR);
                } else if (e.getKeyCode() == KeyEvent.VK_V) {

                    switch (camera) {
                        case REBANHO:
                            camera = Camera.LIDER;
                            break;
                        case LIDER:
                            camera = Camera.CENTRO;
                            break;
                        case CENTRO:
                            camera = Camera.MEIO_REBANHO_LIDER;
                            break;
                        case MEIO_REBANHO_LIDER:
                            camera = Camera.REBANHO;
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
        rebanho = Rebanho.spawn(w * 0.5, h * 0.5, 59);
    }

    //roda a cada frame, responsavel por desenhar
    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setBackground(getBackground());
        rebanho.run(g, w, h, camera);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setExtendedState(JFrame.MAXIMIZED_BOTH);
            f.setUndecorated(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Boids");
            f.setResizable(true);
            f.add(new BoidsTeste(f), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);

            f.setVisible(true);

        });
    }

    public static Color getContrastColor(Color color) {
        double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
        return y >= 128 ? Color.black : Color.white;
    }
}
