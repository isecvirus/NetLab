package v4j.Component;

import v4j.Component.Panel.Panel;
import v4j.Component.Progress.LineBar;
import java.awt.Color;
import java.awt.ComponentOrientation;
import javax.swing.Icon;
import javax.swing.SwingConstants;

/**
 *
 * @author virus
 */
public class Credit extends Panel {

    private Color accent = new Color(0, 172, 105);
    private Icon icon, beltIcon;

    /**
     * Creates new form Credit
     */
    public Credit() {
        initComponents();
        setArc(25);
        setCardColor(new Color(34, 34, 34, 32));
        
        titlePanel.setArc(25);
        
        mainIconPanel.setArc(25);
        progressLabelPanel.setArc(10);

        titleLabel.setForeground(Color.white);
        beltDesc.setForeground(Color.white);
        
        cardIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        beltIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        titlePanel.setBackground(new Color(0, 0, 0, 0));
        progressPanel.setBackground(new Color(0, 0, 0, 0));

        setAccent(accent);

    }

    public void setAccent(Color c) {
        mainIconPanel.setBackground(c);
        belt.setBackground(c);
        valueLabel.setForeground(c);
        progressBar.setForeground(c);
        progressBar.setBackground(c.darker());
        progressLabelPanel.setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue(), 10));
        progressLabel.setForeground(c);

    }

    public void setProgress(int v) {
        progressBar.setValue(v);
        progressLabel.setText(String.valueOf(v));
    }
    
    public int getProgress() {
        return progressBar.getValue();
    }

    public void setIcon(Icon i) {
        cardIconLabel.setIcon(i);
        this.icon = i;
    }

    public void setBeltIcon(Icon i) {
        beltIconLabel.setIcon(i);
        this.beltIcon = i;
    }
    
    public void setTitle(String t) {
        titleLabel.setText(t);
    }
    
    public String getTitle() {
        return titleLabel.getText();
    }
    
    public void setDescription(String d) {
        beltDesc.setText(d);
    }
    
    public String getDescription() {
        return beltDesc.getText();
    }
    
    public void setValue(String n) {
        valueLabel.setText(n);
    }
    
    public String getValue() {
        return valueLabel.getText();
    }
    
    public void setCardColor(Color c) {
        setBackground(c);
    }
    
    @Override
    public void setComponentOrientation(ComponentOrientation co) {
        int alignment = co.isLeftToRight() ? SwingConstants.LEFT : SwingConstants.RIGHT;
        ComponentOrientation orintation = co.isLeftToRight() ? ComponentOrientation.RIGHT_TO_LEFT : ComponentOrientation.LEFT_TO_RIGHT;
        
//        setComponentOrientation(orintation);
        belt.setComponentOrientation(orintation);
        
        titleLabel.setHorizontalAlignment(alignment);
        valueLabel.setHorizontalAlignment(alignment);
        beltDesc.setHorizontalAlignment(alignment);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        belt = new Panel();
        beltIconLabel = new javax.swing.JLabel();
        beltDesc = new javax.swing.JLabel();
        progressPanel = new javax.swing.JPanel();
        progressLabelPanel = new Panel();
        progressLabel = new javax.swing.JLabel();
        progressBar = new LineBar();
        titlePanel = new Panel();
        mainIconPanel = new Panel();
        cardIconLabel = new javax.swing.JLabel();
        valueLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(360, 187));

        belt.setPreferredSize(new java.awt.Dimension(118, 31));

        beltIconLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        beltDesc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout beltLayout = new javax.swing.GroupLayout(belt);
        belt.setLayout(beltLayout);
        beltLayout.setHorizontalGroup(
            beltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(beltLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(beltIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(beltDesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        beltLayout.setVerticalGroup(
            beltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, beltLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(beltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(beltDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(beltIconLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        progressLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        progressLabel.setText("0");
        progressLabel.setMaximumSize(new java.awt.Dimension(8, 20));

        javax.swing.GroupLayout progressLabelPanelLayout = new javax.swing.GroupLayout(progressLabelPanel);
        progressLabelPanel.setLayout(progressLabelPanelLayout);
        progressLabelPanelLayout.setHorizontalGroup(
            progressLabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(progressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );
        progressLabelPanelLayout.setVerticalGroup(
            progressLabelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(progressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout progressPanelLayout = new javax.swing.GroupLayout(progressPanel);
        progressPanel.setLayout(progressPanelLayout);
        progressPanelLayout.setHorizontalGroup(
            progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progressPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressLabelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addContainerGap())
        );
        progressPanelLayout.setVerticalGroup(
            progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progressPanelLayout.createSequentialGroup()
                .addGroup(progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(progressPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(progressLabelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(progressPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainIconPanelLayout = new javax.swing.GroupLayout(mainIconPanel);
        mainIconPanel.setLayout(mainIconPanelLayout);
        mainIconPanelLayout.setHorizontalGroup(
            mainIconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainIconPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cardIconLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainIconPanelLayout.setVerticalGroup(
            mainIconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainIconPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cardIconLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addContainerGap())
        );

        valueLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        valueLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titlePanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(valueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainIconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainIconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(titlePanelLayout.createSequentialGroup()
                        .addComponent(valueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(titleLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(belt, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(titlePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(progressPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(belt, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Panel belt, mainIconPanel, progressLabelPanel, titlePanel;
    private javax.swing.JLabel beltDesc;
    private javax.swing.JLabel beltIconLabel;
    private javax.swing.JLabel cardIconLabel;
    private LineBar progressBar;
    private javax.swing.JLabel progressLabel;
    private javax.swing.JPanel progressPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel valueLabel;
    // End of variables declaration//GEN-END:variables
}