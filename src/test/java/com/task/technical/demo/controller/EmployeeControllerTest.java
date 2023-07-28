package com.task.technical.demo.controller;


import com.task.technical.demo.entity.Employee;
import com.task.technical.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    @Test
    public void shouldReturnEmptyEmployeeList() throws Exception {

        given(service.getAllEmployees()).willReturn(new ArrayList<>());

        mockMvc.perform(get("/api/1/employee/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void shouldReturnOneEmployee() throws Exception {

        Employee alex = new Employee(1L, "alex", 100L, new ArrayList<>(), null);

        List<Employee> allEmployees = Arrays.asList(alex);

        given(service.getAllEmployees()).willReturn(allEmployees);

        mockMvc.perform(get("/api/1/employee/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(alex.getName())));
    }

}
