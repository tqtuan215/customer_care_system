package loyalty.team2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.Customer;
import loyalty.team2.model.CustomerAttribute;
import java.util.List;

@Repository
public interface CustomerAttributeRepository extends JpaRepository<CustomerAttribute, Integer>{
//	public List<CustomerAttribute> findByCustomerId(Integer customerId);
//	public List<CustomerAttribute> findByCustomer(Customer customer);
	
}
