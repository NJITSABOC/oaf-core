/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.AggregateAncestorDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.AggregateDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.AncestorDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.DisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.ExpandedDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.OverlappingNodeDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.SimpleDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.SubsetDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregateAncestorSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregatePAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregateRootSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AncestorSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.ExpandedSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.PAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.RelationshipSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.RootSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.SimplePAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AggregateAncestorSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AggregateRootSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AggregateTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AncestorSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.ClusterTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.ExpandedSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.RootSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.SimpleClusterTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.TANFromPartitionedNodeDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.TANFromSinglyRootedNodeDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.AggregateTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.ExpandedTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.TargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.ConceptLocationDataFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.PropertyLocationDataFactory;
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
 * @param <O>
 * @param <C>
 * @param <P>
 * @param <A>
 */
public class AbNDerivationCoreParser<O extends Ontology, C extends ConceptLocationDataFactory, P extends PropertyLocationDataFactory, A extends AbNDerivationFactoryTesting> {

    private O sourceOntology;
    private C conceptFactory;
    private P propertyFactory;
    private A testing;

    public AbNDerivationCoreParser() {
    }

    public AbNDerivationCoreParser(O ontology, C conceptFactory, P propertyFactory, A testing) {
        this.sourceOntology = ontology;
        this.conceptFactory = conceptFactory;
        this.propertyFactory = propertyFactory;
        this.testing = testing;

    }

    public <T extends AbNDerivation> T coreParser(JSONArray jsonArr) throws Exception {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "ClassName");
        String className = (String) jsonObject.get("ClassName");
        T result = null;

        if (className.equalsIgnoreCase("SimplePAreaTaxonomyDerivation")) {
            result = (T) parseSimplePAreaTaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("RootSubtaxonomyDerivation")) {
            result = (T) parseRootSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("RelationshipSubtaxonomyDerivation")) {
            result = (T) parseRelationshipSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("ExpandedSubtaxonomyDerivation")) {
            result = (T) parseExpandedSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AncestorSubtaxonomyDerivation")) {
            result = (T) parseAncestorSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateRootSubtaxonomyDerivation")) {
            result = (T) parseAggregateRootSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregatePAreaTaxonomyDerivation")) {
            result = (T) parseAggregatePAreaTaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateAncestorSubtaxonomyDerivation")) {
            result = (T) parseAggregateAncestorSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("SimpleDisjointAbNDerivation")) {
            result = (T) parseSimpleDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("SubsetDisjointAbNDerivation")) {
            result = (T) parseSubsetDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("OverlappingNodeDisjointAbNDerivation")) {
            result = (T) parseOverlappingNodeDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("ExpandedDisjointAbNDerivation")) {
            result = (T) parseExpandedDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AncestorDisjointAbNDerivation")) {
            result = (T) parseAncestorDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateDisjointAbNDerivation")) {
            result = (T) parseAggregateDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateAncestorTANDerivation")) {
            result = (T) parseAggregateAncestorTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("SimpleClusterTANDerivation")) {
            result = (T) parseSimpleClusterTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("RootSubTANDerivation")) {
            result = (T) parseRootSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("ExpandedSubTANDerivation")) {
            result = (T) parseExpandedSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AncestorSubTANDerivation")) {
            result = (T) parseAncestorSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateTANDerivation")) {
            result = (T) parseAggregateTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateRootSubTANDerivation")) {
            result = (T) parseAggregateRootSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("AggregateAncestorSubTANDerivation")) {
            result = (T) parseAggregateAncestorSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("TANFromPartitionedNodeDerivation")) {
            result = (T) parseTANFromPartitionedNodeDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("TANFromSinglyRootedNodeDerivation")) {
            result = (T) parseTANFromSinglyRootedNodeDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("TargetAbNDerivation")) {

        } else if (className.equalsIgnoreCase("ExpandedTargetAbNDerivation")) {

        } else if (className.equalsIgnoreCase("AggregateTargetAbNDerivation")) {

        }

