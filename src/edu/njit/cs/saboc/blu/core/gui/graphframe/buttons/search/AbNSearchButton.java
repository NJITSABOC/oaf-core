package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.abn.ConceptNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.GenericInternalGraphFrame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class AbNSearchButton extends BaseAbNSearchButton {

    public AbNSearchButton(JFrame parent, 
            GenericInternalGraphFrame igf,
            AbNTextConfiguration config) {
        
        super(parent);
        
        String conceptTypeName = config.getConceptTypeName(true);
        String nodeTypeName = config.getNodeTypeName(true);
        
        this.addSearchAction(new BluGraphSearchAction<ConceptNodeDetails<SinglyRootedNode>>(conceptTypeName, igf) {
            
            public ArrayList<SearchButtonResult<ConceptNodeDetails<SinglyRootedNode>>> doSearch(String query) {
                
                ArrayList<SearchButtonResult<ConceptNodeDetails<SinglyRootedNode>>> results = new ArrayList<>();
                
                if (query.length() >= 3) {
                    PartitionedAbstractionNetwork abn = (PartitionedAbstractionNetwork)getGraphFrame().getGraph().getAbstractionNetwork();

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
                ConceptNodeDetails<SinglyRootedNode> result = o.getResult();

                getGraphFrame().focusOnComponent(getGraphFrame().getGraph().getNodeEntries().get(result.getNodes().iterator().next()));
                
                getGraphFrame().getEnhancedGraphExplorationPanel().highlightEntriesForSearch(new ArrayList<>(result.getNodes()));
            }
        });
        
        this.addSearchAction(new BluGraphSearchAction<SinglyRootedNode>(nodeTypeName, igf) {
            public ArrayList<SearchButtonResult<SinglyRootedNode>> doSearch(String query) {
                
                PartitionedAbstractionNetwork abn = (PartitionedAbstractionNetwork)getGraphFrame().getGraph().getAbstractionNetwork();
                
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
                SinglyRootedNode result = o.getResult();

                getGraphFrame().focusOnComponent(getGraphFrame().getGraph().getNodeEntries().get(result));
                
                getGraphFrame().getEnhancedGraphExplorationPanel().highlightEntriesForSearch(new ArrayList<>(Arrays.asList(result)));
            }
        });
        
    }
}