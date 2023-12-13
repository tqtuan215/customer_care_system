package loyalty.team2.service;

import java.util.List;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import loyalty.team2.config.DroolsConfig;
import loyalty.team2.model.Purpose;
import loyalty.team2.repository.PurposeRepository;

@Service
public class PurposeService {
	@Autowired
	private PurposeRepository employeeRepo;


	public Purpose findPurposeById(Integer id) {
		
		return employeeRepo.findPurposeById(id);
	}

	public List<Purpose> findAllPurpose() {
		return employeeRepo.findAll();
	}

	public Purpose savePurpose(Purpose p) {
		return employeeRepo.save(p);
	}
	
//	public String rulePurpose(Purpose p) {
//		kieSession.insert(p);
//		kieSession.fireAllRules();
//		kieSession.dispose();
//		return "Rule applied";
//	}
	
	

}
