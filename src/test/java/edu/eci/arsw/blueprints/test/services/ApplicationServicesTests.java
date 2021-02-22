package edu.eci.arsw.blueprints.test.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.eci.arsw.blueprints.services.BlueprintsServices;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ApplicationServicesTests {

	@Autowired
	@Qualifier("blueprintsService")
	BlueprintsServices bpt;

    @Test
    public void contextLoads() {
        
        
        
        
    }

}
