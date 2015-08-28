package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeSummaryPanel;
import java.awt.Dimension;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class GenericPAreaSummaryPanel<
        CONCEPT_T,
        REL_T,
        TAXONOMY_T extends GenericPAreaTaxonomy,
        PAREA_T extends GenericPArea> extends AbstractNodeSummaryPanel<PAREA_T> {
    
    protected final TAXONOMY_T taxonomy;
    
    protected GenericRelationshipPanel<REL_T> relationshipPanel;
    
    protected final PAreaTaxonomyConfiguration configuration;
    
    public GenericPAreaSummaryPanel(GenericRelationshipPanel<REL_T> relationshipPanel, TAXONOMY_T taxonomy, PAreaTaxonomyConfiguration configuration) {
        this.taxonomy = taxonomy;
        this.configuration = configuration;
        
        this.relationshipPanel = relationshipPanel;
        this.relationshipPanel.setMinimumSize(new Dimension(-1, 100));
        this.relationshipPanel.setPreferredSize(new Dimension(-1, 100));
        
        this.add(this.relationshipPanel);
    }
    
    public void setContents(PAREA_T parea) {
        super.setContents(parea);
        
        relationshipPanel.setContents(configuration.getPAreaRelationships(parea));
    }
    
    public void clearContents() {
        super.clearContents();
        
        relationshipPanel.clearContents();
    }

    protected String createDescriptionStr(PAREA_T group) {
        String conceptType = configuration.getConceptTypeName(true);
        
        String rootName = configuration.getGroupName(group);
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
}
