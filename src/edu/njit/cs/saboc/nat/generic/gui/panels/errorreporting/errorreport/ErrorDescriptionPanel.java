package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErrorReportPanelInitializer;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public class ErrorDescriptionPanel<T extends Concept, V extends OntologyError<T>> extends BaseNATPanel<T> {
    
    private final JEditorPane errorDescriptionPane;
    
    public ErrorDescriptionPanel(NATBrowserPanel<T> mainPanel) {
        super(mainPanel);
        
        this.errorDescriptionPane = new JEditorPane();
        this.errorDescriptionPane.setEditable(false);
        this.errorDescriptionPane.setContentType("text/html");
        this.errorDescriptionPane.setPreferredSize(new Dimension(-1, 100));
        this.errorDescriptionPane.setBorder(BorderFactory.createTitledBorder("Error Report"));
        
        this.setLayout(new BorderLayout());
        
        this.add(errorDescriptionPane, BorderLayout.CENTER);
    }
    
    public void setDescription(ErrorReportPanelInitializer<T, V> theInitializer) {
        this.errorDescriptionPane.setText(theInitializer.getStyledErrorDescriptionText());
    }
    
}
