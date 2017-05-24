
package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AuditCommentPanel<T extends Concept> extends BaseNATPanel<T> {
    
    private final JEditorPane commentPane;
    
    public AuditCommentPanel(NATBrowserPanel<T> mainPanel) {
        super(mainPanel);
        
        this.setLayout(new BorderLayout());
                
        this.commentPane = new JEditorPane();
        this.commentPane.setEditable(true);
        
        this.add(new JScrollPane(commentPane));
        
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                "Comments"));
    }
        
    public String getComment() {
        return commentPane.getText();
    }
    
    public void reset() {
        commentPane.setText("");
    }
    
    public void displayComment(String comment) {
        commentPane.setText(comment);
    }
}