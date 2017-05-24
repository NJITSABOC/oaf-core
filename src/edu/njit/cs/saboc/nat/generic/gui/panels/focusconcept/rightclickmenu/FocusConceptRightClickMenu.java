
package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditResult;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog.AuditCommentReportDialog;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog.ErrorReportDialog;
import edu.njit.cs.saboc.nat.generic.history.BookmarkManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class FocusConceptRightClickMenu<T extends Concept> extends AuditReportRightClickMenu<T, T> {
    
    private final NATBrowserPanel<T> mainPanel;
    
    public FocusConceptRightClickMenu(NATBrowserPanel<T> mainPanel) {
        this.mainPanel = mainPanel;
    }
    
    protected NATBrowserPanel<T> getMainPanel() {
        return mainPanel;
    }
    
    @Override
    public ArrayList<JComponent> buildRightClickMenuFor(T item) {
        
        ArrayList<JComponent> components = new ArrayList<>();
        
        if (mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            AuditSet<T> auditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();
            T focusConcept = mainPanel.getFocusConceptManager().getActiveFocusConcept();

            if(auditSet.getConcepts().contains(focusConcept)) {
                Optional<AuditResult<T>> optAuditResult = auditSet.getAuditResult(focusConcept);
                
                if(optAuditResult.isPresent()) {
                    components.add(new ConceptAuditStateMenu<>(mainPanel, item));
                }
            }
            
            JMenuItem auditCommentBtn = new JMenuItem("Edit audit comment");
            auditCommentBtn.setFont(auditCommentBtn.getFont().deriveFont(14.0f));
            auditCommentBtn.addActionListener((ae) -> {
                 AuditCommentReportDialog.displayAuditCommentDialog(mainPanel, item);
            });
           
            JMenuItem otherErrorBtn = new JMenuItem("Report other type of error");
            otherErrorBtn.setFont(otherErrorBtn.getFont().deriveFont(14.0f));
            otherErrorBtn.addActionListener((ae) -> {
                ErrorReportDialog.displayOtherErrorDialog(mainPanel);
            });
            
            components.add(auditCommentBtn);
            components.add(otherErrorBtn);

            List<OntologyError<T>> reportedChildErrors = auditSet.getAllReportedErrors(focusConcept);
                        
            if(!reportedChildErrors.isEmpty()) {
                components.add(new JSeparator());
                
                components.add(generateRemoveErrorMenu(auditSet, focusConcept, reportedChildErrors));
            }
            
            components.add(new JSeparator());
        }

       
        BookmarkManager<T> bookmarkManager = mainPanel.getBookmarkManager();
        
        JMenuItem addToBookmarks = new JMenuItem("Add Bookmark");
        addToBookmarks.addActionListener( (ae) -> {
           bookmarkManager.addBookmark(
                mainPanel.getFocusConceptManager().getActiveFocusConcept());
        });
        
        components.add(addToBookmarks);



        if (!bookmarkManager.getBookmarks().isEmpty()) {
            
            JMenu bookmarkMenu = new JMenu("Bookmarks");

            int n = bookmarkManager.getBookmarks().size();

            for (int i = 0; i < n; i++) {
                T entry = bookmarkManager.getBookmarks().get(i);

                JMenuItem bookMarkedItem = new JMenuItem(entry.getName());

                bookMarkedItem.addActionListener( (ae) -> {
                    mainPanel.getFocusConceptManager().navigateTo(entry, false);
                });

                bookmarkMenu.add(bookMarkedItem);
            }

            bookmarkMenu.add(bookmarkMenu);
            
            components.add(bookmarkMenu);
        }
        
        return components;
    }

    @Override
    public ArrayList<JComponent> buildEmptyListRightClickMenu() {        
        return new ArrayList<>();
    }
}
