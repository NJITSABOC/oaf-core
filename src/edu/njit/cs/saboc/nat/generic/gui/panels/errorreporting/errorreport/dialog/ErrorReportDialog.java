package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.ErrorReportPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.SelectConceptErrorReportPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.SelectRelationshipErrorReportPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.SimpleErrorReportPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErroneousChildInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErroneousParentInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErroneousSemanticRelationshipInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErrorReportPanelInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.MissingChildInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.MissingConceptInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.MissingParentInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.MissingRelationshipInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.MissingSemanticRelationshipInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.OtherChildErrorInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.OtherErrorReportInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.OtherParentErrorInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.OtherSemanticRelationshipErrorInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.RedundantParentInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ReplaceParentInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ReplaceSemanticRelationshipInitializer;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ReplaceTargetInitializer;
import javax.swing.JDialog;

/**
 *
 * @author Chris O
 */
public class ErrorReportDialog {
    
    protected static void displayErrorReportDialog(
            ErrorReportPanel errorReportPanel, 
            int width, 
            int height) {
        
        JDialog dialog = new JDialog();
        
        dialog.setModal(true);
        dialog.setSize(width, height);
        dialog.setLocationRelativeTo(errorReportPanel.getMainPanel().getParentFrame());
        
        ErrorReportPanelInitializer initializer = (ErrorReportPanelInitializer)errorReportPanel.getInitializer().get();
        
        dialog.setTitle(String.format("Report %s", initializer.getErrorTypeName()));
        
        dialog.add(errorReportPanel);
        
        dialog.setVisible(true);
    }
    
    protected static void displaySimpleErrorReportPanel(
            NATBrowserPanel browserPanel,
            ConceptBrowserDataSource dataSource,
            ErrorReportPanelInitializer initializer) {
        
        SimpleErrorReportPanel errorReportPanel = new SimpleErrorReportPanel(browserPanel, dataSource);
        errorReportPanel.setInitializer(initializer);
        
        ErrorReportDialog.displayErrorReportDialog(errorReportPanel, 600, 400);
    }
    
    protected static void displaySelectConceptErrorReportPanel(
            NATBrowserPanel browserPanel,
            ConceptBrowserDataSource dataSource,
            MissingConceptInitializer initializer) {
        
        SelectConceptErrorReportPanel errorReportPanel = new SelectConceptErrorReportPanel(browserPanel, dataSource);
        errorReportPanel.setInitializer(initializer);
                
        ErrorReportDialog.displayErrorReportDialog(errorReportPanel, 600, 650);
    }
    
    protected static void displaySelectRelationshipErrorReportPanel(
            NATBrowserPanel browserPanel,
            ConceptBrowserDataSource dataSource,
            MissingRelationshipInitializer initializer) {
        
        SelectRelationshipErrorReportPanel errorReportPanel = new SelectRelationshipErrorReportPanel(browserPanel, dataSource);
        errorReportPanel.setInitializer(initializer);
        
        ErrorReportDialog.displayErrorReportDialog(errorReportPanel, 1000, 650);
    }
    
