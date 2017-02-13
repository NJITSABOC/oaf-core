package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AncestorPanel<T extends Concept> extends BaseNATPanel<T>  {
    
    private final ParentsPanel<T> parentsPanel;
    private final GrandparentsPanel<T> grandparentsPanel;
    
    private final CardLayout contentPanelLayout;
    private final JPanel contentPanel;

    private final JCheckBox chkShowGrandparents;
    
    public AncestorPanel(NATBrowserPanel<T> browserPanel, ConceptBrowserDataSource<T> dataSource) {
        super(browserPanel, dataSource);
        
        this.setLayout(new BorderLayout());
        
        this.parentsPanel = new ParentsPanel<>(browserPanel, dataSource, true);
        this.grandparentsPanel = new GrandparentsPanel<>(browserPanel, dataSource);
        
        this.contentPanelLayout = new CardLayout();
        this.contentPanel = new JPanel(contentPanelLayout);
                
        this.chkShowGrandparents = new JCheckBox("Show Grandparents");
        
        this.chkShowGrandparents.addActionListener((ae) -> {
            
            if(chkShowGrandparents.isSelected()) {
                contentPanelLayout.show(contentPanel, "Grandparents");
            } else {
                contentPanelLayout.show(contentPanel, "Parents");
            }
        });
        
        this.contentPanel.add(parentsPanel, "Parents");
        this.contentPanel.add(grandparentsPanel, "Grandparents");
        
        this.add(chkShowGrandparents, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    protected void setFontSize(int fontSize) {
        
    }
}
