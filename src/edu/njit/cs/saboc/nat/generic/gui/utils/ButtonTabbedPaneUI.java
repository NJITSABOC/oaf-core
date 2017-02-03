package edu.njit.cs.saboc.nat.generic.gui.utils;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

public class ButtonTabbedPaneUI extends MetalTabbedPaneUI {
    
    private final ButtonTabbedPaneUI thisUI = this;
    private final EventListenerList listenerList = new EventListenerList();
    
    private boolean recursing = false;

    private final ArrayList<TabButton> tabButtons = new ArrayList<>();

    public void addActionListener(ActionListener l) {
        listenerList.add(ActionListener.class, l);
    }

    @Override
    protected LayoutManager createLayoutManager() {
        return new ButtonTabbedPaneLayout();
    }

    protected Insets getButtonlessTabInsets(int tabIndex) {
        return super.getTabInsets(0, tabIndex);
    }

    @Override
    protected Insets getTabInsets(int tabPlacement, int tabIndex) {
        
        if(recursing) {
            return super.getTabInsets(tabPlacement, tabIndex);
        }
        
        recursing = true;
        int defaultHeight = calculateTabHeight(tabPlacement, tabIndex, getFontMetrics().getHeight());
        recursing = false;

        ensureFilterButtonsExist();

        TabButton thisFilterButton = tabButtons.get(tabIndex);

        int bufferX = (thisFilterButton.getPreferredSize().width
                + thisFilterButton.getPadding().left
                + thisFilterButton.getPadding().right);

        int heightDiff = thisFilterButton.getPreferredSize().height
                + thisFilterButton.getPadding().top
                + thisFilterButton.getPadding().bottom
                - defaultHeight + 2;
        
        // Note that the insets that are returned to us are not copies.
        Insets defaultInsets = (Insets)super.getTabInsets(tabPlacement, tabIndex).clone();
        defaultInsets.right = bufferX + 40;

        if(heightDiff > 0) {
            defaultInsets.top += heightDiff / 2;
            defaultInsets.bottom += heightDiff / 2 + heightDiff % 2;
        }

        return defaultInsets;
    }

    @Override
    protected void layoutLabel(int tabPlacement, FontMetrics metrics,
            int tabIndex, String title, Icon icon, Rectangle tabRect,
            Rectangle iconRect, Rectangle textRect, boolean isSelected) {

        TabButton thisFilterButton = tabButtons.get(tabIndex);

        int bufferX = (thisFilterButton.getPreferredSize().width
                + thisFilterButton.getPadding().left
                + thisFilterButton.getPadding().right);

        Rectangle newTabRect = new Rectangle(tabRect);
        newTabRect.width -= bufferX;
        super.layoutLabel(tabPlacement, metrics, tabIndex, title, icon, newTabRect, iconRect, textRect, isSelected);
    }

    protected TabButton createTabButton(int tabIndex) {
        return new TabButton(tabIndex);
    }

    protected TabButton createPopoutTabButton(int tabIndex) {
        return new TabButton(tabIndex);
    }

    protected TabButton createFilterTabButton(int tabIndex) {
        return new TabButton(tabIndex);
    }

    public JButton getTabFilterButton(int index) {
        ensureFilterButtonsExist();
        return tabButtons.get(index);
    }

    void ensureFilterButtonsExist() {
        while(tabPane.getTabCount() > tabButtons.size()) {
            tabButtons.add(createFilterTabButton(tabButtons.size()));
        }
    }

    public JButton [] getFilterTabButtons() {
        ensureFilterButtonsExist();
        
        JButton [] ret = new JButton[1];
        ret = tabButtons.toArray(ret);
        
        return ret;
    }

    private class ButtonTabbedPaneLayout extends TabbedPaneLayout {
        
        @Override
        public void layoutContainer(Container parent) {
            super.layoutContainer(parent);

            Rectangle rect = new Rectangle();
            
            int i;
            
            for(i = 0; i < tabPane.getTabCount(); i++) {
                rect = getTabBounds(i, rect);

                TabButton filterButton = tabButtons.get(i);
                Dimension d = filterButton.getPreferredSize();
                Insets padding = filterButton.getPadding();

                filterButton.setLocation(rect.x + rect.width - d.width - padding.right - 20, rect.y + padding.top + 3);
                filterButton.setSize(d);
                tabPane.add(filterButton);
            }

            for(; i < tabButtons.size(); i++) {
                //remove any extra close buttons
                tabPane.remove(tabButtons.get(i));
            }
        }
    }

    protected class TabButton extends JButton implements UIResource {
        
        private final int index;
        private Insets padding = new Insets(0, 0, 0, 0);

        public TabButton(int index) {
            super(new TabButtonAction(index));
            this.index = index;

            //remove the typical padding for the button
            setMargin(new Insets(0, 0, 0, 0));
            setPreferredSize(new Dimension(16, 16));
        }

        public int getIndex() {
            return index;
        }

        public Insets getPadding() {
            return padding;
        }

        public void setPadding(Insets i) {
            padding = i;
        }
    }

    private class TabButtonAction extends AbstractAction {
        int index;

        public TabButtonAction(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ActionListener[] listeners =
                    (listenerList.getListeners(ActionListener.class));

            ActionEvent e2 = new ActionEvent(thisUI, index, e.getActionCommand());
            
            for(ActionListener l : listeners) {
                l.actionPerformed(e2);
            }
        }
    }
}
