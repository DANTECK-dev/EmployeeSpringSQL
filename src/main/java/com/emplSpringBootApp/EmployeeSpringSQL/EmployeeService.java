package com.emplSpringBootApp.EmployeeSpringSQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    public List<Employee> findEmployeesByWorkplaceIgnoreCase(String workplace) {
        return employeeRepository.findByWorkplaceIgnoreCase(workplace);
    }
    public List<Employee> getEmployeesSortedBySalary() {
        return employeeRepository.findAllByOrderBySalaryDesc();
    }

    public List<Employee> getEmployeesWithExperienceGreaterThan(int experience) {
        return employeeRepository.findByExperienceGreaterThan(experience);
    }
}
