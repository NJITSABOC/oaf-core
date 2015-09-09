package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.GenericParentGroupInfo;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractChildGroupTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractParentGroupTableModel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 */
public abstract class AbstractGroupHierarchyPanel<CONCEPT_T, GROUP_T extends GenericConceptGroup> extends AbNNodeInformationPanel<GROUP_T> {

    private final JSplitPane splitPane;
    
    private final AbstractEntityList<GenericParentGroupInfo<CONCEPT_T, GROUP_T>> parentGroupList;
    
    private final AbstractGroupList<GROUP_T> childGroupList;
    
    protected BLUAbstractParentGroupTableModel<CONCEPT_T, GROUP_T, GenericParentGroupInfo<CONCEPT_T, GROUP_T>> parentModel;
    
    protected BLUAbstractChildGroupTableModel<GROUP_T> childModel;

    public AbstractGroupHierarchyPanel(
           final BLUAbNConfiguration<CONCEPT_T, GROUP_T> config,
           final BLUAbstractParentGroupTableModel<CONCEPT_T, GROUP_T, GenericParentGroupInfo<CONCEPT_T, GROUP_T>> parentTableModel,
           final BLUAbstractChildGroupTableModel<GROUP_T> childTableModel) {

        this.setLayout(new BorderLayout());
        
        this.parentModel = parentTableModel;
        this.childModel = childTableModel;
        
        parentGroupList = new AbstractEntityList<GenericParentGroupInfo<CONCEPT_T, GROUP_T>>(parentTableModel) {
            public String getBorderText(Optional<ArrayList<GenericParentGroupInfo<CONCEPT_T, GROUP_T>>> entries) {
                String baseStr = String.format("Root's Parent %s %s", 
                        config.getConceptTypeName(true), 
                        config.getGroupTypeName(true));
                
                if(entries.isPresent()) {
                    return String.format("%s (%d)", baseStr, entries.get().size());
                } else {
                    return baseStr;
                }
            }
        };
        
        childGroupList = new AbstractGroupList<GROUP_T>(childTableModel) {
            public String getBorderText(Optional<ArrayList<GROUP_T>> entries) {
                String baseStr = String.format("Child %s",
                        config.getGroupTypeName(true));
                
                if(entries.isPresent()) {
                    return String.format("%s (%d)", baseStr, entries.get().size());
                } else {
                    return baseStr;
                }
            }
        };
        
        config.getGroupSelectedListeners().forEach( (EntitySelectionListener<GROUP_T> listener) -> {
            childGroupList.addEntitySelectionListener(listener);
        });
        
        splitPane = AbstractNodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        splitPane.setTopComponent(parentGroupList);
        splitPane.setBottomComponent(childGroupList);
        
        splitPane.setDividerLocation(150);

        this.add(splitPane, BorderLayout.CENTER);
    }
    
    @Override
    public void setContents(GROUP_T group) {
        loadParentGroupInfo(group);
        loadChildGroupInfo(group);
    }

    @Override
    public void clearContents() {
        parentModel.setContents(new ArrayList<>());
        childModel.setContents(new ArrayList<>());
    }   
    
    protected abstract void loadParentGroupInfo(GROUP_T group);
    protected abstract void loadChildGroupInfo(GROUP_T group);
}
