package loyalty.team2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.Purpose;
import loyalty.team2.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepo;
	
	public Purpose findPurposeById(Integer id) {
		return employeeRepo.findPurposeById(id);
	}
	
	public List<Purpose> findAllPurpose() {
		return employeeRepo.findAll();
	}
	
	public Purpose savePurpose(Purpose p) {
		return employeeRepo.save(p);
	}
	
}
