package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class AllPathsToNodeVisitor<T> extends SingleRootedHierarchyTopologicalVisitor<T> {
    private ArrayList<ArrayList<T>> allPaths = new ArrayList<>();
    
    private HashMap<T, ArrayList<ArrayList<T>>> pathMap = new HashMap<>();
    
    private T endPoint;
    
    public AllPathsToNodeVisitor(SingleRootedHierarchy<T, ? extends SingleRootedHierarchy> theHierarchy, T endPoint) {
        super(theHierarchy);
        
        this.endPoint = endPoint;
        
        ArrayList<T> startingList = new ArrayList<>();
        startingList.add(theHierarchy.getRoot());
        
        ArrayList<ArrayList<T>> rootPath = new ArrayList<>();
        rootPath.add(startingList);
        
        pathMap.put(theHierarchy.getRoot(), rootPath);
    }
    
    public void visit(T node) {
        
        if(node.equals(theHierarchy.getRoot())) {
            return;
        }
        
        HashSet<T> parents = theHierarchy.getParents(node);

        ArrayList<ArrayList<T>> pathsToConcept = new ArrayList<>();

        for (T parent : parents) {
            ArrayList<ArrayList<T>> parentPaths = (ArrayList<ArrayList<T>>) pathMap.get(parent).clone();

            for (ArrayList<T> parentPath : parentPaths) {
                ArrayList<T> path = (ArrayList<T>) parentPath.clone();
                path.add(node);

                pathsToConcept.add(path);
            }
        }

        if (node.equals(endPoint)) {
            this.allPaths = pathsToConcept;
        } else {
            pathMap.put(node, pathsToConcept);
        }

    }
    
    public ArrayList<ArrayList<T>> getAllPaths() {
        return allPaths;
    }
}
