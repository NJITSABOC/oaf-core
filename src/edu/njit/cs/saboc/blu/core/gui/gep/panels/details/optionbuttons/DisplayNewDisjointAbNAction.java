package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

public class DisplayNewDisjointAbNAction extends DisplayNewAbN{
	private final CreateDisjointAbNButton btn;
        private final PartitionedNode node;
	
	public DisplayNewDisjointAbNAction(CreateDisjointAbNButton btn, DisplayAbNAction displayAction, PartitionedNode node, String displayText){
			super(displayText, displayAction);
			this.btn = btn;
                        this.node = node;
	}
	
        @Override
	public AbstractionNetwork getAbN(){
		return btn.config.getDisjointAbstractionNetworkFor(node);
	}

}