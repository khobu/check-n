package com.khobu.checkn.service;


import com.khobu.checkn.domain.Employee;
import com.khobu.checkn.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
	private EmployeeRepository employeeRepository;

    public List<Employee> retrieveAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(long id){
        return employeeRepository.findById(id)
                .orElse(null);
			//.orElseThrow(() -> new Exception("Employee Not Found")); //EmployeeNotFoundException(id)
    }

    public List<Employee> findByUsername(String username){
        return employeeRepository.findByUsername(username);

    }

    public Employee saveEmployee(Employee employee){
        Employee returnValue = null;
        try{
            returnValue = employeeRepository.save(employee);
        } catch (Exception ex){
        }
        return  returnValue;
    }

    public Employee updateEmployee(Employee pEmployee, long id){
        return employeeRepository.findById(id)
			.map(employee -> {
				employee = updateEmployee(employee, pEmployee);
				return employeeRepository.save(employee);
			})
			.orElseGet(() -> {
				pEmployee.setId(id);
				return employeeRepository.save(pEmployee);
			});
    }

    private Employee updateEmployee(Employee originalEmployee, Employee updatedEmployee){

        return updatedEmployee;
    }

    public boolean deleteEmployee(long id){
       return employeeRepository.findById(id)
			.map(employee -> {
			    employee.setActive(false);
				employeeRepository.save(employee);
				return true;
			}).orElse(false);
    }
}
