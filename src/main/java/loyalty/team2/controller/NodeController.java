package loyalty.team2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import loyalty.team2.model.Node;
import loyalty.team2.service.NodeService;

@RestController
public class NodeController {
	@Autowired
	private NodeService nodeSv;
	
	@GetMapping("/get/nodes")
	public ResponseEntity<?> getNodes(){
		return new ResponseEntity<List<Node>>(nodeSv.getNodes(),HttpStatus.OK);
	}
}
