package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons;

import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
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
    
    private class PopupStateManager {

        private boolean popupHasFocus = false;
        
        private boolean buttonHasFocus = false;
        
        private boolean popupLostFocus = false;
                
        private boolean clickedToClose = false;
        
        private boolean clickedToOpen = false;
        
        
        public void popupHasFocus(boolean value) {
            popupHasFocus = value;

            if(value) {
                popupLostFocus = false;
            } else {
                popupLostFocus = true;
                
                if(clickedToOpen) {
                    clickedToOpen = false;
                } else {
                    doUnselected();
                }
            }
        }
        
        public void buttonHasFocus(boolean value) {
            buttonHasFocus = value;
            
            if(value) {               
                if (popupLostFocus) {
                    popupLostFocus = false;
                    clickedToClose = true;
                }
            } else {
                popupLostFocus = false;
                clickedToClose = false;
            }
        }
        
        public void buttonClicked() {

            if(clickedToClose) {
                clickedToClose = false;
                
                doUnselected();
            } else {
                clickedToOpen = true;
                
                doSelected();
            }
        }

    }
    
    private final PopupStateManager popupState = new PopupStateManager();
            
    private final JDialog popup;
    
    public PopupToggleButton(JFrame parent, String text) {
        this.setText(text);
        
        popup = new JDialog(parent);
        popup.setUndecorated(true);
        popup.setFocusableWindowState(true);
        popup.setFocusable(true);
        
        popup.addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent e) {
                popupState.popupHasFocus(false);
            }

            public void windowLostFocus(WindowEvent e) {
                popupState.popupHasFocus(false);
            }
        });

        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                popupState.buttonClicked();
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

        this.addFocusListener(new FocusListener() {

            public void focusLost(FocusEvent e) {
                popupState.buttonHasFocus(false);
            }

            public void focusGained(FocusEvent e) {
                popupState.buttonHasFocus(true);
            }
        });
    }
        
    public void doSelected() {
        setIcon(IconManager.getIconManager().getIcon("cancel.png"));
        
        setSelected(true);
        
        popup.setVisible(true);
        
        updatePopupLocation();
    }
    
    public void doUnselected() {
        setIcon(null);
        
        setSelected(false);

        popup.setVisible(false);
    }
    
    public void closePopup() {
        doUnselected();
    }
    
    protected void setPopupContent(JComponent component) {
        popup.add(component);
        popup.pack();
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
