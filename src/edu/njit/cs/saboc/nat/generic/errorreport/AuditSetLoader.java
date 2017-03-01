package edu.njit.cs.saboc.nat.generic.errorreport;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author CHris O
 */
public class AuditSetLoader {
    
    public static <T extends Concept> AuditSet<T> createAuditSetFromConceptIds(
            File file, ConceptBrowserDataSource<T> dataSource) throws AuditSetLoaderException {
        
        Set<String> idStrs = new HashSet<>();
        
        if(file.exists()) {
            try(Scanner scanner = new Scanner(file)) {
                
                while(scanner.hasNext()) {
                    idStrs.add(scanner.nextLine().trim());
                }
                
                Set<T> concepts = dataSource.getConceptsFromIds(idStrs);
                
                AuditSet<T> auditSet = new AuditSet<>(file.getName(), concepts);
                
                return auditSet;
                
            } catch (IOException ioe) {
                throw new AuditSetLoaderException("Error opening Concept ID file");
            }
        } else {
            throw new AuditSetLoaderException("Concept ID file not found");
        }
    }
}
