package loyalty.team2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import loyalty.team2.model.CustomerAttribute;
import loyalty.team2.model.Node;
import loyalty.team2.service.CustomerAttributeService;
import loyalty.team2.service.RuleService;

@RestController
public class RuleController {

	@Autowired
	private RuleService ruleSv;
	@Autowired
	private CustomerAttributeService cusAttSv;

	@GetMapping("/rule/{id}")
	public ResponseEntity<?> duyet1CustomerQuaRule(@PathVariable Integer id) {
		System.out.println("rule is being checked for" + id);
		if (ruleSv.recommend(id))
			return new ResponseEntity<String>("rule applied", HttpStatus.OK);
		else
			return new ResponseEntity<String>("no rule applied", HttpStatus.OK);

	}

	@GetMapping("/rule")
	public ResponseEntity<?> duyetRule() {
		System.out.println("rule is being checked for all");
		if (ruleSv.recommend())
			return new ResponseEntity<String>("rule applied", HttpStatus.OK);
		else
			return new ResponseEntity<String>("no rule applied", HttpStatus.OK);

	}
	
	@GetMapping("/rule/all")
	public ResponseEntity<?> duyetRuleForAll() {
		System.out.println("rule is being checked for all");
		ruleSv.ruleForAll1Rule();
		return new ResponseEntity<String>("done", HttpStatus.OK);
		
	}
	@GetMapping("/rule/forall")
	public ResponseEntity<?> duyetRuleForAll1() {
		System.out.println("rule is being checked for all");
		ruleSv.ruleForAll();
		return new ResponseEntity<String>("done", HttpStatus.OK);
		
	}
	
	

}
