package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class PAreaTaxonomy<T extends PArea> extends PartitionedAbstractionNetwork<T, Area> 
    implements AggregateableAbstractionNetwork<PAreaTaxonomy<T>> {
    
    private boolean isAggregated = false;
       
    public PAreaTaxonomy(
            AreaTaxonomy areaTaxonomy,
            Hierarchy<T> pareaHierarchy, 
            Hierarchy<Concept> conceptHierarchy) {

        super(areaTaxonomy, pareaHierarchy, conceptHierarchy);
    }
    
    protected PAreaTaxonomyFactory getPAreaTaxonomyFactory() {
        return getAreaTaxonomy().getPAreaTaxonomyFactory();
    }
        
    public AreaTaxonomy getAreaTaxonomy() {
        return (AreaTaxonomy)super.getBaseAbstractionNetwork();
    }
    
    public Hierarchy<T> getPAreaHierarchy() {
        return super.getNodeHierarchy();
    }
    
    public Area getAreaFor(T parea) {
        return super.getPartitionNodeFor(parea);
    }
    
    public PArea getRootPArea() {
        return getPAreaHierarchy().getRoot();
    }
    
    public Set<Area> getAreas() {
        return getAreaTaxonomy().getAreas();
    }
    
    public Set<T> getPAreas() {
        return super.getNodes();
    }
    
    @Override
    public Set<ParentNodeDetails<T>> getParentNodeDetails(T parea) {
        return AbstractionNetworkUtils.getSinglyRootedNodeParentNodeDetails(
                parea, 
                this.getSourceHierarchy(), 
                this.getPAreas());
    }
   
    public RootSubtaxonomy<T> createRootSubtaxonomy(T root) {
        Hierarchy<T> subhierarchy = this.getPAreaHierarchy().getSubhierarchyRootedAt(root);

        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();
        
        PAreaTaxonomy<T> subtaxonomy = generator.createTaxonomyFromPAreas(
                getPAreaTaxonomyFactory(), 
                subhierarchy,
                this.getSourceHierarchy());
        
        RootSubtaxonomy rootSubtaxonomy = new RootSubtaxonomy(
                this, 
                subtaxonomy.getAreaTaxonomy(), 
                subtaxonomy.getPAreaHierarchy(), 
                subtaxonomy.getSourceHierarchy());
               
        if (this.isAggregated()) {
            rootSubtaxonomy.setAggregated(true);
        }
        
        return rootSubtaxonomy;
    }

    public AncestorSubtaxonomy<T> createAncestorSubtaxonomy(T source) {
        Hierarchy<T> subhierarchy = this.getPAreaHierarchy().getAncestorHierarchy(source);

        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();
        
        PAreaTaxonomy<T> taxonomy = generator.createTaxonomyFromPAreas(
                getPAreaTaxonomyFactory(), 
                subhierarchy,
                this.getSourceHierarchy());
        
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

        return aggregateTaxonomy;
    }
}
