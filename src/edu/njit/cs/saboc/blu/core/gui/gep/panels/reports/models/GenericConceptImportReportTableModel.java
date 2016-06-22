package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ImportedConceptGroupReport;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class GenericConceptImportReportTableModel<GROUP_T extends GenericConceptGroup, 
        CONCEPT_T> extends OAFAbstractTableModel<ImportedConceptGroupReport<GROUP_T, CONCEPT_T>> {

    private final BLUConfiguration config;

    public GenericConceptImportReportTableModel(BLUConfiguration config) {
        super(new String [] {
            String.format("%s ID", config.getTextConfiguration().getConceptTypeName(false)),
            String.format("%s Name", config.getTextConfiguration().getConceptTypeName(false)),
            String.format("# %s", config.getTextConfiguration().getNodeTypeName(true)), 
            config.getTextConfiguration().getNodeTypeName(true)
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(ImportedConceptGroupReport<GROUP_T, CONCEPT_T> item) {
        ArrayList<String> groupNames = new ArrayList<>();
        
        item.getGroups().forEach( (GROUP_T group) -> {
            groupNames.add(String.format("%s (%d)", config.getTextConfiguration().getGroupName(group), group.getConceptCount()));
        });
        
        Collections.sort(groupNames);
        
        String groupsStr;
        
        if (!groupNames.isEmpty()) {
            groupsStr = groupNames.get(0);

            for (int c = 1; c < groupNames.size(); c++) {
                groupsStr += "\n" + groupNames.get(c);
            }
        } else {
            groupsStr = "(none)";
        }
        
        return new Object[] {
            config.getTextConfiguration().getConceptUniqueIdentifier(item.getConcept()),
            config.getTextConfiguration().getConceptName(item.getConcept()),
            item.getGroups().size(),
            groupsStr
        };
    }
}
