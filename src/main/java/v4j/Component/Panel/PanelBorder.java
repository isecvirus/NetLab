package v4j.Component.Panel;

import javax.swing.*;
import java.awt.*;

public class PanelBorder extends JPanel {
    private Color color;
    private int arc = 10;
    private int stroke = 1;

    public PanelBorder() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder());
        this.color = Color.BLACK;
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    public void setArc(int a) {
        this.arc = a;
        repaint();
    }

    public void setStroke(int s) {
        this.stroke = s;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (stroke > 0) {
            g2d.setStroke(new BasicStroke(stroke));
            g2d.drawRoundRect(
                    stroke / 2,
                    stroke / 2,
                    getWidth() - stroke,
                    getHeight() - stroke,
                    arc,
                    arc
            );
        }

        g2d.dispose();
    }
}