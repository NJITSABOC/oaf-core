package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.abn.node.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class PAreaTaxonomy extends PartitionedAbstractionNetwork<PArea, Area> {
    
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
    
   
    public PAreaTaxonomy createRootSubtaxonomy(PArea root, PAreaTaxonomyGenerator generator) {
        NodeHierarchy<PArea> subhierarchy = this.getNodeHierarchy().getSubhierarchyRootedAt(root);

        PAreaTaxonomy subtaxonomy = generator.createTaxonomyFromPAreas(subhierarchy);
        
        return subtaxonomy;
    }
    
    /*
    
    protected TAXONOMY_T createAncestorSubtaxonomy(PAREA_T source, PAreaTaxonomyGenerator generator) {
        SingleRootedHierarchy<PAREA_T> convertedHierarchy = this.groupHierarchy.getSubhierarchyRootedAt(getRootGroup());
        
        MultiRootedHierarchy<PAREA_T> ancestorSubhierarhcy = convertedHierarchy.getAncestorHierarchy(source);
        
        GroupHierarchy<PAREA_T> pareaSubhierarchy = new GroupHierarchy<>(ancestorSubhierarhcy);
        
        HashSet<PAREA_T> pareas = pareaSubhierarchy.getNodesInHierarchy();
        
        HashMap<Integer, PAREA_T> pareaIds = new HashMap<>();
        
        pareas.forEach((PAREA_T parea) -> {
            pareaIds.put(parea.getId(), parea);
        });
        
        TAXONOMY_T subtaxonomy = (TAXONOMY_T)generator.createTaxonomyFromPAreas(pareaIds, pareaSubhierarchy);
        
        if(this.isReduced()) {
            subtaxonomy.setReduced(isReduced);
        }
        
        return subtaxonomy;
    }
    */
}
