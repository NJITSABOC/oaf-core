package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
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
        PAREA_T extends PArea> extends AbstractNodeSummaryPanel<PAREA_T> {
    
    protected final TAXONOMY_T taxonomy;
    
    protected GenericRelationshipPanel<REL_T> relationshipPanel;
    
    protected final BLUGenericPAreaTaxonomyConfiguration configuration;
    
    public GenericPAreaSummaryPanel(GenericRelationshipPanel<REL_T> relationshipPanel, TAXONOMY_T taxonomy, BLUGenericPAreaTaxonomyConfiguration configuration) {
        this.taxonomy = taxonomy;
        this.configuration = configuration;
        
        this.relationshipPanel = relationshipPanel;
        this.relationshipPanel.setMinimumSize(new Dimension(-1, 100));
        this.relationshipPanel.setPreferredSize(new Dimension(-1, 100));
        
        this.relationshipPanel.addEntitySelectionListener(configuration.getUIConfiguration().getListenerConfiguration().getGroupRelationshipSelectedListener());
        
        this.add(this.relationshipPanel);
    }
    
    public void setContents(PAREA_T parea) {
        super.setContents(parea);
        
        relationshipPanel.setContents(configuration.getDataConfiguration().getPAreaRelationships(parea));
    }
    
    public void clearContents() {
        super.clearContents();
        
        relationshipPanel.clearContents();
    }

    protected String createDescriptionStr(PAREA_T group) {
        String rootName = configuration.getTextConfiguration().getGroupName(group);
        int classCount = group.getConceptCount();

        int parentCount = taxonomy.getParentGroups(group).size();
        int childCount = taxonomy.getChildGroups(group).size();

        HashSet<PAREA_T> descendantPAreas = taxonomy.getDescendantGroups(group);

        HashSet<CONCEPT_T> descendantClasses = new HashSet<>();

        descendantPAreas.forEach((PAREA_T parea) -> {
            descendantClasses.addAll(parea.getConceptsInPArea());
        });
        
        StringBuilder result = new StringBuilder();
        
        result.append(String.format("<html><b>%s</b> is a partial-area that summarizes %d %s. ", 
                rootName, 
                classCount,  
                configuration.getTextConfiguration().getConceptTypeName(classCount > 1 || classCount == 0).toLowerCase()));
        
        if(parentCount > 0) {
            result.append(String.format("It has %d parent %s and ", 
                    parentCount, 
                    configuration.getTextConfiguration().getGroupTypeName(parentCount > 1 || parentCount == 0).toLowerCase()));
        } else {
            result.append("It has no parent partial-areas and ");
        }
        
        if(childCount > 0) {
            result.append(String.format("%d child %s.", childCount,
                    configuration.getTextConfiguration().getGroupTypeName(childCount > 1 || childCount == 0).toLowerCase()));
            
            if(descendantPAreas.size() > childCount) {
                int descPAreaCount = descendantPAreas.size();
                int descClassCount = descendantClasses.size();
                
                result.append(String.format("It has a total of %d descendant %s which summarize a total of %d %s.", 
                        descPAreaCount, 
                        configuration.getTextConfiguration().getGroupTypeName(descPAreaCount > 1).toLowerCase(),
                        descClassCount,
                        configuration.getTextConfiguration().getConceptTypeName(descClassCount > 1).toLowerCase())
                );
            }
            
        } else {
            result.append("no child partial-areas.");
        }
        
        result.append("<p>");
        result.append("<b>Help / Description</b><p>");
        result.append(configuration.getTextConfiguration().getGroupHelpDescriptions(group));

        return result.toString();
    }
}
