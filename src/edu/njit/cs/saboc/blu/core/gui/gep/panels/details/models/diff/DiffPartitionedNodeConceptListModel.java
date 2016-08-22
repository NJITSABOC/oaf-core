
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.NodeConceptChange;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class DiffPartitionedNodeConceptListModel extends DiffNodeConceptListModel {
    public DiffPartitionedNodeConceptListModel(PartitionedAbNConfiguration config) {
        super(config);
    }
    
    protected PartitionedAbNConfiguration getConfiguration() {
        return (PartitionedAbNConfiguration)super.getConfiguration();
    }
    
    protected String getChangeName(NodeConceptChange change) {
        PartitionedAbNConfiguration config = this.getConfiguration();
        
        switch(change.getChangeType()) {

            case AddedToOnt:
                return "Added to ontology";
                
            case AddedToHierarchy:
                return "Added to subhierarchy";
                
            case AddedToNode:
                return String.format("Added to %s (still summarized by other %s)", 
                        config.getTextConfiguration().getContainerTypeName(false).toLowerCase(),
                        config.getTextConfiguration().getContainerTypeName(true).toLowerCase());
                
            case MovedFromNode:
                return String.format("Moved from %s", 
                        config.getTextConfiguration().getContainerTypeName(false));
    

            case RemovedFromOnt:
                return "Removed from ontology";
                
            case RemovedFromHierarchy:
                return "Removed from subhierarchy";
                
            case RemovedFromNode:
                return String.format("Removed from %s (still in other %s)", 
                        config.getTextConfiguration().getContainerTypeName(false).toLowerCase(),
                        config.getTextConfiguration().getContainerTypeName(true).toLowerCase());
                
            case MovedToNode:
                return String.format("Moved to %s", 
                        config.getTextConfiguration().getContainerTypeName(false));
        }
        
        return "[UNKNOWN CHANGE TYPE]";
    }
    
}
