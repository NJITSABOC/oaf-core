package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

public class DisplayNewDisjointAbNAction extends DisplayNewAbN{
	private final PartitionedAbNConfiguration config;
        private final PartitionedNode node;
	
	public DisplayNewDisjointAbNAction(PartitionedAbNConfiguration config, DisplayAbNAction displayAction, PartitionedNode node, String displayText){
			super(displayText, displayAction);
			this.config = config;
                        this.node = node;
	}
	
        @Override
	public AbstractionNetwork getAbN(){
		return config.getDisjointAbstractionNetworkFor(node);
	}

}