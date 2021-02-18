/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.bluesprints.services;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.bluesprints.model.Blueprint;
import edu.eci.arsw.bluesprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.bluesprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.bluesprints.persistence.BlueprintsPersistence;

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
    
    public Set<Blueprint> getAllBlueprints(){
        return null;
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException, NullPointerException{
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
    
}
