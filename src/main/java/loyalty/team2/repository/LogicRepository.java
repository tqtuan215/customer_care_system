package loyalty.team2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.Logic;
@Repository
public interface LogicRepository  extends JpaRepository<Logic, Integer>{
	public Logic findByLogicId(int logicId);
}
