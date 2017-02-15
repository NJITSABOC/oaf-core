package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons;

import edu.njit.cs.saboc.blu.core.abn.node.Node;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by atsukiima on 2/12/2017.
 */
public class HelpButton<T extends Node> extends NodeOptionButton<T> {

    public HelpButton(){
        super("BluHelp.png", "Help");
        System.out.println("check this out 1111!");
        this.addActionListener((ActionEvent ae) -> {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    System.out.println("check this out!");
                    displayDetailsWindow();
                }
            });
        });
    }

    private void displayDetailsWindow() {
        System.out.println("check this out 222!");
        JDialog detailsDialog = new JDialog();
        detailsDialog.setSize(700, 600);

//        detailsDialog.add(generatorAction.generatePanel());
        detailsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        detailsDialog.setVisible(true);
    }

    @Override
    public void setEnabledFor(T entity) {
        this.setEnabled(true);
    }
}
