package edu.eci.arsw.blueprints.filter.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

@Component("SubsamplingBlueprintFilter")
public class SubsamplingBlueprintFilter implements BlueprintFilter{

	@Override
	public Blueprint reduceSize(Blueprint bp) {
	    ArrayList<Point> bpAux = new ArrayList<Point>();
	    for (int i=0; i< bp.getPoints().size() ; i+=2){
	    		bpAux.add(bp.getPoints().get(i));
	    	}

	    Point[] newPoints = convertToArray(bpAux);
	    return new Blueprint(bp.getAuthor(),bp.getName(),newPoints);
	}
	
	private Point[] convertToArray(ArrayList<Point> pots){
	    Point[] points = new Point[pots.size()];
	    for(int i=0; i<pots.size(); i++ ){
	        points[i] = pots.get(i);
	    }
	    return points;
	}

	@Override
	public Set<Blueprint> reduceSize(Set<Blueprint> bp) {
		Set<Blueprint> byAuthor = new HashSet<Blueprint>();
		for (Blueprint bps : bp) {
            byAuthor.add(reduceSize(bps));
        }
		return byAuthor;
	}		
}
