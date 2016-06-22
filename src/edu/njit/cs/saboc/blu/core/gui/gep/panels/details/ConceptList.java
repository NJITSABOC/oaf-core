package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ConceptTableModel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class ConceptList extends AbstractEntityList<Concept> {
    
    private final BLUConfiguration config;
    
    public ConceptList(BLUConfiguration config) {
        super(new ConceptTableModel(config));
        
        this.config = config;
    }

    @Override
    protected String getBorderText(Optional<ArrayList<Concept>> entities) {
        String base = config.getTextConfiguration().getConceptTypeName(true);

        if(entities.isPresent()) {
            return String.format("%s (%d)", base, entities.get().size());
        } else {
            return String.format("%s (0)", base);
        }
    }
}
