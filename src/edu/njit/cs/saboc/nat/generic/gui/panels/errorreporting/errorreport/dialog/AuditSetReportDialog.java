package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.auditset.AuditReportPanel;
import javax.swing.JDialog;

/**
 *
 * @author Chris O
 */
public class AuditSetReportDialog {
    
    public static <T extends Concept> void showAuditSetReport(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        JDialog dialog = new JDialog();
        
        dialog.setModal(true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(mainPanel.getParentFrame());
        
        AuditReportPanel<T> auditReportPanel = new AuditReportPanel<>(mainPanel, dataSource);
        dialog.add(auditReportPanel);
        
        dialog.setTitle("Audit Set Report");
              
        dialog.setVisible(true);
    }
}
