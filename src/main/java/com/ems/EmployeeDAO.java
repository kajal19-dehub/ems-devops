package com.ems;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> loadAll();
    void saveAll(List<Employee> list);
}