    public static void displayErroneousChildDialog(
            NATBrowserPanel browserPanel, 
            ConceptBrowserDataSource dataSource,
            Concept erroneousChild) {
        
        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel,
                dataSource,
                new ErroneousChildInitializer(dataSource.getOntology(), erroneousChild));
    }
    
    public static void displayOtherChildErrorDialog(
            NATBrowserPanel browserPanel, 
            ConceptBrowserDataSource dataSource,
            Concept erroneousChild) {
        
        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel, 
                dataSource, 
                new OtherChildErrorInitializer(dataSource.getOntology(), erroneousChild));
    }
    
    public static void displayMissingChildDialog(
            NATBrowserPanel browserPanel, 
            ConceptBrowserDataSource dataSource) {
        
        ErrorReportDialog.displaySelectConceptErrorReportPanel(
                browserPanel, 
                dataSource, 
                new MissingChildInitializer(dataSource.getOntology()));
    }
    
    public static void displayErroneousParentDialog(
            NATBrowserPanel browserPanel, 
            ConceptBrowserDataSource dataSource,
            Concept erroneousParent) {
        
        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel, 
                dataSource, 
                new ErroneousParentInitializer(dataSource.getOntology(), erroneousParent));
    }
    
    public static void displayRedundantParentErrorDialog(
            NATBrowserPanel browserPanel,
            ConceptBrowserDataSource dataSource,
            Concept erroneousParent) {

        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel,
                dataSource,
                new RedundantParentInitializer(dataSource.getOntology(), erroneousParent));
    }
    
    public static void displayOtherParentErrorDialog(
            NATBrowserPanel browserPanel,
            ConceptBrowserDataSource dataSource,
            Concept erroneousParent) {

        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel,
                dataSource,
                new OtherParentErrorInitializer(dataSource.getOntology(), erroneousParent));
    }
    
    public static void displayMissingParentDialog(
            NATBrowserPanel browserPanel, 
            ConceptBrowserDataSource dataSource) {

        ErrorReportDialog.displaySelectConceptErrorReportPanel(
                browserPanel, 
                dataSource, 
                new MissingParentInitializer(dataSource.getOntology()));
    }
    
    public static void displayReplaceParentDialog(
            NATBrowserPanel browserPanel, 
            ConceptBrowserDataSource dataSource,
            Concept erroneousParent) {
        
        ErrorReportDialog.displaySelectConceptErrorReportPanel(
                browserPanel, 
                dataSource, 
                new ReplaceParentInitializer(dataSource.getOntology(), erroneousParent));
    }
    
    public static void displayErroneousSemanticRelationshipDialog(
            NATBrowserPanel browserPanel, 
            ConceptBrowserDataSource dataSource,
            InheritableProperty erroneousProperty,
            Concept erroneousParent) {
        
        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel,
                dataSource,
                new ErroneousSemanticRelationshipInitializer(
                    dataSource.getOntology(), 
                    erroneousProperty, 
                    erroneousParent));
    }
    
    public static void displayOtherSemanticRelationshipErrorDialog(
            NATBrowserPanel browserPanel, 
            ConceptBrowserDataSource dataSource,
            InheritableProperty erroneousProperty,
            Concept erroneousParent) {
        
        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel,
                dataSource,
                new OtherSemanticRelationshipErrorInitializer(
                        dataSource.getOntology(),
                        erroneousProperty,
                        erroneousParent));
    }
    
    public static void displayReplaceTargetDialog(
            NATBrowserPanel browserPanel, 
            ConceptBrowserDataSource dataSource,
            InheritableProperty property,
            Concept erroneousTarget) {
        
        ErrorReportDialog.displaySelectConceptErrorReportPanel(
                browserPanel, 
                dataSource, 
                new ReplaceTargetInitializer(dataSource.getOntology(), property, erroneousTarget));
    }
    
    public static void displayMissingSemanticRelationshipDialog(
            NATBrowserPanel browserPanel, 
            ConceptBrowserDataSource dataSource) {
        
        ErrorReportDialog.displaySelectRelationshipErrorReportPanel(
                browserPanel, 
                dataSource,
                new MissingSemanticRelationshipInitializer(dataSource.getOntology()));
    }
        
    public static void displayReplaceSemanticRelationshipDialog(
            NATBrowserPanel browserPanel, 
            ConceptBrowserDataSource dataSource,
            InheritableProperty erroneousProperty,
            Concept erroneousTarget) {
        
        ErrorReportDialog.displaySelectRelationshipErrorReportPanel(
                browserPanel, 
                dataSource,
                new ReplaceSemanticRelationshipInitializer(
                        dataSource.getOntology(), 
                        erroneousProperty, 
                        erroneousTarget));
    }
    
    public static void displayOtherErrorDialog(
            NATBrowserPanel browserPanel,
            ConceptBrowserDataSource dataSource) {

        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel,
                dataSource,
                new OtherErrorReportInitializer(dataSource.getOntology()));
    }
}
