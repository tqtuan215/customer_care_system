package loyalty.team2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer>{
//	@Query("select l from log l where l.finalActionId = :id")
//	public List<Log> findByFinalActionId(@Param("id") int id);
	public List<Log> findByFinalAction_finalActionId(int id);
	public List<Log> findByFinalActionValue_finalActionValueId(int id);
//	public List<Log> findByCustomerValue_customerId(int id);
}
