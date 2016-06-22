package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeInformation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ChildNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ParentNodeTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericDisjointGroupHierarchyPanel<CONCEPT_T, 
        DISJOINTGROUP_T extends DisjointNode,
        CONFIG_T extends BLUDisjointConfiguration> extends NodeHierarchyPanel<CONCEPT_T, DISJOINTGROUP_T, CONFIG_T> {
    
    private final DisjointAbstractionNetwork disjointAbN;
    
    private final CONFIG_T configuration;

    public GenericDisjointGroupHierarchyPanel(
            ParentNodeTableModel<CONCEPT_T, DISJOINTGROUP_T, ParentNodeInformation<CONCEPT_T, DISJOINTGROUP_T>> parentTableModel,
            ChildNodeTableModel<DISJOINTGROUP_T> childTableModel,
            DisjointAbstractionNetwork disjointAbN, 
            CONFIG_T configuration) {
        
        super(configuration, parentTableModel, childTableModel);

        this.disjointAbN = disjointAbN;
        this.configuration = configuration;
    }

    protected void loadParentGroupInfo(DISJOINTGROUP_T parea) {
        ArrayList<ParentNodeInformation<CONCEPT_T, DISJOINTGROUP_T>> entries = new ArrayList<>(parea.getParentGroups());
        
        Collections.sort(entries, new Comparator<ParentNodeInformation<CONCEPT_T, DISJOINTGROUP_T>>() {
            public int compare(ParentNodeInformation<CONCEPT_T, DISJOINTGROUP_T> a, ParentNodeInformation<CONCEPT_T, DISJOINTGROUP_T> b) {
                String aName = configuration.getTextConfiguration().getConceptName(a.getParentConcept());
                String bName = configuration.getTextConfiguration().getConceptName(b.getParentConcept());
                
                return aName.compareToIgnoreCase(bName);
            }
        });

        parentModel.setContents(entries);
    }
    
    protected void loadChildGroupInfo(DISJOINTGROUP_T parea) {
        HashSet<DISJOINTGROUP_T> children = disjointAbN.getChildGroups(parea);
        
        ArrayList<DISJOINTGROUP_T> childPAreas = new ArrayList<>(children);
        
        Collections.sort(childPAreas, new Comparator<DISJOINTGROUP_T>() {
            public int compare(DISJOINTGROUP_T a, DISJOINTGROUP_T b) {
                if(a.getOverlaps() == b.getOverlaps()) {
                    return a.getRoot().getName().compareToIgnoreCase(b.getRoot().getName());
                } else {
                    return a.getOverlaps().size() - b.getOverlaps().size();
                }
            }
        });
        
        childModel.setContents(childPAreas);
    }
}
