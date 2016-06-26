package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeSummaryPanel;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class PAreaSummaryPanel extends NodeSummaryPanel {
    
    private final RelationshipPanel relationshipPanel;
    
    private final PAreaTaxonomyConfiguration configuration;
    
    public PAreaSummaryPanel(PAreaTaxonomyConfiguration configuration) {
        super(new PAreaSummaryTextFactory(configuration));
        
        this.configuration = configuration;
        
        this.relationshipPanel = new RelationshipPanel(null);
        this.relationshipPanel.setMinimumSize(new Dimension(-1, 100));
        this.relationshipPanel.setPreferredSize(new Dimension(-1, 100));
        
        this.relationshipPanel.addEntitySelectionListener(configuration.getUIConfiguration().getListenerConfiguration().getGroupRelationshipSelectedListener());
        
        this.add(this.relationshipPanel);
    }
    
    public void setContents(Node node) {        
        super.setContents(node);
        
        PArea parea = (PArea)node;
        
        ArrayList<InheritableProperty> sortedProperties = new ArrayList<>(parea.getRelationships());
        
        sortedProperties.sort( (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        
        relationshipPanel.setContents(sortedProperties);
    }
    
    public void clearContents() {
        super.clearContents();
        
        relationshipPanel.clearContents();
    }

}
