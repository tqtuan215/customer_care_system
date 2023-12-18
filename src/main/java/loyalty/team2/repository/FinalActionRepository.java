package loyalty.team2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.Action;
import loyalty.team2.model.FinalAction;


@Repository
public interface FinalActionRepository extends JpaRepository<FinalAction, Integer>{
	public FinalAction findByAction(Action action);
	public FinalAction findByFinalActionId(int id);
}
