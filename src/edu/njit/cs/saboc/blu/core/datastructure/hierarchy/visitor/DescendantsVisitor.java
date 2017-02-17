package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class DescendantsVisitor<T> extends TopologicalVisitor<T> {
    
    private final Map<T, Set<T>> descendants = new HashMap<>();
    
    public DescendantsVisitor(Hierarchy<T> theHierarchy) {
        super(theHierarchy);
    }

    @Override
    public void visit(T node) {
        Set<T> nodeDescendants = new HashSet<>();
        
        Set<T> children = super.getHierarchy().getChildren(node);
        
        nodeDescendants.addAll(children);
        
        if(children == null) {
            System.out.println("Null children: " + node);
        }
        
        children.forEach((child) -> {
            if (descendants.get(child) == null) {
                System.out.println("Null descendants: " + child);
            }

            nodeDescendants.addAll(descendants.get(child));
        });
        
        descendants.put(node, nodeDescendants);
    }
    
    public Map<T, Set<T>> getDescendants() {
        return descendants;
    }
    
    public Map<T, Integer> getDescendantCounts() {
        Map<T, Integer> descendantCounts = new HashMap<>();
        
        descendants.forEach( (node, nodeDescendants) -> {
            descendantCounts.put(node, nodeDescendants.size());
        });
        
        return descendantCounts;
    }
}
