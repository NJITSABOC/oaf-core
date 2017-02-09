package edu.njit.cs.saboc.nat.generic;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.layout.NATLayout;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The main panel (and top-level container of functionality) of the NAT
 * 
 * @param <T> The type of concept used in this NAT (e.g., OWL Class or SNOMED CT concept)
 */
public class NATBrowserPanel<T extends Concept> extends JPanel {

    private final ConceptBrowserDataSource<T> dataSource;

    private final FocusConceptManager<T> focusConcept;
    
    private final JFrame parentFrame;
     
    private final NATLayout layout;
    
    /**
     * 
     * @param parentFrame The top-level JFrame that this NAT belongs to
     * 
     * @param dataSource
     * @param layout 
     * @param options
     */
    public NATBrowserPanel(
            JFrame parentFrame, 
            ConceptBrowserDataSource<T> dataSource, 
            NATLayout layout) {
        
        this.setLayout(new BorderLayout());
               
        this.dataSource = dataSource;
        
        this.parentFrame = parentFrame;
        
        this.layout = layout;
        
        this.add(layout, BorderLayout.CENTER);    
        
        focusConcept = new FocusConceptManager<>(this, dataSource);
        
        layout.createLayout(this);
        
        this.revalidate();
        this.repaint();
    }
    
    public ConceptBrowserDataSource<T> getDataSource() {
        return dataSource;
    }

    public void navigateTo(T c) {
        focusConcept.navigateTo(c);
    }

    public void initialize() {
        
    }

    public FocusConceptManager<T> getFocusConceptManager() {
        return focusConcept;
    }

    public JFrame getParentFrame() {
        return parentFrame;
    }
    
    public NATLayout getNATLayout() {
        return layout;
    }
}
