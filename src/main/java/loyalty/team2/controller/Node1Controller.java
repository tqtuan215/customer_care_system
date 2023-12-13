package loyalty.team2.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import loyalty.team2.model.Node1;
import loyalty.team2.service.Node1Service;

@RestController
public class Node1Controller {

	@Autowired
	private Node1Service nodeSv;

	@GetMapping("/nodes")
	public ResponseEntity<?> getNodes(){
		return new ResponseEntity<List<Node1>>(nodeSv.getNodes(),HttpStatus.OK);
	}
}

