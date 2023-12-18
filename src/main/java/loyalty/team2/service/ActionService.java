package loyalty.team2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.Action;
import loyalty.team2.repository.ActionRepository;

@Service
public class ActionService {
	@Autowired
	private ActionRepository actionRepo;

	public Action getOneAction(int id) {

		return actionRepo.findByActionId(id);
	}
}
