package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingDetailsEntry;
import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointableConfiguration;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class BLUAbstractOverlappingDetailsTableModel<GROUP_T extends GenericConceptGroup, 
        DISJOINTGROUP_T extends DisjointGenericConceptGroup,
        CONCEPT_T> extends BLUAbstractTableModel<OverlappingDetailsEntry<GROUP_T, DISJOINTGROUP_T>> {
    
    protected final BLUDisjointableConfiguration configuration;
    
    public BLUAbstractOverlappingDetailsTableModel(BLUDisjointableConfiguration configuration) {
        super(new String[] {
            String.format("Other %s", configuration.getTextConfiguration().getGroupTypeName(false)),
            String.format("# Common %s", configuration.getTextConfiguration().getDisjointGroupTypeName(true)),
            String.format("# Overlapping %s", configuration.getTextConfiguration().getConceptTypeName(true))
        });

        this.configuration = configuration;
    }

    @Override
    protected Object[] createRow(OverlappingDetailsEntry<GROUP_T, DISJOINTGROUP_T> item) {
        HashSet<CONCEPT_T> overlappingConcepts = new HashSet<>();
        
        HashSet<DISJOINTGROUP_T> disjointGroups = item.getDisjointGroups();
        
        disjointGroups.forEach( (DISJOINTGROUP_T disjointGroup) -> {
            overlappingConcepts.addAll(disjointGroup.getConceptsAsList());
        });
        
        return new Object[] {
            configuration.getTextConfiguration().getGroupName(item.getOverlappingGroup()),
            disjointGroups.size(),
            overlappingConcepts.size()
        };
    }
    
}
