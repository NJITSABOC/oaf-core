package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.result.AncestorDepthResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class AncestorDepthVisitor<T> extends TopologicalVisitor<T> {
    
    private HashMap<T, Integer> depth = new HashMap<>();
    
    private ArrayList<AncestorDepthResult<T>> result = new ArrayList<>();
       
    public AncestorDepthVisitor(MultiRootedHierarchy<T> theHierarchy) {
        super(theHierarchy);
    }
    
    public void visit(T node) {
        
        HashSet<T> parents = theHierarchy.getParents(node);

        int maxParentDepth = -1;

        for (T parent : parents) {
            if (depth.get(parent) > maxParentDepth) {
                maxParentDepth = depth.get(parent);
            }
        }

        depth.put(node, maxParentDepth + 1);
        
        result.add(new AncestorDepthResult<>(node, depth.get(node)));
    }
    
    public ArrayList<AncestorDepthResult<T>> getResult() {
        return result;
    }
}
