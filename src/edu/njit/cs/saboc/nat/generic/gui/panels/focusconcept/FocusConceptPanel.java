package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.rightclickmanager.EntityRightClickManager;
import edu.njit.cs.saboc.blu.core.utils.rightclickmanager.EntityRightClickMenuGenerator;
import edu.njit.cs.saboc.nat.generic.data.NATConceptSearchResult;
import edu.njit.cs.saboc.nat.generic.history.FocusConceptHistory;
import edu.njit.cs.saboc.nat.generic.FocusConceptManager;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.EditFocusConceptPanel.EditFocusConceptListener;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.linkeddata.GoogleSearchConfig;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.linkeddata.OpenBrowserButton;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.linkeddata.PubMedSearchConfig;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.linkeddata.WikipediaSearchConfig;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu.FocusConceptRightClickMenu;
import edu.njit.cs.saboc.nat.generic.workspace.NATWorkspaceButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ToolTipManager;

/**
 * A panel for displaying details about the current focus concept
 * 
 * @param <T>
 */
public class FocusConceptPanel<T extends Concept> extends BaseNATPanel<T> {
    
    private JEditorPane jtf;
    
    private JButton backButton;
    private JButton forwardButton;
    
    private EditFocusConceptPanel editFocusConceptPanel;

    private FocusConceptHistory<T> history;
    
    private JPanel optionsPanel;
    
    private JPanel focusConceptPanel;
    
    private final ArrayList<JButton> optionButtons = new ArrayList<>();
        
    private boolean pending = false;
    
    private final EntityRightClickManager<T> rightClickManager = new EntityRightClickManager<>();

