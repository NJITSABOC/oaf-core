package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris
 */
public class ClusterTribalAbstractionNetwork<T extends Cluster> extends PartitionedAbstractionNetwork<T, Band> {
    
    public ClusterTribalAbstractionNetwork(
            BandTribalAbstractionNetwork bandTan,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        super(bandTan, clusterHierarchy, sourceHierarchy);
    }
    
    public BandTribalAbstractionNetwork getBandTAN() {
        return (BandTribalAbstractionNetwork)super.getBaseAbstractionNetwork();
    }

    public Set<T> getPatriarchClusters() {
        return super.getNodeHierarchy().getRoots();
    }
    
    public Band getBandFor(T cluster) {
        return super.getPartitionNodeFor(cluster);
    }
    
    public Set<Band> getBands() {
        return super.getBaseAbstractionNetwork().getNodes();
    }

    public Set<T> getClusters() {
        return super.getNodes();
    }
    
    public Hierarchy<T> getClusterHierarchy() {
        return super.getNodeHierarchy();
    }
    
    public Set<T> getNonOverlappingPatriarchClusters() {
        Set<T> nonoverlappingPatriarchClusters = new HashSet<>();
                
        getPatriarchClusters().forEach( (patriarchCluster) -> {           
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
    public Set<ParentNodeDetails<T>> getParentNodeDetails(T cluster) {
        return AbstractionNetworkUtils.getSinglyRootedNodeParentNodeDetails(
                cluster, 
                this.getSourceHierarchy(),
                this.getClusters());
    }
    
    public RootSubTAN createRootSubTAN(T root) {
        Hierarchy<T> subhierarchy = this.getClusterHierarchy().getSubhierarchyRootedAt(root);

        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();
        
        ClusterTribalAbstractionNetwork tan = generator.createTANFromClusters(subhierarchy);
        
        RootSubTAN subTAN = new RootSubTAN(this, tan.getBandTAN(), tan.getClusterHierarchy(), tan.getSourceHierarchy());
                
        return subTAN;
    }
    
    public AncestorSubTAN createAncestorTAN(T source) {
        
        Hierarchy<T> subhierarchy = this.getClusterHierarchy().getAncestorHierarchy(source);
        
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();

        ClusterTribalAbstractionNetwork tan = generator.createTANFromClusters(subhierarchy);
        
        AncestorSubTAN subTAN = new AncestorSubTAN(this, source, tan.getBandTAN(), tan.getClusterHierarchy(), tan.getSourceHierarchy());
        
        return subTAN;
    }
}
