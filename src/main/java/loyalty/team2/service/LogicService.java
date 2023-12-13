package loyalty.team2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.Logic;
import loyalty.team2.repository.LogicRepository;

@Service
public class LogicService {
	@Autowired
	private LogicRepository logicRepo;
	
	public Logic getLogic(int id) {
		return logicRepo.findByLogicId(id);
	}
}
