package edu.njit.cs.saboc.nat.generic.gui.layout;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

/**
 *
 * @author Chris
 * @param <T>
 */
public abstract class BaseNATAdjustableLayout<T extends Concept> extends NATLayout<T> {
    
    private final JPanel leftPanel;
    private final JPanel midPanel;
    private final JPanel rightPanel;

    public BaseNATAdjustableLayout(ConceptBrowserDataSource<T> dataSource) {
        super(dataSource);
        
        this.leftPanel = new JPanel(new BorderLayout());
        this.midPanel = new JPanel(new BorderLayout());
        this.rightPanel = new JPanel(new BorderLayout());
    }
    
    @Override
    public void createLayout(NATBrowserPanel<T> mainPanel) {
        
        JSplitPane leftPane = createStyledSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        leftPane.setDividerLocation(400);
        
        leftPane.setLeftComponent(leftPanel);
        
        JSplitPane rightPane = createStyledSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        rightPane.setDividerLocation(450);
        rightPane.setLeftComponent(midPanel);
        rightPane.setRightComponent(rightPanel);
        
        leftPane.setRightComponent(rightPane);
        
        this.setLayout(new BorderLayout());

        this.add(leftPane, BorderLayout.CENTER);

        mainPanel.add(this);
    }
    
    protected void setLeftPanelContents(JComponent panel) {
        this.leftPanel.add(panel, BorderLayout.CENTER);
    }
    
    protected void setMiddlePanelContents(JComponent panel) {
        this.midPanel.add(panel, BorderLayout.CENTER);
    }
    
    protected void setRightPanelContents(JComponent panel) {
        this.rightPanel.add(panel, BorderLayout.CENTER);
    }
    
    protected JSplitPane createStyledSplitPane(int alignment) {
        JSplitPane splitPane = new JSplitPane(alignment);
        splitPane.setBorder(null);

        Optional<BasicSplitPaneDivider> divider = Optional.empty();

        for (Component c : splitPane.getComponents()) {
            if (c instanceof BasicSplitPaneDivider) {
                divider = Optional.of((BasicSplitPaneDivider) c);
                break;
            }
        }

        if (divider.isPresent()) {
            divider.get().setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            divider.get().setDividerSize(4);
        }
        
        return splitPane;
    }
}
