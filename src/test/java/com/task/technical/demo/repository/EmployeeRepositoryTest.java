package com.task.technical.demo.repository;

import com.task.technical.demo.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void verifyRepositoryByPersistingAnEmployee() {

        Employee employee = new Employee();
        employee.setName("name");
        employee.setSalary(1000L);

        Assertions.assertNull(employee.getId());
        employeeRepository.save(employee);
        Assertions.assertNotNull(employee.getId());
    }

    @Test
    void verifyRepositoryByPersistingLinkedEmployees() {

        Employee employee1 = new Employee();
        employee1.setName("name1");
        employee1.setSalary(1000L);

        employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setName("name2");
        employee2.setSalary(2000L);
        employee1.setSuperior(employee1.getId());

        employeeRepository.save(employee2);

        Employee employee3 = employeeRepository.getReferenceById(employee1.getId());
        Assertions.assertNotNull(employee3);
        Assertions.assertNotNull(employee3.getId());
        Assertions.assertEquals(employee1.getId(), employee3.getId());
        Assertions.assertNotNull(employee3.getSubordinates());
    }

    @Test
    void verifyRepositoryCustomMethod() {

        Employee employee1 = new Employee();
        employee1.setName("name1");
        employee1.setSalary(1000L);

        employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setName("name2");
        employee2.setSalary(2000L);
        employee1.setSuperior(employee1.getId());

        employeeRepository.save(employee2);

        Long sum = employeeRepository.selectTotalSalaryOf(Arrays.asList(employee1.getId(), employee2.getId()));
        Assertions.assertEquals(3000L, sum);
    }


}
