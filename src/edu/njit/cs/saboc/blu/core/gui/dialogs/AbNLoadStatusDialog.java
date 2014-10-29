package edu.njit.cs.saboc.blu.core.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author Chris
 */
public class AbNLoadStatusDialog extends JDialog {
    
    public static void display(final JFrame parentFrame, final AbNLoadingStatusMonitor statusMonitor, final String type) {
        new AbNLoadStatusDialog(parentFrame, statusMonitor, type);
    }
    
    public static final String TYPE_PAREA = "Partial-area Taxonomy";
    public static final String TYPE_CLUSTER = "Tribal Abstraction Network";

    private class AbNLoadMonitorTask extends SwingWorker {

        private AbNLoadingStatusMonitor loadMonitor;

        public AbNLoadMonitorTask(AbNLoadingStatusMonitor stateMonitor) {
            this.loadMonitor = stateMonitor;
        }

        public Void doInBackground() {

            setProgress(0);
            
            while (loadMonitor.getPercentComplete() < 100) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {
                    
                }

                setProgress(loadMonitor.getPercentComplete());
            }
            
            AbNLoadStatusDialog.this.setVisible(false);
            AbNLoadStatusDialog.this.dispose();

            return null;
        }
    }
    
    private JProgressBar loadProgressBar;
    
    private AbNLoadStatusDialog(JFrame parentFrame, final AbNLoadingStatusMonitor statusMonitor, String type) {
        super(parentFrame, true);
        
        this.setTitle(type + " Load Status");
        
        loadProgressBar = new JProgressBar(0, 100);
        loadProgressBar.setStringPainted(true);
        
        this.setSize(256, 128);
        
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.add(new JLabel(type + " creation status:"), BorderLayout.NORTH);
        
        dialogPanel.add(loadProgressBar, BorderLayout.CENTER);
        
        this.add(dialogPanel);

        this.setLocationRelativeTo(parentFrame);
        this.setResizable(false);
        this.setModal(true);
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                
            }
        });
        
        AbNLoadMonitorTask task = new AbNLoadMonitorTask(statusMonitor);

        task.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent pce) {
                loadProgressBar.setValue(statusMonitor.getPercentComplete());
                loadProgressBar.setString(statusMonitor.getStateName());
            }
        });

        task.execute();

        this.setVisible(true);
    }
}
