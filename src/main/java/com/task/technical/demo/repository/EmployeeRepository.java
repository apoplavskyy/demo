package com.task.technical.demo.repository;

import com.task.technical.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT SUM(e.salary) FROM Employee e WHERE e.id in :ids")
    Long selectTotalSalaryOf(@Param("ids") List<Long> ids);

}
