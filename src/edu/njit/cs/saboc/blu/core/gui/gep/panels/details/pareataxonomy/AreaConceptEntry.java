package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ContainerConceptEntry;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class AreaConceptEntry<CONCEPT_T, PAREA_T extends GenericPArea> extends ContainerConceptEntry<CONCEPT_T, PAREA_T> {
    
    public AreaConceptEntry(CONCEPT_T concept, HashSet<PAREA_T> pareas) {
        super(concept, pareas);
    }
    
}
