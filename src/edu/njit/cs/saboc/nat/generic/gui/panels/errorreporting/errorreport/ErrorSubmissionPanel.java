package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ErrorSubmissionPanel<T extends Concept> extends BaseNATPanel<T> {
    
    private final ErrorReportPanel errorReportPanel;
    
    private final JButton btnReset;
    private final JButton btnSubmitReport;
    
    public ErrorSubmissionPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource,
            ErrorReportPanel errorReportPanel) {
        
        super(mainPanel, dataSource);
        
        this.errorReportPanel = errorReportPanel;
        
        this.btnReset = new JButton("Reset Error Report");
        this.btnSubmitReport = new JButton("Submit Error Report");
        
        this.setLayout(new BorderLayout());
        
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        southPanel.add(btnReset);
        southPanel.add(btnSubmitReport);
        
        this.add(errorReportPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }
    
    

    @Override
    protected void setFontSize(int fontSize) {
        
    }
}
