package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeInformation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractChildGroupTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractParentGroupTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericPAreaHierarchyPanel<CONCEPT_T, PAREA_T extends PArea,
        CONFIG_T extends BLUGenericPAreaTaxonomyConfiguration> extends AbstractGroupHierarchyPanel<CONCEPT_T, PAREA_T, CONFIG_T> {
    
    private final GenericPAreaTaxonomy taxonomy;
    
    private final CONFIG_T configuration;

    public GenericPAreaHierarchyPanel(
            BLUAbstractParentGroupTableModel<CONCEPT_T, PAREA_T, ParentNodeInformation<CONCEPT_T, PAREA_T>> parentTableModel,
            BLUAbstractChildGroupTableModel<PAREA_T> childTableModel,
            GenericPAreaTaxonomy taxonomy, 
            CONFIG_T configuration) {
        
        super(configuration, parentTableModel, childTableModel);

        this.taxonomy = taxonomy;
        this.configuration = configuration;
    }

    protected void loadParentGroupInfo(PAREA_T parea) {
        HashSet<ParentNodeInformation<CONCEPT_T, PAREA_T>> parents = parea.getParentPAreaInfo();
        
        ArrayList<ParentNodeInformation<CONCEPT_T, PAREA_T>> parentsList;
        
        if(parents == null) { // Guards for current version of diff taxonomies
            parentsList = new ArrayList<>();
        } else {
            parentsList = new ArrayList<>(parents);
        }

        parentModel.setContents(parentsList);
    }
    
    protected void loadChildGroupInfo(PAREA_T parea) {
        HashSet<PAREA_T> children = taxonomy.getChildGroups(parea);
        
        ArrayList<PAREA_T> childPAreas;
        
        if(children == null ){ // Guard for current version of diff taxonomies
            childPAreas = new ArrayList<>();
        } else {
            childPAreas = new ArrayList<>(children);
        }
        
        BLUGenericPAreaTaxonomyConfiguration config = (BLUGenericPAreaTaxonomyConfiguration)configuration;

        Collections.sort(childPAreas, config.getDataConfiguration().getChildPAreaComparator());
        
        childModel.setContents(childPAreas);
    }
}
