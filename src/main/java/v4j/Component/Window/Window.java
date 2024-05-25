package v4j.Component.Window;

import v4j.Component.Panel.Panel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

/**
 *
 * @author virus
 */
public class Window extends JFrame {

    private int arc = 0;
    private int opacity = 0;
    private Panel panel;

    public Window() {
        if (opacity < 0) opacity = 1;
        else if (opacity > 255) opacity = 255;
        
        setUndecorated(true);

        setBackground(new Color(0, 0, 0, opacity));

        panel = new Panel();
        panel.setArc(getArc());
        panel.setBackground(new Color(0, 0, 0, opacity));
        panel.setLayout(new BorderLayout());
        setContentPane(panel);
    }
    
    public void addComp(Component comp) {
        panel.add(comp);
    }
    
    public void addComp(Component comp, Object constraints) {
        panel.add(comp, constraints);
    }

    public Panel getPanel() {
        return panel;
    }

    public void maxSize() {
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
    }
        
    public void open() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
        
    public void close() {
        dispose();
    }

    public void addCtrlBind(String name, int key, Runnable run) {
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run.run();
            }
        };

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, InputEvent.CTRL_DOWN_MASK), name);
        panel.getActionMap().put(name, action);
    }

    public int getArc() {
        return arc;
    }

    public void setArc(int arc) {
        this.arc = arc;
    }
}