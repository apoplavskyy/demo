package com.task.technical.demo.service;

import com.task.technical.demo.entity.Employee;
import com.task.technical.demo.repository.EmployeeRepository;
import com.task.technical.demo.utils.ScanConfig;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service("dataService")
@Log4j2
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private ScanConfig scanConfig;

    public Optional<Employee> getEmployee(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getAllEmployeesByIds(List<Long> ids) {
        return employeeRepository.findAllById(ids);
    }

    public Long salarySumAllSubordinatesOfEmployee(Long id) {
        // TODO use logs!!!

        List<Long> directSubordinatesOf = Arrays.asList(id);
        List<Long> subordinatesAll = new ArrayList<>();
        Long limit = 0L;  // TODO move to config

        do {

            directSubordinatesOf = subordinateIdsOfEmployees(directSubordinatesOf);
            subordinatesAll.addAll(directSubordinatesOf);
            limit++;

        } while (!directSubordinatesOf.isEmpty() && limit < scanConfig.getDepth());

        // TODO Throw clear exception to change limit if limit reached but it still has Subordinates

        return salarySumEmployees(subordinatesAll);
    }

    private Long salarySumEmployees(List<Long> ids) {
        return employeeRepository.selectTotalSalaryOf(ids);
    }

    private List<Long> subordinateIdsOfEmployees(List<Long> ids) {
        return employeeRepository.findAllById(ids).stream().flatMap(s -> s.getSubordinates().stream().map(Employee::getId)).collect(Collectors.toList());
    }

    public Employee save(Employee newEmployee) {

        // TODO check if boss exists and cycling links  -> clear exception

        return employeeRepository.save(newEmployee);
    }
}
