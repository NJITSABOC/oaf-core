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
        
    private final JCheckBox chkShowStrictOnly;
    
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

        this.chkShowStrictOnly = new JCheckBox("Show Strict Siblings Only");
        
        this.chkShowStrictOnly.addActionListener((ae) -> {
            
           if(chkShowStrictOnly.isSelected()) {
               siblingPanel.setDataRetriever(CommonBrowserDataRetrievers.getStrictSiblingsRetriever(dataSource));
           } else {
                siblingPanel.setDataRetriever(CommonBrowserDataRetrievers.getSiblingsRetriever(dataSource));
           }
        });

        this.siblingPanel.addOptionsComponent(chkShowStrictOnly);

        this.add(siblingPanel, BorderLayout.CENTER);
    }

    @Override
    protected void setFontSize(int fontSize) {
        
    }
}
