
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.Timer;
import static java.awt.event.KeyEvent.*;

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

        Timer t = new Timer(10, (ActionEvent e) -> {
            repaint();
        });
        t.start();

        this.setFocusable(true);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseReleased(e);
                requestFocus();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        if (BoidsPanel.this.hasFocus()) {
                            int x = e.getX();
                            int y = e.getY();
                            
                            rebanho.add10(x, y);
                        }
                        break;
                    case MouseEvent.BUTTON2:
                        
                        break;
                    case MouseEvent.BUTTON3:
                        if (BoidsPanel.this.hasFocus()) {
                            rebanho.remove10();
                        }
                        break;
                    default:
                        break;
                }
            }

        });
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case VK_ESCAPE:
                        frame.dispose();
                        System.exit(0);
                    case VK_LEFT:
                        rebanho.getLider().esquerda = true;
                        break;
                    case VK_RIGHT:
                        rebanho.getLider().direita = true;
                        break;
                    case VK_UP:
                        rebanho.getLider().cima = true;
                        break;
                    case VK_DOWN:
                        rebanho.getLider().baixo = true;
                        break;
                    case VK_ADD:
                    case VK_EQUALS:
                        rebanho.add10();
                        break;
                    case VK_SUBTRACT:
                    case VK_MINUS:
                        rebanho.remove10();
                        break;
                    case VK_H:
                        rebanho.setDrawHUD(!rebanho.isDrawHUD());
                        frame.jPanel2.setVisible(rebanho.isDrawHUD());
                        break;
                    case VK_R:
                        rebanho.setDrawRastro(!rebanho.isDrawRastro());
                        break;
                    case VK_S:
                        rebanho.getLider().velocidade.x = 0;
                        rebanho.getLider().velocidade.y = 0;
                        break;
                    case VK_B:
                        if (BACKGROUND_COLOR == Color.black) {
                            BACKGROUND_COLOR = Color.white;
                        } else {
                            BACKGROUND_COLOR = Color.black;
                        }
                        setBackground(BACKGROUND_COLOR);
                        break;
                    case VK_V:
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
                        break;
                    default:
                        break;
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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        rebanho = Rebanho.spawn(screenSize.width / 2, screenSize.height / 2, 59);
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

    public static Color getContrastColor(Color color) {
        double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
        return y >= 128 ? Color.black : Color.white;
    }
}
