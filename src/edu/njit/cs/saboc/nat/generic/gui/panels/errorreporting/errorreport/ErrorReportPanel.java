package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErrorReportPanelInitializer;
import java.util.Optional;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public abstract class ErrorReportPanel<T extends Concept, V extends OntologyError> 
    extends BaseNATPanel<T> {

    public ErrorReportPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel, dataSource);
    }
    
    public abstract Optional<? extends ErrorReportPanelInitializer> getInitializer();
    
    public abstract void reset();
    public abstract boolean errorReady();
    
    public abstract V getError();
}
