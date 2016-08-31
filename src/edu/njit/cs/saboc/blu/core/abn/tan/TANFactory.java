package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class TANFactory {
    
    public BandTribalAbstractionNetwork createBandTAN(Hierarchy<Band> bandHierarchy, 
            Hierarchy<Concept> sourceHierarchy) {
        
        return new BandTribalAbstractionNetwork(this, bandHierarchy, sourceHierarchy);
    }
    
    public <T extends Cluster> ClusterTribalAbstractionNetwork createClusterTAN(
            BandTribalAbstractionNetwork bandTAN,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy) {
        
        return new ClusterTribalAbstractionNetwork<>(bandTAN, clusterHierarchy, sourceHierarchy);
    }
}
