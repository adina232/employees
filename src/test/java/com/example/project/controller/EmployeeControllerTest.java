package com.example.project.controller;

import com.example.project.entity.Employee;
import com.example.project.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {
    EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    EmployeeController employeeController = new EmployeeController(employeeRepository);

    @Test
    void showEmployees_whenThereIsNoEmployee_thenReturnNoEmployee() {
        List<Employee> list = new ArrayList<>();
        when(employeeRepository.findAll()).thenReturn(list);

        ModelAndView result = employeeController.showEmployees();

        assertEquals("list-employees", result.getViewName());
        assertTrue(result.getModel().containsKey("employees"));
        assertTrue(((List<Employee>) result.getModel().get("employees")).isEmpty());
    }

    @Test
    void showEmployees_whenThereAreEmployees_expectEmployees() {
        List<Employee> list = new ArrayList<>();
        Employee employee = new Employee(1L, "Adina", "adina.luca@", "it");
        list.add(employee);

        when(employeeRepository.findAll()).thenReturn(list);
        ModelAndView result = employeeController.showEmployees();

        assertEquals("list-employees", result.getViewName());
        assertTrue(result.getModel().containsKey("employees"));
        assertFalse(((List<Employee>) result.getModel().get("employees")).isEmpty());
    }

    @Test
    void addEmployeeForm() {
        Employee employee = new Employee();

        ModelAndView result = employeeController.addEmployeeForm();

        assertEquals("add-employee-form", result.getViewName());
        assertTrue(result.getModel().containsKey("employee"));
        assertEquals(employee, result.getModel().get("employee"));
    }

    @Test
    void saveEmployee() {
        Employee employee = new Employee(1L, "Test", "test@yahoo.com", "testare");

        String result = employeeController.saveEmployee(employee);

        verify(employeeRepository).save(employee);
        assertEquals("redirect:/list", result);
    }

    @Test
    void showUpdateForm() {
        Employee employee = new Employee(1L, "Test", "test@yahoo.com", "testare");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        ModelAndView result = employeeController.showUpdateForm(1L);

        assertEquals("add-employee-form", result.getViewName());
        assertTrue(result.getModel().containsKey("employee"));
        assertEquals(((Employee) result.getModel().get("employee")), employee);
    }

    @Test
    void deleteEmployee() {
        Long id = 1L;
        String result = employeeController.deleteEmployee(1L);
        verify(employeeRepository).deleteById(1L);
        assertEquals("redirect:/list", result);
    }
}