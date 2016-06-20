
package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.node.SimilarityNode;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
/**
 *
 * Represents a Band in a tribal abstraction network
 * 
 * @author Chris O
 */
public class Band extends SimilarityNode {

    private final Set<Concept> patriarchs;

    public Band(Set<Cluster> clusters, Set<Concept> patriarchs) {
        super(clusters);
        
        this.patriarchs = patriarchs;
    }

    public Set<Cluster> getClusters() {
        return (Set<Cluster>)super.getInternalNodes();
    }
    
    public Set<Concept> getPatriarchs() {
        return patriarchs;
    }

    public boolean equals(Object o) {
        if(o instanceof Band) {
            return ((Band)o).patriarchs.equals(this.patriarchs);
        }

        return false;
    }
    
    public String getName() {
        return "";
    }
    
    public String getName(String separator) {
        return "";
    }
    
    public int hashCode() {
        return patriarchs.hashCode();
    }
}