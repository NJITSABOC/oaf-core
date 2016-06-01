
package edu.njit.cs.saboc.blu.core.datastructure.hierarchy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.AllPathsToNodeVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.AncestorDepthVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.AncestorHierarchyBuilderVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.HierarchyVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.SubhierarchyMembersVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.SubhierarchySizeVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.TopRootVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.TopologicalVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.result.AncestorDepthResult;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Chris
 */
public class MultiRootedHierarchy<T> {
    
    protected HashSet<T> roots;
    
    protected HashMap<T, HashSet<T>> children = 
            new HashMap<>();
    
    protected HashMap<T, HashSet<T>> parents =
            new HashMap<>();
        
    public MultiRootedHierarchy(HashSet<T> roots) {
        this.roots = new HashSet<>(roots);
        
        for(T root : roots) {
            children.put(root, new HashSet<>());
            parents.put(root, new HashSet<>());
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
    
    public int size() {
        return children.keySet().size();
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
    
    public SingleRootedHierarchy<T> getSubhierarchyRootedAt(T root) {
        return new SingleRootedHierarchy<T>(root, this.children);
    }
    
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
    
    public HashMap<T, HashSet<T>> getAllChildRelationships() {
        return children;
    }
    
    public HashMap<T, HashSet<T>> getAllParentRelationships() {
        return parents;
    }
    
    public boolean contains(T item) {
        return children.containsKey(item);
    }
    
    public void BFSDown(T startingPoint, HierarchyVisitor<T> visitor) {
        BFSDown(new HashSet<>(Arrays.asList(startingPoint)), visitor);
    }
    
    public void BFSDown(HashSet<T> startingPoints, HierarchyVisitor<T> visitor) {
        Queue<T> queue = new ArrayDeque<>();
        queue.addAll(startingPoints);

        HashSet<T> visited = new HashSet<>();
        visited.addAll(startingPoints);

        while (!queue.isEmpty() && !visitor.isFinished()) {
            T node = queue.remove();

            visitor.visit(node);

            HashSet<T> nodeChildren = this.getChildren(node);

            nodeChildren.forEach((T child) -> {
                if (!visited.contains(child)) {
                    queue.add(child);
                    visited.add(child);
                }
            });
        }
    }

    public void BFSUp(T startingPoint, HierarchyVisitor<T> visitor) {
        BFSUp(new HashSet<>(Arrays.asList(startingPoint)), visitor);
    }
    
    public void BFSUp(HashSet<T> startingPoints, HierarchyVisitor<T> visitor) {
        Queue<T> queue = new ArrayDeque<>(startingPoints);

        HashSet<T> visited = new HashSet<>(startingPoints);

        while (!queue.isEmpty() && !visitor.isFinished()) {
            T node = queue.remove();

            visitor.visit(node);

            HashSet<T> nodeParents = this.getParents(node);

            nodeParents.forEach((T parent) -> {
                if (!visited.contains(parent)) {
                    queue.add(parent);
                    visited.add(parent);
                }
            });
        }
    }
    
    public void topologicalDown(HierarchyVisitor<T> visitor) {
        HashMap<T, Integer> parentCounts = new HashMap<>();
        
        HashSet<T> nodesInHierarchy = this.getNodesInHierarchy();
        
        nodesInHierarchy.forEach((T node) -> {
            parentCounts.put(node, this.getParents(node).size());
        });
        
        Queue<T> queue = new ArrayDeque<>();
        queue.addAll(this.getRoots());
        
        while(!queue.isEmpty() && !visitor.isFinished()) {
            T node = queue.remove();
            
            visitor.visit(node);
            
            HashSet<T> nodeChildren = this.getChildren(node);
            
            nodeChildren.forEach((T child) -> {
                if(parentCounts.get(child) == 1) {
                    queue.add(child);
                } else {
                    parentCounts.put(child, parentCounts.get(child) - 1);
                }
            });
        }
    }
    
    public void topologicalDownInSubhierarchy(T startingPoint, TopologicalVisitor<T> visitor) {
        HashSet<T> subhierarchy = this.getDescendants(startingPoint);
        subhierarchy.add(startingPoint);
        
        HashMap<T, Integer> parentCountInSubhierarchy = new HashMap<>();
        
        subhierarchy.forEach((T node) -> {
            int parentCount = 0;

            HashSet<T> nodeParents = this.getParents(node);

            for (T parent : nodeParents) {
                if (subhierarchy.contains(parent)) {
                    parentCount++;
                }
            }

            parentCountInSubhierarchy.put(node, parentCount);
        });

        Queue<T> queue = new ArrayDeque<>();
        queue.add(startingPoint);

        while (!queue.isEmpty() && !visitor.isFinished()) {
            T node = queue.remove();

            visitor.visit(node);

            HashSet<T> nodeChildren = this.getChildren(node);

            nodeChildren.forEach((T child) -> {
                if (parentCountInSubhierarchy.get(child) == 1) {
                    queue.add(child);
                } else {
                    parentCountInSubhierarchy.put(child, parentCountInSubhierarchy.get(child) - 1);
                }
            });
        }
    }
    
    public int countDescendants(T node) {
        SubhierarchySizeVisitor<T> visitor = new SubhierarchySizeVisitor<>(this);
        
        this.BFSDown(node, visitor);
        
        return visitor.getDescandantCount() - 1;
    }
    
    public HashSet<T> getDescendants(T node) {
        SubhierarchyMembersVisitor<T> visitor = new SubhierarchyMembersVisitor<>(this);
        
        this.BFSDown(node, visitor);
        
        HashSet<T> members = visitor.getSubhierarchyMembers();
        members.remove(node);
        
        return members;
    }
    
    public HashSet<T> getMemberSubhierarchyRoots(T node) {
        TopRootVisitor<T> visitor = new TopRootVisitor<>(this);
        
        this.BFSUp(node, visitor);
        
        return visitor.getRoots();
    }
    
    public MultiRootedHierarchy<T> getAncestorHierarchy(T node) {
        return getAncestorHierarchy(new HashSet<>(Arrays.asList(node)));
    }
    
    public MultiRootedHierarchy<T> getAncestorHierarchy(HashSet<T> nodes) {
        HashSet<T> roots = new HashSet<>();
        
        nodes.forEach( (node) -> {
            roots.addAll(getMemberSubhierarchyRoots(node));
        });

        AncestorHierarchyBuilderVisitor<T> ancestorHierarchy = new AncestorHierarchyBuilderVisitor<>(this, 
                new MultiRootedHierarchy<>(roots));
        
        this.BFSUp(nodes, ancestorHierarchy);
        
        return ancestorHierarchy.getAncestorHierarchy();
    }
    
    public ArrayList<ArrayList<T>> getAllPathsTo(T node) {
        MultiRootedHierarchy<T> ancestorHierarchy = this.getAncestorHierarchy(node);
        
        AllPathsToNodeVisitor<T> visitor = new AllPathsToNodeVisitor<>(this, node);
        
        ancestorHierarchy.topologicalDown(visitor);
        
        return visitor.getAllPaths();
    }
        
    public SingleRootedHierarchy<T> getDescendantHierarchyWithinDistance(T node, int maxDistance) {
        SingleRootedHierarchy<T> hierarchy = new SingleRootedHierarchy<>(node);
        
        int levelProcessed = 0;
                
        Queue<T> levelQueue = new ArrayDeque<>();
        levelQueue.add(node);
        
        HashSet<T> processed = new HashSet<>();

        while(!levelQueue.isEmpty() && levelProcessed < maxDistance) {
            levelProcessed++;
            
            processed.addAll(levelQueue);
            
            Queue<T> nextLevelQueue = new ArrayDeque<>();
            
            while(!levelQueue.isEmpty()) {
                T levelNode = levelQueue.remove();
                
                HashSet<T> nodeChildren = this.getChildren(levelNode);
                
                nodeChildren.forEach((T child) -> {
                    if(!processed.contains(child)) {
                        nextLevelQueue.add(child);
                    }
                    
                    hierarchy.addIsA(child, levelNode);
                });
            }
            
            levelQueue = nextLevelQueue;
        }
        
        return hierarchy;
    }
    
    public ArrayList<AncestorDepthResult<T>> getTopologicalDescendantListWithinDistance(T node, int distance) {
        MultiRootedHierarchy<T> hierarchyWithinDistance = this.getDescendantHierarchyWithinDistance(node, distance);
        
        AncestorDepthVisitor<T> visitor = new AncestorDepthVisitor<>(hierarchyWithinDistance);
        
        hierarchyWithinDistance.topologicalDown(visitor);
        
        return visitor.getResult();
    }
}
