package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.fields.NATDataField;
import edu.njit.cs.saboc.nat.generic.gui.listeners.DataLoadedListener;
import java.awt.BorderLayout;
import java.util.ArrayList;


/**
 * A class that displays the parents or children of the Focus Concept.  The
 * top middle and bottom middle NAT panels are instances of this class.
 */
public class ParentChildPanel<T> extends NATLayoutPanel implements Toggleable {
    
    public enum PanelType {
        PARENT, CHILD
    }
    
    private PanelType panelType;
    
    private ConceptListPanel<T> listPanel;
    
    private String name;
    
    public ParentChildPanel(final GenericNATBrowser<T> mainPanel, PanelType panelType, ConceptBrowserDataSource<T> dataSource) {
        this.setLayout(new BorderLayout());
        
        this.setBackground(mainPanel.getNeighborhoodBGColor());
        
        NATDataField field = panelType == PanelType.CHILD ? mainPanel.getFocusConcept().COMMON_DATA_FIELDS.CHILDREN : 
                mainPanel.getFocusConcept().COMMON_DATA_FIELDS.PARENTS;
        
        this.listPanel = new ConceptListPanel<T>(mainPanel, field, dataSource, new DataLoadedListener<ArrayList<T>>() {
            public void dataLoaded(ArrayList<T> concepts) {
                setBorder(BaseNavPanel.createTitledLineBorder(String.format("%s (%d)", name, concepts.size())));
            }
        }, true);

        this.panelType = panelType;
        
        if(this.panelType == PanelType.PARENT) {
            name = "Parents";
        } else {
            name = "Children";
        }
        
        setBorder(BaseNavPanel.createTitledLineBorder(name));
          
        add(listPanel, BorderLayout.CENTER);
    }
    
    public void toggle() {
        listPanel.toggle();
    }
}
