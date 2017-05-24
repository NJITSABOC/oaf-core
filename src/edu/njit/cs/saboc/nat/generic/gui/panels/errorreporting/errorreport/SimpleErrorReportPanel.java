package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErrorReportPanelInitializer;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import java.awt.BorderLayout;
import java.util.Optional;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public class SimpleErrorReportPanel<T extends Concept, V extends OntologyError<T>> extends ErrorReportPanel<T, V> {

    private Optional<ErrorReportPanelInitializer<T, V>> initializer = Optional.empty();
    
    private final ErrorDescriptionPanel<T, V> errorDescriptionPane;

    private final ErrorSeverityPanel<T> errorSeverityPanel;
    private final AuditCommentPanel<T> auditCommentPanel;
    
    private final ErrorSubmissionPanel<T> errorSubmissionPanel;

    public SimpleErrorReportPanel(NATBrowserPanel<T> mainPanel) {
        
        super(mainPanel);
        
        this.errorDescriptionPane = new ErrorDescriptionPanel<>(mainPanel);
        
        this.errorSeverityPanel = new ErrorSeverityPanel<>(mainPanel);
        
        this.auditCommentPanel = new AuditCommentPanel<>(mainPanel);
        
        this.errorSubmissionPanel = new ErrorSubmissionPanel<>(
                mainPanel, 
                new ErrorSubmissionPanel.ErrorSubmissionListener() {

                    @Override
                    public void resetClicked() {
                        reset();
                    }

                    @Override
                    public void submitClicked() {
                        submitError();
                    }
        });
        
        this.setLayout(new BorderLayout());
        
        
        this.add(errorDescriptionPane, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(errorSeverityPanel, BorderLayout.NORTH);
        centerPanel.add(auditCommentPanel, BorderLayout.CENTER);
        
        this.add(centerPanel, BorderLayout.CENTER);
        
        this.add(errorSubmissionPanel, BorderLayout.SOUTH);
    }

    @Override
    public void reset() {
        if(initializer.isPresent()) {
            ErrorReportPanelInitializer<T, V> theInitializer = this.initializer.get();

            this.errorDescriptionPane.setDescription(theInitializer);
            this.errorSeverityPanel.setSeverity(theInitializer.getDefaultSeverity());
            this.auditCommentPanel.reset();
        }
    }

    @Override
    public boolean errorReady() {
        return true;
    }
    
    public void setInitializer(ErrorReportPanelInitializer<T, V> initializer) {
        this.initializer = Optional.of(initializer);
        
        reset();
    }
    
    public void clearInitializer() {
        this.initializer = Optional.empty();
    }
    
    @Override
    public V getError() {
        return initializer.get().generateError(
                auditCommentPanel.getComment(), 
                errorSeverityPanel.getSeverity());
    }

    @Override
    public Optional<? extends ErrorReportPanelInitializer<T, V>> getInitializer() {
        return this.initializer;
    }
}
