package com.emplSpringBootApp.EmployeeSpringSQL;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByWorkplaceIgnoreCase(String workplace);
    List<Employee> findAllByOrderBySalaryDesc();
    List<Employee> findByExperienceGreaterThan(int experience);
}