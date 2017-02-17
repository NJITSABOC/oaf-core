package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 *
 * @author Chris O
 */
public class EditFocusConceptPanel extends JPanel {
    
    public interface EditFocusConceptListener {
        public void acceptClicked();
        public void cancelClicked();
    }
    
    private UndoManager undoManager;

    private final JButton undoButton;
    private final JButton redoButton;
    private final JButton acceptButton;
    private final JButton cancelButton;
    
    private final JEditorPane focusConceptTextPane;
    
    private final ArrayList<EditFocusConceptListener> editFocusConceptListeners = new ArrayList<>();

    public EditFocusConceptPanel(FocusConceptPanel panel, JEditorPane focusConceptTextPane) {
        
        this.focusConceptTextPane = focusConceptTextPane;
        
        this.focusConceptTextPane.addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK
                        && e.getKeyCode() == KeyEvent.VK_Z
                        && focusConceptTextPane.isEditable()) {

                    try {
                        undoManager.undo();
                    } catch (CannotUndoException cue) {

                    }
                    
                } else if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK
                        && e.getKeyCode() == KeyEvent.VK_Y
                        && focusConceptTextPane.isEditable()) {
                    
                    try {
                        undoManager.redo();
                    } catch (CannotRedoException cre) {

                    }
                }
            }
        });
        
        this.setLayout(new BorderLayout());
        
        undoButton = new JButton();
        undoButton.setPreferredSize(new Dimension(24, 24));
        undoButton.setMaximumSize(new Dimension(24, 24));
        undoButton.setAlignmentY(1);
        undoButton.setToolTipText("Undo Text");
        undoButton.setIcon(ImageManager.getImageManager().getIcon("undo.png"));
        
        undoButton.addActionListener((ae) -> {
            try{
                if(focusConceptTextPane.isEditable()){
                    undoManager.undo();
                }
            }
            catch(CannotUndoException cue){
                
            }
        });

        redoButton = new JButton();
        redoButton.setPreferredSize(new Dimension(24, 24));
        redoButton.setMaximumSize(new Dimension(24, 24));
        redoButton.setAlignmentY(1);
        redoButton.setToolTipText("Redo Text");
        redoButton.setIcon(ImageManager.getImageManager().getIcon("redo.png"));
        redoButton.addActionListener( (ae) -> {
            try{
                undoManager.redo();
            }
            catch(CannotRedoException cre){
                
            }
        });

        acceptButton = new JButton();
        acceptButton.setPreferredSize(new Dimension(24, 24));
        acceptButton.setMaximumSize(new Dimension(24, 24));
        acceptButton.setAlignmentY(1);
        acceptButton.setToolTipText("Accept Change");
        acceptButton.setIcon(ImageManager.getImageManager().getIcon("check.png"));
        acceptButton.addActionListener( (ae) -> {
            editFocusConceptListeners.forEach((listener) -> {
                listener.acceptClicked();
            });
        });

        cancelButton = new JButton();
        cancelButton.setPreferredSize(new Dimension(24, 24));
        cancelButton.setMaximumSize(new Dimension(24, 24));
        cancelButton.setAlignmentY(1);
        cancelButton.setToolTipText("Cancel Change");
        cancelButton.setIcon(ImageManager.getImageManager().getIcon("cross.png"));
        cancelButton.addActionListener((ae) -> {
            editFocusConceptListeners.forEach( (listener) -> {
               listener.cancelClicked();
            });
        });

        JPanel tempPanel = new JPanel();

        setVisible(false);
        
        tempPanel.add(undoButton);
        tempPanel.add(redoButton);
        tempPanel.add(acceptButton);
        tempPanel.add(cancelButton);

        add(new JLabel("Search by Name (exact) or ID:"), BorderLayout.WEST);
        add(tempPanel, BorderLayout.EAST);
        
        
        undoManager = new UndoManager() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                super.undoableEditHappened(e);

                updateUndoButtons();
            }

            @Override
            public void undo() {
                super.undo();

                updateUndoButtons();
                focusConceptTextPane.grabFocus();
            }

            @Override
            public void redo() {
                super.redo();

                updateUndoButtons();
                focusConceptTextPane.grabFocus();
            }
        };
    }
    
    public void addEditFocusConceptListener(EditFocusConceptListener listener) {
        this.editFocusConceptListeners.add(listener);
    }
    
    public void removeEditFocusConceptListener(EditFocusConceptListener listener) {
        this.editFocusConceptListeners.remove(listener);
    }
    
    public void update() {
        this.focusConceptTextPane.getDocument().addUndoableEditListener(undoManager);
    }
    
    public void clearEdits() {
        this.undoManager.discardAllEdits();
    }
    
    public void updateUndoButtons() {
        undoButton.setEnabled(undoManager.canUndo());
        redoButton.setEnabled(undoManager.canRedo());
    }
}
