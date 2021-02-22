/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {
   
    @Autowired
    @Qualifier("InMemoryBlueprintPersistence")
    BlueprintsPersistence bpp;
    
    @Autowired
    @Qualifier("SubsamplingBlueprintFilter")
    BlueprintFilter rf;
    
    /**
     * 
     * @param bp blueprint
     * @throws BlueprintPersistenceException if there is no such blueprint
     */
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException{
    	bpp.saveBlueprint(bp);
        
    }
    
    /**
     * @return set of all the blueprints
     * @throws BlueprintNotFoundException if there are no such blueprints
     */
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException{
        return bpp.getAllBlueprints();
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException {
        return rf.reduceSize(bpp.getBlueprint(author, name));
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
    	return rf.reduceSize(bpp.getBlueprintsByAuthor(author)); 
    }

    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @param bp blueprint
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
	public void updateBlueprint(String author, String bpname, Blueprint blueprint) throws BlueprintNotFoundException {
		bpp.changeData(author,bpname,blueprint);
	}
    
}
