package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.GenericParentGroupInfo;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractChildGroupTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractParentGroupTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericDisjointGroupHierarchyPanel<CONCEPT_T, DISJOINTGROUP_T extends DisjointGenericConceptGroup> extends AbstractGroupHierarchyPanel<CONCEPT_T, DISJOINTGROUP_T> {
    
    private final DisjointAbstractionNetwork disjointAbN;
    
    private final BLUDisjointAbNConfiguration configuration;

    public GenericDisjointGroupHierarchyPanel(
            BLUAbstractParentGroupTableModel<CONCEPT_T, DISJOINTGROUP_T, GenericParentGroupInfo<CONCEPT_T, DISJOINTGROUP_T>> parentTableModel,
            BLUAbstractChildGroupTableModel<DISJOINTGROUP_T> childTableModel,
            DisjointAbstractionNetwork disjointAbN, 
            BLUDisjointAbNConfiguration configuration) {
        
        super(parentTableModel, childTableModel);

        this.disjointAbN = disjointAbN;
        this.configuration = configuration;
    }

    protected void loadParentGroupInfo(DISJOINTGROUP_T parea) {
        ArrayList<GenericParentGroupInfo<CONCEPT_T, DISJOINTGROUP_T>> entries = new ArrayList<>(parea.getParentGroups());
        
        Collections.sort(entries, new Comparator<GenericParentGroupInfo<CONCEPT_T, DISJOINTGROUP_T>>() {
            public int compare(GenericParentGroupInfo<CONCEPT_T, DISJOINTGROUP_T> a, GenericParentGroupInfo<CONCEPT_T, DISJOINTGROUP_T> b) {
                String aName = configuration.getConceptName(a.getParentConcept());
                String bName = configuration.getConceptName(b.getParentConcept());
                
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