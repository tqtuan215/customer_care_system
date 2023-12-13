package loyalty.team2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.Node1;
import loyalty.team2.repository.Node1Repository;

@Service
public class Node1Service {
	@Autowired
	private Node1Repository node1Repo;
	
//	public List<Node1> getNodes(int acrId){
//		return nodeRepo.findByActionCriteriaResultId(acrId);
//	}
	public List<Node1> getNodes(){
		return node1Repo.findAll();
	}
	
}
