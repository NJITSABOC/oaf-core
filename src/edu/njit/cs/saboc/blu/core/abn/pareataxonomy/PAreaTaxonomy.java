package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.DerivedPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.DerivedSimplePAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class PAreaTaxonomy<T extends PArea> extends PartitionedAbstractionNetwork<T, Area> 
    implements AggregateableAbstractionNetwork<PAreaTaxonomy<T>> {
    
    public PAreaTaxonomy(
            AreaTaxonomy areaTaxonomy,
            Hierarchy<T> pareaHierarchy, 
            Hierarchy<Concept> conceptHierarchy,
            DerivedPAreaTaxonomy derivation) {

        super(areaTaxonomy, pareaHierarchy, conceptHierarchy, derivation);
    }
    
    public PAreaTaxonomy(
            AreaTaxonomy areaTaxonomy,
            Hierarchy<T> pareaHierarchy, 
            Hierarchy<Concept> conceptHierarchy) {

        this(areaTaxonomy, 
                pareaHierarchy, 
                conceptHierarchy, 
                new DerivedSimplePAreaTaxonomy(
                        areaTaxonomy.getPAreaTaxonomyFactory().getSourceOntology(), 
                        conceptHierarchy.getRoot(), 
                        areaTaxonomy.getPAreaTaxonomyFactory()));
    }
    
    public PAreaTaxonomy(PAreaTaxonomy taxonomy) {
        
        this(taxonomy.getAreaTaxonomy(), 
                taxonomy.getPAreaHierarchy(), 
                taxonomy.getSourceHierarchy(),
                taxonomy.getDerivation());
    }
    
    public PAreaTaxonomy(PAreaTaxonomy taxonomy, DerivedPAreaTaxonomy derivation) {
        
        this(taxonomy.getAreaTaxonomy(), 
                taxonomy.getPAreaHierarchy(), 
                taxonomy.getSourceHierarchy(),
                derivation);
    }
    
    @Override
    public DerivedPAreaTaxonomy getDerivation() {
        return (DerivedPAreaTaxonomy)super.getDerivation();
    }
    
    public PAreaTaxonomyFactory getPAreaTaxonomyFactory() {
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
    
    public T getRootPArea() {
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
   
    public PAreaTaxonomy createRootSubtaxonomy(T root) {
        Hierarchy<T> subhierarchy = this.getPAreaHierarchy().getSubhierarchyRootedAt(root);

        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();
        
        PAreaTaxonomy<T> subtaxonomy = generator.createTaxonomyFromPAreas(
                getPAreaTaxonomyFactory(), 
                subhierarchy,
                this.getSourceHierarchy());
        
        RootSubtaxonomy<T> rootSubtaxonomy = new RootSubtaxonomy<>(
                this, 
                subtaxonomy);

        return rootSubtaxonomy;
    }

    public PAreaTaxonomy createAncestorSubtaxonomy(T source) {
        Hierarchy<T> subhierarchy = this.getPAreaHierarchy().getAncestorHierarchy(source);

        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();
        
        PAreaTaxonomy<T> ancestorSubtaxonomy = generator.createTaxonomyFromPAreas(
                getPAreaTaxonomyFactory(), 
                subhierarchy,
                this.getSourceHierarchy());
        
        return new AncestorSubtaxonomy(this, 
                source, 
                ancestorSubtaxonomy);
    }

    @Override
    public boolean isAggregated() {
        return false;
    }
    
    public Set<InheritableProperty> getPropertiesInTaxonomy() {
        return this.getAreaTaxonomy().getPropertiesInTaxonomy();
    }
    
    public PAreaTaxonomy getRelationshipSubtaxonomy(Set<InheritableProperty> allowedRelTypes) {

        if (allowedRelTypes.equals(this.getAreaTaxonomy().getPropertiesInTaxonomy())) {
            return this;
        } else {
            PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();
            
            PAreaRelationshipSubtaxonomyFactory factory = 
                    new PAreaRelationshipSubtaxonomyFactory(
                        this.getPAreaTaxonomyFactory().getSourceOntology(),
                        this,
                        allowedRelTypes);

            return generator.derivePAreaTaxonomy(factory, getSourceHierarchy());
        }
    }

    @Override
    public PAreaTaxonomy getAggregated(int aggregateBound) {
        return AggregatePAreaTaxonomy.generateAggregatePAreaTaxonomy(this, aggregateBound);
    }
}
