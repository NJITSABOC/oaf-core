package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class ParentPAreaTableModel extends OAFAbstractTableModel<ParentNodeDetails> {
    
    private final PAreaTaxonomyConfiguration config;
    
    public ParentPAreaTableModel(PAreaTaxonomyConfiguration config) {
        super(new String [] {
            config.getTextConfiguration().getParentConceptTypeName(false),
            "Parent Partial-area",
            String.format("# %s", config.getTextConfiguration().getConceptTypeName(true)),
            "Area"
        });
        
        this.config = config;
    }
    
    public Concept getParentConcept(int row) {
        return this.getItemAtRow(row).getParentConcept();
    }
    
    public Node getParentNode(int row) {
        return this.getItemAtRow(row).getParentNode();
    }

    @Override
    protected Object[] createRow(ParentNodeDetails parentInfo) {
        Area area = config.getPAreaTaxonomy().getPartitionNodeFor((PArea)parentInfo.getParentNode());
        
        return new Object [] {
            parentInfo.getParentConcept().getName(), 
            parentInfo.getParentNode().getName(),
            parentInfo.getParentNode().getConceptCount(),
            area.getName("\n")
        };
    }
}