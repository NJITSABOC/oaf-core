package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeSummaryPanel;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class GenericDisjointGroupSummaryPanel<
        CONCEPT_T,
        GROUP_T extends GenericConceptGroup,
        DISJOINTABN_T extends DisjointAbstractionNetwork,
        DISJOINTGROUP_T extends DisjointGenericConceptGroup> extends AbstractNodeSummaryPanel<DISJOINTGROUP_T> {
    
    protected final DISJOINTABN_T disjointAbN;
    
    protected GenericOverlappingGroupList<GROUP_T> overlapsPanel;
    
    protected final BLUDisjointConfiguration configuration;
    
    public GenericDisjointGroupSummaryPanel(
            GenericOverlappingGroupList<GROUP_T> overlapsPanel, 
            DISJOINTABN_T disjointAbN, 
            BLUDisjointConfiguration configuration) {
        
        this.disjointAbN = disjointAbN;
        this.configuration = configuration;
        
        this.overlapsPanel = overlapsPanel;
        this.overlapsPanel.setMinimumSize(new Dimension(-1, 100));
        this.overlapsPanel.setPreferredSize(new Dimension(-1, 100));
        
        this.add(this.overlapsPanel);
    }
    
    public void setContents(DISJOINTGROUP_T disjointGroup) {
        super.setContents(disjointGroup);
        
        ArrayList<GROUP_T> overlaps = new ArrayList<>(disjointGroup.getOverlaps());
        
        Collections.sort(overlaps, new Comparator<GROUP_T>() {
            public int compare(GROUP_T a, GROUP_T b) {
                return a.getRoot().getName().compareTo(b.getRoot().getName());
            }
        });
        
        overlapsPanel.setContents(overlaps);
    }
    
    public void clearContents() {
        super.clearContents();
        
        overlapsPanel.clearContents();
    }

    protected String createDescriptionStr(DISJOINTGROUP_T group) {
        
        String rootName = configuration.getTextConfiguration().getGroupName(group);
        int classCount = group.getConceptCount();

        HashSet<DISJOINTGROUP_T> descendantDisjointGroups = disjointAbN.getDescendantGroups(group);
        
        int descendantGroupCount = descendantDisjointGroups.size();

        HashSet<CONCEPT_T> descendantClasses = new HashSet<>();

        descendantDisjointGroups.forEach((DISJOINTGROUP_T parea) -> {
            descendantClasses.addAll(parea.getConceptsAsList());
        });
        
        int descendantConceptCount = descendantClasses.size();
        
        HashSet<GROUP_T> overlaps = group.getOverlaps();
        
        String result;
        
        if(overlaps.size() == 1) {
            result = String.format("<b>%s</b> is a basis (non-overlapping) %s that summarizes %d non-overlapping %s. It has %d descendant overlapping %s which summarizes %d overlapping %s.",
                    rootName, 
                    configuration.getTextConfiguration().getGroupTypeName(false).toLowerCase(), 
                    classCount, 
                    configuration.getTextConfiguration().getConceptTypeName(classCount > 1 || classCount == 0).toLowerCase(),
                    descendantGroupCount, 
                    configuration.getTextConfiguration().getGroupTypeName(descendantGroupCount > 1 || descendantGroupCount == 0).toLowerCase(),
                    descendantConceptCount,
                    configuration.getTextConfiguration().getConceptTypeName(descendantConceptCount > 1 || descendantConceptCount == 0).toLowerCase());
        } else {
            
            ArrayList<String> overlappingGroupNames = new ArrayList<>();
            
            overlaps.forEach((GROUP_T overlappingGroup) -> {
                overlappingGroupNames.add(configuration.getTextConfiguration().getOverlappingGroupName(overlappingGroup));
            });
            
            Collections.sort(overlappingGroupNames);
            
            String overlapsStr = overlappingGroupNames.get(0);
            
            for(int c = 1; c < overlappingGroupNames.size(); c++) {
                overlapsStr += ", " + overlappingGroupNames.get(c);
            }
            
            result = String.format("<b>%s</b> is an overlapping %s that summarizes %d overlapping %s. It overlaps between the following %s: <b>%s</b>."
                    + " It has %d descendant overlapping %s which summarizes %d overlapping %s.",
                    rootName, 
                    configuration.getTextConfiguration().getGroupTypeName(false).toLowerCase(), 
                    classCount, 
                    configuration.getTextConfiguration().getConceptTypeName(classCount > 1 || classCount == 0).toLowerCase(),
                    configuration.getTextConfiguration().getOverlappingGroupTypeName(true).toLowerCase(),
                    overlapsStr,
                    descendantGroupCount, 
                    configuration.getTextConfiguration().getGroupTypeName(descendantGroupCount > 1 || descendantGroupCount == 0).toLowerCase(),
                    descendantConceptCount,
                    configuration.getTextConfiguration().getConceptTypeName(descendantConceptCount > 1 || descendantConceptCount == 0).toLowerCase());
        }
        
        result += "<p><b>Help / Description:</b><br>";
        
        result += configuration.getTextConfiguration().getGroupHelpDescriptions(group);
        
        return result;
    }
}
