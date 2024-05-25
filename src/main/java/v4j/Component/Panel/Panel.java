package v4j.Component.Panel;

/**
 *
 * @author virus
 */

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;


/**
 *
 * @author virus
 */
public class Panel extends JPanel {

    private int arcw, arch, Xgradient_offset, Ygradient_offset, Xrotation, Yrotation;
    private Color bgColor = new Color(34, 34, 34, 255);
    private Color color1 = new Color(34, 34, 34, 255);
    private Color color2 = new Color(34, 34, 34, 255);
    private Image image;
    private boolean isGradient = false;
    private boolean isRipple = false;

    public Panel() {
        setOpaque(false);
        
        Yrotation = 50;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isGradient()) {
            GradientPaint gradient = new GradientPaint(Xrotation, Yrotation, color1, getWidth() + getXGradientOffset(), getHeight() + getYGradientOffset(), color2);

            g2d.setPaint(gradient);
        } else {
            g2d.setColor(getBackground());
        }

        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getArcw(), getArch());

        if (image != null) {
            // Draw the image centered within the panel
            int x = (getWidth() - image.getWidth(null)) / 2;
            int y = (getHeight() - image.getHeight(null)) / 2;
                        
            g2d.drawImage(image, x, y, null);
        }

        g2d.dispose();

        super.paint(g);
    }

    public int[] getArc() {
        return new int[]{this.arcw, this.arch};
    }

    public int getArcw() {
        return this.arcw;
    }

    public int getArch() {
        return this.arch;
    }

    public void setArc(int arc) {
        this.arcw = arc;
        this.arch = arc;
    }

    public int getXGradientOffset() {
        return Xgradient_offset;
    }

    public void setXGradientOffset(int Xgradient_offset) {
        this.Xgradient_offset = Xgradient_offset;
    }

    public int getYGradientOffset() {
        return Ygradient_offset;
    }

    public void setYGradientOffset(int Ygradient_offset) {
        this.Ygradient_offset = Ygradient_offset;
    }

    public int getYrotation() {
        return Yrotation;
    }

    public void setYrotation(int Yr) {
        this.Yrotation = Yr;
    }
    
    public int getXrotation() {
        return Xrotation;
    }

    public void setXrotation(int Xr) {
        this.Xrotation = Xr;
    }
    
    @Override
    public Color getBackground() {
        return bgColor;
    }
    
    @Override
    public void setBackground(Color bgColor) {
        this.bgColor = bgColor;
    }

    public void setBackgroundImage(Image image) {
        this.image = image;
    }

    public boolean isGradient() {
        return this.isGradient;
    }

    public Color[] getGradientColors() {
        return new Color[]{this.color1, this.color2};
    }

    public void setGradient(Color c1, Color c2) {
        this.color1 = c1;
        this.color2 = c2;
        this.isGradient = true;
    }
}