        return result;

    }

    public <T extends AbNDerivation> T coreParser(JSONArray jsonArr, O sourceOntology, C conceptFactory, P propertyFactory, A testing, T... baseDerivation) {

        JSONObject jsonObject = findJSONObjectByName(jsonArr, "ClassName");
        String className = (String) jsonObject.get("ClassName");
        T result = null;

        if (className.equalsIgnoreCase("SimplePAreaTaxonomyDerivation")) {
            result = (T) parseSimplePAreaTaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("RootSubtaxonomyDerivation")) {
            result = (T) parseRootSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("RelationshipSubtaxonomyDerivation")) {
            result = (T) parseRelationshipSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("ExpandedSubtaxonomyDerivation")) {
            result = (T) parseExpandedSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("AncestorSubtaxonomyDerivation")) {
            result = (T) parseAncestorSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("AggregateRootSubtaxonomyDerivation")) {
            result = (T) parseAggregateRootSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("AggregatePAreaTaxonomyDerivation")) {
            result = (T) parseAggregatePAreaTaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("AggregateAncestorSubtaxonomyDerivation")) {
            result = (T) parseAggregateAncestorSubtaxonomyDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("SimpleDisjointAbNDerivation")) {
            result = (T) parseSimpleDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("SubsetDisjointAbNDerivation")) {
            result = (T) parseSubsetDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("OverlappingNodeDisjointAbNDerivation")) {
            result = (T) parseOverlappingNodeDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("ExpandedDisjointAbNDerivation")) {
            result = (T) parseExpandedDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("AncestorDisjointAbNDerivation")) {
            result = (T) parseAncestorDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("AggregateDisjointAbNDerivation")) {
            result = (T) parseAggregateDisjointAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("AggregateAncestorTANDerivation")) {
            result = (T) parseAggregateAncestorTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("SimpleClusterTANDerivation")) {
            result = (T) parseSimpleClusterTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing);
        } else if (className.equalsIgnoreCase("RootSubTANDerivation")) {
            result = (T) parseRootSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("ExpandedSubTANDerivation")) {
            result = (T) parseExpandedSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("AncestorSubTANDerivation")) {
            result = (T) parseAncestorSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("AggregateTANDerivation")) {
            result = (T) parseAggregateTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("AggregateRootSubTANDerivation")) {
            result = (T) parseAggregateRootSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("AggregateAncestorSubTANDerivation")) {
            result = (T) parseAggregateAncestorSubTANDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("TANFromPartitionedNodeDerivation")) {
            result = (T) parseTANFromPartitionedNodeDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("TANFromSinglyRootedNodeDerivation")) {
            result = (T) parseTANFromSinglyRootedNodeDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("TargetAbNDerivation")) {
            result = (T) parseTargetAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("ExpandedTargetAbNDerivation")) {
            result = (T) parseExpandedTargetAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        } else if (className.equalsIgnoreCase("AggregateTargetAbNDerivation")) {
            result = (T) parseAggregateTargetAbNDerivation(jsonArr, sourceOntology, conceptFactory, propertyFactory, testing, baseDerivation);
        }

        return result;

    }

    // parea parser
    public SimplePAreaTaxonomyDerivation parseSimplePAreaTaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        Concept root = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            root = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        SimplePAreaTaxonomyDerivation result = new SimplePAreaTaxonomyDerivation(sourceOntology, root, testing.getPAreaTaxonomyFactory());
        return result;
    }

    public <T extends AbNDerivation> RootSubtaxonomyDerivation parseRootSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        PAreaTaxonomyDerivation base = null;

        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            base = (PAreaTaxonomyDerivation) baseDerivation[0];
        }

        Concept pareaRootConcept = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            pareaRootConcept = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }
        return new RootSubtaxonomyDerivation(base, pareaRootConcept);
    }

    public <T extends AbNDerivation> RelationshipSubtaxonomyDerivation parseRelationshipSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        PAreaTaxonomyDerivation base = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            base = (PAreaTaxonomyDerivation) baseDerivation[0];
        }

        Set<InheritableProperty> selectedProperties = null;
        JSONObject propertyObject = findJSONObjectByName(jsonArr, "PropertyIDs");
        ArrayList<String> arr_property = (ArrayList<String>) propertyObject.get("PropertyIDs");
        selectedProperties = propertyFactory.getPropertiesFromIds(arr_property);

        return new RelationshipSubtaxonomyDerivation(base, selectedProperties);
    }

    public <T extends AbNDerivation> ExpandedSubtaxonomyDerivation parseExpandedSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        PAreaTaxonomyDerivation base = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            base = (PAreaTaxonomyDerivation) baseDerivation[0];
        }

        Concept aggregatePAreaRoot = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            aggregatePAreaRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new ExpandedSubtaxonomyDerivation(base, aggregatePAreaRoot);
    }

    public <T extends AbNDerivation> AncestorSubtaxonomyDerivation parseAncestorSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        PAreaTaxonomyDerivation base = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            base = (PAreaTaxonomyDerivation) baseDerivation[0];
        }

        Concept pareaRootConcept = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            pareaRootConcept = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new AncestorSubtaxonomyDerivation(base, pareaRootConcept);
    }

    public <T extends AbNDerivation> AggregateRootSubtaxonomyDerivation parseAggregateRootSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {

        PAreaTaxonomyDerivation aggregateBase = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            aggregateBase = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            aggregateBase = (PAreaTaxonomyDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = ((Long) boundObject.get("Bound")).intValue();

        Concept selectedAggregatePAreaRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregatePAreaRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }
        return new AggregateRootSubtaxonomyDerivation(aggregateBase, minBound, selectedAggregatePAreaRoot);

    }

    public <T extends AbNDerivation> AggregatePAreaTaxonomyDerivation parseAggregatePAreaTaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        PAreaTaxonomyDerivation nonAggregateSourceDerivation = null;

        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            nonAggregateSourceDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            nonAggregateSourceDerivation = (PAreaTaxonomyDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int bound = ((Long) boundObject.get("Bound")).intValue();

        return new AggregatePAreaTaxonomyDerivation(nonAggregateSourceDerivation, bound);
    }

    public <T extends AbNDerivation> AggregateAncestorSubtaxonomyDerivation parseAggregateAncestorSubtaxonomyDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        PAreaTaxonomyDerivation aggregateBase = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            aggregateBase = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            aggregateBase = (PAreaTaxonomyDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = ((Long) boundObject.get("Bound")).intValue();

        Concept selectedAggregatePAreaRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregatePAreaRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }
        return new AggregateAncestorSubtaxonomyDerivation(aggregateBase, minBound, selectedAggregatePAreaRoot);
    }

    //disjoint parser
    public <T extends AbNDerivation> SimpleDisjointAbNDerivation parseSimpleDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        T parentAbNDerivation = null;

        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            parentAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            parentAbNDerivation = baseDerivation[0];
        }

        Set<Concept> root = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "RootIDs");
        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("RootIDs");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            root = newRoot;
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new SimpleDisjointAbNDerivation(testing.getDisjointPAreaAbNFactory(), parentAbNDerivation, root);
    }

    public <T extends AbNDerivation> SubsetDisjointAbNDerivation parseSubsetDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        DisjointAbNDerivation sourceDisjointAbNDerivation = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            sourceDisjointAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            sourceDisjointAbNDerivation = (DisjointAbNDerivation) baseDerivation[0];
        }

        //how to deserialize this??
        Set<SinglyRootedNode> subsSet = null;
        JSONObject rootObj = findJSONObjectByName(jsonArr, "RootNodeNames");
        ArrayList<String> rootNodeNames = (ArrayList<String>) rootObj.get("RootNodeNames");

        for (String name : rootNodeNames) {
            try {
                subsSet.addAll(sourceDisjointAbNDerivation.getAbstractionNetwork().searchNodes(name));
            } catch (Exception e) {
                System.err.println("Cannot find node: " + name + " Fail at getting node from sourceDisjointAbNDerivation!!!");
            }
        }

        SubsetDisjointAbNDerivation result = new SubsetDisjointAbNDerivation(sourceDisjointAbNDerivation, subsSet);
        return result;
    }

    public <T extends AbNDerivation> OverlappingNodeDisjointAbNDerivation parseOverlappingNodeDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        DisjointAbNDerivation sourceDisjointAbNDerivation = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            sourceDisjointAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            sourceDisjointAbNDerivation = (DisjointAbNDerivation) baseDerivation[0];
        }

        //how to deserialize this??
        SinglyRootedNode overlappingNode = null;
        JSONObject rootObj = findJSONObjectByName(jsonArr, "NodeName");
        String nodeName = (String) rootObj.get("NodeName");

        try {
            overlappingNode = (SinglyRootedNode) sourceDisjointAbNDerivation.getAbstractionNetwork().searchNodes(nodeName).iterator().next();
        } catch (Exception e) {
            System.err.println("Cannot find node: " + nodeName + " Fail at getting node from sourceDisjointAbNDerivation!!!");
        }
        return new OverlappingNodeDisjointAbNDerivation(sourceDisjointAbNDerivation, overlappingNode);
    }

    public <T extends AbNDerivation> ExpandedDisjointAbNDerivation parseExpandedDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        DisjointAbNDerivation sourceDisjointAbNDerivation = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            sourceDisjointAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            sourceDisjointAbNDerivation = (DisjointAbNDerivation) baseDerivation[0];
        }

        Concept root = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptID");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            root = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new ExpandedDisjointAbNDerivation(sourceDisjointAbNDerivation, root);
    }

    public <T extends AbNDerivation> AncestorDisjointAbNDerivation parseAncestorDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        DisjointAbNDerivation sourceDisjointAbNDerivation = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            sourceDisjointAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            sourceDisjointAbNDerivation = (DisjointAbNDerivation) baseDerivation[0];
        }

        Concept disjointNodeRoot = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptID");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            disjointNodeRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new AncestorDisjointAbNDerivation(sourceDisjointAbNDerivation, disjointNodeRoot);
    }

    public <T extends AbNDerivation> AggregateDisjointAbNDerivation parseAggregateDisjointAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        DisjointAbNDerivation nonAggregateDerivation = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            nonAggregateDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            nonAggregateDerivation = (DisjointAbNDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int aggregateBound = ((Long) boundObject.get("Bound")).intValue();

        return new AggregateDisjointAbNDerivation(nonAggregateDerivation, aggregateBound);
    }

    public <T extends AbNDerivation> AggregateAncestorDisjointAbNDerivation parseAggregateAncestorTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        DisjointAbNDerivation aggregateBase = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            aggregateBase = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            aggregateBase = (DisjointAbNDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = ((Long) boundObject.get("Bound")).intValue();

        Concept selectedAggregatePAreaRoot = null;
        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptID");
        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregatePAreaRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new AggregateAncestorDisjointAbNDerivation(aggregateBase, minBound, selectedAggregatePAreaRoot);
    }

    //Tan parser
    public SimpleClusterTANDerivation parseSimpleClusterTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing) {

        Set<Concept> root = null;

        JSONObject rootObj = findJSONObjectByName(jsonArr, "ConceptIDs");

        ArrayList<String> conceptIDs = (ArrayList<String>) rootObj.get("ConceptIDs");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(conceptIDs);
            System.out.println("conceptFactory:  " + newRoot.toString());
            root = newRoot;
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new SimpleClusterTANDerivation(root, sourceOntology, testing.getTANFactory());
    }

    public <T extends AbNDerivation> RootSubTANDerivation parseRootSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        ClusterTANDerivation base = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            base = (ClusterTANDerivation) baseDerivation[0];
        }

        Concept clusterRootConcept = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            clusterRootConcept = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new RootSubTANDerivation(base, clusterRootConcept);
    }

    public <T extends AbNDerivation> ExpandedSubTANDerivation parseExpandedSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        ClusterTANDerivation base = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            base = (ClusterTANDerivation) baseDerivation[0];
        }

        Concept aggregateClusterRoot = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            aggregateClusterRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }
        return new ExpandedSubTANDerivation(base, aggregateClusterRoot);
    }

    public <T extends AbNDerivation> AncestorSubTANDerivation parseAncestorSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        ClusterTANDerivation base = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            base = (ClusterTANDerivation) baseDerivation[0];
        }

        Concept clusterRootConcept = null;

        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            clusterRootConcept = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new AncestorSubTANDerivation(base, clusterRootConcept);
    }

    public <T extends AbNDerivation> AggregateTANDerivation parseAggregateTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        ClusterTANDerivation base = null;
        if (baseDerivation == null || baseDerivation.length == 0) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            System.out.println("base: " + baseDerivation.toString());
            base = (ClusterTANDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int bound = ((Long) boundObject.get("Bound")).intValue();

        return new AggregateTANDerivation(base, bound);
    }

    public <T extends AbNDerivation> AggregateRootSubTANDerivation parseAggregateRootSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        ClusterTANDerivation aggregateBase = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            aggregateBase = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            aggregateBase = (ClusterTANDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = ((Long) boundObject.get("Bound")).intValue();

        Concept selectedAggregateClusterRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregateClusterRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new AggregateRootSubTANDerivation(aggregateBase, minBound, selectedAggregateClusterRoot);
    }

    public <T extends AbNDerivation> AggregateAncestorSubTANDerivation parseAggregateAncestorSubTANDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        ClusterTANDerivation aggregateBase = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            aggregateBase = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            aggregateBase = (ClusterTANDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int minBound = ((Long) boundObject.get("Bound")).intValue();

        Concept selectedAggregateClusterRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            selectedAggregateClusterRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new AggregateAncestorSubTANDerivation(aggregateBase, minBound, selectedAggregateClusterRoot);
    }

    public <T extends AbNDerivation> TANFromPartitionedNodeDerivation parseTANFromPartitionedNodeDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {

        // how to deserialize this??
        AbNDerivation parentAbNDerivation = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "ParentDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("ParentDerivation");
            parentAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            parentAbNDerivation = baseDerivation[0];
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

        return new TANFromPartitionedNodeDerivation(parentAbNDerivation, testing.getTANFactory(), node);
    }

    public <T extends AbNDerivation> TANFromSinglyRootedNodeDerivation parseTANFromSinglyRootedNodeDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {

        // how to deserialize this??
        AbNDerivation parentAbNDerivation = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "ParentDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("ParentDerivation");
            parentAbNDerivation = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            parentAbNDerivation = baseDerivation[0];
        }

        Concept nodeRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            nodeRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new TANFromSinglyRootedNodeDerivation(parentAbNDerivation, testing.getTANFactory(), nodeRoot);
    }

    //Target Parser
    public <T extends AbNDerivation> TargetAbNDerivation parseTargetAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {

        Concept sourceHierarchyRoot = null;
        JSONObject sourceObject = findJSONObjectByName(jsonArr, "SourceRootID");
        String sourceConceptID = (String) sourceObject.get("SourceRootID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(sourceConceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            sourceHierarchyRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        InheritableProperty selectedProperty = null;
        JSONObject propertyObject = findJSONObjectByName(jsonArr, "PropertyID");
        String propertyID = (String) propertyObject.get("PropertyID");
        Set<InheritableProperty> foundProperty = propertyFactory.getPropertiesFromIds(new ArrayList<String>(Arrays.asList(propertyID)));
        selectedProperty = foundProperty.stream().findAny().get();

        Concept targetHierarchyRoot = null;
        JSONObject targetObject = findJSONObjectByName(jsonArr, "TargetRootID");
        String targetConceptID = (String) targetObject.get("TargetRootID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(targetConceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            targetHierarchyRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new TargetAbNDerivation(sourceOntology, testing.getTargetAbNFactory(), sourceHierarchyRoot, selectedProperty, targetHierarchyRoot);
    }

    public <T extends AbNDerivation> ExpandedTargetAbNDerivation parseExpandedTargetAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {

        TargetAbNDerivation base = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            base = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            base = (TargetAbNDerivation) baseDerivation[0];
        }

        Concept aggregateTargetGroupRoot = null;
        JSONObject conceptObject = findJSONObjectByName(jsonArr, "ConceptID");
        String conceptID = (String) conceptObject.get("ConceptID");
        try {
            Set<Concept> newRoot = conceptFactory.getConceptsFromIds(new ArrayList<String>(Arrays.asList(conceptID)));
            System.out.println("conceptFactory:  " + newRoot.toString());
            aggregateTargetGroupRoot = newRoot.stream().findAny().get();
        } catch (Exception e) {
            System.err.println("Fail at getting root from conceptFactory!!!");
            e.printStackTrace();
        }

        return new ExpandedTargetAbNDerivation(base, aggregateTargetGroupRoot);
    }

    public <T extends AbNDerivation> AggregateTargetAbNDerivation parseAggregateTargetAbNDerivation(JSONArray jsonArr, Ontology sourceOntology, ConceptLocationDataFactory conceptFactory, PropertyLocationDataFactory propertyFactory, AbNDerivationFactoryTesting testing, T... baseDerivation) {
        TargetAbNDerivation nonAggregateSource = null;
        if (baseDerivation == null) {
            JSONObject jsonObject = findJSONObjectByName(jsonArr, "BaseDerivation");
            JSONArray arr_base = (JSONArray) jsonObject.get("BaseDerivation");
            nonAggregateSource = coreParser(arr_base, (O) sourceOntology, (C) conceptFactory, (P) propertyFactory, (A) testing);
        } else {
            nonAggregateSource = (TargetAbNDerivation) baseDerivation[0];
        }

        JSONObject boundObject = findJSONObjectByName(jsonArr, "Bound");
        int bound = ((Long) boundObject.get("Bound")).intValue();

        return new AggregateTargetAbNDerivation(nonAggregateSource, bound);
    }

    public static JSONObject findJSONObjectByName(JSONArray jsonArr, String name) {

        JSONObject result = new JSONObject();

        if (jsonArr == null || jsonArr.isEmpty()) {
            return result;
        }
        JSONParser parser = new JSONParser();
        for (Object o : jsonArr) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = (JSONObject) parser.parse(o.toString());
            } catch (ParseException ex) {
                Logger.getLogger(AbNDerivationCoreParser.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (jsonObject.containsKey(name)) {
                result = jsonObject;
                break;
            }

        }

        return result;
    }
}
