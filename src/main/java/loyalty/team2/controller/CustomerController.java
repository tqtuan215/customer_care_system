package loyalty.team2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import loyalty.team2.model.Customer;
import loyalty.team2.service.CustomerService;

@RestController
public class CustomerController {
	@Autowired
	private CustomerService customerSv;
	
	@GetMapping("/customer/{id}/attribute")
	public Customer getOneCustomer(@PathVariable int id) {
		return customerSv.getCustomerById(id);
	}
}
