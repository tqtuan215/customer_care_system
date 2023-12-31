package loyalty.team2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.Condition;
@Repository
public interface ConditionRepository  extends JpaRepository<Condition, Integer> {
	public Condition findByConditionId(int conditionId);
}
