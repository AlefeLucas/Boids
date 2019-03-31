
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.Timer;

public class BoidsPanel extends JPanel {

    private static Color BACKGROUND_COLOR = Color.black;
    Rebanho rebanho;

    Camera camera;
    JFrame frame;

    public BoidsPanel(TelaFrame frame) {

        this.frame = frame;
        


        camera = Camera.CENTRO;
        //setPreferredSize(new Dimension(w, h));
        setBackground(BACKGROUND_COLOR);

        spawnRebanho();

        new Timer(17, (ActionEvent e) -> {
            repaint();
        }).start();
        this.setFocusable(true);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                super.mouseReleased(e);
                requestFocus();
            }
        });
        
        
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                    System.exit(0);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    rebanho.getLider().esquerda = true;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    rebanho.getLider().direita = true;
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    rebanho.getLider().cima = true;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    rebanho.getLider().baixo = true;
                } else if (e.getKeyCode() == KeyEvent.VK_ADD || e.getKeyCode() == KeyEvent.VK_EQUALS) {
                    rebanho.add10();
                } else if (e.getKeyCode() == KeyEvent.VK_SUBTRACT || e.getKeyCode() == KeyEvent.VK_MINUS) {
                    rebanho.remove10();
                } else if (e.getKeyCode() == KeyEvent.VK_H) {
                    rebanho.setDrawHUD(!rebanho.isDrawHUD());
                    frame.jPanel2.setVisible(rebanho.isDrawHUD());
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
        rebanho = Rebanho.spawn(200, 200, 59);
    }

    //roda a cada frame, responsavel por desenhar
    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setBackground(getBackground());
        int alturaPanel = this.getHeight();
        rebanho.run(g, getWidth(), getHeight(), camera);

    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame();
//            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//            frame.setUndecorated(true);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setTitle("Boids");
//            frame.setResizable(true);
//            frame.add(new BoidsPanel(frame), BorderLayout.CENTER);
//            frame.pack();
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//
//        });
//    }

    public static Color getContrastColor(Color color) {
        double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
        return y >= 128 ? Color.black : Color.white;
    }
}
