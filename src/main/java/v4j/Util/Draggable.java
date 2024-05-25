package v4j.Util;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author virus
 * @version 1.1.1
 */
public class Draggable {

    private int x, y;
    
    public Draggable(Component comp) {
        comp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }
        });

        comp.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                comp.setLocation(e.getXOnScreen()- x, e.getYOnScreen()- y);
            }
        });
    }
}