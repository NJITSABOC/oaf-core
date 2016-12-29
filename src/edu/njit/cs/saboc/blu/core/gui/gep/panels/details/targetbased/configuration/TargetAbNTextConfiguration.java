package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class TargetAbNTextConfiguration implements AbNTextConfiguration<TargetGroup> {
    
    private final TargetAbstractionNetwork targetAbN;
    
    public TargetAbNTextConfiguration(TargetAbstractionNetwork taxonomy) {
        this.targetAbN = taxonomy;
    }
    
    public TargetAbstractionNetwork getTargetAbN() {
        return targetAbN;
    }

    @Override
    public String getAbNName() {
        return "Target Abstraction Network";
    }

    @Override
    public String getAbNSummary() {
       return "*** TARGET ABN SUMMARY TEXT ***";
    }
    
    @Override
    public String getAbNHelpDescription() {
        return "*** TARGET ABN HELP DESCRIPTION ***";
    }

    @Override
    public String getAbNTypeName(boolean plural) {
        if(plural) {
            return "Target Abstraction Networks";
        } else {
            return "Target Abstraction Network";
        }
    }

    @Override
    public String getNodeTypeName(boolean plural) {
        if(plural) {
            return "Target Groups";
        } else {
            return "Target Group";
        }
    }
}
