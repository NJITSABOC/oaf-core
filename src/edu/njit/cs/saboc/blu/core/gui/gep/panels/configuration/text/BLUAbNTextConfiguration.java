
package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text;

import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 */
public interface BLUAbNTextConfiguration {
    
    public String getAbNName();
    public String getAbNSummary();
    public String getAbNHelpDescription();
    
    public String getAbNTypeName(boolean plural);
    public String getGroupTypeName(boolean plural);
    public String getConceptTypeName(boolean plural);
    public String getNodeHelpDescription(Node node);
}
