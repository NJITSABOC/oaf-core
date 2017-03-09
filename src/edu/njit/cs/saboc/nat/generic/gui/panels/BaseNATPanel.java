package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.FocusConceptManager;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import javax.swing.JPanel;


/**
 * Base class for defining a panel in the NAT. All NAT panels should
 * extend this panel, as it provides access to the data source and main panel
 * 
 * @author cro3
 * @param <T> 
 */
public abstract class BaseNATPanel<T extends Concept> extends JPanel {
    
    private final ConceptBrowserDataSource<T> dataSource;
    private final FocusConceptManager<T> focusConceptManager;
    private final NATBrowserPanel<T> mainPanel;
    
    public BaseNATPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {

        this.mainPanel = mainPanel;
        this.focusConceptManager = mainPanel.getFocusConceptManager();
        this.dataSource = dataSource;
    }
    
    public NATBrowserPanel<T> getMainPanel() {
        return mainPanel;
    }
    
    public FocusConceptManager<T> getFocusConceptManager() {
        return focusConceptManager;
    }
    
    public ConceptBrowserDataSource<T> getDataSource() {
        return dataSource;
    }
}
