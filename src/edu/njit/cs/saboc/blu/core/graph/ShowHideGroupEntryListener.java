package edu.njit.cs.saboc.blu.core.graph;

import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

/**
 *
 * @author William
 */
public class ShowHideGroupEntryListener implements ActionListener {

    private final AbstractionNetworkGraph graph;

    public ShowHideGroupEntryListener(AbstractionNetworkGraph graph) {
        this.graph = graph;
    }
    
    public void actionPerformed(ActionEvent e) {
        GenericPartitionEntry currentPartitionEntry = graph.getCurrentPartitionEntry();
        JPopupMenu regionMenu = graph.getPartitionMenu();
        JDialog customDialog;

        regionMenu.setVisible(false);
        currentPartitionEntry.setBackground(currentPartitionEntry.getParent().getBackground());
        customDialog = createCustomJDialog();

        customDialog.setVisible(true);
        customDialog.requestFocusInWindow();
    }

    private JDialog createCustomJDialog() {

        final JDialog customDialog = new JDialog();
        
        final JList visibleGroupList = new JList();
        final JList hiddenGroupList = new JList();

        final JButton moveToVisible = new JButton("<");
        final JButton moveToHidden = new JButton(">");
        final JButton confirmChanges = new JButton("Apply Changes");
        final JButton cancelChanges = new JButton("Cancel");

        JScrollPane visibleListScroll = new JScrollPane(visibleGroupList);
        JScrollPane hiddenListScroll = new JScrollPane(hiddenGroupList);
        
        final DefaultListModel visibleListModel = new DefaultListModel();
        final DefaultListModel hiddenListModel = new DefaultListModel();
        
        JLabel visibleListLabel = new JLabel("Visible PAreas");
        JLabel hiddenListLabel = new JLabel("Hidden PAreas");

        GenericPartitionEntry currentPartitionEntry = graph.getCurrentPartitionEntry();

        final ArrayList<SinglyRootedNodeEntry> visibleGroups = (ArrayList<SinglyRootedNodeEntry>)currentPartitionEntry.getVisibleGroups().clone();
        final ArrayList<SinglyRootedNodeEntry> hiddenGroups = (ArrayList<SinglyRootedNodeEntry>)currentPartitionEntry.getHiddenGroups().clone();
        
        for(SinglyRootedNodeEntry group : visibleGroups) {
            visibleListModel.addElement(String.format("%s (%d)", 
                    group.getNode().getRoot().getName(), group.getNode().getConceptCount()));
        }

        for (SinglyRootedNodeEntry parea : hiddenGroups) {
            hiddenListModel.addElement(String.format("%s (%d)",
                    parea.getNode().getRoot().getName(), parea.getNode().getConceptCount()));
        }

        // Setup the styling of the lists
        visibleGroupList.setModel(visibleListModel);
        hiddenGroupList.setModel(hiddenListModel);

        visibleGroupList.add(new JScrollPane());
        hiddenGroupList.add(new JScrollPane());

        visibleGroupList.setBorder(BorderFactory.createLoweredBevelBorder());
        hiddenGroupList.setBorder(BorderFactory.createLoweredBevelBorder());

        customDialog.setLayout(null);
        customDialog.setFocusable(true);

        //Set up the action listeners for relevant objects in the dialog
        moveToVisible.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int toMove = hiddenGroupList.getSelectedIndex();

                while (toMove >= 0) {
                    visibleListModel.addElement(hiddenListModel.get(toMove));
                    hiddenListModel.remove(toMove);
                    toMove = hiddenGroupList.getSelectedIndex();
                }
            }
        });

        moveToHidden.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int toMove = visibleGroupList.getSelectedIndex();
                while (toMove >= 0) {
                    hiddenListModel.addElement(visibleListModel.get(toMove));
                    visibleListModel.remove(toMove);
                    toMove = visibleGroupList.getSelectedIndex();
                }
            }
        });

        cancelChanges.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                customDialog.setVisible(false);
            }
        });

        confirmChanges.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GenericPartitionEntry currentPartitionEntry = graph.getCurrentPartitionEntry();

                for(int c = 0; c < visibleListModel.size(); c++) {
                    String entry = (String)visibleListModel.getElementAt(c);

                    for(int p = hiddenGroups.size() - 1; p >= 0; p--) {
                        SinglyRootedNodeEntry parea = hiddenGroups.get(p);
                        
                        if(entry.startsWith(parea.getNode().getRoot().getName())) {
                            visibleGroups.add(parea);
                            parea.setVisible(true);
                            hiddenGroups.remove(p);
                        }
                    }
                }

                for (int c = 0; c < hiddenListModel.size(); c++) {
                    String entry = (String) hiddenListModel.getElementAt(c);

                    for (int p = visibleGroups.size() - 1; p >= 0; p--) {
                        SinglyRootedNodeEntry parea = visibleGroups.get(p);
                        if (entry.startsWith(parea.getNode().getRoot().getName())) {
                            hiddenGroups.add(parea);
                            visibleGroups.remove(p);
                            parea.hideIncidentEdges();
                            parea.setVisible(false);
                            parea.getParentContainer().remove(parea);
                        }
                    }
                }

                Collections.sort(visibleGroups, new Comparator<SinglyRootedNodeEntry>() {
                    public int compare(SinglyRootedNodeEntry a, SinglyRootedNodeEntry b) {
                        Integer aCount = a.getNode().getConceptCount();
                        Integer bCount = b.getNode().getConceptCount();
                        
                        return -aCount.compareTo(bCount);
                    }
                });

                currentPartitionEntry.getVisibleGroups().clear();
                currentPartitionEntry.getVisibleGroups().addAll(visibleGroups);

                currentPartitionEntry.getHiddenGroups().clear();
                currentPartitionEntry.getHiddenGroups().addAll(hiddenGroups);

                int cols = (int) Math.ceil(Math.sqrt(visibleGroups.size()));
                int rows = cols;

                int spaces = cols * cols;

                if (spaces >= visibleGroups.size() + cols) {
                    rows = cols - 1;
                }

                graph.getGraphLayout().resizePartitionEntry(currentPartitionEntry, rows, cols, visibleGroups);

                customDialog.setVisible(false);
            }
        });

        customDialog.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                
            }

            public void focusLost(FocusEvent e) {
                graph.requestFocusInWindow();
            }
        });

        //Center the dialog on the screen.
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        customDialog.setBounds(dim.width / 4, dim.height / 4, dim.width / 2,dim.height / 2);

        //The height and width of the dialog are divided into 64ths for dynamic sizing.
        visibleListLabel.setBounds((customDialog.getWidth() / 64), (customDialog.getWidth() / 64), (customDialog.getWidth() * 27) / 64, (customDialog.getHeight() * 2 / 64));
        hiddenListLabel.setBounds((customDialog.getWidth() * 36) / 64, (customDialog.getWidth()) / 64, (customDialog.getWidth() * 27) / 64, (customDialog.getHeight() * 2 / 64));
        visibleListScroll.setBounds((customDialog.getWidth()) / 64 , (customDialog.getHeight() * 4) / 64, (customDialog.getWidth() * 27) / 64, (customDialog.getHeight() * 50) / 64);
        hiddenListScroll.setBounds((customDialog.getWidth() * 36) / 64, (customDialog.getHeight() * 4) / 64, (customDialog.getWidth() * 27) / 64, (customDialog.getHeight() * 50) / 64);
        moveToHidden.setBounds((customDialog.getWidth() * 29) / 64, (customDialog.getHeight() * 26) / 64, (customDialog.getWidth() * 6) / 64, (customDialog.getHeight() * 3) / 64);
        moveToVisible.setBounds((customDialog.getWidth() * 29) / 64, (customDialog.getHeight() * 30) / 64, (customDialog.getWidth() * 6) / 64, (customDialog.getHeight() * 3) / 64);
        cancelChanges.setBounds((customDialog.getWidth() * 31) / 64, (customDialog.getHeight() * 55) / 64, (customDialog.getWidth() * 10) / 64, (customDialog.getHeight() * 4) / 64);
        confirmChanges.setBounds((customDialog.getWidth() * 44) / 64, (customDialog.getHeight() * 55) / 64, (customDialog.getWidth() * 16) / 64, (customDialog.getHeight() * 4) / 64);
        
        //The objects are all set up, add them to the container dialog
        customDialog.setTitle("Show or Hide PAreas");
        
        customDialog.add(visibleListLabel);
        customDialog.add(hiddenListLabel);
        customDialog.add(moveToVisible);
        customDialog.add(moveToHidden);
        customDialog.add(confirmChanges);
        customDialog.add(cancelChanges);
        customDialog.add(visibleListScroll);
        customDialog.add(hiddenListScroll);

        return customDialog;
    }

}