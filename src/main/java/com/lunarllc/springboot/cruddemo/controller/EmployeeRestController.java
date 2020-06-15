package com.lunarllc.springboot.cruddemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lunarllc.springboot.cruddemo.entity.Employee;
import com.lunarllc.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api") //this makes the base /api in the URL
public class EmployeeRestController {

	//private EmployeeService eServ;
	private EmployeeService eServ;
	//
	@Autowired
	public EmployeeRestController(EmployeeService inEServ) {
		eServ= inEServ;
	}
	
	//create /employee endpoint that returns all employees
	@GetMapping("/employees")
	public List<Employee> findAll(){
		return eServ.findAll();
	}
	
	
	
	//add mapping for GET /employeeid
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		Employee theEmployee = eServ.findById(employeeId);
		
		if(theEmployee == null) {
			throw new RuntimeException("Employee is not found - "+employeeId);
		}
		return theEmployee;
		
	}
	
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee) {
		
		//just in case they pass an id in JSON, force a save of new item
		
		theEmployee.setId(0);
		eServ.save(theEmployee);
		return theEmployee;
	}
	
	//add mapping for update
	//request body takes in JSON
	//adding a "/" at the end of /employees will break this put mapping?!?!?!?!
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		eServ.save(theEmployee);
		return theEmployee;
		
	}
	
	//add mapping for delete /employeeid
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {
		Employee theEmployee = eServ.findById(employeeId);
		if(theEmployee == null) {
			throw new RuntimeException("Employee is not found - "+employeeId);
		}
		eServ.deleteById(employeeId);
		return "Deleted Employee ID:" + employeeId;
	}
	
}
