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
    
    protected final BLUGenericPAreaTaxonomyConfiguration configuration;
    
    public GenericAreaSummaryPanel(GenericRelationshipPanel<REL_T> relTable, BLUGenericPAreaTaxonomyConfiguration configuration) {        
        this.configuration = configuration;
        
        relationshipPanel = relTable;

        relationshipPanel.setMinimumSize(new Dimension(-1, 100));
        relationshipPanel.setPreferredSize(new Dimension(-1, 100));
                
        relationshipPanel.addEntitySelectionListener(configuration.getUIConfiguration().getListenerConfiguration().getContainerRelationshipSelectedListener());
        
        this.add(relationshipPanel);
    }
    
    public void setContents(AREA_T area) {
        super.setContents(area);
        
        relationshipPanel.setContents(configuration.getDataConfiguration().getAreaRelationships(area));
    }
    
    public void clearContents() {
        super.clearContents();
        
        relationshipPanel.clearContents();
    }
    
    protected String createDescriptionStr(AREA_T area) {
        String areaName = configuration.getTextConfiguration().getContainerName(area);
        int classCount = area.getConcepts().size();
        
        String conceptType = configuration.getTextConfiguration().getConceptTypeName(classCount > 1 || classCount == 0).toLowerCase();
        
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("<html><b>%s</b> is an area that summarizes %d %s.",
                areaName, classCount, conceptType));
        
        builder.append("<p>");
        builder.append("<b>Help / Description</b><p>");
        builder.append(configuration.getTextConfiguration().getContainerHelpDescription(area));

        return builder.toString();
    }
}
