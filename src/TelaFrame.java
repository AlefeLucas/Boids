
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alefe
 */
public class TelaFrame extends JFrame {

    private final BoidsPanel boidPanel;

    /**
     * Creates new form TelaFrame
     */
    public TelaFrame() {
        
        
        //this.setUndecorated(true);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Boids");
        this.setResizable(true);
        boidPanel = new BoidsPanel(this);
        this.add(boidPanel, BorderLayout.CENTER);
 
        this.pack();
        
        this.setLocationRelativeTo(null);
        
        this.setVisible(true);
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        sliderCoesao.setMinimum(0);
        sliderSeparacao.setMinimum(0);
        sliderAlinhamento.setMinimum(0);
        sliderSeguirLider.setMinimum(0);
        
        sliderCoesao.setMaximum(500);
        sliderSeparacao.setMaximum(500);
        sliderAlinhamento.setMaximum(500);
        sliderSeguirLider.setMaximum(3000);
        sliderSeguirLider.setMinimum(-1000);
        
        sliderCoesao.setValue(130);
        sliderSeparacao.setValue(250);
        sliderAlinhamento.setValue(150);
        sliderSeguirLider.setValue(100);
        jPanel2.setVisible(false);
     
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        jPanel2 = new JPanel();
        panelCoesao = new JPanel();
        sliderCoesao = new JSlider();
        labelCoesao = new JLabel();
        labelNCoesao = new JLabel();
        panelSeparacao = new JPanel();
        sliderSeparacao = new JSlider();
        labelSeparacao = new JLabel();
        labelNSeparacao = new JLabel();
        panelAlinhamento = new JPanel();
        sliderAlinhamento = new JSlider();
        labelAlinhamento = new JLabel();
        labelNAlinhamento = new JLabel();
        panelSeguirLider = new JPanel();
        sliderSeguirLider = new JSlider();
        labelSeguirLider = new JLabel();
        labelNSeguirLider = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0));
        setForeground(Color.black);
        setPreferredSize(new Dimension(800, 600));

        jPanel2.setPreferredSize(new Dimension(606, 50));
        jPanel2.setLayout(new GridLayout(1, 4));

        panelCoesao.setLayout(new GridBagLayout());

        sliderCoesao.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                sliderCoesaoStateChanged(evt);
            }
        });
        sliderCoesao.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                sliderCoesaoMouseReleased(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        panelCoesao.add(sliderCoesao, gridBagConstraints);

        labelCoesao.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        labelCoesao.setText("Coesão");
        panelCoesao.add(labelCoesao, new GridBagConstraints());

        labelNCoesao.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        labelNCoesao.setText("1");
        panelCoesao.add(labelNCoesao, new GridBagConstraints());

        jPanel2.add(panelCoesao);

        panelSeparacao.setLayout(new GridBagLayout());

        sliderSeparacao.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                sliderSeparacaoStateChanged(evt);
            }
        });
        sliderSeparacao.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                sliderSeparacaoMouseReleased(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        panelSeparacao.add(sliderSeparacao, gridBagConstraints);

        labelSeparacao.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        labelSeparacao.setText("Separação");
        panelSeparacao.add(labelSeparacao, new GridBagConstraints());

        labelNSeparacao.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        labelNSeparacao.setText("2");
        panelSeparacao.add(labelNSeparacao, new GridBagConstraints());

        jPanel2.add(panelSeparacao);

        panelAlinhamento.setLayout(new GridBagLayout());

        sliderAlinhamento.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                sliderAlinhamentoStateChanged(evt);
            }
        });
        sliderAlinhamento.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                sliderAlinhamentoMouseReleased(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        panelAlinhamento.add(sliderAlinhamento, gridBagConstraints);

        labelAlinhamento.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        labelAlinhamento.setText("Alinhamento");
        panelAlinhamento.add(labelAlinhamento, new GridBagConstraints());

        labelNAlinhamento.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        labelNAlinhamento.setText("3");
        panelAlinhamento.add(labelNAlinhamento, new GridBagConstraints());

        jPanel2.add(panelAlinhamento);

        panelSeguirLider.setLayout(new GridBagLayout());

        sliderSeguirLider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                sliderSeguirLiderStateChanged(evt);
            }
        });
        sliderSeguirLider.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                sliderSeguirLiderMouseReleased(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        panelSeguirLider.add(sliderSeguirLider, gridBagConstraints);

        labelSeguirLider.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        labelSeguirLider.setText("Seguir Líder");
        panelSeguirLider.add(labelSeguirLider, new GridBagConstraints());

        labelNSeguirLider.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        labelNSeguirLider.setText("4");
        panelSeguirLider.add(labelNSeguirLider, new GridBagConstraints());

        jPanel2.add(panelSeguirLider);

        getContentPane().add(jPanel2, BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sliderCoesaoStateChanged(ChangeEvent evt) {//GEN-FIRST:event_sliderCoesaoStateChanged
        // TODO add your handling code here:
        Boid.coesaoPeso = sliderCoesao.getValue()/100.0;
        labelNCoesao.setText("" + (sliderCoesao.getValue()/100.0)); 
        
    }//GEN-LAST:event_sliderCoesaoStateChanged

    private void sliderSeparacaoStateChanged(ChangeEvent evt) {//GEN-FIRST:event_sliderSeparacaoStateChanged
        // TODO add your handling code here:
        Boid.separacaoPeso = sliderSeparacao.getValue()/100.0;
        labelNSeparacao.setText("" + (sliderSeparacao.getValue()/100.0)); 
    }//GEN-LAST:event_sliderSeparacaoStateChanged

    private void sliderAlinhamentoStateChanged(ChangeEvent evt) {//GEN-FIRST:event_sliderAlinhamentoStateChanged
        // TODO add your handling code here:
        Boid.alinhamentoPeso = sliderAlinhamento.getValue()/100.0;
        labelNAlinhamento.setText("" + (sliderAlinhamento.getValue()/100.0)); 
    }//GEN-LAST:event_sliderAlinhamentoStateChanged

    private void sliderSeguirLiderStateChanged(ChangeEvent evt) {//GEN-FIRST:event_sliderSeguirLiderStateChanged
        // TODO add your handling code here:
        Boid.seguirLiderPeso = sliderSeguirLider.getValue()/100.0;
        labelNSeguirLider.setText("" + (sliderSeguirLider.getValue()/100.0)); 
    }//GEN-LAST:event_sliderSeguirLiderStateChanged

    private void sliderSeparacaoMouseReleased(MouseEvent evt) {//GEN-FIRST:event_sliderSeparacaoMouseReleased
        // TODO add your handling code here:
        boidPanel.requestFocus();
    }//GEN-LAST:event_sliderSeparacaoMouseReleased

    private void sliderCoesaoMouseReleased(MouseEvent evt) {//GEN-FIRST:event_sliderCoesaoMouseReleased

        boidPanel.requestFocus();
    }//GEN-LAST:event_sliderCoesaoMouseReleased

    private void sliderAlinhamentoMouseReleased(MouseEvent evt) {//GEN-FIRST:event_sliderAlinhamentoMouseReleased
        boidPanel.requestFocus();
    }//GEN-LAST:event_sliderAlinhamentoMouseReleased

    private void sliderSeguirLiderMouseReleased(MouseEvent evt) {//GEN-FIRST:event_sliderSeguirLiderMouseReleased
        boidPanel.requestFocus();
    }//GEN-LAST:event_sliderSeguirLiderMouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaFrame();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public JPanel jPanel2;
    private JLabel labelAlinhamento;
    private JLabel labelCoesao;
    private JLabel labelNAlinhamento;
    private JLabel labelNCoesao;
    private JLabel labelNSeguirLider;
    private JLabel labelNSeparacao;
    private JLabel labelSeguirLider;
    private JLabel labelSeparacao;
    private JPanel panelAlinhamento;
    private JPanel panelCoesao;
    private JPanel panelSeguirLider;
    private JPanel panelSeparacao;
    private JSlider sliderAlinhamento;
    private JSlider sliderCoesao;
    private JSlider sliderSeguirLider;
    private JSlider sliderSeparacao;
    // End of variables declaration//GEN-END:variables
}
