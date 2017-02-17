package edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.concept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer.HierarchyDisplayInfo;
import edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.FilterableNestedEntryPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.ExtendedNeighborhoodResult;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public class GrandparentEntryPanel <T extends Concept, V extends ExtendedNeighborhoodResult<T>> 
            extends FilterableNestedEntryPanel<FilterableExtendedNeighborhoodEntry<T, V>> {
    
    public GrandparentEntryPanel(FilterableExtendedNeighborhoodEntry<T, V> entry, ConceptBrowserDataSource<T> dataSource) {
        
        super(entry);
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.setBorder(BorderFactory.createEtchedBorder());
        
        this.setOpaque(false);

        entry.getExpandedNeighborhoodEntries().forEach((grandParentEntry) -> {

            ConceptEntryPanel grandParentEntryPanel = new ConceptEntryPanel(
                    grandParentEntry, 
                    entry.getCurrentFilter(), 
                    dataSource, 
                    HierarchyDisplayInfo.None);
            
            this.add(grandParentEntryPanel);

            super.addSubEntryPanel(grandParentEntryPanel);
        });
        
        Filterable<T> parentEntry = entry.getDirectNeighborhoodEntry();
        
        ConceptEntryPanel parentEntryPanel = new ConceptEntryPanel(
                parentEntry, 
                entry.getCurrentFilter(), 
                dataSource, 
                HierarchyDisplayInfo.None);
        
        JPanel indentPanel = new JPanel(new BorderLayout());
        indentPanel.add(Box.createHorizontalStrut(32), BorderLayout.WEST);
        indentPanel.add(parentEntryPanel, BorderLayout.CENTER);
        indentPanel.setOpaque(false);

        this.add(indentPanel);

        super.addSubEntryPanel(parentEntryPanel);
    }
}