package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class AllPathsToNodeVisitor<T> extends TopologicalVisitor<T> {
    private ArrayList<ArrayList<T>> allPaths = new ArrayList<>();
    
    private HashMap<T, ArrayList<ArrayList<T>>> pathMap = new HashMap<>();
    
    private T endPoint;
    
    public AllPathsToNodeVisitor(MultiRootedHierarchy<T> theHierarchy, T endPoint) {
        super(theHierarchy);
        
        this.endPoint = endPoint;
        
        theHierarchy.getRoots().forEach((e) -> {
            ArrayList<T> startingList = new ArrayList<>();
            startingList.add(e);

            ArrayList<ArrayList<T>> rootPath = new ArrayList<>();
            rootPath.add(startingList);

            pathMap.put(e, rootPath);
        });
    }
    
    public void visit(T node) {
        
        if(theHierarchy.getRoots().contains(node)) {
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
