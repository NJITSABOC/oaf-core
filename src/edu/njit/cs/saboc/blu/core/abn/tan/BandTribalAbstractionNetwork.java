package edu.njit.cs.saboc.blu.core.abn.tan;

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
 * @author Den
 */
public class BandTribalAbstractionNetwork extends AbstractionNetwork<Band> {
    public BandTribalAbstractionNetwork(Hierarchy<Band> bandHierarchy, 
            Hierarchy<Concept> sourceHierarchy) {
        
        super(bandHierarchy, sourceHierarchy);
    }
    
    public Hierarchy<Band> getBandHierarchy(){
        return super.getNodeHierarchy();
    }
    
    public Set<Band> getBands() {
        return super.getNodes();
    }
    
    @Override
    public Set<ParentNodeDetails<Band>> getParentNodeDetails(Band band) {
        return AbstractionNetworkUtils.getMultiRootedNodeParentNodeDetails(
                band,
                this.getSourceHierarchy(),
                (Set<PartitionedNode>) (Set<?>) this.getBands());
    }
    
    @Override
    public Set<Band> searchNodes(String query) {
        return searchBands(query);
    }
    
    public Set<Band> searchBands(String query) {
        Set<Band> searchResults = new HashSet<>();

        String [] searchPatriarchs = query.split(", ");

        if(searchPatriarchs == null) {
            return searchResults;
        }

        for(Band band : this.getBands()) {
            ArrayList<String> bandPatriarchNames = new ArrayList<>();
            
            Set<Concept> patriarchs = band.getPatriarchs();

            patriarchs.stream().forEach((patriarch) -> {
                bandPatriarchNames.add(patriarch.getName());
            });

            boolean allPatriarchsFound = true;

            for(String patriarch : searchPatriarchs) {

                boolean patriarchFound = false;

                for(String bandPatriarch : bandPatriarchNames) {
                    if(bandPatriarch.toLowerCase().contains(patriarch)) {
                        patriarchFound = true;
                        break;
                    }
                }

                if(!patriarchFound) {
                    allPatriarchsFound = false;
                    break;
                }
            }

            if(allPatriarchsFound) {
                searchResults.add(band);
            }
        }

        return searchResults;
    }
}
