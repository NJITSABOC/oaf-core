package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.ErrorReportPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.ErrorReportPanel.ErrorReportPanelListener;
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
        
        errorReportPanel.addErrorReportPanelListener(new ErrorReportPanelListener() {

            @Override
            public void errorSubmitted(OntologyError error) {
                doClose();
            }

            @Override
            public void errorSubmissionCancelled() {
                doClose();
            }
            
            private void doClose() {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        
        dialog.setTitle(String.format("Report %s", initializer.getErrorTypeName()));
        
        dialog.add(errorReportPanel);
        
        dialog.setVisible(true);
    }
    
    protected static void displaySimpleErrorReportPanel(
            NATBrowserPanel browserPanel,
            ErrorReportPanelInitializer initializer) {
        
        SimpleErrorReportPanel errorReportPanel = new SimpleErrorReportPanel(browserPanel);
        errorReportPanel.setInitializer(initializer);
        
        ErrorReportDialog.displayErrorReportDialog(errorReportPanel, 600, 400);
    }
    
    protected static void displaySelectConceptErrorReportPanel(
            NATBrowserPanel browserPanel,
            MissingConceptInitializer initializer) {
        
        SelectConceptErrorReportPanel errorReportPanel = new SelectConceptErrorReportPanel(browserPanel);
        errorReportPanel.setInitializer(initializer);
                
        ErrorReportDialog.displayErrorReportDialog(errorReportPanel, 600, 650);
    }
    
    protected static void displaySelectRelationshipErrorReportPanel(
            NATBrowserPanel browserPanel,
            MissingRelationshipInitializer initializer) {
        
        SelectRelationshipErrorReportPanel errorReportPanel = new SelectRelationshipErrorReportPanel(browserPanel);
        errorReportPanel.setInitializer(initializer);
        
        ErrorReportDialog.displayErrorReportDialog(errorReportPanel, 1000, 650);
    }
    
    public static void displayErroneousChildDialog(
            NATBrowserPanel<?> browserPanel, 
            Concept erroneousChild) {
        
        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel,
                new ErroneousChildInitializer(
                        browserPanel.getDataSource().get().getOntology(), 
                        erroneousChild));
    }
    
    public static void displayOtherChildErrorDialog(
            NATBrowserPanel<?> browserPanel, 
            Concept erroneousChild) {
        
        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel, 
                new OtherChildErrorInitializer(
                        browserPanel.getDataSource().get().getOntology(), 
                        erroneousChild));
    }
    
    public static void displayMissingChildDialog(NATBrowserPanel<?> browserPanel) {
        
        ErrorReportDialog.displaySelectConceptErrorReportPanel(
                browserPanel, 
                new MissingChildInitializer(
                        browserPanel.getDataSource().get().getOntology()));
    }
    
    public static void displayErroneousParentDialog(
            NATBrowserPanel<?> browserPanel, 
            Concept erroneousParent) {
        
        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel, 
                new ErroneousParentInitializer(
                        browserPanel.getDataSource().get().getOntology(), 
                        erroneousParent));
    }
    
    public static void displayRedundantParentErrorDialog(
            NATBrowserPanel<?> browserPanel,
            Concept erroneousParent) {

        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel,
                new RedundantParentInitializer(
                        browserPanel.getDataSource().get().getOntology(), 
                        erroneousParent));
    }
    
    public static void displayOtherParentErrorDialog(
            NATBrowserPanel<?> browserPanel,
            Concept erroneousParent) {

        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel,
                new OtherParentErrorInitializer(
                        browserPanel.getDataSource().get().getOntology(), 
                        erroneousParent));
    }
    
    public static void displayMissingParentDialog(NATBrowserPanel<?> browserPanel) {

        ErrorReportDialog.displaySelectConceptErrorReportPanel(
                browserPanel, 
                new MissingParentInitializer(
                        browserPanel.getDataSource().get().getOntology()));
    }
    
    public static void displayReplaceParentDialog(
            NATBrowserPanel<?> browserPanel, 
            Concept erroneousParent) {
        
        ErrorReportDialog.displaySelectConceptErrorReportPanel(
                browserPanel, 
                new ReplaceParentInitializer(
                        browserPanel.getDataSource().get().getOntology(), 
                        erroneousParent));
    }
    
    public static void displayErroneousSemanticRelationshipDialog(
            NATBrowserPanel<?> browserPanel, 
            InheritableProperty erroneousProperty,
            Concept erroneousParent) {
        
        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel,
                new ErroneousSemanticRelationshipInitializer(
                    browserPanel.getDataSource().get().getOntology(), 
                    erroneousProperty, 
                    erroneousParent));
    }
    
    public static void displayOtherSemanticRelationshipErrorDialog(
            NATBrowserPanel<?> browserPanel, 
            InheritableProperty erroneousProperty,
            Concept erroneousParent) {
        
        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel,
                new OtherSemanticRelationshipErrorInitializer(
                        browserPanel.getDataSource().get().getOntology(),
                        erroneousProperty,
                        erroneousParent));
    }
    
    public static void displayReplaceTargetDialog(
            NATBrowserPanel<?> browserPanel, 
            InheritableProperty property,
            Concept erroneousTarget) {
        
        ErrorReportDialog.displaySelectConceptErrorReportPanel(
                browserPanel, 
                new ReplaceTargetInitializer(
                        browserPanel.getDataSource().get().getOntology(), 
                        property, 
                        erroneousTarget));
    }
    
    public static void displayMissingSemanticRelationshipDialog(NATBrowserPanel<?> browserPanel) {
        
        ErrorReportDialog.displaySelectRelationshipErrorReportPanel(
                browserPanel, 
                new MissingSemanticRelationshipInitializer(
                        browserPanel.getDataSource().get().getOntology()));
    }
        
    public static void displayReplaceSemanticRelationshipDialog(
            NATBrowserPanel<?> browserPanel, 
            InheritableProperty erroneousProperty,
            Concept erroneousTarget) {
        
        ErrorReportDialog.displaySelectRelationshipErrorReportPanel(
                browserPanel, 
                new ReplaceSemanticRelationshipInitializer(
                        browserPanel.getDataSource().get().getOntology(),
                        erroneousProperty, 
                        erroneousTarget));
    }
    
    public static void displayOtherErrorDialog(NATBrowserPanel<?> browserPanel) {

        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel,
                new OtherErrorReportInitializer(
                        browserPanel.getDataSource().get().getOntology()));
    }
}
