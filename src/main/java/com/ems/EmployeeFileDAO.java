package com.ems;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFileDAO implements EmployeeDAO {

    private final Path file = Paths.get("data", "employees.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type listType = new TypeToken<List<Employee>>() {}.getType();

    public EmployeeFileDAO() {
        try {
            if (Files.notExists(file.getParent())) {
                Files.createDirectories(file.getParent());
            }
            if (Files.notExists(file)) {
                Files.write(
                        file,
                        gson.toJson(new ArrayList<Employee>()).getBytes(),
                        StandardOpenOption.CREATE
                );
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to initialize data file", e);
        }
    }

    @Override
    public List<Employee> loadAll() {
        try {
            String json = new String(Files.readAllBytes(file));
            List<Employee> list = gson.fromJson(json, listType);
            return list == null ? new ArrayList<>() : list;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void saveAll(List<Employee> list) {
        try {
            String json = gson.toJson(list, listType);
            Files.write(
                    file,
                    json.getBytes(),
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.CREATE
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
