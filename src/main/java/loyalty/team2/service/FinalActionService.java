package loyalty.team2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.FinalAction;
import loyalty.team2.repository.FinalActionRepository;

@Service
public class FinalActionService {
	@Autowired
	private FinalActionRepository finalAcRepo;
	
	public FinalAction saveFinalAction(FinalAction x) {
		return finalAcRepo.save(x);
	}
}
