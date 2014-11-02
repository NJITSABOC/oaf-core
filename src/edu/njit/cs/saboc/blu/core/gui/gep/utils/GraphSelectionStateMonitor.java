/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.njit.cs.saboc.blu.core.gui.gep.utils;

import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry.GroupEntryState;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry.PartitionEntryState;
import java.util.ArrayList;

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
    private GenericGroupEntry selectedGroupEntry = null;
    private GenericPartitionEntry selectedPartitionEntry = null;
    
    private GenericGroupEntry mousedOverGroupEntry = null;
    private GenericPartitionEntry mousedOverPartitionEntry = null;
    
    private BluGraph graph;
    
    public GraphSelectionStateMonitor(BluGraph graph) {
        this.graph = graph;
    }
    
    public GenericGroupEntry getSelectedGroupEntry() {
        return selectedGroupEntry;
    }
    
    public GenericPartitionEntry getSelectedPartitionEntry() {
        return selectedPartitionEntry;
    }
    
    public GenericGroupEntry getMouseOverGroupEntry() {
        return mousedOverGroupEntry;
    }
    
    public GenericPartitionEntry getMousedOverPartitionEntry() {
        return mousedOverPartitionEntry;
    }
    
    public void deselectAll() {
        resetSelectedGroupEntry();
        resetSelectedPartitionEntry();
        
        unhighlightAllSearchResults();
    }
    
    public void resetMousedOver() {
        resetMousedOverGroupEntry();
        resetMousedOverPartitionEntry();
    }
    
    public void setSelectedGroup(GenericGroupEntry entry) {
        resetSelectedPartitionEntry();
        unhighlightAllGroups();
        setGroupEntrySelected(entry);
    }
    
    public void setSelectedPartition(GenericPartitionEntry partition) {
        resetSelectedGroupEntry();
        unhighlightAllGroups();
        setPartitionEntrySelected(partition);
    }
    
    public void setMousedOverGroup(GenericGroupEntry entry) {
        
        resetMousedOverPartitionEntry();

        if (mousedOverGroupEntry != entry && entry.getState() == GroupEntryState.Default) {
            resetMousedOverGroupEntry();
            
            entry.setState(GroupEntryState.MousedOver);
            
            this.mousedOverGroupEntry = entry;
        }
    }
    
    public void setMousedOverPartition(GenericPartitionEntry entry) {
        resetMousedOverGroupEntry();
        
        if(mousedOverPartitionEntry != entry && entry.getState() == PartitionEntryState.Default) {
            resetMousedOverPartitionEntry();
            
            entry.setState(PartitionEntryState.MousedOver);
            
            this.mousedOverPartitionEntry = entry;
        }
    }
    
    public void setSearchResults(ArrayList<Integer> entryIds) {
        
        unhighlightAllSearchResults();
        
        for(int entryId : entryIds) {
            GenericGroupEntry entry = graph.getGroupEntries().get(entryId);
            
            if(entry != selectedGroupEntry) {
                entry.setState(GroupEntryState.HighlightedForSearch);
            }
        }
    }
    
    private void setGroupEntrySelected(GenericGroupEntry entry) {
        if(selectedGroupEntry != entry) {
            resetSelectedGroupEntry();
            
            entry.setState(GroupEntryState.Selected);
            
            selectedGroupEntry = entry;
        }
    }
    
    private void resetSelectedGroupEntry() {
        unhighlightAllGroups();
        
        if (selectedGroupEntry != null) {
            selectedGroupEntry.setState(GroupEntryState.Default);
            
            selectedGroupEntry = null;
        }
    }
    
    private void setPartitionEntrySelected(GenericPartitionEntry entry) {
        if(selectedPartitionEntry != entry) {
            resetSelectedPartitionEntry();
            
            entry.setState(PartitionEntryState.Selected);
            
            selectedPartitionEntry = entry;
        }
    }
    
    private void resetSelectedPartitionEntry() {
        
        if(selectedPartitionEntry != null) {
            selectedPartitionEntry.setState(PartitionEntryState.Default);
            selectedPartitionEntry = null;
        }
    }
    
    private void resetMousedOverGroupEntry() {
        if(mousedOverGroupEntry != null) {
            if(mousedOverGroupEntry != selectedGroupEntry) {
                mousedOverGroupEntry.setState(GroupEntryState.Default);
            }

            mousedOverGroupEntry = null;
        }
    }
    
    private void resetMousedOverPartitionEntry() {
        if(mousedOverPartitionEntry != null) {
            if(mousedOverPartitionEntry != selectedPartitionEntry) {
                mousedOverPartitionEntry.setState(PartitionEntryState.Default);
            }

            mousedOverPartitionEntry = null;
        }
    }
    
    private void unhighlightAllGroups() {
        for (GenericGroupEntry entry : graph.getGroupEntries().values()) {
            entry.setState(GroupEntryState.Default);
        }
    }
 
    private void unhighlightAllSearchResults() {
        for(GenericGroupEntry entry : graph.getGroupEntries().values()) {
            if(entry.getState() == GroupEntryState.HighlightedForSearch) {
                entry.setState(GroupEntryState.Default);
            }
        }
    }
}
