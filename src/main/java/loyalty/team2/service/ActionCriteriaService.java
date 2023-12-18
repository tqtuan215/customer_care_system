package loyalty.team2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.ActionCriteria;
import loyalty.team2.model.Criteria;
import loyalty.team2.repository.ActionCriteriaRepository;

@Service
public class ActionCriteriaService {
	@Autowired
	private ActionCriteriaRepository actionCriRepo;
	
	public List<ActionCriteria> getActionByCriteria(Criteria criteria){
		return actionCriRepo.findByCriteria(criteria);
	}
	
	public List<ActionCriteria> getAllActionCri(){
		return actionCriRepo.findAll();
	}
}
