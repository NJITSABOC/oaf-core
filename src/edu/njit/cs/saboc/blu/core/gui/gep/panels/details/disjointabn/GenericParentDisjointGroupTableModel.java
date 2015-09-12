package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.GenericParentGroupInfo;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractParentGroupTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericParentDisjointGroupTableModel<CONCEPT_T, DISJOINTGROUP_T extends DisjointGenericConceptGroup, GROUP_T extends GenericConceptGroup> 
        extends BLUAbstractParentGroupTableModel<CONCEPT_T, DISJOINTGROUP_T, GenericParentGroupInfo<CONCEPT_T, DISJOINTGROUP_T>> {
    
    private final BLUDisjointAbNConfiguration config;
    
    public GenericParentDisjointGroupTableModel(BLUDisjointAbNConfiguration config) {
        super(new String [] {
            String.format("Parent %s", config.getConceptTypeName(false)),
            String.format("Parent %s", config.getGroupTypeName(false)),
            String.format("Parent Overlapping %s", config.getGroupTypeName(true)),
            String.format("# %s", config.getConceptTypeName(true))
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(GenericParentGroupInfo<CONCEPT_T, DISJOINTGROUP_T> item) {
        ArrayList<String> overlappingPAreaNames = new ArrayList<>();
        
        HashSet<GROUP_T> overlaps = item.getParentGroup().getOverlaps();
        
        overlaps.forEach( (GROUP_T group) -> {
            overlappingPAreaNames.add(String.format("%s (%d)", 
                    config.getOverlappingGroupName(group),
                    group.getConceptCount()));
        });
        
        Collections.sort(overlappingPAreaNames);
        
        String overlapsStr = overlappingPAreaNames.get(0);
        
        for(int c = 1; c < overlappingPAreaNames.size(); c++) {
            overlapsStr += ("\n" + overlappingPAreaNames.get(c));
        }
        
        return new Object [] {
            config.getConceptName(item.getParentConcept()),
            config.getGroupName(item.getParentGroup()),
            overlapsStr,
            item.getParentGroup().getConceptCount()
        };
        
    }
}
