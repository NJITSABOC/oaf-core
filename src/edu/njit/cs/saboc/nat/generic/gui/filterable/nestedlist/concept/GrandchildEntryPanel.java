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
public class GrandchildEntryPanel<T extends Concept, V extends ExtendedNeighborhoodResult<T>> 
            extends FilterableNestedEntryPanel<FilterableExtendedNeighborhoodEntry<T, V>> {
    
    public GrandchildEntryPanel(FilterableExtendedNeighborhoodEntry<T, V> entry, ConceptBrowserDataSource<T> dataSource) {
        super(entry);
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        this.setBorder(BorderFactory.createEtchedBorder());
        
        this.setOpaque(false);

        Filterable<T> childEntry = entry.getDirectNeighborhoodEntry();
        
        ConceptEntryPanel childEntryPanel = new ConceptEntryPanel(
                childEntry, 
                entry.getCurrentFilter(), 
                dataSource, 
                HierarchyDisplayInfo.Descendants);
        
        this.add(childEntryPanel);
        super.addSubEntryPanel(childEntryPanel);

        entry.getExpandedNeighborhoodEntries().forEach((grandChildEntry) -> {
            
            if (!grandChildEntry.getCurrentFilter().isPresent() || 
                    grandChildEntry.containsFilter(grandChildEntry.getCurrentFilter().get())) {
                
                ConceptEntryPanel grandChildEntryPanel = new ConceptEntryPanel(
                        grandChildEntry, 
                        entry.getCurrentFilter(), 
                        dataSource, HierarchyDisplayInfo.Descendants);

                JPanel indentPanel = new JPanel(new BorderLayout());
                indentPanel.add(Box.createHorizontalStrut(32), BorderLayout.WEST);
                indentPanel.add(grandChildEntryPanel, BorderLayout.CENTER);
                indentPanel.setOpaque(false);
                
                this.add(indentPanel);
                super.addSubEntryPanel(grandChildEntryPanel);
            }
        });
    }
}
