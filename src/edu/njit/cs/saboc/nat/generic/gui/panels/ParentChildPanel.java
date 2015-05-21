package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.FocusConcept;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.gui.listeners.DataLoadedListener;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JPanel;


/**
 * A class that displays the parents or children of the Focus Concept.  The
 * top middle and bottom middle NAT panels are instances of this class.
 */
public class ParentChildPanel extends JPanel implements Toggleable {
    
    public enum PanelType {
        PARENT, CHILD
    }
    
    private PanelType panelType;
    
    private ConceptListPanel listPanel;
    
    private String name;
    
    public ParentChildPanel(final GenericNATBrowser mainPanel, PanelType panelType, ConceptBrowserDataSource dataSource) {
        this.setLayout(new BorderLayout());
        
        this.setBackground(mainPanel.getNeighborhoodBGColor());
        
        FocusConcept.Fields field = panelType == PanelType.CHILD ? FocusConcept.Fields.CHILDREN : FocusConcept.Fields.PARENTS;
        
        this.listPanel = new ConceptListPanel(mainPanel, field, dataSource, new DataLoadedListener<ArrayList<BrowserConcept>>() {
            public void dataLoaded(ArrayList<BrowserConcept> concepts) {
                setBorder(BaseNavPanel.createConceptBorder(String.format("%s (%d)", name, concepts.size())));
            }
        });

        this.panelType = panelType;
        
        if(this.panelType == PanelType.PARENT) {
            name = "Parents";
        } else {
            name = "Children";
        }
        
        setBorder(BaseNavPanel.createConceptBorder(name));
          
        add(listPanel, BorderLayout.CENTER);
    }
    
    public void toggle() {
        listPanel.toggle();
    }
}
