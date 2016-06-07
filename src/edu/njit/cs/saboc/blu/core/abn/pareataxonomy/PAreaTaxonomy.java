package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.ontology.SingleRootedConceptHierarchy;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class PAreaTaxonomy extends PartitionedAbstractionNetwork<PArea, Area> {

    private final SingleRootedConceptHierarchy sourceConceptHierarchy;
    
    public PAreaTaxonomy(
            AreaTaxonomy areaTaxonomy,
            SingleRootedConceptHierarchy conceptHierarchy,
            NodeHierarchy<PArea> pareaHierarchy) {

        super(areaTaxonomy, pareaHierarchy);

        this.sourceConceptHierarchy = conceptHierarchy;
    }
    
    public SingleRootedConceptHierarchy getConceptHierarchy() {
        return sourceConceptHierarchy;
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
    
    /*
    protected TAXONOMY_T createRootSubtaxonomy(PAREA_T root, PAreaTaxonomyGenerator generator) {
        SingleRootedGroupHierarchy<PAREA_T> subhierarchy = (SingleRootedGroupHierarchy<PAREA_T>)this.groupHierarchy.getSubhierarchyRootedAt(root);
        
        GroupHierarchy<PAREA_T> pareaSubhierarchy = subhierarchy.asGroupHierarchy();
        
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
