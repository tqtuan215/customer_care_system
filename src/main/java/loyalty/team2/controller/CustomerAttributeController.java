package loyalty.team2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import loyalty.team2.model.CustomerAttribute;
import loyalty.team2.service.CustomerAttributeService;

@RestController
@RequestMapping("/customer")
public class CustomerAttributeController {
	@Autowired
	private CustomerAttributeService cusAttSv;

	
	@GetMapping("/attribute")
	public ResponseEntity<List<CustomerAttribute>> getAllCustomerAttribute(){
		return new ResponseEntity<List<CustomerAttribute>>(cusAttSv.getAllCustomerAttribute(),HttpStatus.OK);
	}
	
//	@GetMapping("/{id}")
//	public ResponseEntity<List<CustomerAttribute>> getAttributesByCusId(@PathVariable Integer id){
//		return new ResponseEntity<List<CustomerAttribute>>( cusAttSv.getAttribute((id)),HttpStatus.OK);
//	}
}
