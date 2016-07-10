package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
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
            Hierarchy<PArea> pareaHierarchy, 
            Hierarchy<Concept> conceptHierarchy) {

        super(areaTaxonomy, pareaHierarchy, conceptHierarchy);
    }
        
    public AreaTaxonomy getAreaTaxonomy() {
        return (AreaTaxonomy)super.getBaseAbstractionNetwork();
    }
    
    public Hierarchy<PArea> getPAreaHierarchy() {
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
    
   
    public RootSubtaxonomy createRootSubtaxonomy(PArea root) {
        Hierarchy<PArea> subhierarchy = this.getNodeHierarchy().getSubhierarchyRootedAt(root);

        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();
        
        PAreaTaxonomy subtaxonomy = generator.createTaxonomyFromPAreas(subhierarchy);
        
        RootSubtaxonomy rootSubtaxonomy = new RootSubtaxonomy(
                this, 
                subtaxonomy.getAreaTaxonomy(), 
                subtaxonomy.getPAreaHierarchy(), 
                subtaxonomy.getSourceHierarchy());
               
        if (this.isAggregated()) {
            subtaxonomy.setAggregated(true);
        }
        
        return rootSubtaxonomy;
    }

    public AncestorSubtaxonomy createAncestorSubtaxonomy(PArea source) {
        Hierarchy<PArea> subhierarchy = this.getPAreaHierarchy().getAncestorHierarchy(source);

        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();
        
        PAreaTaxonomy taxonomy = generator.createTaxonomyFromPAreas(subhierarchy);
        
        AncestorSubtaxonomy subtaxonomy = new AncestorSubtaxonomy(this, 
                source, 
                taxonomy.getAreaTaxonomy(), 
                taxonomy.getPAreaHierarchy(), 
                taxonomy.getSourceHierarchy());
        
        if(this.isAggregated()) {
            subtaxonomy.setAggregated(true);
        }
        
        return subtaxonomy;
    }
    
    protected void setAggregated(boolean value) {
        this.isAggregated = value;
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
