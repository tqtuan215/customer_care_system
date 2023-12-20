package loyalty.team2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.FinalActionEvent;
import loyalty.team2.repository.FinalActionEventRepository;

@Service
public class FinalActionEventService {
	@Autowired
	private FinalActionEventRepository finalAERepo;
	
	public FinalActionEvent persist(FinalActionEvent x) {
		return finalAERepo.save(x);
	}
	
}
