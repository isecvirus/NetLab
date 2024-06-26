package org.virus.netlab;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import v4j.Component.Panel.Panel;

/**
 *
 * @author virus
 */
public class StatusCodePanel extends Panel {

    /**
     * Creates new form StatusCodePanel
     */
    public StatusCodePanel() {
        initComponents();
        
        messageField.setSelectionColor(new Color(196, 196, 196, 48));
        messageField.setCaretColor(new Color(0, 0, 0, 0));
        
        categoryField.setSelectionColor(new Color(196, 196, 196, 48));
        messageField.setCaretColor(new Color(0, 0, 0, 0));
        
        descriptionField.setSelectionColor(new Color(196, 196, 196, 48));
        messageField.setCaretColor(new Color(0, 0, 0, 0));
        
        vendorField.setSelectionColor(new Color(196, 196, 196, 48));
        messageField.setCaretColor(new Color(0, 0, 0, 0));
        
        setArc(35);
        setBackground(Color.decode("#333944"));
    }
    
    public boolean fetch(String service, String code) {
        try {
            String CODEs = new String(Files.readAllBytes(Paths.get("status_codes.json")));
            
            JSONObject jo = new JSONObject(CODEs);

            service = service.toLowerCase().replace(" ", "_");
            if (jo.has(service)) {
                JSONObject suffixes = jo.getJSONObject(service);
                
                for (String suffix : suffixes.keySet()) {
                    
                    if (code.startsWith(suffix)) {
                        JSONObject FullCodes = suffixes.getJSONObject(suffix);

                        if (FullCodes.has(code)) {
                            JSONArray CodeInfo = (JSONArray)FullCodes.get(code);
                            JSONObject Info = CodeInfo.getJSONObject(0);
                            String message = Info.getString("message");
                            String category = Info.getString("category");
                            String description = Info.getString("description");
                            String vendor = Info.getString("vendor");
                            
                            messageField.setText(message);
                            categoryField.setText(category);
                            descriptionField.setText(description);
                            vendorField.setText(vendor);
                            return true;
                        }
                        break;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        }
        
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        messageField = new v4j.Component.Field.SmoothField();
        categoryField = new v4j.Component.Field.SmoothField();
        vendorField = new v4j.Component.Field.SmoothField();
        elapsedLabel1 = new javax.swing.JLabel();
        elapsedLabel2 = new javax.swing.JLabel();
        elapsedLabel3 = new javax.swing.JLabel();
        elapsedLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionField = new javax.swing.JTextArea();

        messageField.setEditable(false);
        messageField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        messageField.setArc(15);
        messageField.setDragEnabled(true);
        messageField.setFillColor(new java.awt.Color(51, 57, 68));
        messageField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        messageField.setLineColor(new java.awt.Color(132, 197, 238));
        messageField.setMaximumSize(new java.awt.Dimension(37, 143));
        messageField.setMinimumSize(new java.awt.Dimension(37, 143));
        messageField.setPreferredSize(new java.awt.Dimension(37, 143));
        messageField.setSelectionColor(new java.awt.Color(132, 197, 238));
        messageField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageFieldActionPerformed(evt);
            }
        });

        categoryField.setEditable(false);
        categoryField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        categoryField.setArc(15);
        categoryField.setDragEnabled(true);
        categoryField.setFillColor(new java.awt.Color(51, 57, 68));
        categoryField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        categoryField.setLineColor(new java.awt.Color(132, 197, 238));
        categoryField.setMaximumSize(new java.awt.Dimension(37, 143));
        categoryField.setMinimumSize(new java.awt.Dimension(37, 143));
        categoryField.setPreferredSize(new java.awt.Dimension(37, 143));
        categoryField.setSelectionColor(new java.awt.Color(132, 197, 238));
        categoryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryFieldActionPerformed(evt);
            }
        });

        vendorField.setEditable(false);
        vendorField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        vendorField.setArc(15);
        vendorField.setDragEnabled(true);
        vendorField.setFillColor(new java.awt.Color(51, 57, 68));
        vendorField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        vendorField.setLineColor(new java.awt.Color(132, 197, 238));
        vendorField.setMaximumSize(new java.awt.Dimension(37, 143));
        vendorField.setMinimumSize(new java.awt.Dimension(37, 143));
        vendorField.setPreferredSize(new java.awt.Dimension(37, 143));
        vendorField.setSelectionColor(new java.awt.Color(132, 197, 238));
        vendorField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendorFieldActionPerformed(evt);
            }
        });

        elapsedLabel1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel1.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        elapsedLabel1.setText("Message");

        elapsedLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel2.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        elapsedLabel2.setText("Category");

        elapsedLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel3.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        elapsedLabel3.setText("Description");

        elapsedLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        elapsedLabel4.setForeground(new java.awt.Color(122, 131, 147));
        elapsedLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        elapsedLabel4.setText("Vendor");

        descriptionField.setEditable(false);
        descriptionField.setBackground(new java.awt.Color(51, 57, 68));
        descriptionField.setColumns(20);
        descriptionField.setLineWrap(true);
        descriptionField.setRows(5);
        descriptionField.setTabSize(4);
        descriptionField.setWrapStyleWord(true);
        descriptionField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(132, 197, 238)));
        descriptionField.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(descriptionField);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(elapsedLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(messageField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(categoryField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(vendorField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 9, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(elapsedLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(elapsedLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(elapsedLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(elapsedLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(elapsedLabel2)
                .addGap(5, 5, 5)
                .addComponent(categoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(elapsedLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(elapsedLabel4)
                .addGap(5, 5, 5)
                .addComponent(vendorField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void messageFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageFieldActionPerformed
        
    }//GEN-LAST:event_messageFieldActionPerformed

    private void categoryFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categoryFieldActionPerformed

    private void vendorFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendorFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vendorFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private v4j.Component.Field.SmoothField categoryField;
    private javax.swing.JTextArea descriptionField;
    private javax.swing.JLabel elapsedLabel1;
    private javax.swing.JLabel elapsedLabel2;
    private javax.swing.JLabel elapsedLabel3;
    private javax.swing.JLabel elapsedLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private v4j.Component.Field.SmoothField messageField;
    private v4j.Component.Field.SmoothField vendorField;
    // End of variables declaration//GEN-END:variables
}
