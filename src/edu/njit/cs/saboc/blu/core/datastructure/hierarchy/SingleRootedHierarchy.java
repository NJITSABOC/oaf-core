package edu.njit.cs.saboc.blu.core.datastructure.hierarchy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.AllPathsToNodeVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.AncestorDepthVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.AncestorHierarchyBuilderVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.SingleRootedHierarchyTopologicalVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.SingleRootedHierarchyVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.SubhierarchyMembersVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.SubhierarchySizeVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.result.AncestorDepthResult;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

/**
 * 
 * @author Chris
 */
public abstract class SingleRootedHierarchy<T, V extends SingleRootedHierarchy<T, V>> extends MultiRootedHierarchy<T> {
    
    public SingleRootedHierarchy(T root) {
        super(new HashSet<T>(Arrays.asList(root)));
    }
    
    public SingleRootedHierarchy(T root, HashMap<T, HashSet<T>> conceptHierarchy) {
        super(new HashSet<T>(Arrays.asList(root)), conceptHierarchy);
    }
    
    public SingleRootedHierarchy(T root, V hierarchy) {
        this(root, hierarchy.children);
    }
    
    public T getRoot() {
        return getRoots().iterator().next();
    }
    
    public void BFSDown(T startingPoint, SingleRootedHierarchyVisitor<T> visitor) {
        Queue<T> queue = new ArrayDeque<T>();
        queue.add(startingPoint);
        
        HashSet<T> visited = new HashSet<>();
        visited.add(startingPoint);
        
        while(!queue.isEmpty() && !visitor.isFinished()) {
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
    
    public void BFSUp(T startingPoint, SingleRootedHierarchyVisitor<T> visitor) {
        Queue<T> queue = new ArrayDeque<T>();
        queue.add(startingPoint);

        HashSet<T> visited = new HashSet<>();
        visited.add(startingPoint);

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
    
    public void topologicalDown(SingleRootedHierarchyVisitor<T> visitor) {
        HashMap<T, Integer> parentCounts = new HashMap<>();
        
        HashSet<T> nodesInHierarchy = this.getNodesInHierarchy();
        
        nodesInHierarchy.forEach((T node) -> {
            parentCounts.put(node, this.getParents(node).size());
        });
        
        Queue<T> queue = new ArrayDeque<>();
        queue.add(this.getRoot());
        
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
    
    public void topologicalDownInSubhierarchy(T startingPoint, SingleRootedHierarchyTopologicalVisitor<T> visitor) {
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
        SubhierarchySizeVisitor<T> visitor = new SubhierarchySizeVisitor<T>(this);
        
        this.BFSDown(node, visitor);
        
        return visitor.getDescandantCount() - 1;
    }
    
    public HashSet<T> getDescendants(T node) {
        SubhierarchyMembersVisitor<T> visitor = new SubhierarchyMembersVisitor<T>(this);
        
        this.BFSDown(node, visitor);
        
        HashSet<T> members = visitor.getSubhierarchyMembers();
        members.remove(node);
        
        return members;
    }
    
    public V getAncestorHierarchy(T node) {
        
        AncestorHierarchyBuilderVisitor<T, V> ancestorHierarchy = new AncestorHierarchyBuilderVisitor<T, V>(this, this.createHierarchy(this.getRoot()));
        
        this.BFSUp(node, ancestorHierarchy);
        
        return ancestorHierarchy.getAncestorHierarchy();
    }
    
    public ArrayList<ArrayList<T>> getAllPathsTo(T node) {
        V ancestorHierarchy = this.getAncestorHierarchy(node);
        
        AllPathsToNodeVisitor<T> visitor = new AllPathsToNodeVisitor<>(this, node);
        
        ancestorHierarchy.topologicalDown(visitor);
        
        return visitor.getAllPaths();
    }
        
    public V getDescendantHierarchyWithinDistance(T node, int maxDistance) {
        V hierarchy = createHierarchy(node);
        
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
        V hierarchyWithinDistance = this.getDescendantHierarchyWithinDistance(node, distance);
        
        AncestorDepthVisitor<T> visitor = new AncestorDepthVisitor<>(hierarchyWithinDistance);
        
        hierarchyWithinDistance.topologicalDown(visitor);
        
        return visitor.getResult();
    }
    
        
    protected abstract V createHierarchy(T root);
}
