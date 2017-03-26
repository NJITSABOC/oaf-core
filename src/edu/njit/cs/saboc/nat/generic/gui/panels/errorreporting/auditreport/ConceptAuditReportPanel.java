
package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.auditreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ConceptAuditReportPanel<T extends Concept> extends JPanel {
    
    private final JLabel conceptNameLabel;
    private final JLabel auditStateLabel;
    
    private final JEditorPane txtAuditComment;
    
    public ConceptAuditReportPanel(NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        this.conceptNameLabel = new JLabel();
        this.auditStateLabel = new JLabel();
        
        this.txtAuditComment = new JEditorPane();
    }
}
