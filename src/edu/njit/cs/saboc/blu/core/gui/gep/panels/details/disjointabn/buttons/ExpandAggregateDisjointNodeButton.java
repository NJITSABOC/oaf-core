
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.buttons;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.AggregateDisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.AggregateDisjointNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.CreateAndDisplayAbNThread;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.ExpandAggregateButton;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public class ExpandAggregateDisjointNodeButton<T extends AggregateDisjointNode> extends ExpandAggregateButton<T> {
    
    private final DisplayAbNAction<AggregateDisjointAbstractionNetwork> disjointDisplayAction;
    private final DisjointAbNConfiguration config;

    public ExpandAggregateDisjointNodeButton(DisjointAbNConfiguration config, 
            DisplayAbNAction<AggregateDisjointAbstractionNetwork> disjointDisplayAction) {
        
        super(config.getTextConfiguration().getAbNTypeName(false), 
                config.getTextConfiguration().getNodeTypeName(false));
        
        this.disjointDisplayAction = disjointDisplayAction;
        this.config = config;
    }

    @Override
    public void expandAggregateAction() {
        if (getCurrentNode().isPresent()) {
            CreateAndDisplayAbNThread display = new CreateAndDisplayAbNThread(
                    String.format("Expanding %s...", getCurrentNode().get().getName()), 
                    disjointDisplayAction) {

                @Override
                public AbstractionNetwork getAbN() {
                    AggregateDisjointAbstractionNetwork disjointAbN = (AggregateDisjointAbstractionNetwork)config.getAbstractionNetwork();
                    
                    return disjointAbN.getExpandedDisjointAbN(getCurrentNode().get());
                }
            };

            display.startThread();
        }
    }

    @Override
    public void setEnabledFor(T node) {
        this.setEnabled(node.getAggregatedNodes().size() > 0);
    }
}
