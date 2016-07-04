package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public class ChildClusterTableModel extends OAFAbstractTableModel<Node> {

    private final TANConfiguration config;
    
    public ChildClusterTableModel(TANConfiguration config) {
        super(new String [] {
            "Child Cluster",
            String.format("# %s", config.getTextConfiguration().getConceptTypeName(true)),
            "Band"
        });
        
        this.config = config;
    }
    
    public Cluster getChildCluster(int row) {
        return (Cluster)this.getItemAtRow(row);
    }

    @Override
    protected Object[] createRow(Node item) {
        Cluster cluster = (Cluster)item;
        
        Band band = config.getTribalAbstractionNetwork().getPartitionNodeFor(cluster);
        
        return new Object [] {
            item.getName(),
            item.getConceptCount(),
            band.getName("\n")
        };
    }
}