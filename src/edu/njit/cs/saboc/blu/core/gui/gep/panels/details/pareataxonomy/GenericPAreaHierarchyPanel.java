package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.GenericParentGroupInfo;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
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
public class GenericPAreaHierarchyPanel<CONCEPT_T, PAREA_T extends GenericPArea,
        CONFIG_T extends BLUGenericPAreaTaxonomyConfiguration> extends AbstractGroupHierarchyPanel<CONCEPT_T, PAREA_T, CONFIG_T> {
    
    private final GenericPAreaTaxonomy taxonomy;
    
    private final CONFIG_T configuration;

    public GenericPAreaHierarchyPanel(
            BLUAbstractParentGroupTableModel<CONCEPT_T, PAREA_T, GenericParentGroupInfo<CONCEPT_T, PAREA_T>> parentTableModel,
            BLUAbstractChildGroupTableModel<PAREA_T> childTableModel,
            GenericPAreaTaxonomy taxonomy, 
            CONFIG_T configuration) {
        
        super(configuration, parentTableModel, childTableModel);

        this.taxonomy = taxonomy;
        this.configuration = configuration;
    }

    protected void loadParentGroupInfo(PAREA_T parea) {
        HashSet<GenericParentGroupInfo<CONCEPT_T, PAREA_T>> parents = parea.getParentPAreaInfo();

        parentModel.setContents(new ArrayList<>(parents));
    }
    
    protected void loadChildGroupInfo(PAREA_T parea) {
        HashSet<PAREA_T> children = taxonomy.getChildGroups(parea);
        
        ArrayList<PAREA_T> childPAreas = new ArrayList<>(children);
        
        BLUGenericPAreaTaxonomyConfiguration config = (BLUGenericPAreaTaxonomyConfiguration)configuration;

        Collections.sort(childPAreas, config.getDataConfiguration().getChildPAreaComparator());
        
        childModel.setContents(childPAreas);
    }
}
