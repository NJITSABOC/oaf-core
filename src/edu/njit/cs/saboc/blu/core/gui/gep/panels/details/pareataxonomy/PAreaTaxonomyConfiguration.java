package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.ConceptGroupHierarchicalViewPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointAbNConfiguration;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Chris O
 */
public abstract class PAreaTaxonomyConfiguration<CONCEPT_T, 
        PAREA_T extends GenericPArea,
        AREA_T extends GenericArea,
        DISJOINTPAREA_T extends DisjointGenericConceptGroup, REL_T> 
        implements BLUDisjointAbNConfiguration<CONCEPT_T, PAREA_T, AREA_T, DISJOINTPAREA_T> {

    private final String CONTAINER_NAME = "Area";
    private final String GROUP_NAME = "Partial-area";
    private final String DISJOINT_GROUP_NAME = "Disjoint partial-area"; 
    
    @Override
    public String getDisjointGroupTypeName(boolean plural) {
        if(plural) {
            return DISJOINT_GROUP_NAME + "s";
        } else {
            return DISJOINT_GROUP_NAME;
        }
    }

    @Override
    public String getContainerTypeName(boolean plural) {
        if(plural) {
            return CONTAINER_NAME + "s";
        } else {
            return CONTAINER_NAME;
        }
    }

    @Override
    public String getGroupTypeName(boolean plural) {
        if(plural) {
            return GROUP_NAME + "s";
        } else {
            return GROUP_NAME;
        }
    }

    public abstract ArrayList<REL_T> getAreaRelationships(AREA_T area);
    public abstract ArrayList<REL_T> getPAreaRelationships(PAREA_T parea);
    
    public abstract Comparator<PAREA_T> getChildPAreaComparator();
}