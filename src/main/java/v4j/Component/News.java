package v4j.Component;

import v4j.Component.Button.Button;
import v4j.Component.Panel.Panel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

/**
 *
 * @author virus
 */
public class News extends Panel {

    private Color accent = new Color(0, 172, 105);

    public News() {
        initComponents();
        IconFontSwing.register(FontAwesome.getIconFont());

        LocalTime time = LocalTime.now(); // Get the current time

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        String formattedTime = time.format(formatter);
        timeLabel.setText(formattedTime);
        timeLabel.setIcon(IconFontSwing.buildIcon(FontAwesome.CLOCK_O, 15, Color.lightGray));

        setArc(25);

        TimePanel.setGradient(new Color(0, 0, 0, 0), new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 35));
        TimePanel.setArc(15);
        TimePanel.setYGradientOffset(10);

        setAccent(accent);

        message.setBackground(new Color(0, 0, 0, 0));
    }

    public void setSubActionIcon(Icon i) {
        subActionButton.setIcon(i);
    }

    public void setTitleActionIcon(Icon i) {
        titleActionButton.setIcon(i);
    }

    public void setAvatar(Icon i) {
        avatar.setImage(i);
    }

    public void setAccent(Color c) {
        mainActionButton.setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue(), 30));
        mainActionButton.setForeground(c);
        subActionButton.setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue(), 30));
        titleActionButton.setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue(), 30));
        
        TimePanel.setGradient(new Color(0, 0, 0, 0), new Color(c.getRed(), c.getGreen(), c.getBlue(), 50));
        
        avatar.setGradientColor1(c.darker().darker());
        avatar.setGradientColor2(c.darker());
        
        this.accent = c;
    }
    
    public void setTitle(String t) {
        title.setText(t);
    }

    public void setMessage(String m) {
        message.setText(m);
    }
    
    public void setMainActionText(String t) {
        mainActionButton.setText(t);
    }
    
    public void setTitleActionText(String t) {
        titleActionButton.setText(t);
    }
    
    public void setSubActionText(String t) {
        subActionButton.setText(t);
    }
    
    public Color getAccent() {
        return this.accent;
    }
    
    public void setTimeForeground(Color c) {
        timeLabel.setForeground(c);
    }
    
    public void setTitleForeground(Color c) {
        title.setForeground(c);
    }
    
    public void setMessageForeground(Color c) {
        message.setForeground(c);
    }
    
    public void setMainAction(ActionListener event) {
        mainActionButton.addActionListener(event);
    }
    
    public void setTitleAction(ActionListener event) {
        titleActionButton.addActionListener(event);
    }
    
    public void setSubAction(ActionListener event) {
        subActionButton.addActionListener(event);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        subActionButton = new Button();
        avatar = new ImageAvatar();
        titleActionButton = new Button();
        TimePanel = new Panel();
        timeIcon = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        mainActionButton = new Button();
        message = new javax.swing.JLabel();

        titleActionButton.setArc(50);
        titleActionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        titleActionButton.setMaximumSize(new java.awt.Dimension(27, 27));
        titleActionButton.setMinimumSize(new java.awt.Dimension(27, 27));
        titleActionButton.setPreferredSize(new java.awt.Dimension(27, 27));

        timeIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        timeLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout TimePanelLayout = new javax.swing.GroupLayout(TimePanel);
        TimePanel.setLayout(TimePanelLayout);
        TimePanelLayout.setHorizontalGroup(
            TimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TimePanelLayout.createSequentialGroup()
                .addComponent(timeIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                .addContainerGap())
        );
        TimePanelLayout.setVerticalGroup(
            TimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TimePanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(TimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(timeIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)))
        );

        title.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("Good Morning");

        mainActionButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        message.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        message.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainActionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(subActionButton, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(267, 267, 267))
            .addGroup(layout.createSequentialGroup()
                .addComponent(TimePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(titleActionButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(titleActionButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TimePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73)
                .addComponent(avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(message, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(mainActionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(subActionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Panel TimePanel;
    private ImageAvatar avatar;
    private Button mainActionButton;
    private javax.swing.JLabel message;
    private Button subActionButton;
    private javax.swing.JLabel timeIcon;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel title;
    private Button titleActionButton;
    // End of variables declaration//GEN-END:variables
}