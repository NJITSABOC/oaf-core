package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.ConceptNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class AbNSearchButton extends BaseAbNSearchButton {

    public AbNSearchButton(JFrame parent, AbNTextConfiguration textConfig) {
        super(parent);
        
        String conceptTypeName = textConfig.getConceptTypeName(true);
        String nodeTypeName = textConfig.getNodeTypeName(true);
        
        this.addSearchAction(new BluGraphSearchAction<ConceptNodeDetails<SinglyRootedNode>>(conceptTypeName) {
            
            public ArrayList<SearchButtonResult<ConceptNodeDetails<SinglyRootedNode>>> doSearch(String query) {
                AbNConfiguration config = getConfiguration();
                
                ArrayList<SearchButtonResult<ConceptNodeDetails<SinglyRootedNode>>> results = new ArrayList<>();
                
                if (query.length() >= 3) {
                    AbstractionNetwork abn = config.getAbstractionNetwork();

                    Set<ConceptNodeDetails<SinglyRootedNode>> searchResults = abn.searchConcepts(query);

                    searchResults.stream().forEach((sr) -> {
                        results.add(new SearchButtonResult(sr.getConcept().getName(), sr));
                    });
                    
                    results.sort( (a,b) -> {
                        return a.getResult().getConcept().getName().compareToIgnoreCase(b.getResult().getConcept().getName());
                    });
                }
                
                return results;
            }
            
            public void resultSelected(SearchButtonResult<ConceptNodeDetails<SinglyRootedNode>> o) {
                AbNConfiguration config = getConfiguration();
                
                ConceptNodeDetails<SinglyRootedNode> result = o.getResult();

                // Java!
                SinglyRootedNodeEntry firstResultEntry = config.getUIConfiguration().getDisplayPanel().getGraph().getNodeEntries().get(
                        result.getNodes().iterator().next());
                
                config.getUIConfiguration().getDisplayPanel().getAutoScroller().snapToNodeEntry(firstResultEntry);
                
                config.getUIConfiguration().getDisplayPanel().highlightSinglyRootedNodes(result.getNodes());
            }
        });
        
        this.addSearchAction(new BluGraphSearchAction<SinglyRootedNode>(nodeTypeName) {
            public ArrayList<SearchButtonResult<SinglyRootedNode>> doSearch(String query) {
                AbNConfiguration config = getConfiguration();
                
                AbstractionNetwork abn = config.getAbstractionNetwork();
                
                ArrayList<SearchButtonResult<SinglyRootedNode>> results = new ArrayList<>();

                Set<Node> pareas = abn.searchNodes(query);
                
                pareas.forEach((node) -> {
                    SinglyRootedNode singlyRootedNode = (SinglyRootedNode)node;
                    
                    results.add(new SearchButtonResult<>(singlyRootedNode.getName(), singlyRootedNode));
                });
                
                results.sort( (a, b) -> {
                    return a.getResult().getName().compareToIgnoreCase(b.getResult().getName());
                });

                return results;
            }
            
            public void resultSelected(SearchButtonResult<SinglyRootedNode> o) {
                AbNConfiguration config = getConfiguration();
                
                SinglyRootedNode result = o.getResult();
                
                SinglyRootedNodeEntry resultEntry = config.getUIConfiguration().getDisplayPanel().getGraph().getNodeEntries().get(result);
                
                config.getUIConfiguration().getDisplayPanel().getAutoScroller().snapToNodeEntry(resultEntry);
                config.getUIConfiguration().getDisplayPanel().highlightSinglyRootedNodes(Collections.singleton(result));
            }
        });       
    }
}