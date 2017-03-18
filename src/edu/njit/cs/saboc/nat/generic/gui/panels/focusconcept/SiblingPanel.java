package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.ConceptListPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.CommonBrowserDataRetrievers;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;

/**
 * Siblings the siblings (or, optionally, strict siblings) of the focus concept
 * 
 * @author Chris O
 * @param <T>
 */
public class SiblingPanel<T extends Concept> extends BaseNATPanel<T> {
    
    private final ConceptListPanel<T> siblingPanel;
        
    private final JCheckBox chkShowStrictOnly;
    
    public SiblingPanel(NATBrowserPanel<T> mainPanel, ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel, dataSource);
        
        this.setLayout(new BorderLayout());

        this.siblingPanel = new ConceptListPanel<>(
                mainPanel,
                dataSource,
                CommonBrowserDataRetrievers.getSiblingsRetriever(dataSource),
                new SimpleConceptRenderer<>(mainPanel, dataSource),
                true,
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
}
