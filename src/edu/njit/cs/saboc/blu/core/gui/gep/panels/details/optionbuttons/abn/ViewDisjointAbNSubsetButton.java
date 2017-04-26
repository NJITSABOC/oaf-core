package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.SubsetDisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.disjointabn.DisjointAbNSubsetSelectionPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.disjointabn.DisjointAbNSubsetSelectionPanel.DeriveDisjointAbNSubsetAction;
import java.util.Set;
import javax.swing.JDialog;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class ViewDisjointAbNSubsetButton<T extends DisjointAbstractionNetwork> extends AbNOptionsButton<T> {
    
    private final DisjointAbNConfiguration config;
    
    public ViewDisjointAbNSubsetButton(DisjointAbNConfiguration config) {
        super("BluDisjointAbN.png", 
                String.format("View subset of %s", 
                        config.getTextConfiguration().getAbNName()));
        
        this.config = config;
        
        this.addActionListener( (ae) -> {
            displayDisjointAbNSubsetDialog();
        });
    }

    @Override
    public void setEnabledFor(T entity) {
        this.setEnabled(true);
    }
    
    private void displayDisjointAbNSubsetDialog() {
        
        /*
        JDialog dialog = new JDialog();

        DeriveDisjointAbNSubsetAction derivationAction = new DeriveDisjointAbNSubsetAction<SinglyRootedNode>() {

            @Override
            public void deriveSubsetDisjointAbN(Set<SinglyRootedNode> subset) {
                DisjointAbstractionNetwork parentDisjointAbN = config.getAbstractionNetwork();

                DisjointAbstractionNetwork subsetDisjointAbN = parentDisjointAbN.getSubsetDisjointAbN(subset);

                displayAction.displayDisjointAbN(subsetDisjointAbN);

                dialog.dispose();
            }

            @Override
            public void deriveCompleteDisjointAbN() {

                DisjointAbstractionNetwork disjointAbN = config.getAbstractionNetwork();

                if (disjointAbN instanceof SubsetDisjointAbstractionNetwork) {
                    SubsetDisjointAbstractionNetwork subsetDisjointAbN = (SubsetDisjointAbstractionNetwork) disjointAbN;

                    displayAction.displayDisjointAbN(subsetDisjointAbN.getSuperAbN());
                } else {
                    displayAction.displayDisjointAbN(disjointAbN);
                }
            }
        };

        DisjointAbNSubsetSelectionPanel<SinglyRootedNode> selectionPanel = new DisjointAbNSubsetSelectionPanel<>(
                parentConfig,
                derivationAction);

        dialog.setSize(1800, 600);
        dialog.add(selectionPanel);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

        selectionPanel.initialize(config.getAbstractionNetwork());
                
        */
    }
}
