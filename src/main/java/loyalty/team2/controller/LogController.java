package loyalty.team2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import loyalty.team2.model.Log;
import loyalty.team2.service.LogService;

@RestController
@RequestMapping("/log")
public class LogController {
	@Autowired
	private LogService logSv;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getListLogByFAId(@PathVariable int id){
		System.out.println("get log for id: "+id);
		List<Log> logs = new ArrayList<Log>();
		logs.addAll(logSv.getListLogByFAId(id));
//		logs.addAll(logSv.getListLogByFAVId(id));
		return new ResponseEntity<List<Log>>(logs, HttpStatus.OK);
//		return new ResponseEntity<List<Log>>(logSv.getListLogByCusId(id), HttpStatus.OK);
	}
	
	
	
}
