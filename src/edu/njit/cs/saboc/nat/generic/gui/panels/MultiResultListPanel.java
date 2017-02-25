package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class MultiResultListPanel<T extends Concept> extends BaseNATPanel<T> {
    
    private final ArrayList<ResultPanel> resultPanels = new ArrayList<>();
    private final ArrayList<JRadioButton> optionButtonList = new ArrayList<>();
    
    private final ButtonGroup optionBtnGroup;
    private final JPanel optionBtnPanel;
    
    private final CardLayout contentPanelLayout;
    private final JPanel contentPanel;
    
    public MultiResultListPanel(NATBrowserPanel<T> browserPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(browserPanel, dataSource);
        
        this.optionBtnGroup = new ButtonGroup();
        this.optionBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        this.setLayout(new BorderLayout());
        
        this.add(optionBtnPanel, BorderLayout.NORTH);
        
        this.contentPanelLayout = new CardLayout();
        this.contentPanel = new JPanel(contentPanelLayout);
        
        
        this.add(contentPanel, BorderLayout.CENTER);
    }
    
    public void addResultListPanel(ResultPanel resultPanel, String optionName) {
        
        resultPanels.add(resultPanel);
        
        contentPanel.add(resultPanel, optionName);
        
        JRadioButton btn = new JRadioButton(optionName);
        btn.addActionListener( (ae) -> {
            
            resultPanel.setActive(true);
            resultPanel.reload();
            
            resultPanels.forEach( (otherPanel) -> {
               if(otherPanel != resultPanel) {
                   otherPanel.setActive(false);
               } 
            });
            
            contentPanelLayout.show(contentPanel, optionName);
        });
        
        optionBtnGroup.add(btn);
        
        if(optionButtonList.isEmpty()) {
            btn.setSelected(true);
        } else {
            resultPanel.setActive(false);
        }
        
        optionButtonList.add(btn);
        optionBtnPanel.add(btn);
        
        this.revalidate();
    }

    @Override
    protected void setFontSize(int fontSize) {
        
    }

}
