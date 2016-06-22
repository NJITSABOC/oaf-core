package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeInformation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ParentNodeTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericParentDisjointGroupTableModel<CONCEPT_T, DISJOINTGROUP_T extends DisjointNode, GROUP_T extends GenericConceptGroup> 
        extends ParentNodeTableModel<CONCEPT_T, DISJOINTGROUP_T, ParentNodeInformation<CONCEPT_T, DISJOINTGROUP_T>> {
    
    private final BLUDisjointConfiguration config;
    
    public GenericParentDisjointGroupTableModel(BLUDisjointConfiguration config) {
        super(new String [] {
            String.format("Parent %s", config.getTextConfiguration().getConceptTypeName(false)),
            String.format("Parent %s", config.getTextConfiguration().getGroupTypeName(false)),
            String.format("Parent Overlapping %s", config.getTextConfiguration().getGroupTypeName(true)),
            String.format("# %s", config.getTextConfiguration().getConceptTypeName(true))
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(ParentNodeInformation<CONCEPT_T, DISJOINTGROUP_T> item) {
        ArrayList<String> overlappingPAreaNames = new ArrayList<>();
        
        HashSet<GROUP_T> overlaps = item.getParentGroup().getOverlaps();
        
        overlaps.forEach( (GROUP_T group) -> {
            overlappingPAreaNames.add(String.format("%s (%d)", 
                    config.getTextConfiguration().getOverlappingGroupName(group),
                    group.getConceptCount()));
        });
        
        Collections.sort(overlappingPAreaNames);
        
        String overlapsStr = overlappingPAreaNames.get(0);
        
        for(int c = 1; c < overlappingPAreaNames.size(); c++) {
            overlapsStr += ("\n" + overlappingPAreaNames.get(c));
        }
        
        return new Object [] {
            config.getTextConfiguration().getConceptName(item.getParentConcept()),
            config.getTextConfiguration().getGroupName(item.getParentGroup()),
            overlapsStr,
            item.getParentGroup().getConceptCount()
        };
        
    }
}
