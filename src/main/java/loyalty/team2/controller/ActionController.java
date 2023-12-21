package loyalty.team2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import loyalty.team2.model.Action;
import loyalty.team2.service.ActionService;

@RestController
@RequestMapping("/action/")
public class ActionController {
	@Autowired
	private ActionService actionSv;
	
	@GetMapping("{id}")
	public ResponseEntity<?> getOneAction(@PathVariable int id){
		return new ResponseEntity<Action>(actionSv.getOneAction(id),HttpStatus.OK);
	}
	
	@GetMapping("all")
	public ResponseEntity<?> getAllAction() {
		return new ResponseEntity<List<Action>>(actionSv.getAllAction(), HttpStatus.OK);
	}
}
