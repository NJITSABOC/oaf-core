package edu.njit.cs.saboc.blu.core.gui.gep.utils;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Enforces the following rules:
 * 
 * Only one group OR partition can be selected at a time (never both).
 * 
 * Only one group OR partition can be moused over at a time (never both).
 * 
 * @author Chris
 */
public class GraphSelectionStateMonitor {
    private SinglyRootedNodeEntry selectedGroupEntry = null;
    private GenericPartitionEntry selectedPartitionEntry = null;
    
    private SinglyRootedNodeEntry mousedOverGroupEntry = null;
    private GenericPartitionEntry mousedOverPartitionEntry = null;
    
    private final Collection<? extends SinglyRootedNodeEntry> groupEntries;
    private final ArrayList<GenericPartitionEntry> partitionEntries = new ArrayList<>();
    
    private final BluGraph graph;
    
    public GraphSelectionStateMonitor(BluGraph graph) {
        this.graph =  graph;
        
        this.groupEntries = graph.getNodeEntries().values();
        
        Collection<? extends PartitionedNodeEntry> containers = graph.getContainerEntries().values();
        
        containers.forEach((PartitionedNodeEntry entry) -> {
            partitionEntries.addAll(entry.getPartitionEntries());
        });
    }
    
    public SinglyRootedNodeEntry getSelectedGroupEntry() {
        return selectedGroupEntry;
    }
    
    public GenericPartitionEntry getSelectedPartitionEntry() {
        return selectedPartitionEntry;
    }
    
    public SinglyRootedNodeEntry getMouseOverGroupEntry() {
        return mousedOverGroupEntry;
    }
    
    public GenericPartitionEntry getMousedOverPartitionEntry() {
        return mousedOverPartitionEntry;
    }
    
    public void resetAll() {
        this.selectedGroupEntry = null;
        this.selectedPartitionEntry = null;
        
        this.mousedOverGroupEntry = null;
        this.mousedOverPartitionEntry = null;
        
        groupEntries.forEach((SinglyRootedNodeEntry entry) -> {
            entry.setMousedOver(false);
            entry.setHighlightState(AbNNodeEntry.HighlightState.None);
        });
        
        partitionEntries.forEach((GenericPartitionEntry entry) -> {
            entry.setMousedOver(false);
            entry.setHighlightState(AbNNodeEntry.HighlightState.None);
        });
    }
    
    public void resetMousedOver() {
        this.mousedOverGroupEntry = null;
        this.mousedOverPartitionEntry = null;
        
         groupEntries.forEach((SinglyRootedNodeEntry entry) -> {
            entry.setMousedOver(false);
        });
        
        partitionEntries.forEach((GenericPartitionEntry entry) -> {
            entry.setMousedOver(false);
        });
    }
    
    public void setSelectedGroup(SinglyRootedNodeEntry group) {
        if(group != selectedGroupEntry) {           
            resetAll();
            
            this.selectedGroupEntry = group;
            
            group.setHighlightState(AbNNodeEntry.HighlightState.Selected);
            group.setMousedOver(true);
            
            highlightGroupParents(group);
            highlightGroupChildren(group);
        }
    }
    
    public void setSelectedPartition(GenericPartitionEntry partition) {
        if(partition != selectedPartitionEntry) {
            resetAll();
            
            this.selectedPartitionEntry = partition;
            
            partition.setHighlightState(AbNNodeEntry.HighlightState.Selected);
            partition.setMousedOver(true);
        }
    }
    
    public void setMousedOverGroup(SinglyRootedNodeEntry group) {
        if(group != mousedOverGroupEntry) {
            resetMousedOver();
            
            this.mousedOverGroupEntry = group;
            
            group.setMousedOver(true);
        }
    }
    
    public void setMousedOverPartition(GenericPartitionEntry partition) {
        if(partition != mousedOverPartitionEntry) {
            resetMousedOver();
            
            this.mousedOverPartitionEntry = partition;
            
            partition.setMousedOver(true);
        }
    }
    
    public void setSearchResults(Set<SinglyRootedNode> nodes) {
        resetAll();
        
        Map<SinglyRootedNode, SinglyRootedNodeEntry> nodeEntries = graph.getNodeEntries();
        
        nodes.forEach((node) -> {
            SinglyRootedNodeEntry entry = nodeEntries.get(node);
            entry.setHighlightState(AbNNodeEntry.HighlightState.SearchResult);
        });
    }   
    
    private void highlightGroupParents(SinglyRootedNodeEntry nodeEntry) {
        AbstractionNetwork<SinglyRootedNode> abn = graph.getAbstractionNetwork();
        
        Map<SinglyRootedNode, SinglyRootedNodeEntry> nodeEntries = graph.getNodeEntries();
        
        Set<SinglyRootedNode> parentNodes = abn.getNodeHierarchy().getParents(nodeEntry.getNode());
        
        parentNodes.forEach((parent) -> {
            if (nodeEntries.containsKey(parent)) {
                SinglyRootedNodeEntry entry = nodeEntries.get(parent);
                entry.setHighlightState(AbNNodeEntry.HighlightState.Parent);
            }
        });
    }
    
    private void highlightGroupChildren(SinglyRootedNodeEntry nodeEntry) {
        
        AbstractionNetwork<SinglyRootedNode> abn = graph.getAbstractionNetwork();
        
        Map<SinglyRootedNode, SinglyRootedNodeEntry> nodeEntries = graph.getNodeEntries();
        
        Set<SinglyRootedNode> parentNodes = abn.getNodeHierarchy().getChildren(nodeEntry.getNode());
        
        parentNodes.forEach((parent) -> {
            if (nodeEntries.containsKey(parent)) {
                SinglyRootedNodeEntry entry = nodeEntries.get(parent);
                entry.setHighlightState(AbNNodeEntry.HighlightState.Child);
            }
        });
    }
}
