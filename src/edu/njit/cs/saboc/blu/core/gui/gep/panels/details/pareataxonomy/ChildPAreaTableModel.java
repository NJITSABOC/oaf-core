
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class ChildPAreaTableModel extends OAFAbstractTableModel<Node> {

    private final PAreaTaxonomyConfiguration config;
    
    public ChildPAreaTableModel(PAreaTaxonomyConfiguration config) {
        super(new String [] {
            "Child Partial-area",
            String.format("# %s", config.getTextConfiguration().getConceptTypeName(true)),
            "Area"
        });
        
        this.config = config;
    }
    
    public PArea getChildPArea(int row) {
        return (PArea)this.getItemAtRow(row);
    }

    @Override
    protected Object[] createRow(Node item) {
        PArea parea = (PArea)item;
        
        Area area = config.getPAreaTaxonomy().getPartitionNodeFor(parea);
        
        return new Object [] {
            item.getName(),
            item.getConceptCount(),
            area.getName("\n")
        };
    }
}
