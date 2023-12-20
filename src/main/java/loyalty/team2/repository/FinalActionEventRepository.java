package loyalty.team2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.FinalActionEvent;

@Repository
public interface FinalActionEventRepository extends JpaRepository<FinalActionEvent, Integer>{
	
}
