package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.GenericParentGroupInfo;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractChildGroupTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractParentGroupTableModel;
import edu.njit.cs.saboc.blu.core.gui.utils.renderers.MultiLineTextRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

/**
 *
 * @author Chris O
 */
public abstract class AbstractGroupHierarchyPanel<CONCEPT_T, GROUP_T extends GenericConceptGroup> extends AbNNodeInformationPanel<GROUP_T> {

    private final JSplitPane splitPane;
    
    private final JTable parentGroupTable;
    
    private final JTable childGroupTable;
    
    protected BLUAbstractParentGroupTableModel<CONCEPT_T, GROUP_T, GenericParentGroupInfo<CONCEPT_T, GROUP_T>> parentModel;
    
    protected BLUAbstractChildGroupTableModel<GROUP_T> childModel;

    public AbstractGroupHierarchyPanel() {
        this.setLayout(new BorderLayout());
        
        parentGroupTable = new JTable();
        parentGroupTable.setFont(parentGroupTable.getFont().deriveFont(Font.PLAIN, 14));
        parentGroupTable.setDefaultRenderer(String.class, new MultiLineTextRenderer());
        
        childGroupTable = new JTable();
        childGroupTable.setFont(childGroupTable.getFont().deriveFont(Font.PLAIN, 14));
        childGroupTable.setDefaultRenderer(String.class, new MultiLineTextRenderer());
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Parents"));
        
        topPanel.add(new JScrollPane(parentGroupTable), BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Children"));
        bottomPanel.add(new JScrollPane(childGroupTable), BorderLayout.CENTER);
        
        splitPane = AbstractNodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        splitPane.setTopComponent(topPanel);
        splitPane.setBottomComponent(bottomPanel);

        this.add(splitPane, BorderLayout.CENTER);
    }
    
    @Override
    public void setContents(GROUP_T group) {
        loadParentGroupInfo(group);
        loadChildGroupInfo(group);
    }

    @Override
    public void initUI() {
        parentGroupTable.setModel(parentModel = createParentGroupTableModel());
        childGroupTable.setModel(childModel = createChildGroupTableModel());
        
        splitPane.setDividerLocation(150);
    }
    
    @Override
    public void clearContents() {
        parentModel.setContents(new ArrayList<>());
        childModel.setContents(new ArrayList<>());
    }
    
    protected abstract void loadParentGroupInfo(GROUP_T group);
    protected abstract void loadChildGroupInfo(GROUP_T group);
    
    protected abstract BLUAbstractParentGroupTableModel<CONCEPT_T, GROUP_T, GenericParentGroupInfo<CONCEPT_T, GROUP_T>> createParentGroupTableModel();
    
    protected abstract BLUAbstractChildGroupTableModel<GROUP_T> createChildGroupTableModel();
}
