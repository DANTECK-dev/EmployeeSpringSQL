package com.emplSpringBootApp.EmployeeSpringSQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {

        this.employeeService = employeeService;
    }

    @GetMapping
    @ResponseBody
    public String getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return formatEmployeeList(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id).orElse(null);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Данный сотрудник не найден");
        }

        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<Void> addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        employeeService.updateEmployee(employee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getWorkplace", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String findEmployeesByWorkplaceIgnoreCase(@RequestParam(name = "workplace") String workplace) {
        List<Employee> employees = employeeService.findEmployeesByWorkplaceIgnoreCase(workplace);
        return "<h1>Сотрудники работающие в " + workplace + "</h1>\n" + formatEmployeeListAsHtml(employees);
    }

    private String formatEmployeeListAsHtml(List<Employee> employees) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<ul>");
        for (Employee employee : employees) {
            htmlBuilder.append("<h2>").append(employee.getFio()).append("</h2>")
                    .append("<li>Должность: ").append(employee.getPost()).append("</li>")
                    .append("<li>Стаж: ").append(employee.getExperience()).append("</li>")
                    .append("<li>Зарплата: ").append(employee.getSalary()).append("</li>");
        }

        htmlBuilder.append("</ul>");
        return htmlBuilder.toString();
    }

    private String formatEmployeeList(List<Employee> employees) {
        if (employees.isEmpty()) {
            return "Сотрудники не найдены.";
        }

        String employeeListHtml = "<ul>";
        employeeListHtml += employees.stream()
                .map(employee -> String.format("<li>ID: %d<br>ФИО: %s<br>Должность: %s<br>Опыт: %d<br>Место работы: %s<br>Зарплата: %.2f</li><br>",
                        employee.getId(), employee.getFio(), employee.getPost(),
                        employee.getExperience(), employee.getWorkplace(), employee.getSalary()))
                .collect(Collectors.joining("\n"));
        employeeListHtml += "</ul>";

        return employeeListHtml;
    }
    @GetMapping("/sorted")
    public String getEmployeesSortedBySalary() {
        List<Employee> employees = employeeService.getEmployeesSortedBySalary();
        return formatEmployeeList(employees);
    }
    @GetMapping("/experience>{experience}")
    public String getEmployeesWithExperienceGreaterThan(@PathVariable int experience) {
        List<Employee> employees = employeeService.getEmployeesWithExperienceGreaterThan(experience);
        return formatEmployeeList(employees);
    }
}
