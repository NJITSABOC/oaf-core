package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ErrorSubmissionPanel<T extends Concept> extends BaseNATPanel<T> {
    
    public interface ErrorSubmissionListener {
        public void resetClicked();
        public void submitClicked();
    }
    
    private final JButton btnReset;
    private final JButton btnSubmitReport;
    
    private final ErrorSubmissionListener listener;
    
    public ErrorSubmissionPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource,
            ErrorSubmissionListener listener) {
        
        super(mainPanel, dataSource);
        
        this.listener = listener;
                
        this.btnReset = new JButton("Reset Error Report");
        this.btnSubmitReport = new JButton("Submit Error Report");
        
        this.btnReset.addActionListener( (ae) -> {
            listener.resetClicked();
        });
        
        this.btnSubmitReport.addActionListener( (ae) -> {
            listener.submitClicked();
        });
        
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
                
        this.add(btnReset);
        this.add(btnSubmitReport);
    }
}
