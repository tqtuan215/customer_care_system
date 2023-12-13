package loyalty.team2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.Node;
import loyalty.team2.model.Node1;
import loyalty.team2.repository.CustomerAttributeRepository;
import loyalty.team2.repository.NodeRepository;
@Service
public class NodeService {
	@Autowired
	private NodeRepository nodeRepo;
	
//	public List<Node1> getNodes(int acrId){
//		return nodeRepo.findByActionCriteriaResultId(acrId);
//	}

	public List<Node> getNodes(){
		return nodeRepo.findAll();
	}
	
	public List<Node> getNodesFlACR(Integer id){
		return nodeRepo.findByActionCriteriaResultId(id);
	}
}
