package loyalty.team2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.Customer;
import loyalty.team2.repository.CustomerRepository;


@Service
public class CustomerService{
	@Autowired
	private CustomerRepository customerRepo;
	
	public Customer getCustomerById(Integer customerId) {
		return customerRepo.findByCustomerId(customerId);
	}
	
	public List<Customer> getAllCustomer(){
		return customerRepo.findAll();
	}
}
