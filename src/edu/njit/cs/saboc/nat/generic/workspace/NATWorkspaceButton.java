package edu.njit.cs.saboc.nat.generic.workspace;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn.ExportAbNUtilities;
import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.toolstate.RecentlyOpenedFile;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import java.awt.Image;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class NATWorkspaceButton<T extends Concept> extends JButton {
    
    private final NATBrowserPanel<T> mainPanel;
    
    public NATWorkspaceButton(NATBrowserPanel<T> mainPanel) {
        
        this.mainPanel = mainPanel;
        
        Image scaledIcon = ImageManager.getImageManager().
                getIcon("BLUWorkspace.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        
        super.setIcon(new ImageIcon(scaledIcon));
        
        super.setToolTipText("Concept Browser Workspaces");
        
        this.addActionListener( (ae) -> {
            showWorkspaceMenu();
        });
    }
    
    public final void showWorkspaceMenu() {
        
        JPopupMenu menu = new JPopupMenu();
        
        if (mainPanel.getWorkspace().isPresent()) {
            NATWorkspace<T> workspace = mainPanel.getWorkspace().get();

            JLabel workspaceNameLabel = new JLabel(String.format(
                    "<html>Current workpace: <font color='BLUE'>%s</font>", workspace.getName()));

            workspaceNameLabel.setFont(workspaceNameLabel.getFont().deriveFont(14.0f));

            menu.add(workspaceNameLabel);

        } else {
            JLabel noWorkspaceLabel = new JLabel("No workspace loaded...");
            noWorkspaceLabel.setFont(noWorkspaceLabel.getFont().deriveFont(14.0f));
            
            menu.add(noWorkspaceLabel);
        }
        
        menu.add(new JSeparator());
        
        if (mainPanel.getDataSource().getRecentlyOpenedWorkspaces() != null) {
            if (!mainPanel.getDataSource().getRecentlyOpenedWorkspaces().getRecentlyOpenedFiles().isEmpty()) {

                ArrayList<RecentlyOpenedFile> recentWorkspaces = mainPanel.getDataSource().
                        getRecentlyOpenedWorkspaces().getRecentlyOpenedFiles(5);

                recentWorkspaces.forEach((workspaceFile) -> {

                    SimpleDateFormat dateFormatter = new SimpleDateFormat();
                    String lastOpenedStr = dateFormatter.format(workspaceFile.getDate());

                    JMenuItem item = new JMenuItem(
                            String.format("<html><font size = '4' color = 'blue'><b>%s</b></font> (Last opened: %s)",
                                    workspaceFile.getFile().getName(),
                                    lastOpenedStr));

                    item.addActionListener((ae) -> {
                        loadWorkspaceFromFile(workspaceFile.getFile());
                    });

                    menu.add(item);
                });
                
                menu.add(new JSeparator());
            }
        }

        JMenuItem saveWorkspace = new JMenuItem("Create New Workspace");
        saveWorkspace.setFont(saveWorkspace.getFont().deriveFont(14.0f));
        saveWorkspace.addActionListener((ae) -> {
            createNewWorkspace();
        });
        
        menu.add(saveWorkspace);
        
        JMenuItem loadWorkspace = new JMenuItem("Load Workspace");
        loadWorkspace.setFont(loadWorkspace.getFont().deriveFont(14.0f));
        loadWorkspace.addActionListener((ae) -> {
            loadWorkspace();
        });

        menu.add(loadWorkspace);

        menu.show(this,
                 10,
                 10);
    }
    
    private void createNewWorkspace() {
        
        Optional<String> workspaceName = promptForWorkspaceName();

        if (workspaceName.isPresent()) {

            Optional<File> workspaceFile = ExportAbNUtilities.displayFileSelectSaveDialog();

            if (workspaceFile.isPresent()) {
                
                NATWorkspace workspace = mainPanel.getWorkspaceManager().createNewWorkspace(
                        workspaceFile.get(), 
                        workspaceName.get());
                
                mainPanel.setWorkspace(workspace);
                
            } else {
                JOptionPane.showMessageDialog(mainPanel.getParentFrame(),
                        "<html>Workspace not created. No file specified.",
                        "Error Creating Workspace",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(mainPanel.getParentFrame(),
                    "<html>Workspace not created. No name specified.",
                    "Error Creating Workspace",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadWorkspace() {
        Optional<File> optFile = ExportAbNUtilities.displayFileSelectOpenDialog();
        
        if(optFile.isPresent()) {
          
           loadWorkspaceFromFile(optFile.get());
        } else {
            JOptionPane.showMessageDialog(
                    mainPanel.getParentFrame(),
                    "<html>No workspace loaded. No file specified.",
                    "Error Opening Workspace",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadWorkspaceFromFile(File file) {

        NATWorkspace workspace = mainPanel.getWorkspaceManager().getWorkspaceFor(file);
        mainPanel.getWorkspaceManager().addOrUpdateWorkspace(workspace);
        mainPanel.setWorkspace(workspace);
    }
    
    private Optional<String> promptForWorkspaceName() {
        
        String auditSetName = "";
        
        while (auditSetName != null && auditSetName.isEmpty()) {

            auditSetName = JOptionPane.showInputDialog(
                    mainPanel.getParentFrame(),
                    "Enter the name of the audit set (required)",
                    "Enter Audit Set Name",
                    JOptionPane.QUESTION_MESSAGE);
        }
        
        return Optional.ofNullable(auditSetName);
    }
    
}