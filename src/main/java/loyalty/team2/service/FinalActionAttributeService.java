package loyalty.team2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.FinalActionAttribute;
import loyalty.team2.repository.FinalActionAttributeRepository;

@Service
public class FinalActionAttributeService {
	@Autowired
	private FinalActionAttributeRepository finalAcAttRepo;
	
	public FinalActionAttribute persist(FinalActionAttribute x) {
		return finalAcAttRepo.save(x);
	}
	
	public List<FinalActionAttribute> getAllFAA(){
		return finalAcAttRepo.findAll();
	}
	
}
