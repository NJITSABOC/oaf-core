package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ChildNodeTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericChildDisjointGroupTableModel<DISJOINTGROUP_T extends DisjointNode, 
        GROUP_T extends GenericConceptGroup> extends ChildNodeTableModel<DISJOINTGROUP_T> {
    
    private final BLUDisjointConfiguration configuration;
    
    public GenericChildDisjointGroupTableModel(BLUDisjointConfiguration configuration) {
        super(new String [] {
            configuration.getTextConfiguration().getGroupTypeName(false), 
            String.format("Overlapping %s", configuration.getTextConfiguration().getGroupTypeName(true)),
            String.format("# %s", configuration.getTextConfiguration().getConceptTypeName(true))
        });
        
        this.configuration = configuration;
    }
    
    @Override
    protected Object[] createRow(DISJOINTGROUP_T item) {
        ArrayList<String> overlappingPAreaNames = new ArrayList<>();
        
        HashSet<GROUP_T> overlaps = item.getOverlaps();
        
        overlaps.forEach( (GROUP_T group) -> {
            overlappingPAreaNames.add(String.format("%s (%d)", 
                    configuration.getTextConfiguration().getOverlappingGroupName(group),
                    group.getConceptCount()));
        });
        
        Collections.sort(overlappingPAreaNames);
        
        String overlapsStr = overlappingPAreaNames.get(0);
        
        for(int c = 1; c < overlappingPAreaNames.size(); c++) {
            overlapsStr += ("\n" + overlappingPAreaNames.get(c));
        }
        
        return new Object [] {
            configuration.getTextConfiguration().getGroupName(item),
            overlapsStr,
            item.getConceptCount()
        };
    }
}
