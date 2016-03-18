package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text.BLUDisjointableAbNTextConfiguration;

/**
 *
 * @author Den
 */
public abstract class BLUGenericPAreaTaxonomyTextConfiguration<
        TAXONOMY_T extends GenericPAreaTaxonomy,
        DISJOINTTAXONOMY_T extends DisjointAbstractionNetwork<TAXONOMY_T, PAREA_T, CONCEPT_T, HIERARCHY_T, DISJOINTPAREA_T>,
        AREA_T extends GenericArea,
        PAREA_T extends GenericPArea,
        AGGREGATEPAREA_T extends GenericPArea & AggregateableConceptGroup<CONCEPT_T, PAREA_T>,
        DISJOINTPAREA_T extends DisjointGenericConceptGroup<PAREA_T, CONCEPT_T, HIERARCHY_T, DISJOINTPAREA_T>, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T>,
        REL_T, 
        CONCEPT_T> implements BLUDisjointableAbNTextConfiguration<TAXONOMY_T, DISJOINTTAXONOMY_T, AREA_T, PAREA_T, DISJOINTPAREA_T, CONCEPT_T> {

    @Override
    public String getAbNTypeName(boolean plural) {
        if(plural) {
            return "Partial-area Taxonomies";
        } else {
            return "Partial-area Taxonomy";
        }
    }
    
    @Override
    public String getDisjointGroupTypeName(boolean plural) {
        if(plural) {
            return "Disjoint partial-areas";
        } else {
            return "Disjoint partial-area";
        }
    }

    @Override
    public String getContainerTypeName(boolean plural) {
        if(plural) {
            return "Areas";
        } else {
            return "Area";
        }
    }

    @Override
    public String getGroupTypeName(boolean plural) {
        if(plural) {
            return "Partial-areas";
        } else {
            return "Partial-area";
        }
    }
}
