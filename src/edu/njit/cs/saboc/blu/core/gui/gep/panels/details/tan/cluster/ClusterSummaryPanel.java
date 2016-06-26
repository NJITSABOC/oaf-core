package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeSummaryPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class ClusterSummaryPanel extends NodeSummaryPanel {

    private final TANConfiguration config;
    
    private final ConceptList patriarchList;
    
    public ClusterSummaryPanel(TANConfiguration config) {
        super(new ClusterSummaryTextFactory(config));
        
        this.config = config;
        
        patriarchList = new ConceptList(config, new ClusterPatriarchsTableModel(config)) {

            @Override
            protected String getBorderText(Optional<ArrayList<Concept>> entities) {
                if(entities.isPresent()) {
                    return String.format("Tribes (%d)", entities.get().size());
                } else {
                    return "Tribes";
                }
            }
        };
        
        this.patriarchList.setMinimumSize(new Dimension(-1, 100));
        this.patriarchList.setPreferredSize(new Dimension(-1, 100));
        
        this.add(this.patriarchList);
    }
    
     public void setContents(Node node) {
        super.setContents(node);
        
        Cluster cluster = (Cluster)node;
        
        TribalAbstractionNetwork tan = config.getAbstractionNetwork();
        
        ArrayList<Concept> sortedPatriarchs = new ArrayList<>(cluster.getPatriarchs());
        sortedPatriarchs.sort((a,b) -> a.getName().compareToIgnoreCase(b.getName()));
       
        this.patriarchList.setContents(sortedPatriarchs);
    }
    
    public void clearContents() {
        super.clearContents();
        
        this.patriarchList.clearContents();
    }
}
