package v4j.Component.Progress;

import v4j.UI.ProgressCircleUI;
import javax.swing.JProgressBar;

public class CircleBar extends JProgressBar {

    private final ProgressCircleUI ui;

    public CircleBar() {
        setOpaque(false);
        setStringPainted(true);
        ui = new ProgressCircleUI();
        setUI(ui);
    }

    @Override
    public String getString() {
        return ((int) (getValue() * ui.getAnimate())) + "%";
    }

    public void start() {
        ui.start();
    }
}