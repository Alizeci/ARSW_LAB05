/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {
	
	@Autowired
	BlueprintsServices bp;
    
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllBlueprints(){
	    try {
	        //obtener datos que se enviarán a través del API
	        return new ResponseEntity<>(bp.getAllBlueprints(),HttpStatus.ACCEPTED);
	    } catch (BlueprintNotFoundException ex) {
	        Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("No hay planos.",HttpStatus.NOT_FOUND);
	    }
	}
	
	@GetMapping("/{author}")
	public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author){
	    try {
	        //obtener datos que se enviarán a través del API
	        return new ResponseEntity<>(bp.getBlueprintsByAuthor(author),HttpStatus.ACCEPTED);
	    } catch (BlueprintNotFoundException ex) {
	    	Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("No existe el autor.",HttpStatus.NOT_FOUND);
	    }
	}
	
	@GetMapping("/{author}/{bpname}")
	public ResponseEntity<?> getBlueprint(@PathVariable String author, @PathVariable String bpname){
	    try {
	        //obtener datos que se enviarán a través del API
	        return new ResponseEntity<>(bp.getBlueprint(author, bpname),HttpStatus.ACCEPTED);
	    } catch (BlueprintNotFoundException ex) {
	    	Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("No existe el plano.",HttpStatus.NOT_FOUND);
	    }
	}
	
	@RequestMapping(method = RequestMethod.POST)	
	public ResponseEntity<?> addNewBlueprint(@RequestBody Blueprint newBlueprint){
	    try {
	        //registrar dato
	    	bp.addNewBlueprint(newBlueprint);
	        return new ResponseEntity<>(HttpStatus.CREATED);
	    } catch (BlueprintPersistenceException ex) {
	        Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("No es posible agregar el plano.",HttpStatus.FORBIDDEN);            
	    }
	}
	
	@RequestMapping(value="/{author}/{bpname}", method = RequestMethod.PUT)	
	public ResponseEntity<?> updateBlueprint(@RequestBody Blueprint blueprint, @PathVariable String author, @PathVariable String bpname){
	    try {
	        //actualizar dato
	    	bp.updateBlueprint(author, bpname, blueprint);
	        return new ResponseEntity<>(HttpStatus.CREATED);
	    } catch (BlueprintNotFoundException ex) {
	        Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("No es posible encontrar el plano para realizar el cambio.",HttpStatus.NO_CONTENT);            
	    }
	}
		
}