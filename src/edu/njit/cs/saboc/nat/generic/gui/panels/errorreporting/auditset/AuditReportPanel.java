package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.auditset;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.comparators.ConceptNameComparator;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditResult;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditResult.State;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AuditReportPanel<T extends Concept> extends BaseNATPanel<T> {
    
    private final JEditorPane txtAuditReport;
    
    private final NATBrowserPanel<T> mainPanel;
    private final ConceptBrowserDataSource<T> dataSource;
    
    public AuditReportPanel(NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel, dataSource);
        
        this.mainPanel = mainPanel;
        this.dataSource = dataSource;
        
        this.setLayout(new BorderLayout());
        
        this.txtAuditReport = new JEditorPane();
        this.txtAuditReport.setEditable(false);
        this.txtAuditReport.setContentType("text/html");
        
        JScrollPane scroller = new JScrollPane(txtAuditReport);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        this.add(scroller, BorderLayout.CENTER);
        
        if(mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            txtAuditReport.setText(generateAuditReport(mainPanel.getAuditDatabase().getLoadedAuditSet().get()));
            
            txtAuditReport.setSelectionStart(0);
            txtAuditReport.setSelectionEnd(0);
        } else {
            
        }
    }
    
    private String generateAuditReport(AuditSet<T> auditSet) {
        String reportText = "<html>";
        
        reportText += generateAuditReportSummaryTable(auditSet);
        reportText += "<p>";
        
        ArrayList<T> concepts = new ArrayList<>(auditSet.getConcepts());
        concepts.sort(new ConceptNameComparator());
        
        for(T concept : concepts) {
            reportText += generateConceptErrorReportTable(auditSet, concept);
            reportText += "<p>";
        }
        
        reportText += "</html>";
        
        return reportText;
        
    }
    
    private String generateAuditReportSummaryTable(AuditSet<T> auditSet) {
        String summaryTable = "<table width = '100%'>";
        
        String titleRow = String.format("<tr><td><font size = '6'><b>Audit Report</b></font></td></tr>");
        
        int conceptCount = auditSet.size();
        
        int erroneousCount = 0;
        int correctCount = 0;
        int unauditedCount = 0;
        
        int errorCount = 0;
        
        for(T concept : auditSet.getConcepts()) {
            Optional<AuditResult<T>> optAuditResult = auditSet.getAuditResult(concept);
            
            if(optAuditResult.isPresent()) {
               AuditResult<T> auditResult = optAuditResult.get();
               
               errorCount += auditResult.getErrors().size();
               
               if(auditResult.getState() == State.Correct) {
                   correctCount++;
               } else if(auditResult.getState() == State.Error) {
                   erroneousCount++;
               } else {
                   unauditedCount++;
               }
            } else {
                unauditedCount++;
            }
        }
        
        double erroneousRatio = erroneousCount / (double) conceptCount;
        double correctRatio = correctCount / (double) conceptCount;
        double unauditedRatio = unauditedCount / (double) conceptCount;
        
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMinimumFractionDigits(2);
        
        String nameRow = String.format("<tr><td width = '150'><b>Audit Set Name</b></td><td>%s</td></tr>", auditSet.getName());
        String sizeRow = String.format("<tr><td width = '150'><b>Audit Set Size</b></td><td>%d Concepts</td></tr>", conceptCount);
        
        String erroneousRow = String.format("<tr><td width = '150'><b>Erroneous Concepts</b></td><td>%d (%s)</td></tr>", 
                erroneousCount, percentFormat.format(erroneousRatio));

        String correctRow = String.format("<tr><td width = '150'><b>Correct Concepts</b></td><td>%d (%s)</td></tr>",
                correctCount, percentFormat.format(correctRatio));
        
        String unauditedRow = String.format("<tr><td width = '150'><b>Unaudited Concepts</b></td><td>%d (%s)</td></tr>",
                unauditedCount, percentFormat.format(unauditedRatio));
        
        String errorRow = String.format("<tr><td width = '150'><b>Errors</b></td><td>%d</td></tr>",
                errorCount);
        
        summaryTable += titleRow;
        summaryTable += nameRow;
        summaryTable += sizeRow;
        summaryTable += erroneousRow;
        summaryTable += correctRow;
        summaryTable += unauditedRow;
        summaryTable += errorRow;
        
        summaryTable += "</table>";
        
        return summaryTable;
    }
        
    private String generateConceptErrorReportTable(AuditSet<T> auditSet, T concept) {
        
        String result = "<table width = '100%' style = 'border: 1px solid black;'>";
        
        String nameRow = String.format("<tr><td><font size = '5'><b>%s</b></font></td></tr>", concept.getName());
        
        String auditStatusRow = String.format("<tr><td width = '150'>%s</td></tr>", dataSource.getStyledAuditStatusText(auditSet, concept));
        
        result += nameRow;
        result += auditStatusRow;
        
        Optional<AuditResult<T>> optAuditResult = auditSet.getAuditResult(concept);
        
        if(optAuditResult.isPresent()) {
            AuditResult<T> auditResult = optAuditResult.get();
            
            String auditComment;
            
            if(auditResult.getComment().isEmpty()) {
                auditComment = "[no comment]";
            } else {
                auditComment = auditResult.getComment();
            }
            
            String auditCommentRow = String.format("<tr><td><b>Audit comment: </b>%s</td></tr>", auditComment);
            
            result += auditCommentRow;
            
            if(!auditResult.getErrors().isEmpty()) {
                String errorList = "<ul>";
                
                for(OntologyError<T> error : auditResult.getErrors()) {
                    errorList += String.format("<li>%s</li>", error.getStyledText());
                }
                
                errorList += "</ul>";
                
                String errorRow = String.format("<tr><td>%s</td></tr>", errorList);
                
                result += errorRow;
            }
        }

        result += "</table>";
        
        return result;
    }
}
