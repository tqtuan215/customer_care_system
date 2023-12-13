package loyalty.team2.config;

import java.io.IOException;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class DroolsConfig {
	 @Value("${drools.rule}")
	    private String RULES_PATH;

	    @Bean
	    public KieContainer kieContainer() {
	        KieServices kieServices = KieServices.Factory.get();
	        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
	        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH));
	        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
	        kieBuilder.buildAll();
	        KieModule kieModule = kieBuilder.getKieModule();
	        return kieServices.newKieContainer(kieModule.getReleaseId());
	    }

}
