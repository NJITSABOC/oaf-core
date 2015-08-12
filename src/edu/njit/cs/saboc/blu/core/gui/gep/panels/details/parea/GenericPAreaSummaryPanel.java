package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.parea;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupSummaryPanel;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class GenericPAreaSummaryPanel<
        CONCEPT_T,
        REL_T,
        TAXONOMY_T extends GenericPAreaTaxonomy,
        PAREA_T extends GenericPArea> extends AbstractGroupSummaryPanel<PAREA_T> {
    
    protected TAXONOMY_T taxonomy;
    
    protected GenericPAreaPropertyPanel<REL_T> relationshipPanel;
    
    public GenericPAreaSummaryPanel(TAXONOMY_T taxonomy) {
        this.taxonomy = taxonomy;
    }
    
    public void setContents(PAREA_T parea) {
        super.setContents(parea);
        
        relationshipPanel.setContents(getPAreaRelationships(parea));
    }
    
    public void clearContents() {
        super.clearContents();
        
        relationshipPanel.clearContents();
    }
    
    public void initUI() {

        relationshipPanel = createPropertyDetailsPanel();

        relationshipPanel.initUI();

        relationshipPanel.setMaximumSize(new Dimension(10000, 100));
        relationshipPanel.setMinimumSize(new Dimension(-1, 100));
        relationshipPanel.setPreferredSize(new Dimension(-1, 100));
        
        this.add(relationshipPanel);

    }

    protected String createDescriptionStr(PAREA_T group) {
        String conceptType = getConceptType();
        
        String rootName = getPAreaRootName(group);
        int classCount = group.getConceptCount();

        int parentCount = taxonomy.getParentGroups(group).size();
        int childCount = taxonomy.getChildGroups(group).size();

        HashSet<PAREA_T> descendantPAreas = taxonomy.getDescendantGroups(group);

        HashSet<CONCEPT_T> descendantClasses = new HashSet<>();

        descendantPAreas.forEach((PAREA_T parea) -> {
            descendantClasses.addAll(parea.getConceptsInPArea());
        });

        return String.format("<html><b>%s</b> is a partial-area that summarizes %d %s. It has %d parent partial-areas and %d child partial-areas. "
                + "There are a total of %d descendant partial-areas that summarizes %d %s",
                rootName, classCount, conceptType, parentCount, childCount, descendantPAreas.size(), descendantClasses.size(), conceptType);
    }
    
    protected abstract ArrayList<REL_T> getPAreaRelationships(PAREA_T parea);
    
    protected abstract String getConceptType();
    
    protected abstract String getPAreaRootName(PAREA_T parea);
    
    protected abstract GenericPAreaPropertyPanel<REL_T> createPropertyDetailsPanel();
}
