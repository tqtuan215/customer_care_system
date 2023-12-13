package loyalty.team2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	public Customer findByCustomerId(Integer customerId);
}
