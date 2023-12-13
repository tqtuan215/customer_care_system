package loyalty.team2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.Attribute1;
import loyalty.team2.repository.AttributeRepository1;
@Service
public class AttributeService1 {
	@Autowired
	private AttributeRepository1 attRepo;
	
	public Attribute1 getAtt(Integer id) {
		return attRepo.findByAttributeId(id);
	}
	
	public List<Attribute1> getAtts(){
		return attRepo.findAll();
	}
}
