package loyalty.team2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.Purpose;

@Repository
public interface PurposeRepository extends JpaRepository<Purpose, Integer> {
	Purpose findPurposeById(Integer id);
	Purpose findPurposeByPurpose(String s);
	
}
