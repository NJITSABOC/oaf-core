package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractChildGroupTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericChildDisjointGroupTableModel<DISJOINTGROUP_T extends DisjointGenericConceptGroup, 
        GROUP_T extends GenericConceptGroup> extends BLUAbstractChildGroupTableModel<DISJOINTGROUP_T> {
    
    private final BLUDisjointAbNConfiguration configuration;
    
    public GenericChildDisjointGroupTableModel(BLUDisjointAbNConfiguration configuration) {
        super(new String [] {
            configuration.getDisjointGroupTypeName(false), 
            String.format("Overlapping %s", configuration.getGroupTypeName(true)),
            String.format("# %s", configuration.getConceptTypeName(true))
        });
        
        this.configuration = configuration;
    }
    
    @Override
    protected Object[] createRow(DISJOINTGROUP_T item) {
        ArrayList<String> overlappingPAreaNames = new ArrayList<>();
        
        HashSet<GROUP_T> overlaps = item.getOverlaps();
        
        overlaps.forEach( (GROUP_T group) -> {
            overlappingPAreaNames.add(String.format("%s (%d)", 
                    configuration.getGroupName(group),
                    group.getConceptCount()));
        });
        
        Collections.sort(overlappingPAreaNames);
        
        String overlapsStr = overlappingPAreaNames.get(0);
        
        for(int c = 1; c < overlappingPAreaNames.size(); c++) {
            overlapsStr += ("\n" + overlappingPAreaNames.get(c));
        }
        
        return new Object [] {
            configuration.getDisjointGroupName(item),
            overlapsStr,
            item.getConceptCount()
        };
    }
}