    public FocusConceptPanel(NATBrowserPanel<T> mainPanel) {
        
        super(mainPanel);

        setLayout(new BorderLayout());
        
        this.setBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.BLACK), 
                    "Focus Concept"));
        
        this.setMinimumSize(new Dimension(-1, 150));
        
        history = mainPanel.getFocusConceptManager().getHistory();


        focusConceptPanel = new JPanel();
        focusConceptPanel.setLayout(new BorderLayout());

        backButton = new JButton();
        forwardButton = new JButton();

        this.optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.optionsPanel.setOpaque(false);
        
        backButton.setIcon(ImageManager.getImageManager().getIcon("left-arrow.png"));
        backButton.addActionListener((ae) -> {
            
            if(history.getPosition() > 0) {
                history.historyBack();
                
                mainPanel.getFocusConceptManager().navigateTo(history.getHistory().get(history.getPosition()).getConcept(), false);
                
                history.addNavigationHistory(history.getHistory().get(history.getPosition()).getConcept());
                
                forwardButton.setEnabled(true);
                
                if(history.getPosition() == 0) {
                    backButton.setEnabled(false);
                }
            }
        });
        
        backButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON3){
                    showRecentHistoryMenu(history, e, mainPanel);
                }
            }
        });
        
        forwardButton.setIcon(ImageManager.getImageManager().getIcon("right-arrow.png"));
        forwardButton.addActionListener((ae) -> {
            
            if(history.getPosition() < (history.getHistory().size() - 1)) {
                history.historyForward();
                
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
                
                if(!editFocusConceptPanel.isVisible()) {
                    
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
        
        editFocusConceptPanel = new EditFocusConceptPanel(this, jtf);
        editFocusConceptPanel.addEditFocusConceptListener(new EditFocusConceptListener() {

            @Override
            public void acceptClicked() {
                 setConcept();
            }

            @Override
            public void cancelClicked() {
                if (!pending) {
                    setVisible(false);
                    display();
                }
            }
        });

        jtf.setFont(jtf.getFont().deriveFont(Font.PLAIN, 14f));
        
        JScrollPane pane = new JScrollPane(jtf);
        ToolTipManager.sharedInstance().registerComponent(jtf);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel alignmentPanel = new JPanel(new BorderLayout());
        alignmentPanel.add(backButton, BorderLayout.WEST);
        alignmentPanel.add(forwardButton, BorderLayout.EAST);
        alignmentPanel.add(optionsPanel, BorderLayout.CENTER);

        focusConceptPanel.add(editFocusConceptPanel, BorderLayout.NORTH);
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
                        
                    } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE
                            && jtf.isEditable()) {
                        
                        if(!pending) {
                            editFocusConceptPanel.setVisible(false);
                            display();
                        }
                    }
                }
            }
        });

        jtf.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!pending && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    
                    if(!jtf.isEditable() ){
                        openEditorPane();
                    } else{
                        editFocusConceptPanel.setVisible(false);
                        
                        display();
                    }
                }
                
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (mainPanel.getDataSource().isPresent()) {
                        rightClickManager.setRightClickedItem(mainPanel.getFocusConceptManager().getActiveFocusConcept());
                        rightClickManager.showPopup(e);
                    }
                }
            }
        });
        
        jtf.setContentType("text/html");
        
        mainPanel.getFocusConceptManager().addFocusConceptListener( (concept) -> {
            display();
        });
        
        this.addOptionButton(new OpenBrowserButton(mainPanel, new GoogleSearchConfig()));
        this.addOptionButton(new OpenBrowserButton(mainPanel, new WikipediaSearchConfig()));
        this.addOptionButton(new OpenBrowserButton(mainPanel, new PubMedSearchConfig()));
        
        this.addOptionButton(new NATWorkspaceButton(mainPanel));
        
        this.setRightClickMenuGenerator(new FocusConceptRightClickMenu(mainPanel));
    }

    private void setConcept() {
        
        if(jtf.isEditable()) {
            doConceptChange(jtf.getText());
        }

        editFocusConceptPanel.setVisible(false);
        display();
    }
    
    public final void addOptionButton(JButton button) {        
        optionsPanel.add(button);
        optionButtons.add(button);
    }

    public void display() {
        
        if(!getMainPanel().getDataSource().isPresent()) {
            return;
        }
        
        jtf.setContentType("text/html");
        
        editFocusConceptPanel.setVisible(false);

        T fc = getMainPanel().getFocusConceptManager().getActiveFocusConcept();
        
        String conceptString = getMainPanel().getDataSource().get().getFocusConceptText(fc);
                
        jtf.setText(conceptString);
        
        jtf.setToolTipText(conceptString);

        jtf.setCaretPosition(0);
        jtf.getCaret().setVisible(false);
        jtf.setEditable(false);
        
        editFocusConceptPanel.clearEdits();
    }

    private void doConceptChange(String str) {
        
        if(!getMainPanel().getDataSource().isPresent()) {
            return;
        }
        
        NATBrowserPanel<T> mainPanel = this.getMainPanel();
        
        FocusConceptManager<T> focusConceptManager = mainPanel.getFocusConceptManager();
        
        Optional<T> concept = mainPanel.getDataSource().get().getOntology().getConceptFromID(str);

        if (concept.isPresent()) {
            focusConceptManager.navigateTo(concept.get());
        }

        ArrayList<NATConceptSearchResult<T>> results = mainPanel.getDataSource().get().searchExact(str);

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
        
        editFocusConceptPanel.setVisible(true);
        editFocusConceptPanel.update();
        editFocusConceptPanel.updateUndoButtons();
    }

    public void dataEmpty() {
        jtf.setContentType("text/html");
        
        editFocusConceptPanel.setVisible(false);
        jtf.setFont(jtf.getFont().deriveFont(Font.BOLD));
        jtf.setText("Please enter a valid concept.");
    }
    
    public void showRecentHistoryMenu(
            FocusConceptHistory<T> history,
            MouseEvent e, 
            NATBrowserPanel<T> mainPanel) {
        
        JPopupMenu popup = new JPopupMenu();
        
        Set<T> recentHistorySet = new HashSet<>();

        int count = history.getHistory().size();
        int lastEntryIdx = count - 1;
        int i = 0;
        
        final int MAX_RECENT_HISTORY = 10;
        
        while ( i < count && recentHistorySet.size() < MAX_RECENT_HISTORY) {
            int idx = lastEntryIdx - i;
            
            T concept = history.getHistory().get(idx).getConcept();
            
            if (recentHistorySet.contains(concept)) {
                i++;
                
                continue;
            } else {
                recentHistorySet.add(concept);
            }
            
            JMenuItem conceptMenuItem = new JMenuItem(concept.getName());

            conceptMenuItem.addActionListener((ae) -> {
                mainPanel.getFocusConceptManager().navigateTo(
                        history.getHistory().get(idx).getConcept(), false);
            });

            popup.add(conceptMenuItem);
            i++;
        }

        popup.show(e.getComponent(), e.getX(), e.getY());
    }

    public final void setRightClickMenuGenerator(EntityRightClickMenuGenerator<T> generator) {
        rightClickManager.setMenuGenerator(generator);
    }
}
