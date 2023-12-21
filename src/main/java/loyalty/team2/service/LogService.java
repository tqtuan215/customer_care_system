package loyalty.team2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.Log;
import loyalty.team2.repository.LogRepository;

@Service
public class LogService {
	@Autowired
	private LogRepository logRepo;

	public Log persist(Log x) {
		return logRepo.save(x);
	}

	public List<Log> persist(List<Log> x) {
		return logRepo.saveAll(x);
	}

	public List<Log> getListLogByFAId(int id) {
		return logRepo.findByFinalAction_finalActionId(id);
	}

	public List<Log> getListLogByFAVId(int id) {
		return logRepo.findByFinalActionValue_finalActionValueId(id);
	}
//	
//	public List<Log> getListLogByCusId(int id) {
//		return logRepo.findByCustomer_customerId(id);
//	}
	
}
