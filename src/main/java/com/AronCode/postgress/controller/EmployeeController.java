package com.AronCode.postgress.controller;

import com.AronCode.postgress.exception.ResourceNotFoundException;
import com.AronCode.postgress.model.Employee;
import com.AronCode.postgress.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //get Employees
    @GetMapping("employees")
    public List<Employee> getAllEmployees(){
        return this.employeeRepository.findAll();
    }

    //get ById

    @GetMapping("employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") long employeeId)
        throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).
                orElseThrow(()->new ResourceNotFoundException("Employee not found for this id"+ employeeId));
        return ResponseEntity.ok().body(employee);
    }
    //save
    @PostMapping("employees")
    public Employee createEmployee(@Valid @RequestBody Employee employee){
        return employeeRepository.save(employee);
    }


    //Update
    @PutMapping("employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable (value = "id")Long employeeId,
                                                   @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not Found for this id"+employeeId));
        employee.setEmail(employeeDetails.getEmail());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());

        return ResponseEntity.ok(this.employeeRepository.save(employee));


    }
    //delete
    @DeleteMapping("employee/{id}")
    public Map<String,Boolean>deleteEmployee(@PathVariable(value ="id")long employeeId) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()->new ResourceNotFoundException("Employee not Found for this id"+employeeId));

       employeeRepository.delete(employee);

        Map<String,Boolean>response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);

        return response;
    }


}
