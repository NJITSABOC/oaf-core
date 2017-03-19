package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.errorsummarylabel.ParentErrorSummaryLabel;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JList;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ParentErrorDetailsRenderer<T extends Concept> extends BaseFilterableRenderer<T> {
    
    private final SimpleConceptRenderer<T> conceptRenderer;
    private final ParentErrorSummaryLabel<T> parentErrorLabel;
    
    private final NATBrowserPanel<T> mainPanel;
    private final ConceptBrowserDataSource<T> dataSource;
    
    public ParentErrorDetailsRenderer(
            NATBrowserPanel<T> mainPanel,
            ConceptBrowserDataSource<T> dataSource) {
        
        this.mainPanel = mainPanel;
        this.dataSource = dataSource;

        this.conceptRenderer = new SimpleConceptRenderer<>(mainPanel, dataSource);
        this.parentErrorLabel = new ParentErrorSummaryLabel<>(mainPanel, dataSource);
        
        this.conceptRenderer.setOpaque(false);
        this.conceptRenderer.setPreferredSize(null);
        
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        this.add(conceptRenderer);
        this.add(parentErrorLabel);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Filterable<T>> list, Filterable<T> value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); 
        
        showDetailsFor(value);
        
        return this;
    }

    @Override
    public void showDetailsFor(Filterable<T> filterableEntry) {
        
        conceptRenderer.showDetailsFor(filterableEntry);

        if(mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            AuditSet<T> currentAuditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();
            T focusConcept = mainPanel.getFocusConceptManager().getActiveFocusConcept();
            
            if(currentAuditSet.getConcepts().contains(focusConcept)) {
                this.parentErrorLabel.showDetailsFor(filterableEntry.getObject());
            }
        }
    }
}
