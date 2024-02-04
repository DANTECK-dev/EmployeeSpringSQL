package com.emplSpringBootApp.EmployeeSpringSQL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindByWorkplaceIgnoreCase() {
        Employee employee1 = new Employee("Тестовое ФИО", "Тестовая должность", 5, "Тестовая компания", 5000);
        Employee employee2 = new Employee("Таргашин Альбедо Екатеринович", "Супер пупер разработчик", 3, "Супер пупер компания", 900000);
        entityManager.persist(employee1);
        entityManager.persist(employee2);
        entityManager.flush();

        List<Employee> foundEmployees = employeeRepository.findByWorkplaceIgnoreCase("Тестовая компания");

        assertThat(foundEmployees).hasSize(1);
        assertThat(foundEmployees.get(0).getFio()).isEqualTo("Тестовое ФИО");
    }

    @Test
    public void testFindAllByOrderBySalaryDesc() {
        Employee employee1 = new Employee("Тестовое ФИО", "Тестовая должность", 5, "Тестовая компания", 5000);
        Employee employee2 = new Employee("Таргашин Альбедо Екатеринович", "Супер пупер разработчик", 3, "Супер пупер компания", 900);
        entityManager.persist(employee1);
        entityManager.persist(employee2);
        entityManager.flush();

        List<Employee> sortedEmployees = employeeRepository.findAllByOrderBySalaryDesc();

        assertThat(sortedEmployees).hasSize(2);
        assertThat(sortedEmployees.get(0).getFio()).isEqualTo("Тестовое ФИО");
    }

    @Test
    public void testFindByExperienceGreaterThan() {
        Employee employee1 = new Employee("Тестовое ФИО", "Тестовая должность", 5, "Тестовая компания", 5000);
        Employee employee2 = new Employee("Таргашин Альбедо Екатеринович", "Супер пупер разработчик", 3, "Супер пупер компания", 900000);
        entityManager.persist(employee1);
        entityManager.persist(employee2);
        entityManager.flush();

        List<Employee> experiencedEmployees = employeeRepository.findByExperienceGreaterThan(4);

        assertThat(experiencedEmployees).hasSize(1);
        assertThat(experiencedEmployees.get(0).getFio()).isEqualTo("Тестовое ФИО");
    }
}