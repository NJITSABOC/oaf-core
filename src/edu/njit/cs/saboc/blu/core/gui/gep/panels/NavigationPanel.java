package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author cro3
 */
public class NavigationPanel extends JPanel {
    
    public static enum NavigationDirection {
        Up,
        Down,
        Left,
        Right
    }
    
    public interface NavigationPanelListener {
        public void zoomLevelChanged(int zoomLevel);
        public void navigationButtonPressed(NavigationDirection direction);
    }
    
    private final JSlider zoomSlider;
    private final JButton moveUpBtn;
    private final JButton moveDownBtn;
    private final JButton moveLeftBtn;
    private final JButton moveRightBtn;

    private final ArrayList<NavigationPanelListener> listeners = new ArrayList<>();
    
    public NavigationPanel() {
        this.setLayout(null);
        this.setOpaque(false);
        
        zoomSlider = new JSlider(JSlider.VERTICAL, 10, 200, 100);
        zoomSlider.setBounds(0, 0, 40, 150);
        zoomSlider.setMajorTickSpacing(10);
        zoomSlider.setPaintTicks(true);
        zoomSlider.setSnapToTicks(true);
        zoomSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                if (!zoomSlider.getValueIsAdjusting()) {
                    listeners.forEach((listener) -> {
                        listener.zoomLevelChanged(zoomSlider.getValue());
                    });
                }
            }
        });

        this.add(zoomSlider);

        moveUpBtn = new JButton("\u2191");
        moveUpBtn.setBounds(90, 15, 50, 25);

        moveLeftBtn = new JButton("\u2190");
        moveLeftBtn.setBounds(40, 40, 50, 25);

        moveDownBtn = new JButton("\u2193");
        moveDownBtn.setBounds(90, 40, 50, 25);

        moveRightBtn = new JButton("\u2192");
        moveRightBtn.setBounds(140, 40, 50, 25);

        this.add(moveUpBtn);
        this.add(moveLeftBtn);
        this.add(moveDownBtn);
        this.add(moveRightBtn);
    }
    
    public void setZoomLevel(int newZoomLevel) {
        zoomSlider.setValue(newZoomLevel);
    }
    
    public int getZoomLevel() {
        return zoomSlider.getValue();
    }
    
    public void update() {

        NavigationDirection direction = null;

        if (moveUpBtn.getModel().isPressed()) {
            direction = NavigationDirection.Up;
        } else if (moveDownBtn.getModel().isPressed()) {
            direction = NavigationDirection.Down;
        } else if (moveLeftBtn.getModel().isPressed()) {
            direction = NavigationDirection.Left;
        } else if (moveRightBtn.getModel().isPressed()) {
            direction = NavigationDirection.Right;
        }
        
        if(direction != null) {
            for(NavigationPanelListener listener : listeners) {
                listener.navigationButtonPressed(direction);
            }
        }
    }
    
    public void addNavigationPanelListener(NavigationPanelListener listener) {
        this.listeners.add(listener);
    }
    
    public void removeNavigationPanelListener(NavigationPanelListener listener) {
        this.listeners.remove(listener);
    }
}
