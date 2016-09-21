
package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.ArrayList;

/**
 *
 * @author cro3
 */
public class TopologicalListVisitor<T> extends TopologicalVisitor<T> {
    
    private final ArrayList<T> result = new ArrayList<>();
    
    public TopologicalListVisitor(Hierarchy<T> theHierarchy) {
        super(theHierarchy);
        
    }
    
    public void visit(T element) {
        result.add(element);
    }
    
    public ArrayList<T> getTopologicalList() {
        return result;
    }
}
