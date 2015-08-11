package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.parea;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericParentPAreaInfo;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupHierarchyPanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class GenericPAreaHierarchyPanel<CONCEPT_T, PAREA_T extends GenericPArea> extends AbstractGroupHierarchyPanel<CONCEPT_T, PAREA_T> {
    
    private final GenericPAreaTaxonomy taxonomy;
    
    public GenericPAreaHierarchyPanel(GenericPAreaTaxonomy taxonomy) {
        this.taxonomy = taxonomy;
    }
    
    protected void loadParentGroupInfo(PAREA_T parea) {
        HashSet<GenericParentPAreaInfo<CONCEPT_T, PAREA_T>> parents = parea.getParentPAreaInfo();

        parentModel.setContents(new ArrayList<>(parents));
    }
    
    protected void loadChildGroupInfo(PAREA_T parea) {
        HashSet<PAREA_T> children = taxonomy.getChildGroups(parea);
        
        ArrayList<PAREA_T> childPAreas = new ArrayList<>(children);

        Collections.sort(childPAreas, getChildPAreaComparator());
        
        childModel.setContents(childPAreas);
    }
    
    protected abstract Comparator<PAREA_T> getChildPAreaComparator();
}
