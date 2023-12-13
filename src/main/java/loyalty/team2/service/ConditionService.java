package loyalty.team2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.Condition;
import loyalty.team2.repository.ConditionRepository;
@Service
public class ConditionService {
	@Autowired
	private ConditionRepository conditionRepo;
	
	public Condition getCondition(int id) {		
		return conditionRepo.findByConditionId(id);
	}
}
