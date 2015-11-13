package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons;

import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JToggleButton;

/**
 *
 * @author Chris
 */
public class PopupToggleButton extends JToggleButton {
    private JDialog popup;
    
    protected BluGraph graph;
    
    public PopupToggleButton(JFrame parent, String text) {
        this.setText(text);
        
        popup = new JDialog(parent);
        popup.setUndecorated(true);
        popup.setFocusableWindowState(true);
        popup.setFocusable(true);
        
        popup.addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent e) {
                
            }

            public void windowLostFocus(WindowEvent e) {
                setIcon(null);
                setSelected(false);
                hidePopup();
            }
        });

        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if(PopupToggleButton.this.isSelected()) {
                    popup.setVisible(true);
                    updatePopupLocation();
                } else {
                    popup.setVisible(false);
                }
            }
        });
        
        parent.addComponentListener(new ComponentListener() {

            public void componentShown(ComponentEvent e) {
                updatePopupLocation();
            }

            public void componentResized(ComponentEvent e) {
                updatePopupLocation();
            }

            public void componentMoved(ComponentEvent e) {
                updatePopupLocation();
            }

            public void componentHidden(ComponentEvent e) {
                updatePopupLocation();
            }
        });

        addFocusListener(new FocusListener() {

            public void focusLost(FocusEvent e) {
                
            }

            public void focusGained(FocusEvent e) {
                
            }
        });
    }
    
    protected void setPopupContent(JComponent component) {
        popup.add(component);
        popup.pack();
    }
    
    public void hidePopup() {
        popup.setVisible(false);
    }

    public void setGraph(BluGraph graph) {
        this.graph = graph;
    }
    
    public void disposePopup() {
        popup.dispose();
    }

    private void updatePopupLocation() {
        try {
            Point location = getLocationOnScreen();
            
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            
            if(location.x + popup.getWidth() > screenSize.width) {
                int widthDiff = location.x + popup.getWidth() - screenSize.width;
                
                location.x -= (widthDiff + 4); 
            }
            
            location.y += getHeight();
            popup.setLocation(location);
        } catch (Exception e) {
            
        }
    }
    
    public void updatePopupLocation(Dimension parentFrame) {
        Point location = getLocationOnScreen();
        
        location.x = parentFrame.width - popup.getWidth() - 4;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        if(location.x + popup.getWidth() > screenSize.width) {
            int widthDiff = location.x + popup.getWidth() - screenSize.width;

            location.x -= (widthDiff + 4); 
        }
        
        location.y += getHeight();
        
        popup.setLocation(location);
    }
}
