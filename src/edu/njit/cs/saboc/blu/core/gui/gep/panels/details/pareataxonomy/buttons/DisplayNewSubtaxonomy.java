package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.DisplayNewAbN;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

public class DisplayNewSubtaxonomy extends DisplayNewAbN{
	private final CreateSubtaxonomyButton btn;
	
	public DisplayNewSubtaxonomy(CreateSubtaxonomyButton btn, DisplayAbNAction display, String displayText){
			super(displayText, display);
			this.btn = btn;
	}
	
        @Override
	public AbstractionNetwork getAbN(){
		return btn.createSubtaxonomy();
	}

}