package edu.njit.cs.saboc.blu.core.abn.tan.nodes;

import SnomedShared.Concept;
import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.GenericParentGroupInfo;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericCluster<CONCEPT_T, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T>, 
        CLUSTER_T extends GenericCluster<CONCEPT_T, HIERARCHY_T, CLUSTER_T>> extends GenericConceptGroup {

    private final HashSet<CONCEPT_T> patriarchs;
    
    private final HIERARCHY_T hierarchy;
    
    private HashSet<GenericParentGroupInfo<CONCEPT_T, CLUSTER_T>> parentClusters;
    
    public GenericCluster(
            int id, 
            Concept headerConcept, 
            HIERARCHY_T conceptHierarchy,
            HashSet<Integer> parentClusters, 
            HashSet<CONCEPT_T> patriarchs) {
        
        super(id, headerConcept, conceptHierarchy.getNodesInHierarchy().size(), parentClusters);

        this.patriarchs = patriarchs;
        
        this.hierarchy = conceptHierarchy;
    }

    public HashSet<CONCEPT_T> getPatriarchs() {
        return patriarchs;
    }
    
    public HashSet<CONCEPT_T> getConcepts() {
        return hierarchy.getNodesInHierarchy();
    }
    
    public HIERARCHY_T getHierarchy() {
        return hierarchy;
    }
    
    public void setParentClusterInfo(HashSet<GenericParentGroupInfo<CONCEPT_T, CLUSTER_T>> parentClusters) {
        this.parentClusters = parentClusters;
    }
    
    public HashSet<GenericParentGroupInfo<CONCEPT_T, CLUSTER_T>> getParentClusterInfo() {
        return parentClusters;
    }
    
    public int hashCode() {
        return Long.hashCode(root.getId());
    }

    public boolean equals(Object o) {
        if(o instanceof GenericCluster) {
            GenericCluster other = ((GenericCluster)o);

            return this.getRoot().getId() == other.getRoot().getId();
        }

        return false;
    }
}
