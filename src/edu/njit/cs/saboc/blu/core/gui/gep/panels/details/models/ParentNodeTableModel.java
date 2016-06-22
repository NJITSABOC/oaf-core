package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory.NodeTypeNameFactory;
import edu.njit.cs.saboc.blu.core.ontology.Concept;


/**
 *
 * @author Chris O
 */
public class ParentNodeTableModel extends OAFAbstractTableModel<ParentNodeDetails> {

    public ParentNodeTableModel(BLUConfiguration config) {
        super(new String [] {
            config.getTextConfiguration().getParentConceptTypeName(false),
            String.format("Parent %s", config.getTextConfiguration().getNodeTypeName(false),
            String.format("# %s", config.getTextConfiguration().getConceptTypeName(true)))
        });
    }
    
    public Concept getParentConcept(int row) {
        return this.getItemAtRow(row).getParentConcept();
    }
    
    public Node getParentNode(int row) {
        return this.getItemAtRow(row).getParentNode();
    }

    @Override
    protected Object[] createRow(ParentNodeDetails parentInfo) {
        return new Object [] {
            parentInfo.getParentConcept(), 
            parentInfo.getParentNode().getName(),
            parentInfo.getParentNode().getConceptCount()
        };
    }
}
