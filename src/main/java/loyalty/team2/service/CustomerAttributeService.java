package loyalty.team2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.CustomerAttribute;
import loyalty.team2.repository.CustomerAttributeRepository;

@Service
public class CustomerAttributeService {
	@Autowired
	private CustomerAttributeRepository cusAttRepo;
	
	public List<CustomerAttribute> getAttributeForOneCustomer(Integer customerId) {
		return cusAttRepo.findByCustomerId(customerId);
	}
	
//	public List<CustomerAttribute> getAttributeForOneCustomer(Customer customer) {
//		return cusAttRepo.findByCustomer(customer);
//	}
	
	public List<CustomerAttribute> getAllCustomerAttribute(){
		return cusAttRepo.findAll();
	}
	
}
