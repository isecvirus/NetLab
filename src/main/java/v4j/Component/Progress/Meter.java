package v4j.Component.Progress;

import v4j.Component.Panel.Panel;
import v4j.Component.Panel.Panel;
import java.awt.Color;

/**
 *
 * @author virus
 */
public class Meter extends Panel {
    
    private Color accent = new Color(0, 172, 105);

    public Meter() {
        initComponents();
        
        setArc(25);
        setAccent(accent);
    }
    
    public void setAccent(Color c) {
        setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue(), 10));
        bar.setForeground(c);
        bar.setBackground(c.darker());
        progressLabel.setForeground(c);
    }
    
    public void setProgress(int v) {
        bar.setValue(v);
        progressLabel.setText(String.valueOf(v));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progressLabel = new javax.swing.JLabel();
        bar = new v4j.Component.Progress.LineBar();

        progressLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        progressLabel.setText("0");
        progressLabel.setPreferredSize(new java.awt.Dimension(18, 18));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(progressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bar, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(progressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private v4j.Component.Progress.LineBar bar;
    private javax.swing.JLabel progressLabel;
    // End of variables declaration//GEN-END:variables
}