package loyalty.team2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Integer>{
	public Action findByActionId(Integer actionId);
}
