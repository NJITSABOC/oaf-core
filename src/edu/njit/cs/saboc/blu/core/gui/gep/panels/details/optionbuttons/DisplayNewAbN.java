package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.dialogs.LoadStatusDialog;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;
import javax.swing.SwingUtilities;

public abstract class DisplayNewAbN{
	protected String displayText;
        protected DisplayAbNAction displayAction;
	
	public DisplayNewAbN(String displayText, DisplayAbNAction displayAction){
		this.displayText = displayText;
                this.displayAction = displayAction;
	}
	
	public void startThread(){
		Thread loadThread = new Thread(new Runnable() {

			private LoadStatusDialog loadStatusDialog = null;
			private boolean doLoad = true;

			public void run() {
				
				loadStatusDialog = LoadStatusDialog.display(null,
						displayText, 
						( ) -> {
							doLoad = false;
				});

				//ClusterTribalAbstractionNetwork tan = deriveTAN();
				AbstractionNetwork abn = getAbN();
				
				SwingUtilities.invokeLater(() -> {
					if (doLoad) {
						displayAction.displayAbstractionNetwork(abn);

						loadStatusDialog.setVisible(false);
						loadStatusDialog.dispose();
					}
				});
			}
		});

		loadThread.start();
	
	}
	
	public abstract AbstractionNetwork getAbN();

}