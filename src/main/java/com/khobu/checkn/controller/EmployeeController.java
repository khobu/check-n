package com.khobu.checkn.controller;


import com.khobu.checkn.annotation.IsAdmin;
import com.khobu.checkn.domain.Employee;
import com.khobu.checkn.service.EmployeeService;
import com.khobu.checkn.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api")
public class EmployeeController{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@Autowired
    private UserService userService;

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getEmployees() {
		LOGGER.info("getting all employees");
    	List<Employee> results = employeeService.retrieveAllEmployees();
    	return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@IsAdmin
	@PostMapping("/employees")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		LOGGER.info("creating employee");
	    employee.setUpdatedByEmployeeId(userService.getUserId());
    	Employee result = employeeService.saveEmployee(employee);
    	return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> findEmployee(@PathVariable Long id) {
		LOGGER.info("finding employee");
		Employee result = employeeService.findEmployeeById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@IsAdmin
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		LOGGER.info("replacing employee");
        newEmployee.setUpdatedByEmployeeId(userService.getUserId());
    	Employee result = employeeService.updateEmployee(newEmployee, id);
    	return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@IsAdmin
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long id) {
		LOGGER.info("deleting employee");
		boolean result = employeeService.deleteEmployee(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
