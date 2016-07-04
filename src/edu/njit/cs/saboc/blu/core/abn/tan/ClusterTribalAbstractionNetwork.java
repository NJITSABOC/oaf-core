package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris
 */
public class ClusterTribalAbstractionNetwork extends PartitionedAbstractionNetwork<Cluster, Band> {
    
    public ClusterTribalAbstractionNetwork(
            BandTribalAbstractionNetwork bandTan,
            NodeHierarchy<Cluster> clusterHierarchy,
            ConceptHierarchy sourceHierarchy) {

        super(bandTan, clusterHierarchy, sourceHierarchy);
    }
    
    public BandTribalAbstractionNetwork getBandTAN() {
        return (BandTribalAbstractionNetwork)super.getBaseAbstractionNetwork();
    }

    public Set<Cluster> getPatriarchClusters() {
        return super.getNodeHierarchy().getRoots();
    }
    
    public Set<Band> getBands() {
        return super.getBaseAbstractionNetwork().getNodes();
    }

    public Set<Cluster> getClusters() {
        return super.getNodes();
    }
    
    public NodeHierarchy<Cluster> getClusterHierarchy() {
        return super.getNodeHierarchy();
    }
    
    public Set<Cluster> getNonOverlappingPatriarchClusters() {
        Set<Cluster> nonoverlappingPatriarchClusters = new HashSet<>();
                
        getPatriarchClusters().forEach( (Cluster patriarchCluster) -> {           
            Concept root = patriarchCluster.getRoot();
            
            if(!getClusters().stream().anyMatch( (cluster) -> {
                return cluster.getPatriarchs().size() != 1 && cluster.getPatriarchs().contains(root);
            })) {
                nonoverlappingPatriarchClusters.add(patriarchCluster);
            }
        });
        
        return nonoverlappingPatriarchClusters;
    }
    
    
    @Override
    public Set<ParentNodeDetails> getParentNodeDetails(Cluster cluster) {
        return AbstractionNetworkUtils.getSinglyRootedNodeParentNodeDetails(
                cluster, 
                this.getSourceHierarchy(),
                (Set<SinglyRootedNode>)(Set<?>)this.getClusters());
    }
    
    public ClusterTribalAbstractionNetwork createRootSubTAN(Cluster root) {
        NodeHierarchy<Cluster> subhierarchy = this.getClusterHierarchy().getSubhierarchyRootedAt(root);

        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();
        
        ClusterTribalAbstractionNetwork subtaxonomy = generator.createTANFromClusters(subhierarchy);
                
        return subtaxonomy;
    }
    
    public ClusterTribalAbstractionNetwork createAncestorTAN(Cluster source) {
        
        NodeHierarchy<Cluster> subhierarchy = this.getClusterHierarchy().getAncestorHierarchy(source);
        
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();

        ClusterTribalAbstractionNetwork tan = generator.createTANFromClusters(subhierarchy);
        
        return tan;
    }
}
