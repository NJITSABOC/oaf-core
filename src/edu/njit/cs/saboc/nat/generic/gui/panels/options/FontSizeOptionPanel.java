package edu.njit.cs.saboc.nat.generic.gui.panels.options;

import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNavPanel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

/**
 *
 * @author Chris O
 */
public class FontSizeOptionPanel extends OptionPanel {

    private final JRadioButton tinyBtn = new JRadioButton("Tiny");
    private final JRadioButton smallBtn = new JRadioButton("Small");
    private final JRadioButton normalBtn = new JRadioButton("Normal");
    private final JRadioButton largeBtn = new JRadioButton("Large");
    private final JRadioButton hugeBtn = new JRadioButton("Huge");
    
    public FontSizeOptionPanel(GenericNATBrowser mainPanel) {
        super(mainPanel);
        
        this.setBorder(BaseNavPanel.createTitledLineBorder("Font Size", mainPanel.getOptions().getFontSize()));

        tinyBtn.addActionListener((ActionEvent ae) -> {
            mainPanel.getOptions().setFontSize(10);
        });

        smallBtn.addActionListener((ActionEvent ae) -> {
            mainPanel.getOptions().setFontSize(12);
        });

        normalBtn.addActionListener((ActionEvent ae) -> {
            mainPanel.getOptions().setFontSize(14);
        });

        largeBtn.addActionListener((ActionEvent ae) -> {
            mainPanel.getOptions().setFontSize(16);
        });

        hugeBtn.addActionListener((ActionEvent ae) -> {
            mainPanel.getOptions().setFontSize(22);
        });

        ButtonGroup fontSizeGroup = new ButtonGroup();
        fontSizeGroup.add(tinyBtn);
        fontSizeGroup.add(smallBtn);
        fontSizeGroup.add(normalBtn);
        fontSizeGroup.add(largeBtn);
        fontSizeGroup.add(hugeBtn);

        this.add(tinyBtn);
        this.add(smallBtn);
        this.add(normalBtn);
        this.add(largeBtn);
        this.add(hugeBtn);

        normalBtn.setSelected(true);
    }
    
    public void setFontSize(int fontSize) {
        Font optFont = tinyBtn.getFont().deriveFont(Font.BOLD, fontSize);

        tinyBtn.setFont(optFont);
        smallBtn.setFont(optFont);
        normalBtn.setFont(optFont);
        largeBtn.setFont(optFont);
        hugeBtn.setFont(optFont);

        this.setBorder(BaseNavPanel.createTitledLineBorder("Font Size", getMainPanel().getOptions().getFontSize()));
    }
}
