/******************************************************************************
BaseNavPanel.java

Description:  This is a base class for the panels that display concepts
in the neighborhood of the focus concept; one (or two) steps from the
focus concept.

 ******************************************************************************/
package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.FocusConcept;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * The base class for all NAT panels.
 */
public abstract class BaseNavPanel<T> extends NATLayoutPanel {
    
    protected ConceptBrowserDataSource<T> dataSource;
    
    protected GenericNATBrowser<T> mainPanel;
    protected FocusConcept<T> focusConcept;
    protected Color baseColor = Color.darkGray;

    public BaseNavPanel(GenericNATBrowser<T> mainPanel, ConceptBrowserDataSource<T> dataSource) {
        this.mainPanel = mainPanel;
        this.focusConcept = mainPanel.getFocusConcept();
        this.dataSource = dataSource;
    }

    public abstract void focusConceptChanged();

    public abstract void dataPending();

    public abstract void dataReady();
    
    public abstract void dataEmpty();

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1, 1);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(1, 1);
    }

    // Creates a new line boarder with the given title
    public static TitledBorder createConceptBorder(String title){
        return (new TitledBorder(new LineBorder(Color.black, 1), title) {
            
            public Insets getBorderInsets(Component c, Insets insets) {
                super.getBorderInsets(c, insets);
                insets.top += 2;
                insets.left += 4;
                insets.right += 4;
                
                if(insets.top < 0) {
                    insets.top = 0;
                }
                return insets;
            }
        });
    }
}
