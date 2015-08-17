package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingDetailsEntry;
import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class BLUAbstractOverlappingDetailsTableModel<GROUP_T extends GenericConceptGroup, 
        DISJOINTGROUP_T extends DisjointGenericConceptGroup,
        CONCEPT_T> extends BLUAbstractTableModel<OverlappingDetailsEntry<GROUP_T, DISJOINTGROUP_T>> {
    
    protected final BLUDisjointAbNConfiguration configuration;
    
    public BLUAbstractOverlappingDetailsTableModel(BLUDisjointAbNConfiguration configuration) {
        super(new String[] {
            String.format("Other %s", configuration.getGroupTypeName(false)),
            String.format("# Common %s", configuration.getDisjointGroupTypeName(true)),
            String.format("# Overlapping %s", configuration.getConceptTypeName(true))
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
            configuration.getGroupName(item.getOverlappingGroup()),
            disjointGroups.size(),
            overlappingConcepts.size()
        };
    }
    
}