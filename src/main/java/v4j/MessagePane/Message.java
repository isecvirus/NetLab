/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package v4j.MessagePane;

import v4j.Component.Panel.Panel;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;

/**
 *
 * @author virus
 */
public class Message extends Panel {

    public Color getBackground() {
        return Background;
    }

    public void setBackground(Color Background) {
        this.Background = Background;
    }

    public Color getForeground() {
        return Foreground;
    }

    public void setForeground(Color Foreground) {
        this.Foreground = Foreground;
    }

    public Color getOkBackground() {
        return okBackground;
    }

    public void setOkBackground(Color okBackground) {
        this.okBackground = okBackground;
    }

    public Color getCancelBackground() {
        return cancelBackground;
    }

    public void setCancelBackground(Color cancelBackground) {
        this.cancelBackground = cancelBackground;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
        this.message = message;
    }

    public String getOkText() {
        return okText;
    }

    public void setOkText(String okText) {
        this.okText = okText;
    }
    
    private Color Background = Color.decode("#262545");
    private Color Foreground = Color.decode("#cfcfcf");
    private Color okBackground = Color.decode("#443E61");
    private Color okForeground = Color.decode("#cfcfcf");
    private Color cancelBackground = Color.decode("#232241");
    private Color cancelForeground = Color.decode("#cfcfcf");
    private String title;
    private String message;
    private String okText = "Ok";
    private String cancelText = "Cancel";
    
    /**
     * Creates new form Message
     */
    public Message() {
        initComponents();
        
        okButton.setForeground(this.okForeground);
        okButton.setBackground(this.okBackground);
        okButton.setText(this.okText);
        
        cancelButton.setForeground(this.cancelForeground);
        cancelButton.setBackground(this.cancelBackground);
        cancelButton.setText(this.cancelText);
        
//        messageContent.setBackground(new Color(0, 0, 0, 0)); // Set the background to transparent
//        messageContent.setForeground(Color.WHITE); // Set the text color to white
//        messageContent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add a small empty border
//        messageContent.setOpaque(false); // Make the background transparent

        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });
        setArc(25);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new v4j.MessagePane.Button();
        cancelButton = new v4j.MessagePane.Button();
        titleLabel = new javax.swing.JLabel();
        messageLabel = new javax.swing.JLabel();

        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        titleLabel.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        titleLabel.setText("Quit!");

        messageLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        messageLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        messageLabel.setText("Are you sure you want to quit?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(messageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 105, Short.MAX_VALUE)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(titleLabel)
                .addGap(15, 15, 15)
                .addComponent(messageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
        GlassPanePopup.closePopupLast();
    }//GEN-LAST:event_cmdCancelActionPerformed

    public void eventOK(ActionListener event) {
        okButton.addActionListener(event);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private v4j.MessagePane.Button cancelButton;
    private javax.swing.JLabel messageLabel;
    private v4j.MessagePane.Button okButton;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
