package loyalty.team2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer>{
	
}
