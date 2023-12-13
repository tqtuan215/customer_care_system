package loyalty.team2.controller;

import java.util.List;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import loyalty.team2.model.Purpose;
import loyalty.team2.service.PurposeService;

@RestController
public class PurposeController {
	@Autowired
	private PurposeService employeeService;

//	@GetMapping("/purpose/single")
//	public ResponseEntity<Purpose> getSinglePurpose(@RequestParam int id) {
//		Purpose p = employeeService.findPurposeById(id);
//		employeeService.rulePurpose(p);	
//		return new ResponseEntity<Purpose>(p,HttpStatus.OK);
//	}

	@GetMapping("/purpose")
	public ResponseEntity<List<Purpose>> getAllPurpose() {
		System.out.println("get all purpose: ");
		return new ResponseEntity<List<Purpose>>(employeeService.findAllPurpose(), HttpStatus.OK);
	}

	@PostMapping("/uploadPurpose")
	public ResponseEntity<Purpose> uploadPurpose(@RequestBody Purpose purpose) {
		System.out.println("UPLOAD purpose: " + purpose);
		return new ResponseEntity<Purpose>(employeeService.savePurpose(purpose), HttpStatus.OK);
	}

}
