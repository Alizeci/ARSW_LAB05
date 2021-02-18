package edu.eci.arsw.blueprints.filter;

import java.util.Set;

import edu.eci.arsw.bluesprints.model.Blueprint;

public interface BlueprintFilter {
	/**
     * 
     * @param bp the blueprint
     * @return A filtered blueprint
     */
    public Blueprint reduceSize(Blueprint bp);
    
    /**
     * 
     * @param blueprints The set of blueprints to filter
     * @return A filtered set of blueprints
     */
    public Set<Blueprint> reduceSize(Set<Blueprint> bp);

}
