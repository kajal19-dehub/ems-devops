package com.ems;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class EmployeeService {
    private final EmployeeDAO dao;
    private final List<Employee> employees;
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public EmployeeService(EmployeeDAO dao) {
        this.dao = dao;
        this.employees = dao.loadAll();
        // set idCounter to max id + 1 to avoid id collision
        int maxId = employees.stream().mapToInt(Employee::getId).max().orElse(0);
        idCounter.set(maxId + 1);
    }

    public Employee addEmployee(String name, String department, double salary) {
        int id = idCounter.getAndIncrement();
        Employee e = new Employee(id, name, department, salary);
        employees.add(e);
        dao.saveAll(employees);
        return e;
    }

    public boolean deleteEmployee(int id) {
        boolean removed = employees.removeIf(emp -> emp.getId() == id);
        if (removed) dao.saveAll(employees);
        return removed;
    }

    public boolean updateEmployee(int id, String name, String department, double salary) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                emp.setName(name);
                emp.setDepartment(department);
                emp.setSalary(salary);
                dao.saveAll(employees);
                return true;
            }
        }
        return false;
    }

    public List<Employee> listEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public Optional<Employee> findById(int id) {
        return employees.stream().filter(e -> e.getId() == id).findFirst();
    }

    // search by name (case-insensitive)
    public List<Employee> searchByName(String q) {
        String t = q.toLowerCase();
        List<Employee> out = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getName().toLowerCase().contains(t)) out.add(e);
        }
        return out;
    }
}
