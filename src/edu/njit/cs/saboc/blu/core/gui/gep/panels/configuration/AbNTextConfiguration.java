
package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 */
public interface AbNTextConfiguration {
    
    public String getAbNName();
    public String getAbNSummary();
    public String getAbNHelpDescription();
    
    public String getAbNTypeName(boolean plural);
    public String getNodeTypeName(boolean plural);
    public String getConceptTypeName(boolean plural);
    
    public String getParentConceptTypeName(boolean plural);
    public String getChildConceptTypeName(boolean plural);
    
    public String getNodeHelpDescription(Node node);
}
