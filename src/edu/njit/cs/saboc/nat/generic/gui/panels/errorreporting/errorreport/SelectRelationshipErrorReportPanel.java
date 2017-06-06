package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.InheritablePropertySelectionPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.InheritablePropertySelectionPanel.SelectionType;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErrorReportPanelInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.MissingRelationshipInitializer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Optional;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 * @param <U>
 */
public class SelectRelationshipErrorReportPanel<
        T extends Concept, 
        V extends InheritableProperty, 
        U extends OntologyError<T>> extends ErrorReportPanel<T, U> {
    
    private Optional<MissingRelationshipInitializer<T, V, U>> initializer = Optional.empty();
    
    private final JRadioButton btnSearchForMissingParent;
    private final JRadioButton btnCommentOnly;

    private final ErrorDescriptionPanel<T, U> errorDescriptionPanel;
    
    private final ErrorSeverityPanel<T> errorSeverityPanel;

    private final InheritablePropertySelectionPanel propertySelectPanel;
    
    private final SelectConceptPanel<T> selectConceptPanel;
    
    private final AuditCommentPanel<T> commentPanel;
    
    private final ErrorSubmissionPanel<T> errorSubmissionPanel;
        
    public SelectRelationshipErrorReportPanel(NATBrowserPanel<T> mainPanel) {
        
        super(mainPanel);
        
        this.setLayout(new BorderLayout());

        this.btnSearchForMissingParent = new JRadioButton("Search for Relationship and Target");
        this.btnCommentOnly = new JRadioButton("Comment Only");
        
        ButtonGroup group = new ButtonGroup();
        group.add(btnSearchForMissingParent);
        group.add(btnCommentOnly);
            
        JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        optionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Report Type"));
        
        optionPanel.add(btnSearchForMissingParent);
        optionPanel.add(btnCommentOnly);
        
        this.errorDescriptionPanel = new ErrorDescriptionPanel<>(mainPanel);
        
        this.errorSeverityPanel = new ErrorSeverityPanel<>(mainPanel);
        
        this.commentPanel = new AuditCommentPanel<>(mainPanel);
        this.commentPanel.setPreferredSize(new Dimension(-1, 200));
                
        this.propertySelectPanel = new InheritablePropertySelectionPanel(SelectionType.Single, true);
        this.propertySelectPanel.initialize(mainPanel.getDataSource().get().getAvailableProperties());
        
        this.propertySelectPanel.setBorder(BorderFactory.createTitledBorder("Select Relationship Type"));
        
        this.selectConceptPanel = new SelectConceptPanel<>(mainPanel);
        this.selectConceptPanel.setBorder(BorderFactory.createTitledBorder("Select Target"));
        
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

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(errorDescriptionPanel, BorderLayout.NORTH);
        northPanel.add(optionPanel, BorderLayout.CENTER);
        
        this.add(northPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        centerPanel.add(errorSeverityPanel);
        
        JPanel selectionPanel = new JPanel(new GridLayout(1, 2));
        selectionPanel.add(propertySelectPanel);
        selectionPanel.add(selectConceptPanel);
        
        centerPanel.add(selectionPanel);
        
        centerPanel.add(commentPanel);
            
        this.add(centerPanel, BorderLayout.CENTER);
        
        this.add(errorSubmissionPanel, BorderLayout.SOUTH);
        
        btnSearchForMissingParent.setSelected(true);
        
        btnSearchForMissingParent.addActionListener( (ae) -> {
            this.selectConceptPanel.setVisible(true);
            this.propertySelectPanel.setVisible(true);
            
            this.selectConceptPanel.clearSelectedConcept();
            
            this.revalidate();
        });
        
        btnCommentOnly.addActionListener( (ae) ->{
            
            this.propertySelectPanel.setVisible(false);
            this.selectConceptPanel.setVisible(false);
            
            this.selectConceptPanel.clearSelectedConcept();
            
            this.revalidate();
        });
    }
    
    public void setInitializer(MissingRelationshipInitializer<T, V, U> initializer) {
        this.initializer = Optional.of(initializer);
        
        reset();
    }
    
    public void clearInitializer() {
        this.initializer = Optional.empty();
    }

    @Override
    public U getError() {
        
        MissingRelationshipInitializer<T, V, U> theInitializer = this.initializer.get();
        
        Set<InheritableProperty> selectedRel = propertySelectPanel.getUserSelectedProperties();
        Optional<T> selectedTarget = selectConceptPanel.getSelectedConcept();
        
        if(selectedRel.isEmpty()) {
            
            if(selectedTarget.isPresent()) {
                return theInitializer.generateError(
                    commentPanel.getComment(),
                    errorSeverityPanel.getSeverity(), 
                    selectConceptPanel.getSelectedConcept().get());
                
            } else {
                 return theInitializer.generateError(commentPanel.getComment(), errorSeverityPanel.getSeverity());
            }
        } else {
            if(selectedTarget.isPresent()) {
                return theInitializer.generateError(
                        (V)selectedRel.iterator().next(), 
                        selectedTarget.get(), 
                        commentPanel.getComment(), 
                        errorSeverityPanel.getSeverity());
            } else {
                return theInitializer.generateError( 
                        (V)selectedRel.iterator().next(),
                        commentPanel.getComment(), 
                        errorSeverityPanel.getSeverity());
            }
        }
    }

    @Override
    public void reset() {
        if(initializer.isPresent()) {
            this.errorDescriptionPanel.setDescription(initializer.get());
            this.errorSeverityPanel.setSeverity(initializer.get().getDefaultSeverity());
            
            this.selectConceptPanel.clearSelectedConcept();
            this.commentPanel.reset();
        }
    }

    @Override
    public boolean errorReady() {
        return true;
    }

    @Override
    public Optional<? extends ErrorReportPanelInitializer<T, U>> getInitializer() {
        return this.initializer;
    }
}