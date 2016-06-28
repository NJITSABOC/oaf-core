package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.abn.node.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class PAreaTaxonomy extends PartitionedAbstractionNetwork<PArea, Area> 
    implements AggregateableAbstractionNetwork<PAreaTaxonomy> {
    
    private boolean isAggregated = false;
       
    public PAreaTaxonomy(
            AreaTaxonomy areaTaxonomy,
            NodeHierarchy<PArea> pareaHierarchy, 
            ConceptHierarchy conceptHierarchy) {

        super(areaTaxonomy, pareaHierarchy, conceptHierarchy);
    }
        
    public AreaTaxonomy getAreaTaxonomy() {
        return (AreaTaxonomy)super.getBaseAbstractionNetwork();
    }
    
    public NodeHierarchy<PArea> getPAreaHierarchy() {
        return super.getNodeHierarchy();
    }
    
    public PArea getRootPArea() {
        return getPAreaHierarchy().getRoot();
    }
    
    public Set<Area> getAreas() {
        return getAreaTaxonomy().getAreas();
    }
    
    public Set<PArea> getPAreas() {
        return super.getNodes();
    }
    
    @Override
    public Set<ParentNodeDetails> getParentNodeDetails(PArea parea) {
        
        return AbstractionNetworkUtils.getSinglyRootedNodeParentNodeDetails(
                parea, 
                this.getSourceHierarchy(), 
                (Set<SinglyRootedNode>)(Set<?>)this.getPAreas());
    }
    
   
    public PAreaTaxonomy createRootSubtaxonomy(PArea root) {
        NodeHierarchy<PArea> subhierarchy = this.getNodeHierarchy().getSubhierarchyRootedAt(root);

        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();
        
        PAreaTaxonomy subtaxonomy = generator.createTaxonomyFromPAreas(subhierarchy);
               
        if (this.isAggregated()) {
            subtaxonomy.isAggregated = true;
        }
        
        return subtaxonomy;
    }

    public PAreaTaxonomy createAncestorSubtaxonomy(PArea source) {
        NodeHierarchy<PArea> subhierarchy = this.getPAreaHierarchy().getAncestorHierarchy(source);

        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();
        
        PAreaTaxonomy subtaxonomy = generator.createTaxonomyFromPAreas(subhierarchy);
        
        if(this.isAggregated()) {
            subtaxonomy.isAggregated = true;
        }
        
        return subtaxonomy;
    }
    
    @Override
    public boolean isAggregated() {
        return isAggregated;
    }

    @Override
    public PAreaTaxonomy getAggregated(int smallestNode) {
        AggregatePAreaTaxonomyGenerator generator = new AggregatePAreaTaxonomyGenerator();
        
        PAreaTaxonomy aggregateTaxonomy = generator.createAggregatePAreaTaxonomy(this, 
            new PAreaTaxonomyGenerator(),
            new AggregateAbNGenerator<>(),
            smallestNode);
        
        aggregateTaxonomy.isAggregated = true;
        
        return aggregateTaxonomy;
    }
}
