package loyalty.team2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import loyalty.team2.model.FinalAction;
import loyalty.team2.service.FinalActionService;

@RestController
public class FinalActionController {
	@Autowired
	private FinalActionService finalAcSv;
	
	@GetMapping("/finalaction/{id}")	
	public ResponseEntity<?> getOneFinalAction(@PathVariable int id){
		return new ResponseEntity<FinalAction>(finalAcSv.getOneFinalAction(id),HttpStatus.OK);
	}
}
