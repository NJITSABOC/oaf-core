package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.IconManager;
import edu.njit.cs.saboc.nat.generic.data.BrowserSearchResult;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.History;
import edu.njit.cs.saboc.nat.generic.NATOptions;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ToolTipManager;
import javax.swing.border.LineBorder;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 * The center panel, which displays the information about the Focus
 * Concept.
 */
public class FocusConceptPanel<T> extends BaseNavPanel<T> {
    private UndoManager undoManager;
    private Document document;
    private JEditorPane jtf;
    boolean pending = false;

    private JButton backButton;
    private JButton forwardButton;
    
    private JPanel editPanel;
    private JButton undoButton;
    private JButton redoButton;
    private JButton acceptButton;
    private JButton cancelButton;

    private History<T> history;
    
    private JPanel optionsPanel;
    
    private JPanel focusConceptPanel;
    
    private ArrayList<JButton> optionButtons = new ArrayList<>();

    public FocusConceptPanel(final GenericNATBrowser<T> mainPanel, ConceptBrowserDataSource<T> dataSource) {
        super(mainPanel, dataSource);
        
        NATOptions options = mainPanel.getOptions();

        Color bgColor = mainPanel.getNeighborhoodBGColor();
        setLayout(new BorderLayout());
        setBackground(bgColor);
        focusConcept.addDisplayPanel(mainPanel.getFocusConcept().COMMON_DATA_FIELDS.CONCEPT, this);

        history = focusConcept.getHistory();

        undoButton = new JButton();
        undoButton.setBackground(bgColor);
        undoButton.setPreferredSize(new Dimension(24, 24));
        undoButton.setMaximumSize(new Dimension(24, 24));
        undoButton.setAlignmentY(1);
        undoButton.setToolTipText("Undo Text");
        undoButton.setIcon(IconManager.getIconManager().getIcon("undo.png"));
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                try{
                    if(jtf.isEditable()){
                        undoManager.undo();
                    }
                }
                catch(CannotUndoException cue){

                }
            }
        });

        redoButton = new JButton();
        redoButton.setBackground(bgColor);
        redoButton.setPreferredSize(new Dimension(24, 24));
        redoButton.setMaximumSize(new Dimension(24, 24));
        redoButton.setAlignmentY(1);
        redoButton.setToolTipText("Redo Text");
        redoButton.setIcon(IconManager.getIconManager().getIcon("redo.png"));
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                try{
                    undoManager.redo();
                }
                catch(CannotRedoException cre){

                }
            }
        });

        acceptButton = new JButton();
        acceptButton.setBackground(bgColor);
        acceptButton.setPreferredSize(new Dimension(24, 24));
        acceptButton.setMaximumSize(new Dimension(24, 24));
        acceptButton.setAlignmentY(1);
        acceptButton.setToolTipText("Accept Change");
        acceptButton.setIcon(IconManager.getIconManager().getIcon("check.png"));
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                setConcept();
            }
        });

        cancelButton = new JButton();
        cancelButton.setBackground(bgColor);
        cancelButton.setPreferredSize(new Dimension(24, 24));
        cancelButton.setMaximumSize(new Dimension(24, 24));
        cancelButton.setAlignmentY(1);
        cancelButton.setToolTipText("Cancel Change");
        cancelButton.setIcon(IconManager.getIconManager().getIcon("cross.png"));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                if(!pending) {
                    editPanel.setVisible(false);
                    display();
                }
            }
        });

        editPanel = new JPanel( new BorderLayout() );

        JPanel tempPanel = new JPanel();

        editPanel.setVisible(false);
        editPanel.setBackground(bgColor);
        tempPanel.setBackground(bgColor);
        tempPanel.add(undoButton);
        tempPanel.add(redoButton);
        tempPanel.add(acceptButton);
        tempPanel.add(cancelButton);

        editPanel.add(new JLabel("     Enter Concept Name or CUI:"),
                BorderLayout.WEST);
        editPanel.add(tempPanel, BorderLayout.EAST);

        focusConceptPanel = new JPanel();
        focusConceptPanel.setLayout(new BorderLayout());
        focusConceptPanel.setBackground(bgColor);
        
        focusConceptPanel.setBorder(BaseNavPanel.createTitledLineBorder("Focus Concept", options.getFontSize()));
       
        backButton = new JButton("Back");
        forwardButton = new JButton("Forward");

        this.optionsPanel = new JPanel();
        this.optionsPanel.setOpaque(false);
        
        backButton.setIcon(IconManager.getIconManager().getIcon("left-arrow.png"));
        backButton.setBackground(mainPanel.getNeighborhoodBGColor());
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(history.getPosition() > 0) {
                    history.minusPosition();
                    focusConcept.navigate(history.getHistoryList().get(history.getPosition()));

                    forwardButton.setEnabled(true);

                    if(history.getPosition() == 0) {
                        backButton.setEnabled(false);
                    }
                }
            }
        });

        forwardButton.setIcon(IconManager.getIconManager().getIcon("right-arrow.png"));
        forwardButton.setBackground(mainPanel.getNeighborhoodBGColor());
        forwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(history.getPosition() < (history.getHistoryList().size() - 1)) {
                    history.plusPosition();
                    focusConcept.navigate(history.getHistoryList().get(history.getPosition()));

                    backButton.setEnabled(true);

                    if(history.getPosition() == history.getHistoryList().size() - 1) {
                        forwardButton.setEnabled(false);
                    }
                }
            }
        });

        jtf = new JEditorPane() {
            @Override
            public String getToolTipText(MouseEvent evt) {
                if(!editPanel.isVisible()) {
                    Rectangle conceptRect = new Rectangle(jtf.getX(),
                            jtf.getY(),jtf.getWidth(),jtf.getPreferredSize().height);
                    if(!conceptRect.contains(evt.getPoint())) {
                        return null;
                    }

                    return focusConcept.getConceptName();
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
        alignmentPanel.setBackground(mainPanel.getNeighborhoodBGColor());
        alignmentPanel.add(backButton, BorderLayout.WEST);
        alignmentPanel.add(forwardButton, BorderLayout.EAST);
        ImageIcon arrow = IconManager.getIconManager().getIcon("arrow.gif");        
        
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
                    }
                    else if((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != InputEvent.CTRL_DOWN_MASK
                            && !e.isActionKey() && !jtf.isEditable()) {
                        openEditorPane();
                    }
                    //this happens before the default paste behavior, so the paste
                    //will occur into an editable area on its own
                    else if((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK
                            && e.getKeyCode() == KeyEvent.VK_V
                            && !jtf.isEditable()) {
                        
                        openEditorPane();
                    }
                    else if((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK
                            && e.getKeyCode() == KeyEvent.VK_Z
                            && jtf.isEditable()) {
                        try{
                            undoManager.undo();
                        }
                        catch(CannotUndoException cue){

                        }
                    }
                    else if((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK
                            && e.getKeyCode() == KeyEvent.VK_Y
                            && jtf.isEditable()) {
                        try{
                            undoManager.redo();
                        }
                        catch(CannotRedoException cre){

                        }
                    }
                    else if(e.getKeyCode() == KeyEvent.VK_ESCAPE
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
                    }
                    else{
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
    }

    private void setConcept(){
        document = null;
        if(jtf.isEditable()) {
            doConceptChange(jtf.getText());
        }

        editPanel.setVisible(false);
        display();
    }
    
    public void addOptionButton(JButton button) {
        button.setBackground(mainPanel.getNeighborhoodBGColor());
        
        button.setFont(button.getFont().deriveFont(Font.BOLD, mainPanel.getOptions().getFontSize()));
        optionsPanel.add(button);
        optionButtons.add(button);
    }

    public void display() {
        NATOptions options = mainPanel.getOptions();
        
        // When the focus concept is changed, hide the edit panel.
        editPanel.setVisible(false);
        document = null;

        T fc = focusConcept.getConcept();
        
        String conceptString = String.format("%s\n%s", dataSource.getConceptName(fc), dataSource.getConceptId(fc));

        jtf.setText(conceptString);
        jtf.setToolTipText(conceptString);

        jtf.setCaretPosition(0);
        jtf.getCaret().setVisible(false);
        jtf.setEditable(false);
        undoManager.discardAllEdits();
    }

    private void doConceptChange(String str) {
        
        Optional<T> c = dataSource.getConceptFromId(str);

        if (c.isPresent()) {
            focusConcept.navigate(c.get());
        }

        ArrayList<BrowserSearchResult<T>> results = dataSource.searchExact(str);

        if(results.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "The concept '" + str + "' was not found.\n" +
                    "You may want to try the Search Panel.",
                    "Concept Not Found", JOptionPane.ERROR_MESSAGE);
        }
        else if(results.size() == 1) {
            focusConcept.navigate(results.get(0).getConcept());
        }
        else {
            Object sel = JOptionPane.showInputDialog(this,
                    "'" + str + "' is a synonym of more than one Concept.\n" +
                    "Which concept did you mean?", "Search Ambiguity",
                    JOptionPane.QUESTION_MESSAGE, null, results.toArray(), null);

            if(sel != null) {
                focusConcept.navigate(((BrowserSearchResult<T>)sel).getConcept());
            }
        }
    }

    @Override
    public void dataPending() {
        editPanel.setEnabled(false);
    }

    public void openEditorPane() {
        jtf.setContentType("text/plain");
        jtf.setFont(jtf.getFont().deriveFont(Font.BOLD));
        jtf.setText(focusConcept.getConceptName());
        jtf.selectAll();
        jtf.setEditable(true);
        editPanel.setVisible(true);
        jtf.getCaret().setVisible(true);
        document = jtf.getDocument();
        document.addUndoableEditListener(undoManager);
        updateUndoButtons();
    }

    private void validateHistoryButtons() {
        backButton.setEnabled(history.getPosition() > 0);

        if(history.getPosition() > 0) {
            backButton.setEnabled(true);
            
            T prev = history.getHistoryList().get(history.getPosition() - 1);
            
            
            backButton.setToolTipText(dataSource.getConceptName(prev));
        } else {
            backButton.setEnabled(false);
            backButton.setToolTipText(null);
        }
        
        if(history.getPosition() < (history.getHistoryList().size() - 1)) {
            forwardButton.setEnabled(true);
            
            T prev = history.getHistoryList().get(history.getPosition() + 1);
            
            
            forwardButton.setToolTipText(dataSource.getConceptName(prev));
        } else {
            forwardButton.setEnabled(false);
            forwardButton.setToolTipText(null);
        }
    }
    
    protected void setFontSize(int fontSize) {
        
        focusConceptPanel.setBorder(BaseNavPanel.createTitledLineBorder("Focus Concept", fontSize));

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

    @Override
    public void dataReady() {
        display();
        validateHistoryButtons();
    }

    public void dataEmpty() {
        jtf.setContentType("text/plain");
        editPanel.setVisible(false);
        jtf.setFont(jtf.getFont().deriveFont(Font.BOLD));
        jtf.setText("Please enter a valid concept.");
    }
    
    public void focusConceptChanged() {

    }

    public void updateUndoButtons() {
        undoButton.setEnabled(undoManager.canUndo());
        redoButton.setEnabled(undoManager.canRedo());
    }
}
