
package edu.njit.cs.saboc.blu.core.datastructure.hierarchy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Stack;

/**
 *
 * @author Chris
 */
public abstract class MultiRootedHierarchy<T> {
    protected HashSet<T> roots;
    
    protected HashMap<T, HashSet<T>> children = 
            new HashMap<T, HashSet<T>>();
    
    protected HashMap<T, HashSet<T>> parents =
            new HashMap<T, HashSet<T>>();
    
    public MultiRootedHierarchy(HashSet<T> roots) {
        this.roots = new HashSet<T>(roots);
        
        for(T root : roots) {
            children.put(root, new HashSet<T>());
            parents.put(root, new HashSet<T>());
        }
    }
    
    public MultiRootedHierarchy(HashSet<T> roots, HashMap<T, HashSet<T>> hierarchy) {
        this.roots = new HashSet<T>(roots);
        
        Stack<T> convertStack = new Stack<T>();

        for (T root : roots) {
            convertStack.add(root);

            // Construct the subhierarchy at the given root.
            while (!convertStack.isEmpty()) {
                T concept = convertStack.pop();

                HashSet<T> conceptChildren = hierarchy.get(concept);

                if (conceptChildren == null) {
                    continue;
                }

                for (T child : conceptChildren) {
                    addIsA(child, concept);

                    if (!convertStack.contains(child)) {
                        convertStack.add(child);
                    }
                }
            }
        }
    }

    public HashSet<T> getRoots() {
        return roots;
    }
    
    /**
     * Adds an IS A relationship between from and to. e.g., from IS A to.
     * @param from The child concept
     * @param to The parent concept
     */
    final public void addIsA(T from, T to) {
        if(!parents.containsKey(from)) {
            parents.put(from, new HashSet<T>());
        }
        
        if(!parents.containsKey(to)) {
            parents.put(to, new HashSet<T>());
        }
        
        if(!children.containsKey(to)) {
            children.put(to, new HashSet<T>());
        }
        
        if(!children.containsKey(from)) {
            children.put(from, new HashSet<T>());
        }
        
        parents.get(from).add(to);
        children.get(to).add(from);       
    }
    
    public void addHierarchy(MultiRootedHierarchy<T> hierarchy) {
        roots.addAll(hierarchy.roots);
        
        addAllHierarchicalRelationships(hierarchy);
    }
    
    public void addAllHierarchicalRelationships(MultiRootedHierarchy<T> hierarchy) {
        for (Entry<T, HashSet<T>> childEntry : hierarchy.children.entrySet()) {
            for (T child : childEntry.getValue()) {
                this.addIsA(child, childEntry.getKey());
            }
        }
    }
    
    public abstract SingleRootedHierarchy<T, ? extends SingleRootedHierarchy> getSubhierarchyRootedAt(T root);
    
    public HashSet<T> getNodesInHierarchy() {
        HashSet<T> allNodes = new HashSet<T>(children.keySet());
        allNodes.addAll(roots);
        
        return allNodes;
    }
    
    public HashSet<T> getChildren(T c) {
        if(children.containsKey(c)) {
            return children.get(c);
        }
        
        return new HashSet<T>();
    }
    
    public HashSet<T> getParents(T c) {
        if(parents.containsKey(c)) {
            return parents.get(c);
        }
        
        return new HashSet<T>();
    }
    
    public boolean contains(T item) {
        return children.containsKey(item);
    }
}
