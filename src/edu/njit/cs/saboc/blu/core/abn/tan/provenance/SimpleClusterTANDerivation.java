
package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.blu.core.utils.comparators.ConceptNameComparator;
import java.util.ArrayList;
import java.util.Set;

/**
 * Stores the arguments needed to create a TAN for a selected set of ontology
 * subhierarchies
 * 
 * @author Chris O
 */
public class SimpleClusterTANDerivation extends ClusterTANDerivation {
    
    private final Set<Concept> patriarchs;
    
    public SimpleClusterTANDerivation(Set<Concept> patriarchs, Ontology sourceOntology, TANFactory factory) {
        super(sourceOntology, factory);
        
        this.patriarchs = patriarchs;
    }
    
    public SimpleClusterTANDerivation(SimpleClusterTANDerivation base) {
        this(base.getPatriarchs(), base.getSourceOntology(), base.getFactory());
    }
            
    public Set<Concept> getPatriarchs() {
        return patriarchs;
    }
    
    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();

        ClusterTribalAbstractionNetwork tan = generator.deriveTANFromMultiRootedHierarchy(
                super.getSourceOntology().getConceptHierarchy().getSubhierarchyRootedAt(patriarchs), super.getFactory());

        return tan;
    }

    @Override
    public String getDescription() {
        return String.format("Derived tribal abstraction network");
    }

    @Override
    public String getName() {
        ArrayList<Concept> sortedPatriarchs = new ArrayList<>(this.getPatriarchs());
        sortedPatriarchs.sort(new ConceptNameComparator());
        
        String name;
        
        if(sortedPatriarchs.size() < 4) {
            name = sortedPatriarchs.toString().substring(1, sortedPatriarchs.size() - 1);
        } else {
            name = sortedPatriarchs.get(0).getName();
            
            for(int c = 1; c <= 2; c++) {
                name += (", " + sortedPatriarchs.get(c));
            }
            
            name += ", ...";
        }
        
        name = String.format("{%s}", name);
        
        return String.format("%s %s", name, getAbstractionNetworkTypeName());
    }
}
