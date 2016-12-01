
package edu.njit.cs.saboc.blu.core.datastructure.hierarchy;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class Graph<T> {
    
    private final HashMap<T, Set<T>> outgoingEdges = new HashMap<>();
    private final HashMap<T, Set<T>> incomingEdges = new HashMap<>();
    
    public Graph() {
        
    }
    
    public Graph(Set<Edge<T>> edges) {
        edges.forEach( (edge) -> {
            addEdge(edge);
        });
    }
    
    public void addNode(T node) {
        if(!outgoingEdges.containsKey(node)) {
            outgoingEdges.put(node, new HashSet<>());
        }
        
        if(!incomingEdges.containsKey(node)) {
            incomingEdges.put(node, new HashSet<>());
        }
    }
    
    public final void addEdge(Edge<T> edge) {
        addEdge(edge.getFrom(), edge.getTo());
    }
    
    public final void addEdge(T from, T to) {
        addNode(from);
        addNode(to);

        outgoingEdges.get(from).add(to);
        incomingEdges.get(to).add(from);
    }
    
    public Set<T> getIncomingEdges(T node) {
        return incomingEdges.getOrDefault(node, Collections.emptySet());
    }
    
    public Set<T> getOutgoingEdges(T node) {
        return outgoingEdges.getOrDefault(node, Collections.emptySet());
    }
    
    public Set<T> getNodes() {
        return new HashSet<>(outgoingEdges.keySet());
    }
    
    public Set<Edge<T>> getEdges() {
        Set<Edge<T>> edges = new HashSet<>();
        
        Set<T> allNodes = new HashSet<>();

        outgoingEdges.forEach((node, adjacentNodes) -> {
            
            allNodes.add(node);
            allNodes.addAll(adjacentNodes);
            
            adjacentNodes.forEach( (adjacentNode) -> {
                edges.add(new Edge(node, adjacentNode));
            });
        });

        return edges;
    }
    
    public boolean contains(T node) {
        return incomingEdges.containsKey(node);
    }
    
    public boolean contains(Edge<T> edge) {
        if(outgoingEdges.containsKey(edge.getFrom())) {
            return outgoingEdges.get(edge.getFrom()).contains(edge.getTo());
        }
        
        return false;
    }
}
