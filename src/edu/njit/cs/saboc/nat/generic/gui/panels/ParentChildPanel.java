package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.NATOptions;
import edu.njit.cs.saboc.nat.generic.fields.NATDataField;
import edu.njit.cs.saboc.nat.generic.gui.listeners.DataLoadedListener;
import java.awt.BorderLayout;
import java.util.ArrayList;


/**
 * A class that displays the parents or children of the Focus Concept.  The
 * top middle and bottom middle NAT panels are instances of this class.
 */
public class ParentChildPanel extends NATLayoutPanel implements Toggleable {
    
    public enum PanelType {
        PARENT, CHILD
    }
    
    private PanelType panelType;
    
    private ConceptListPanel listPanel;
    
    private String name;
    
    public ParentChildPanel(GenericNATBrowser mainPanel, PanelType panelType, ConceptBrowserDataSource dataSource) {
        super(mainPanel);
        
        this.setLayout(new BorderLayout());
        
        this.setBackground(mainPanel.getNeighborhoodBGColor());
        
        final NATOptions options = mainPanel.getOptions();
        
        NATDataField field = panelType == PanelType.CHILD ? mainPanel.getFocusConcept().COMMON_DATA_FIELDS.CHILDREN : 
                mainPanel.getFocusConcept().COMMON_DATA_FIELDS.PARENTS;
        
        this.listPanel = new ConceptListPanel(mainPanel, field, dataSource, true);
        listPanel.addDataLoadedListener(new DataLoadedListener<ArrayList<Concept>>() {
            public void dataLoaded(ArrayList<Concept> concepts) {
                setBorder(BaseNavPanel.createTitledLineBorder(String.format("%s (%d)", name, concepts.size()), options.getFontSize()));
            }
        });

        this.panelType = panelType;
        
        if(this.panelType == PanelType.PARENT) {
            name = "Parents";
        } else {
            name = "Children";
        }
        
        setBorder(BaseNavPanel.createTitledLineBorder(name, options.getFontSize()));
          
        add(listPanel, BorderLayout.CENTER);
    }
    
    protected void setFontSize(int fontSize) {
        setBorder(BaseNavPanel.createTitledLineBorder(name, fontSize));
    }
    
    public void toggle() {
        listPanel.toggle();
    }
}
