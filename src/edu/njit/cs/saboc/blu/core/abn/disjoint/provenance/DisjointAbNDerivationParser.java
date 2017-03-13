/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.PAreaTaxonomyDerivationParser;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.ConceptLocationDataFactory;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.AccessibleRole;
import javax.swing.Action;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author hl395
 */
public class DisjointAbNDerivationParser {

    public DisjointAbNDerivationParser() {
    }

    public <T extends DisjointAbNDerivation> T disjointParser(JSONArray jsonArr, Ontology sourceOntology, DisjointAbNFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "ClassName");
        String className = (String) jsonObject.get("ClassName");
        T result = null;
        if (className.equalsIgnoreCase("SimpleDisjointAbNDerivation")) {
            result = (T) parseSimpleDisjointAbNDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("SubsetDisjointAbNDerivation")) {
            result = (T) parseSubsetDisjointAbNDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("OverlappingNodeDisjointAbNDerivation")) {
            result = (T) parseOverlappingNodeDisjointAbNDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("ExpandedDisjointAbNDerivation")) {
            result = (T) parseExpandedDisjointAbNDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("AncestorDisjointAbNDerivation")) {
            result = (T) parseAncestorDisjointAbNDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("AggregateDisjointAbNDerivation")) {
            result = (T) parseAggregateDisjointAbNDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        } else if (className.equalsIgnoreCase("AggregateAncestorTANDerivation")) {
            result = (T) parseAggregateAncestorTANDerivation(jsonArr, sourceOntology, factory, conceptFactory);
        }

        return result;

    }

    public DisjointAbNDerivation parseSimpleDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, DisjointAbNFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        DisjointAbNDerivation parentAbNDerivation = disjointParser(arr_base, sourceOntology, factory, conceptFactory);

        Set<Concept> root = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "RootIDs");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("RootIDs");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            root = newRoot;
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }
        SimpleDisjointAbNDerivation result = new SimpleDisjointAbNDerivation(factory, parentAbNDerivation, root);
        return result;
    }

    public SubsetDisjointAbNDerivation parseSubsetDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, DisjointAbNFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        DisjointAbNDerivation sourceDisjointAbNDerivation = disjointParser(arr_base, sourceOntology, factory, conceptFactory);

        //how to deserialize this??
        Set<SinglyRootedNode> subsSet = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "RootNodeNames");

        ArrayList<String> rootNodeNames = (ArrayList<String>) rootObj.get("RootNodeNames");

        rootNodeNames.forEach((String name) -> {
            try {
                subsSet.addAll(sourceDisjointAbNDerivation.getAbstractionNetwork().searchNodes(name));
            } catch (Exception e) {
                System.err.println("Cannot find node: " + name +" Fail at getting node from sourceDisjointAbNDerivation!!!");
            }
        });

        SubsetDisjointAbNDerivation result = new SubsetDisjointAbNDerivation(sourceDisjointAbNDerivation, subsSet);
        return result;
    }

    public OverlappingNodeDisjointAbNDerivation parseOverlappingNodeDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, DisjointAbNFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        DisjointAbNDerivation sourceDisjointAbNDerivation = disjointParser(arr_base, sourceOntology, factory, conceptFactory);

        //how to deserialize this??
        SinglyRootedNode overlappingNode = null;
      
        JSONObject rootObj = findJSONObjectByName(jsonArr, "NodeName");

        String nodeName = (String)rootObj.get("NodeName");

            try {
                overlappingNode = (SinglyRootedNode)sourceDisjointAbNDerivation.getAbstractionNetwork().searchNodes(nodeName).iterator().next();
            } catch (Exception e) {
                System.err.println("Cannot find node: " + nodeName +" Fail at getting node from sourceDisjointAbNDerivation!!!");
            }


        OverlappingNodeDisjointAbNDerivation result = new OverlappingNodeDisjointAbNDerivation(sourceDisjointAbNDerivation, overlappingNode);
        return result;
    }

    public ExpandedDisjointAbNDerivation parseExpandedDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, DisjointAbNFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        DisjointAbNDerivation sourceDisjointAbNDerivation = disjointParser(arr_base, sourceOntology, factory, conceptFactory);

        Concept root = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptID");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            root = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        ExpandedDisjointAbNDerivation result = new ExpandedDisjointAbNDerivation(sourceDisjointAbNDerivation, root);
        return result;
    }

    public AncestorDisjointAbNDerivation parseAncestorDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, DisjointAbNFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        DisjointAbNDerivation sourceDisjointAbNDerivation = disjointParser(arr_base, sourceOntology, factory, conceptFactory);

        Concept disjointNodeRoot = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptID");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            disjointNodeRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        AncestorDisjointAbNDerivation result = new AncestorDisjointAbNDerivation(sourceDisjointAbNDerivation, disjointNodeRoot);
        return result;
    }

    public AggregateDisjointAbNDerivation parseAggregateDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, DisjointAbNFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        DisjointAbNDerivation nonAggregateDerivation = disjointParser(arr_base, sourceOntology, factory, conceptFactory);

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int aggregateBound = (int) boundObject.get("Bound");

        AggregateDisjointAbNDerivation result = new AggregateDisjointAbNDerivation(nonAggregateDerivation, aggregateBound);
        return result;
    }

    public AggregateAncestorDisjointAbNDerivation parseAggregateAncestorTANDerivation(JSONArray jsonArr, Ontology sourceOntology, DisjointAbNFactory factory, ConceptLocationDataFactory conceptFactory) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
        JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
        DisjointAbNDerivation aggregateBase = disjointParser(arr_base, sourceOntology, factory, conceptFactory);

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = (int) boundObject.get("Bound");

        Concept selectedAggregatePAreaRoot = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptID");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregatePAreaRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
        }

        AggregateAncestorDisjointAbNDerivation result = new AggregateAncestorDisjointAbNDerivation(aggregateBase, minBound, selectedAggregatePAreaRoot);
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
