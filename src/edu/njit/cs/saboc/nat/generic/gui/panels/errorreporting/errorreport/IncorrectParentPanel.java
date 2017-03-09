package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.error.IncorrectParentError;
import edu.njit.cs.saboc.nat.generic.gui.panels.search.SearchPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class IncorrectParentPanel<T extends Concept> extends ErrorReportPanel<T, IncorrectParentError> {
    
    private final JRadioButton btnRemoveParent;
    private final JRadioButton btnReplaceParent;
    private final JRadioButton btnCommentOnly;
    

    private final JButton btnClearSelection;
    private final JLabel selectedReplacementParentLabel;
    
    private final ErrorSeverityPanel<T> errorSeverityPanel;
    
    private final SearchPanel<T> replacementParentSearchPanel;
    
    private final AuditCommentPanel<T> commentPanel;
    
    private Optional<T> replacementParent = Optional.empty();
    
    private final JPanel searchForParentPanel;
    
    public IncorrectParentPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource,
            T incorrectParent) {
        
        super(mainPanel, dataSource);
        
        this.setLayout(new BorderLayout());

        this.searchForParentPanel = new JPanel(new BorderLayout());

        this.btnRemoveParent = new JRadioButton("Remove Incorrect Parent");
        this.btnReplaceParent = new JRadioButton("Replace Incorrect Parent");
        this.btnCommentOnly = new JRadioButton("Comment Only");
        
        ButtonGroup group = new ButtonGroup();
        group.add(btnRemoveParent);
        group.add(btnReplaceParent);
        group.add(btnCommentOnly);
        
        btnRemoveParent.setSelected(true);
        
              
        JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        optionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Error Report Type"));
        
        optionPanel.add(btnRemoveParent);
        optionPanel.add(btnReplaceParent);
        optionPanel.add(btnCommentOnly);
        

        JLabel typeLabel = new JLabel("Incorrect parent: ");
        typeLabel.setFont(typeLabel.getFont().deriveFont(Font.BOLD, 14.0f));
        
        JLabel incorrectParentNameLabel = new JLabel();
        incorrectParentNameLabel.setFont(incorrectParentNameLabel.getFont().deriveFont(14.0f));
        
        JPanel incorrectParentNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        incorrectParentNamePanel.add(typeLabel);
        incorrectParentNamePanel.add(incorrectParentNameLabel);
        
        incorrectParentNameLabel.setText(incorrectParent.getName());
        
        JPanel northPanel = new JPanel(new GridLayout(2, 1));
        northPanel.add(incorrectParentNamePanel);
        northPanel.add(optionPanel);
        
        this.add(northPanel, BorderLayout.NORTH);
        
        this.btnClearSelection = new JButton("Clear Selection");
        this.btnClearSelection.addActionListener( (ae) -> {
            clearSelectedReplacementParent();
        });
        
        
        JPanel missingParentNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        missingParentNamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED), 
                "Selected Replacement Parent Concept"));
        
        missingParentNamePanel.setOpaque(false);
        
        this.selectedReplacementParentLabel = new JLabel();
        this.selectedReplacementParentLabel.setForeground(Color.BLUE);
        this.selectedReplacementParentLabel.setOpaque(false);
        this.selectedReplacementParentLabel.setFont(this.selectedReplacementParentLabel.getFont().deriveFont(14.0f));
        
        missingParentNamePanel.add(selectedReplacementParentLabel);
        
        JPanel errorDetailsPanel = new JPanel(new BorderLayout());
        errorDetailsPanel.setOpaque(false);
        
        JPanel clearSelectionPanel = new JPanel();
        clearSelectionPanel.add(btnClearSelection);
        
        errorDetailsPanel.add(missingParentNamePanel, BorderLayout.CENTER);
        errorDetailsPanel.add(clearSelectionPanel, BorderLayout.EAST);
        
        this.commentPanel = new AuditCommentPanel<>(mainPanel, dataSource);
        this.commentPanel.setPreferredSize(new Dimension(-1, 200));
        

        this.errorSeverityPanel = new ErrorSeverityPanel<>(mainPanel, dataSource);
        

        this.replacementParentSearchPanel = new SearchPanel<>(mainPanel, dataSource);
        this.replacementParentSearchPanel.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                        "Search for Replacement Parent"));
        
        this.replacementParentSearchPanel.addSearchResultSelectedListener( (result) -> {
            setSelectedReplacementParent(result.getConcept());
        });
        
        
        
        this.searchForParentPanel.add(errorDetailsPanel, BorderLayout.NORTH);
        this.searchForParentPanel.add(replacementParentSearchPanel, BorderLayout.CENTER);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        centerPanel.add(errorSeverityPanel);
        centerPanel.add(searchForParentPanel);
        centerPanel.add(commentPanel);
        
        this.add(centerPanel, BorderLayout.CENTER);
        
        this.searchForParentPanel.setVisible(false);
        clearSelectedReplacementParent();
        
        btnRemoveParent.addActionListener( (ae) -> {
            this.searchForParentPanel.setVisible(false);
            
            this.revalidate();
        });
        
        btnReplaceParent.addActionListener( (ae) -> {
            this.searchForParentPanel.setVisible(true);
            
            this.revalidate();
        });
        
        btnCommentOnly.addActionListener( (ae) ->{
            this.searchForParentPanel.setVisible(false);
            
            this.revalidate();
        });
    }

    private void setSelectedReplacementParent(T concept) {
        this.selectedReplacementParentLabel.setText(concept.getName());
        this.replacementParent = Optional.of(concept);
    }
    
    private void clearSelectedReplacementParent() {
        this.selectedReplacementParentLabel.setText("(not selected)");
        this.replacementParent = Optional.empty();
    }
    
    @Override
    public IncorrectParentError getError() {
        return null;
    }

    @Override
    public void displayError(IncorrectParentError error) {
        
    }
    
    @Override
    public void reset() {
        this.commentPanel.reset();
        
        this.replacementParent = Optional.empty();
        
        
    }

    @Override
    protected void setFontSize(int fontSize) {
       
    }

    @Override
    public boolean errorReady() {
        return true;
    }
}
