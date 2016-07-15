package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AreaTaxonomy extends AbstractionNetwork<Area> {
    
    private final PAreaTaxonomyFactory factory;
    
    public AreaTaxonomy(
            PAreaTaxonomyFactory factory,
            Hierarchy<Area> areaHierarchy, 
            Hierarchy<Concept> sourceHierarchy) {
        
        super(areaHierarchy, sourceHierarchy);
        
        this.factory = factory;
    }
    
    protected PAreaTaxonomyFactory getPAreaTaxonomyFactory() {
        return factory;
    }
    
    public Hierarchy<Area> getAreaHierarchy() {
        return super.getNodeHierarchy();
    }
    
    public Set<Area> getAreas() {
        return super.getNodes();
    }

    @Override
    public Set<Area> searchNodes(String query) {
        return findAreas(query);
    }
        
    public Set<Area> findAreas(String query) {
        
        query = query.toLowerCase();
        
        Set<Area> searchResults = new HashSet<>();

        Set<Area> areas = this.getAreas();

        String[] searchedRels = query.split(", ");

        if (searchedRels == null) {
            return searchResults;
        }

        for (Area area : areas) {
            ArrayList<String> relsInArea = new ArrayList<>();
            
            Set<InheritableProperty> areaRels = area.getRelationships();
            
            areaRels.forEach((rel) -> {
                relsInArea.add(rel.getName().toLowerCase());
            });

            boolean allRelsFound = true;

            for (String rel : searchedRels) {

                boolean relFound = false;

                for (String areaRel : relsInArea) {
                    if (areaRel.toLowerCase().contains(rel)) {
                        relFound = true;
                        break;
                    }
                }

                if (!relFound) {
                    allRelsFound = false;
                    break;
                }
            }

            if (allRelsFound) {
                searchResults.add(area);
            }
        }

        return searchResults;
    }

    @Override
    public Set<ParentNodeDetails<Area>> getParentNodeDetails(Area area) {
        return AbstractionNetworkUtils.getMultiRootedNodeParentNodeDetails(
                area, 
                this.getSourceHierarchy(), 
                (Set<PartitionedNode>)(Set<?>)this.getAreas());
    }

}
