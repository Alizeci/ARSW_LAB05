/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
    }
    
    @Test
    public void getBluePrint() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("Pedro", "thejardin",pts);
        
        try {
            ibpp.getBlueprint(bp.getAuthor(),bp.getName());
        } catch (BlueprintNotFoundException ex) {
            fail("Blueprint not found");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(50, 50)};
        Blueprint bp2=new Blueprint("Pedro", "thepicapiedra",pts2);

        try {
            ibpp.getBlueprint(bp2.getAuthor(),bp2.getName());
        } catch (BlueprintNotFoundException ex) {
            fail("Blueprint not found");
        }          
        
    }
    
    @Test
    public void getBlueprintsByAuthor() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(15, 15)};
        Blueprint bp=new Blueprint("Juan", "theeci",pts);
        try {
            ibpp.getBlueprintsByAuthor(bp.getAuthor());
        } catch (BlueprintNotFoundException ex) {
            fail("Blueprint not found");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(50, 50)};
        Blueprint bp2=new Blueprint("Maria", "thepepe",pts2);

        try {
            ibpp.saveBlueprint(bp2);
            ibpp.getBlueprintsByAuthor(bp2.getAuthor());
        } catch (BlueprintNotFoundException | BlueprintPersistenceException ex) {
            fail("Blueprint not found");
        }          
        
    }
    
    @Test
    public void updatePointsTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("Wonkas", "thefactorys",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        int v2 = ibpp.getBlueprint("Wonkas", "thefactorys").getPoints().size();
        
        assertTrue(v2 == 2); 
        
        Point[] pts1=new Point[]{new Point(20, 20),new Point(5, 5),new Point(50, 50)};
        Blueprint bp1=new Blueprint("Wonkas", "thefactorys",pts1);
        ibpp.changeData("Wonkas", "thefactorys",bp1);
        
        int v = ibpp.getBlueprint("Wonkas", "thefactorys").getPoints().size();
        
        assertTrue(v == 3); 
    }
}
