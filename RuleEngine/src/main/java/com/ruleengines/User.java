package com.ruleengines;

import java.util.HashMap;
import java.util.Map;

public class User {
    private int age;
    private String department;
    private int salary;
    private int experience;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("age", age);
        userMap.put("department", department);
        userMap.put("salary", salary);
        userMap.put("experience", experience);
        return userMap;
    }
}
