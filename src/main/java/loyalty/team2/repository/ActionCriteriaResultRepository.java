package loyalty.team2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.ActionCriteria;
import loyalty.team2.model.ActionCriteriaResult;



@Repository
public interface ActionCriteriaResultRepository extends JpaRepository<ActionCriteriaResult, Integer>{
	public ActionCriteriaResult findByActionCriteriaResultId(Integer actionCriteriaResultId);
	public List<ActionCriteriaResult> findByActionCriteria(ActionCriteria actionCriteria);
	
	
}
