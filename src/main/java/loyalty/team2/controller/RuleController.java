package loyalty.team2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import loyalty.team2.model.CustomerActionWhy;
import loyalty.team2.model.FinalAction;
import loyalty.team2.model.FinalActionDetail;
import loyalty.team2.service.CustomerAttributeService;
import loyalty.team2.service.RuleService;
import loyalty.team2.service.RuleService2;

@RestController
public class RuleController {

	@Autowired
	private RuleService ruleSv;
	@Autowired
	private CustomerAttributeService cusAttSv;
	@Autowired
	private RuleService2 ruleSv2;

	@GetMapping("/rule/detail")
	public ResponseEntity<?> duyet(){
		System.out.println("dang duyet...");
		return new ResponseEntity<List<FinalActionDetail>>(ruleSv2.finalActionDetail(),HttpStatus.OK);
	}
	
	@GetMapping("/rule/{id}")
	public ResponseEntity<?> duyet1CustomerQuaRule(@PathVariable Integer id) {
		System.out.println("rule is being checked for" + id);
		return new ResponseEntity<List<FinalAction>>(ruleSv.recommend1(id),HttpStatus.OK);
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
	public ResponseEntity<?> duyetRuleForAllCusRule() {
		System.out.println("rule is being checked for all");
		ruleSv.ruleForAll();
		return new ResponseEntity<String>("done", HttpStatus.OK);

	}

	@GetMapping("/rule/forall/12")
	public ResponseEntity<?> duyetRuleForAllJson() {
		System.out.println("rule is being checked for all");		
		return new ResponseEntity<List<FinalAction>>(ruleSv2.ruleForAll(), HttpStatus.OK);

	}
	
	@GetMapping("/rule/forall/why")
	public ResponseEntity<?> duyetRuleAndWhy() {
		System.out.println("rule is being checked for all");		
		return new ResponseEntity<List<CustomerActionWhy>>(ruleSv2.CusAcCon(), HttpStatus.OK);

	}

}
