/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

/**
 *
 * @author hcadavid
 */
@Component("InMemoryBlueprintPersistence")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final ConcurrentHashMap<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap<>();

	public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("Pedro", "thepicapiedra",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        
        Point[] pts2=new Point[]{new Point(150, 150),new Point(15, 15), new Point(100, 100)};
        Blueprint bp2=new Blueprint("Pedro", "thejardin",pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        
        Point[] pts3=new Point[]{new Point(4, 4),new Point(50, 50),new Point(40, 40),new Point(70, 70)};
        Blueprint bp3=new Blueprint("Juan", "theeci",pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);   
        
        Point[] pts4=new Point[]{new Point(10, 10),new Point(11, 11)};
        Blueprint bp4=new Blueprint("Wonka", "thefactory",pts4);
        blueprints.put(new Tuple<>(bp4.getAuthor(),bp4.getName()), bp4);
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
    	
    	if ((blueprints.get(new Tuple<>(author, bprintname)) == null)) {
    		throw new BlueprintNotFoundException("The author "+author+" & name "+bprintname+" not exist.");
    	}
        return blueprints.get(new Tuple<>(author, bprintname));
    }
    
    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
    	Set<Blueprint> bluePrintsByAuthor = new HashSet<Blueprint>();
    	
    	for (Tuple<String,String> bps : blueprints.keySet()) {
			if (bps.getElem1().equals(author)) {
				bluePrintsByAuthor.add(blueprints.get(bps));
			}
		}
    	
    	if (bluePrintsByAuthor.isEmpty()) {
    		throw new BlueprintNotFoundException("The author "+author+" not exist.");
    	}
    	return bluePrintsByAuthor;
    }
    
    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
    	Set<Blueprint> allBlueprints = new HashSet<Blueprint>();
	    
    	if(blueprints.isEmpty()) {
			throw new BlueprintNotFoundException("The blueprints Stub is empty");
		} else {
	    	for (Tuple<String,String> bps : blueprints.keySet()) {
	    		allBlueprints.add(blueprints.get(bps));
	    	}
		}
		return allBlueprints;
	}

	@Override
	public void changeData(String author, String bpname, Blueprint blueprint) throws BlueprintNotFoundException {
		List<Point> newPoints = blueprint.getPoints();
		for (Tuple<String,String> bps : blueprints.keySet()) {
			if (bps.getElem1().equals(author) && bps.getElem2().equals(bpname) ) {
				blueprints.get(bps).setPoints(newPoints);
			}
		}
	}
}
