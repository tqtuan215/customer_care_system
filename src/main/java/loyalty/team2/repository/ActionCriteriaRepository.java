package loyalty.team2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.ActionCriteria;
import java.util.List;
import loyalty.team2.model.Criteria;


@Repository
public interface ActionCriteriaRepository extends JpaRepository<ActionCriteria, Integer>{
	public List<ActionCriteria> findByCriteria(Criteria criteria);
}
