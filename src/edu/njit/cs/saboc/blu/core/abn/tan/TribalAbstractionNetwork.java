package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.abn.node.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris
 */
public class TribalAbstractionNetwork extends PartitionedAbstractionNetwork<Cluster, Band> {
    
    public TribalAbstractionNetwork(
            BandTribalAbstractionNetwork bandTan,
            NodeHierarchy<Cluster> clusterHierarchy,
            ConceptHierarchy sourceHierarchy) {

        super(bandTan, clusterHierarchy, sourceHierarchy);
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
    
    /*
    
    protected TAN_T createRootSubTAN(CLUSTER_T root, TribalAbstractionNetworkGenerator generator) {
        SingleRootedHierarchy<CLUSTER_T> subhierarchy = this.groupHierarchy.getSubhierarchyRootedAt(root);
        
        GroupHierarchy<CLUSTER_T> clusterSubhierarchy = new GroupHierarchy<>(subhierarchy);
        
        HashSet<CLUSTER_T> clusters = clusterSubhierarchy.getNodesInHierarchy();
        
        HashMap<Integer, CLUSTER_T> clusterIds = new HashMap<>();
        
        clusters.forEach((CLUSTER_T parea) -> {
            clusterIds.put(parea.getId(), parea);
        });
        
        TAN_T subtaxonomy = (TAN_T)generator.createTANFromClusters(clusterIds, clusterSubhierarchy);
                
        return subtaxonomy;
    }
    
    protected TAN_T createAncestorTAN(CLUSTER_T source, TribalAbstractionNetworkGenerator generator) {
        
        MultiRootedHierarchy<CLUSTER_T> ancestorSubhierarhcy = groupHierarchy.getAncestorHierarchy(source);
        
        GroupHierarchy<CLUSTER_T> clusterSubhierarchy = new GroupHierarchy<>(ancestorSubhierarhcy);
        
        HashSet<CLUSTER_T> clusters = clusterSubhierarchy.getNodesInHierarchy();
        
        HashMap<Integer, CLUSTER_T> clusterIds = new HashMap<>();
        
        clusters.forEach((CLUSTER_T cluster) -> {
            clusterIds.put(cluster.getId(), cluster);
        });
        
        TAN_T tan = (TAN_T)generator.createTANFromClusters(clusterIds, clusterSubhierarchy);
        
        return tan;
    }
    
    */
}
