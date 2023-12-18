package loyalty.team2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.Action;
import loyalty.team2.model.FinalAction;
import loyalty.team2.repository.FinalActionRepository;

@Service
public class FinalActionService {
	@Autowired
	private FinalActionRepository finalAcRepo;

	public List<FinalAction> getAllFinalAction(){
		return finalAcRepo.findAll();
	}
	
	public FinalAction getOneFinalAction(int id) {
		return finalAcRepo.findByFinalActionId(id);
	}

	public FinalAction getFinalActionByAction(Action action) {
		return finalAcRepo.findByAction(action);
	}

	public FinalAction saveFinalAction(FinalAction x) {
		return finalAcRepo.save(x);
	}

	public List<FinalAction> saveAllFinalAction(List<FinalAction> list) {
		return finalAcRepo.saveAll(list);
	}
}
