package loyalty.team2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import loyalty.team2.model.Action;
import loyalty.team2.service.ActionService;

@RestController
public class ActionController {
	@Autowired
	private ActionService actionSv;
	
	@GetMapping("/action/{id}")
	public ResponseEntity<?> getOneAction(@PathVariable int id){
		return new ResponseEntity<Action>(actionSv.getOneAction(id),HttpStatus.OK);
	}
}
