package edu.njit.cs.saboc.nat.generic.gui.panels.search;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Chris O
 */
public class SpinnerTextField extends JPanel {

    private final JTextField textField;
    private final JLabel spinner;

    public SpinnerTextField() {
        super(new BorderLayout());

        JPanel p = new JPanel(new BorderLayout());

        textField = new JTextField();
        
        spinner = new JLabel(ImageManager.getImageManager().getIcon("spinner.gif"));
        spinner.setOpaque(true);
        spinner.setBackground(textField.getBackground());

        setBorder(textField.getBorder());

        textField.setBorder(null);

        add(spinner, BorderLayout.EAST);
        add(textField, BorderLayout.CENTER);

        spinner.setVisible(false);
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setSpinnerVisible(boolean v) {
        spinner.setVisible(v);
    }
}
