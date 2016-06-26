package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ChildNodeTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class ChildDisjointNodeTableModel extends ChildNodeTableModel {
    
    private final DisjointAbNConfiguration configuration;
    
    public ChildDisjointNodeTableModel(DisjointAbNConfiguration configuration) {
        super(new String [] {
            configuration.getTextConfiguration().getNodeTypeName(false), 
            String.format("Overlapping %s", configuration.getTextConfiguration().getOverlappingNodeTypeName(true)),
            String.format("# %s", configuration.getTextConfiguration().getConceptTypeName(true))
        });
        
        this.configuration = configuration;
    }
    
    @Override
    protected Object[] createRow(Node item) {
        DisjointNode disjointNode = (DisjointNode)item;
        
        ArrayList<String> overlappingPAreaNames = new ArrayList<>();
        
        Set<Node> overlaps = disjointNode.getOverlaps();
        
        overlaps.forEach( (node) -> {
            overlappingPAreaNames.add(String.format("%s (%d)", 
                    node.getName(),
                    node.getConceptCount()));
        });
        
        Collections.sort(overlappingPAreaNames);
        
        String overlapsStr = overlappingPAreaNames.get(0);
        
        for(int c = 1; c < overlappingPAreaNames.size(); c++) {
            overlapsStr += ("\n" + overlappingPAreaNames.get(c));
        }
        
        return new Object [] {
            item.getName(),
            overlapsStr,
            item.getConceptCount()
        };
    }
}
