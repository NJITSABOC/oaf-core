package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.data.NATConceptSearchResult;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.history.FocusConceptHistory;
import edu.njit.cs.saboc.nat.generic.FocusConceptManager;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.event.UndoableEditEvent;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ToolTipManager;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 * The center panel, which displays the information about the Focus
 * Concept.
 * @param <T>
 */
public class FocusConceptPanel<T extends Concept> extends BaseNATPanel<T> {
    
    private UndoManager undoManager;
    private Document document;
    private JEditorPane jtf;
    
    private JButton backButton;
    private JButton forwardButton;
    
    private JPanel editPanel;
    private JButton undoButton;
    private JButton redoButton;
    private JButton acceptButton;
    private JButton cancelButton;

    private FocusConceptHistory<T> history;
    
    private JPanel optionsPanel;
    
    private JPanel focusConceptPanel;
    
    private ArrayList<JButton> optionButtons = new ArrayList<>();
    
    private boolean pending = false;

    public FocusConceptPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel, dataSource);

        setLayout(new BorderLayout());
        
        this.setBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.BLACK), 
                    "Focus Concept"));
        
        history = mainPanel.getFocusConceptManager().getHistory();

        undoButton = new JButton();
        undoButton.setPreferredSize(new Dimension(24, 24));
        undoButton.setMaximumSize(new Dimension(24, 24));
        undoButton.setAlignmentY(1);
        undoButton.setToolTipText("Undo Text");
        undoButton.setIcon(ImageManager.getImageManager().getIcon("undo.png"));
        
        undoButton.addActionListener( (ae) -> {
            try{
                if(jtf.isEditable()){
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
            setConcept();
        });

        cancelButton = new JButton();
        cancelButton.setPreferredSize(new Dimension(24, 24));
        cancelButton.setMaximumSize(new Dimension(24, 24));
        cancelButton.setAlignmentY(1);
        cancelButton.setToolTipText("Cancel Change");
        cancelButton.setIcon(ImageManager.getImageManager().getIcon("cross.png"));
        cancelButton.addActionListener((ae) -> {
            
            if (!pending) {
                editPanel.setVisible(false);
                display();
            }
        });

        editPanel = new JPanel( new BorderLayout() );

        JPanel tempPanel = new JPanel();

        editPanel.setVisible(false);
        
        tempPanel.add(undoButton);
        tempPanel.add(redoButton);
        tempPanel.add(acceptButton);
        tempPanel.add(cancelButton);

        editPanel.add(new JLabel("Search by Name (exact) or ID:"), BorderLayout.WEST);
        editPanel.add(tempPanel, BorderLayout.EAST);

        focusConceptPanel = new JPanel();
        focusConceptPanel.setLayout(new BorderLayout());

        backButton = new JButton("Back");
        forwardButton = new JButton("Forward");

        this.optionsPanel = new JPanel();
        this.optionsPanel.setOpaque(false);
        
        backButton.setIcon(ImageManager.getImageManager().getIcon("left-arrow.png"));
        backButton.addActionListener((ae) -> {
            if(history.getPosition() > 0) {
                history.minusPosition();
                
                mainPanel.getFocusConceptManager().navigateTo(history.getHistory().get(history.getPosition()).getConcept(), false);
                
                forwardButton.setEnabled(true);
                
                if(history.getPosition() == 0) {
                    backButton.setEnabled(false);
                }
            }
        });

        forwardButton.setIcon(ImageManager.getImageManager().getIcon("right-arrow.png"));
        forwardButton.addActionListener((ae) -> {
            
            if(history.getPosition() < (history.getHistory().size() - 1)) {
                history.plusPosition();
                
                mainPanel.getFocusConceptManager().navigateTo(history.getHistory().get(history.getPosition()).getConcept(), false);
                
                backButton.setEnabled(true);
                
                if(history.getPosition() == history.getHistory().size() - 1) {
                    forwardButton.setEnabled(false);
                }
            }
        });

        jtf = new JEditorPane() {
            
            @Override
            public String getToolTipText(MouseEvent evt) {
                
                if(!editPanel.isVisible()) {
                    
                    Rectangle conceptRect = new Rectangle(
                            jtf.getX(),
                            jtf.getY(),
                            jtf.getWidth(),
                            jtf.getPreferredSize().height);
                    
                    if(!conceptRect.contains(evt.getPoint())) {
                        return null;
                    }

                    return mainPanel.getFocusConceptManager().getActiveFocusConcept().getName();
                }

                return null;
            }

            @Override
            public Point getToolTipLocation(MouseEvent evt) {
                
                if(getToolTipText(evt) == null) {
                    return null;
                }
                
                return new Point(evt.getX(), evt.getY() + 21);
            }
        };
        
        jtf.setFont(jtf.getFont().deriveFont(Font.PLAIN, 14f));
        
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
                jtf.grabFocus();
            }

            @Override
            public void redo() {
                super.redo();
                updateUndoButtons();
                jtf.grabFocus();
            }
        };
        
        JScrollPane pane = new JScrollPane(jtf);
        ToolTipManager.sharedInstance().registerComponent(jtf);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel alignmentPanel = new JPanel(new BorderLayout());
        alignmentPanel.add(backButton, BorderLayout.WEST);
        alignmentPanel.add(forwardButton, BorderLayout.EAST);
        alignmentPanel.add(optionsPanel, BorderLayout.CENTER);

        focusConceptPanel.add(editPanel, BorderLayout.NORTH);
        focusConceptPanel.add(pane, BorderLayout.CENTER);

        add(alignmentPanel, BorderLayout.NORTH);
        add(focusConceptPanel, BorderLayout.CENTER);
        
        jtf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                
                if(!pending) {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                        setConcept();
                        e.consume();
                    } else if((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != InputEvent.CTRL_DOWN_MASK
                            && !e.isActionKey() && !jtf.isEditable()) {
                        openEditorPane();
                    } else if((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK
                            && e.getKeyCode() == KeyEvent.VK_V
                            && !jtf.isEditable()) {
                        
                        openEditorPane();
                    } else if((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK
                            && e.getKeyCode() == KeyEvent.VK_Z
                            && jtf.isEditable()) {
                        try{
                            undoManager.undo();
                        }
                        catch(CannotUndoException cue){

                        }
                    } else if((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK
                            && e.getKeyCode() == KeyEvent.VK_Y
                            && jtf.isEditable()) {
                        try{
                            undoManager.redo();
                        }
                        catch(CannotRedoException cre){

                        }
                    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE
                            && jtf.isEditable()) {
                        
                        if(!pending) {
                            editPanel.setVisible(false);
                            display();
                        }
                    }
                }
            }
        });

        jtf.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!pending && e.getClickCount() == 2) {
                    if( !jtf.isEditable() ){
                        openEditorPane();
                    } else{
                        editPanel.setVisible(false);
                        display();
                    }
                }
            }
        });
        
        jtf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (jtf.isEditable()) {
                    editPanel.setVisible(false);
                    
                    display();
                }
            }
        });
        
        
        mainPanel.getFocusConceptManager().addFocusConceptListener( (concept) -> {
            this.display();
        });
    }

    private void setConcept() {
        document = null;
        if(jtf.isEditable()) {
            doConceptChange(jtf.getText());
        }

        editPanel.setVisible(false);
        display();
    }
    
    public void addOptionButton(JButton button) {        
        optionsPanel.add(button);
        optionButtons.add(button);
    }

    public void display() {
        editPanel.setVisible(false);
        
        document = null;

        T fc = getMainPanel().getFocusConceptManager().getActiveFocusConcept();
        
        String conceptString = String.format("%s\n%s", 
                fc.getName(), 
                fc.getIDAsString());

        jtf.setText(conceptString);
        jtf.setToolTipText(conceptString);

        jtf.setCaretPosition(0);
        jtf.getCaret().setVisible(false);
        jtf.setEditable(false);
        
        undoManager.discardAllEdits();
    }

    private void doConceptChange(String str) {
        
        FocusConceptManager<T> focusConceptManager = getMainPanel().getFocusConceptManager();
        
        Optional<T> concept = getMainPanel().getDataSource().getOntology().getConceptFromID(str);

        if (concept.isPresent()) {
            getMainPanel().getFocusConceptManager().navigateTo(concept.get());
        }

        ArrayList<NATConceptSearchResult<T>> results = getMainPanel().getDataSource().searchExact(str);

        if(results.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "The concept '" + str + "' was not found.\n" +
                    "You may want to try the Search Panel.",
                    "Concept Not Found", JOptionPane.ERROR_MESSAGE);
        } else if(results.size() == 1) {
            focusConceptManager.navigateTo(results.get(0).getConcept());
        } else {
            Object sel = JOptionPane.showInputDialog(this,
                    "'" + str + "' is a synonym of more than one Concept.\n" +
                    "Which concept did you mean?", "Search Ambiguity",
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    results.toArray(), 
                    null);

            if(sel != null) {
                focusConceptManager.navigateTo(((NATConceptSearchResult<T>)sel).getConcept());
            }
        }
    }

    public void openEditorPane() {
        T activeFC = getMainPanel().getFocusConceptManager().getActiveFocusConcept();
        
        jtf.setContentType("text/plain");
        jtf.setFont(jtf.getFont().deriveFont(Font.BOLD));
        jtf.setText(activeFC.getName());
        jtf.selectAll();
        jtf.setEditable(true);
        jtf.getCaret().setVisible(true);
        
        editPanel.setVisible(true);
        
        document = jtf.getDocument();
        document.addUndoableEditListener(undoManager);
        
        updateUndoButtons();
    }

    private void validateHistoryButtons() {
        backButton.setEnabled(history.getPosition() > 0);

        if(history.getPosition() > 0) {
            backButton.setEnabled(true);
            
            T prev = history.getHistory().get(history.getPosition() - 1).getConcept();
            
            backButton.setToolTipText(prev.getName());
        } else {
            backButton.setEnabled(false);
            backButton.setToolTipText(null);
        }
        
        if(history.getPosition() < (history.getHistory().size() - 1)) {
            forwardButton.setEnabled(true);
            
            T next = history.getHistory().get(history.getPosition() + 1).getConcept();
            
            forwardButton.setToolTipText(next.getName());
        } else {
            forwardButton.setEnabled(false);
            forwardButton.setToolTipText(null);
        }
    }
    
    @Override
    protected void setFontSize(int fontSize) {
        
        if (editPanel.isEnabled()) {
            jtf.setFont(jtf.getFont().deriveFont(Font.BOLD, fontSize));
        } else {
            jtf.setFont(jtf.getFont().deriveFont(Font.PLAIN, fontSize));
        }
        
        backButton.setFont(backButton.getFont().deriveFont(Font.BOLD, fontSize));
        forwardButton.setFont(forwardButton.getFont().deriveFont(Font.BOLD, fontSize));
        
        optionButtons.forEach((JButton btn) -> {
            btn.setFont(btn.getFont().deriveFont(Font.BOLD, fontSize));
        });
    }

    public void updateUndoButtons() {
        undoButton.setEnabled(undoManager.canUndo());
        redoButton.setEnabled(undoManager.canRedo());
    }
    
    public void dataEmpty() {
        jtf.setContentType("text/plain");
        
        editPanel.setVisible(false);
        jtf.setFont(jtf.getFont().deriveFont(Font.BOLD));
        jtf.setText("Please enter a valid concept.");
    }
}
