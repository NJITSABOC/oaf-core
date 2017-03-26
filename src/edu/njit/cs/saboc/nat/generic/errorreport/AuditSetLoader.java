package edu.njit.cs.saboc.nat.generic.errorreport;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.errorreport.error.ErrorParser;
import edu.njit.cs.saboc.nat.generic.errorreport.error.ErrorParser.ErrorParseException;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author CHris O
 */
public class AuditSetLoader {

    public static <T extends Concept> AuditSet<T> createAuditSetFromConceptIds(
            File file, ConceptBrowserDataSource<T> dataSource) throws AuditSetLoaderException {

        Set<String> idStrs = new HashSet<>();

        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {

                while (scanner.hasNext()) {
                    idStrs.add(scanner.nextLine().trim());
                }

                Set<T> concepts = dataSource.getConceptsFromIds(idStrs);

                AuditSet<T> auditSet = new AuditSet<>(
                        dataSource,
                        file.getName(),
                        concepts);

                return auditSet;

            } catch (IOException ioe) {
                throw new AuditSetLoaderException("Error opening Concept ID file");
            }
        } else {
            throw new AuditSetLoaderException("Concept ID file not found");
        }
    }

    public static <T extends Concept> AuditSet<T> createAuditSetFromJSON(
            File file, ConceptBrowserDataSource<T> dataSource) throws AuditSetLoaderException {

        String jsonStr = "";

        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                
                while(scanner.hasNext()) {
                    jsonStr += scanner.nextLine();
                }

                return parseAuditSetJSON(jsonStr, file, dataSource);

            } catch (IOException ioe) {
                throw new AuditSetLoaderException("Error opening Audit Set file");
            }
        } else {
            throw new AuditSetLoaderException("Audit Set file not found");
        }
    }
    
    private static <T extends Concept> AuditSet<T> parseAuditSetJSON(
            String str, 
            File sourceFile,
            ConceptBrowserDataSource<T> dataSource) throws AuditSetLoaderException {
        
        JSONParser parser = new JSONParser();
        
        try {
            JSONObject object = (JSONObject)parser.parse(str);
            
            if(!object.containsKey("type")) {
                throw new AuditSetLoaderException("Type not defined.");
            }
            
            String type = object.get("type").toString();
            
            if(!type.equals("AuditSet")) {
                throw new AuditSetLoaderException("Type is not Audit Set.");
            }

            if(!object.containsKey("name")) {
                 throw new AuditSetLoaderException("Audit set name not set.");
            }
            
            if(!object.containsKey("ontologyid")) {
                throw new AuditSetLoaderException("Ontology ID not set.");
            }
            
            if(!object.containsKey("creationdate")) {
                throw new AuditSetLoaderException("Creation date not set.");
            }
            
            if(!object.containsKey("lastsaveddate")) {
                throw new AuditSetLoaderException("Last save date not set.");
            }
            
            if(!object.containsKey("auditresult")) {
                throw new AuditSetLoaderException("Audit set does not contain audit result.");
            }
            
            String name = object.get("name").toString();
            String ontologyid = object.get("ontologyid").toString();
            
            Date creationDate = new Date(Long.parseLong(object.get("creationdate").toString()));
            Date lastSavedDate = new Date(Long.parseLong(object.get("lastsaveddate").toString()));
            
            JSONArray auditResultJSON = (JSONArray)object.get("auditresult");
            
            Map<T, AuditResult<T>> auditResults = new HashMap<>();
            
            Set<String> conceptIds = new HashSet<>();
            
            for(Object obj : auditResultJSON) {
                JSONObject conceptResult = (JSONObject) obj;

                if (conceptResult.containsKey("conceptid")) {
                    conceptIds.add(conceptResult.get("conceptid").toString());
                }
            }
            
            Set<T> concepts = dataSource.getConceptsFromIds(conceptIds);
            Map<String, T> conceptsById = new HashMap<>();
            
            concepts.forEach( (concept) -> {
                conceptsById.put(concept.getIDAsString().toLowerCase(), concept);
            });
            
            ErrorParser<T, InheritableProperty> errorParser = dataSource.getErrorParser();
            
            for (Object obj : auditResultJSON) {
                JSONObject conceptResult = (JSONObject) obj;

                if (!conceptResult.containsKey("conceptid")) {
                    throw new AuditSetLoaderException("Concept ID not set.");
                }
                
                T concept = conceptsById.get(conceptResult.get("conceptid").toString().toLowerCase());

                if (conceptResult.containsKey("errorreport")) {
                    JSONObject errorReport = (JSONObject) conceptResult.get("errorreport");

                    if (!errorReport.containsKey("state")) {
                        throw new AuditSetLoaderException("Error state not set.");
                    }

                    AuditResult.State state = AuditResult.State.valueOf(errorReport.get("state").toString());

                    String comment = "";

                    ArrayList<OntologyError<T>> errors = new ArrayList<>();

                    if (errorReport.containsKey("comment")) {
                        comment = errorReport.get("comment").toString();
                    }

                    if (errorReport.containsKey("errors")) {
                        JSONArray errorsJSON = (JSONArray) errorReport.get("errors");

                        for (Object errorObj : errorsJSON) {
                            JSONObject errorJSON = (JSONObject) errorObj;

                            try {
                                errors.add(errorParser.parseError(concept, errorJSON));
                            } catch(ErrorParseException epe) {
                                throw new AuditSetLoaderException("Error parsing error.");
                            }
                        }
                    }

                    auditResults.put(concept,  new AuditResult<>(state, comment, errors));
                }
            }
            
            return new AuditSet<>(
                    dataSource, 
                    Optional.of(sourceFile), 
                    name, 
                    creationDate, 
                    lastSavedDate,
                    concepts, 
                    auditResults);
            
        } catch(ParseException pe) {
            throw new AuditSetLoaderException("Error parsing JSON file.");
        }
    }
}
