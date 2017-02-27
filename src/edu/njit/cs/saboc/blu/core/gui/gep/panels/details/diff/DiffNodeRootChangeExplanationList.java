
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeRootChangeExplanationModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeRootChangeExplanationModel.ChangeExplanationRowEntryFactory;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class DiffNodeRootChangeExplanationList extends AbstractEntityList<DiffAbNConceptChange> {

    private final AbNConfiguration config;
    
    public DiffNodeRootChangeExplanationList(ChangeExplanationRowEntryFactory entryFactory, AbNConfiguration config) {
        super(new DiffNodeRootChangeExplanationModel(entryFactory));
        
        this.config = config;
    }

    @Override
    protected String getBorderText(Optional<ArrayList<DiffAbNConceptChange>> entities) {
        
        String base = String.format("Structural Changes that Affected Root %s", 
                config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false));
        
        int count = 0;
        
        if(entities.isPresent()) {
            count = entities.get().size();
        }
        
        return String.format("%s (%d)", base, count);
    }
}
