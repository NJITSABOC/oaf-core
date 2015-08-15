package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeSummaryPanel;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public abstract class GenericAreaSummaryPanel<CONCEPT_T,
        REL_T,
        TAXONOMY_T extends GenericPAreaTaxonomy,
        AREA_T extends GenericArea> extends AbstractNodeSummaryPanel<AREA_T> {
    
    protected TAXONOMY_T taxonomy;
    
    protected GenericTaxonomyPropertyPanel<REL_T> relationshipPanel;
    
    public GenericAreaSummaryPanel(TAXONOMY_T taxonomy) {
        this.taxonomy = taxonomy;
    }
    
    public void setContents(AREA_T parea) {
        super.setContents(parea);
        
        relationshipPanel.setContents(getAreaRelationships(parea));
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

    protected String createDescriptionStr(AREA_T group) {
        String conceptType = getConceptType();
        
        String areaName = getAreaName(group);
        int classCount = group.getConcepts().size();

        return String.format("<html><b>%s</b> is an area that summarizes %d %s.",
                areaName, classCount, conceptType);
    }
    
    protected abstract ArrayList<REL_T> getAreaRelationships(AREA_T area);
    
    protected abstract String getConceptType();
    
    protected abstract String getAreaName(AREA_T area);
    
    protected abstract GenericTaxonomyPropertyPanel<REL_T> createPropertyDetailsPanel();
}
