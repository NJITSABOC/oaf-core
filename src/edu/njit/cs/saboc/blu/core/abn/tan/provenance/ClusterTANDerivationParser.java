/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.DisjointAbNDerivationParser;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.PAreaTaxonomyDerivationParser;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.ConceptLocationDataFactory;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author hl395
 */
public class ClusterTANDerivationParser {

    public ClusterTANDerivationParser() {
    }

    public <T extends ClusterTANDerivation> T tanParser(JSONArray jsonArr, Ontology sourceOntology, TANFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "ClassName");
        String className = (String) jsonObject.get("ClassName");
        T result = null;
        if (className.equalsIgnoreCase("SimpleClusterTANDerivation")) {
            result = (T) parseSimpleClusterTANDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("RootSubTANDerivation")) {
            result = (T) parseRootSubTANDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("ExpandedSubTANDerivation")) {
            result = (T) parseExpandedSubTANDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("AncestorSubTANDerivation")) {
            result = (T) parseAncestorSubTANDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("AggregateTANDerivation")) {
            result = (T) parseAggregateTANDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("AggregateRootSubTANDerivation")) {
            result = (T) parseAggregateRootSubTANDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("AggregateAncestorSubTANDerivation")) {
            result = (T) parseAggregateAncestorSubTANDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        }else if (className.equalsIgnoreCase("TANFromPartitionedNodeDerivation")) {
            result = (T) parseTANFromPartitionedNodeDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        }else if (className.equalsIgnoreCase("TANFromSinglyRootedNodeDerivation")) {
            result = (T) parseTANFromSinglyRootedNodeDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        }

        return result;
    }

    public SimpleClusterTANDerivation parseSimpleClusterTANDerivation(JSONArray jsonArr, Ontology sourceOntology, TANFactory factory, ConceptLocationDataFactory conceptFactory) {

        Set<Concept> root = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptIDs");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptIDs");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            root = newRoot;
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        SimpleClusterTANDerivation result = new SimpleClusterTANDerivation(root, sourceOntology, factory);
        return result;
    }

    public RootSubTANDerivation parseRootSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, TANFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        ClusterTANDerivation base = tanParser(arr_base, sourceOntology, factory, conceptFactory);

        Concept clusterRootConcept = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            clusterRootConcept = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        RootSubTANDerivation result = new RootSubTANDerivation(base, clusterRootConcept);
        return result;
    }

    public ExpandedSubTANDerivation parseExpandedSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, TANFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        ClusterTANDerivation base = tanParser(arr_base, sourceOntology, factory, conceptFactory);

        Concept aggregateClusterRoot = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            aggregateClusterRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        ExpandedSubTANDerivation result = new ExpandedSubTANDerivation(base, aggregateClusterRoot);
        return result;
    }

    public AncestorSubTANDerivation parseAncestorSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, TANFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        ClusterTANDerivation base = tanParser(arr_base, sourceOntology, factory, conceptFactory);

        Concept clusterRootConcept = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            clusterRootConcept = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        AncestorSubTANDerivation result = new AncestorSubTANDerivation(base, clusterRootConcept);
        return result;
    }

    public AggregateTANDerivation parseAggregateTANDerivation(JSONArray jsonArr, Ontology sourceOntology, TANFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        ClusterTANDerivation base = tanParser(arr_base, sourceOntology, factory, conceptFactory);

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int bound = (int) boundObject.get("Bound");

        AggregateTANDerivation result = new AggregateTANDerivation(base, bound);
        return result;
    }

    public AggregateRootSubTANDerivation parseAggregateRootSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, TANFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        ClusterTANDerivation aggregateBase = tanParser(arr_base, sourceOntology, factory, conceptFactory);

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = (int) boundObject.get("Bound");

        Concept selectedAggregateClusterRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregateClusterRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        AggregateRootSubTANDerivation result = new AggregateRootSubTANDerivation(aggregateBase, minBound, selectedAggregateClusterRoot);
        return result;
    }

    public AggregateAncestorSubTANDerivation parseAggregateAncestorSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, TANFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        ClusterTANDerivation aggregateBase = tanParser(arr_base, sourceOntology, factory, conceptFactory);

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = (int) boundObject.get("Bound");

        Concept selectedAggregateClusterRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregateClusterRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        AggregateAncestorSubTANDerivation result = new AggregateAncestorSubTANDerivation(aggregateBase, minBound, selectedAggregateClusterRoot);
        return result;
    }

    public TANFromPartitionedNodeDerivation parseTANFromPartitionedNodeDerivation(JSONArray jsonArr, Ontology sourceOntology, TANFactory factory, ConceptLocationDataFactory conceptFactory) {

        // how to deserialize this??
        AbNDerivation parentAbNDerivation = null;
        JSONObject jsonObject = findJSONObjectByName(jsonArr, "ParentDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("ParentDerivation");
        String classType = (String)arr_base.get(0);
        if (classType.contains("Disjoint")) {
            DisjointAbNDerivationParser disjointParser = new DisjointAbNDerivationParser();
            parentAbNDerivation = disjointParser.disjointParser(arr_base, sourceOntology, factory, conceptFactory);           
        }else if (classType.contains("TAN")) {
            ClusterTANDerivationParser tanParser = new ClusterTANDerivationParser();
            parentAbNDerivation = tanParser.tanParser(arr_base, sourceOntology, factory, conceptFactory);
            
        }else{
            PAreaTaxonomyDerivationParser pparser = new PAreaTaxonomyDerivationParser();
            parentAbNDerivation = pparser.coreParser(arr_base, sourceOntology, factory, conceptFactory);
        }

        // how to deserialize parentAbNderivation and node???
        PartitionedNode node = null;
        JSONObject nodeObj = findJSONObjectByName(jsonArr, "NodeName");
        String nodeName = (String) nodeObj.get("NodeName");

        try {
            node = (PartitionedNode) parentAbNDerivation.getAbstractionNetwork().searchNodes(nodeName).iterator().next();
        } catch (Exception e) {
            System.err.println("Cannot find node: " + nodeName + " Fail at getting node from sourceDisjointAbNDerivation!!!");
        }

        TANFromPartitionedNodeDerivation result = new TANFromPartitionedNodeDerivation(parentAbNDerivation, factory, node);
        return result;

    }
    
    
    
    public TANFromSinglyRootedNodeDerivation parseTANFromSinglyRootedNodeDerivation(JSONArray jsonArr, Ontology sourceOntology, TANFactory factory, ConceptLocationDataFactory conceptFactory){
    
    
        // how to deserialize this??
        AbNDerivation parentAbNDerivation =null;
        
        Concept nodeRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            nodeRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }
        
    TANFromSinglyRootedNodeDerivation result = new TANFromSinglyRootedNodeDerivation(parentAbNDerivation, factory, nodeRoot);
    return result;
    }
    
    public JSONObject findJSONObjectByName(JSONArray jsonArr, String name) {

        JSONObject result = new JSONObject();

        if (jsonArr.isEmpty()) {
            return result;
        }
        JSONParser parser = new JSONParser();
        for (Object o : jsonArr) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = (JSONObject) parser.parse(o.toString());
            } catch (ParseException ex) {
                Logger.getLogger(PAreaTaxonomyDerivationParser.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (jsonObject.containsKey(name)) {
                result = jsonObject;
                break;
            }

        }

        return result;
    }

}
