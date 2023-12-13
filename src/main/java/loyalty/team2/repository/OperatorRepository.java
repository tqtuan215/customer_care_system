package loyalty.team2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.Operator;

@Repository
public interface OperatorRepository  extends JpaRepository<Operator, Integer>{
	

}
