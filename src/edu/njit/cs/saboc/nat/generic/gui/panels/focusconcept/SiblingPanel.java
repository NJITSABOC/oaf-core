package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer;
import edu.njit.cs.saboc.nat.generic.gui.panels.ConceptListPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.NATLayoutPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.CommonBrowserDataRetrievers;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class SiblingPanel<T extends Concept> extends NATLayoutPanel {
    
    private final ConceptListPanel<T> siblingPanel;
    private final ConceptListPanel<T> strictSiblingPanel;
    
    private final CardLayout contentPanelLayout;
    private final JPanel contentPanel;
    
    private final JCheckBox chkSiblingShowStrictOnly;
    private final JCheckBox chkStrictSiblingShowStrictOnly;
    
    public SiblingPanel(NATBrowserPanel<T> mainPanel, ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel);
        
        this.setLayout(new BorderLayout());

        this.siblingPanel = new ConceptListPanel<>(
                mainPanel,
                dataSource,
                CommonBrowserDataRetrievers.getSiblingsRetriever(dataSource),
                new SimpleConceptRenderer<>(dataSource, SimpleConceptRenderer.HierarchyDisplayInfo.None),
                true,
                true);

        this.strictSiblingPanel = new ConceptListPanel<>(
                mainPanel,
                dataSource,
                CommonBrowserDataRetrievers.getStrictSiblingsRetriever(dataSource),
                new SimpleConceptRenderer<>(dataSource, SimpleConceptRenderer.HierarchyDisplayInfo.None),
                true,
                true);
        
        this.contentPanelLayout = new CardLayout();
        this.contentPanel = new JPanel(contentPanelLayout);
                
        this.chkSiblingShowStrictOnly = new JCheckBox("Show Strict Siblings Only");
        this.chkStrictSiblingShowStrictOnly = new JCheckBox("Show Strict Siblings Only");
        
        this.chkSiblingShowStrictOnly.addActionListener((ae) -> {
            
            if(chkSiblingShowStrictOnly.isSelected()) {
                contentPanelLayout.show(contentPanel, "StrictSiblings");
            } else {
                contentPanelLayout.show(contentPanel, "Siblings");
            }
            
            chkStrictSiblingShowStrictOnly.setSelected(chkSiblingShowStrictOnly.isSelected());
        });

        this.chkStrictSiblingShowStrictOnly.addActionListener((ae) -> {
            
            if(chkStrictSiblingShowStrictOnly.isSelected()) {
                contentPanelLayout.show(contentPanel, "StrictSiblings");
            } else {
                contentPanelLayout.show(contentPanel, "Siblings");
            }
            
            chkSiblingShowStrictOnly.setSelected(chkStrictSiblingShowStrictOnly.isSelected());
        });
        
        this.siblingPanel.addOptionsComponent(chkSiblingShowStrictOnly);
        this.strictSiblingPanel.addOptionsComponent(chkStrictSiblingShowStrictOnly);
        

        this.contentPanel.add(siblingPanel, "Siblings");
        this.contentPanel.add(strictSiblingPanel, "StrictSiblings");
        
        this.add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    protected void setFontSize(int fontSize) {
        
    }
}
