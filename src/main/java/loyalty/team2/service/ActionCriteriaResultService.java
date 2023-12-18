package loyalty.team2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.Action;
import loyalty.team2.model.ActionCriteria;
import loyalty.team2.model.ActionCriteriaResult;
import loyalty.team2.repository.ActionCriteriaResultRepository;

@Service
public class ActionCriteriaResultService {
	@Autowired
	private ActionCriteriaResultRepository ACRRepo;

	public List<ActionCriteriaResult> getAllACR() {
		return ACRRepo.findAll();
	}

	public ActionCriteriaResult getAllACRById(Integer id) {
		return ACRRepo.findByActionCriteriaResultId(id);
	}

	public List<ActionCriteriaResult> getAllAcrByAcCri(ActionCriteria actionC) {
		return ACRRepo.findByActionCriteria(actionC);
	}
}
