package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeSummaryPanel;
import java.awt.Dimension;

/**
 *
 * @author Chris O
 */
public class GenericAreaSummaryPanel<CONCEPT_T,
        REL_T,
        TAXONOMY_T extends GenericPAreaTaxonomy,
        AREA_T extends GenericArea> extends AbstractNodeSummaryPanel<AREA_T> {
    
    protected GenericRelationshipPanel<REL_T> relationshipPanel;
    
    protected final PAreaTaxonomyConfiguration configuration;
    
    public GenericAreaSummaryPanel(GenericRelationshipPanel<REL_T> relTable, PAreaTaxonomyConfiguration configuration) {        
        this.configuration = configuration;
        
        relationshipPanel = relTable;

        relationshipPanel.setMaximumSize(new Dimension(10000, 100));
        relationshipPanel.setMinimumSize(new Dimension(-1, 100));
        relationshipPanel.setPreferredSize(new Dimension(-1, 100));
        
        this.add(relationshipPanel);
    }
    
    public void setContents(AREA_T parea) {
        super.setContents(parea);
        
        relationshipPanel.setContents(configuration.getAreaRelationships(parea));
    }
    
    public void clearContents() {
        super.clearContents();
        
        relationshipPanel.clearContents();
    }
    
    protected String createDescriptionStr(AREA_T group) {
        String areaName = configuration.getContainerName(group);
        int classCount = group.getConcepts().size();
        
        String conceptType = configuration.getConceptTypeName(classCount > 1 && classCount < 0);

        return String.format("<html><b>%s</b> is an area that summarizes %d %s.",
                areaName, classCount, conceptType);
    }
}
