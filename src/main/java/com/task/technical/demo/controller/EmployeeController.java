package com.task.technical.demo.controller;

import com.task.technical.demo.entity.Employee;
import com.task.technical.demo.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/1")
@Log4j2
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {

        return employeeService.getEmployee(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/employee/")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }


    @GetMapping("/employee/byids")
    public List<Employee> getAllEmployees(@RequestParam List<Long> id) {
        return employeeService.getAllEmployeesByIds(id);
    }

    @GetMapping("/employee/{id}/statistic")
    public Long getEmployeeStatistic(@PathVariable Long id) {
        return employeeService.salarySumAllSubordinatesOfEmployee(id);
    }

    @PostMapping("/employee")
    Employee createNewAddress(@RequestBody Employee newEmployee) {
        return employeeService.save(newEmployee);
    }

}