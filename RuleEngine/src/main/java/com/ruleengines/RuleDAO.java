package com.ruleengines;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RuleDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/rule_engine";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root123";

    public void insertRule(String ruleString) {
        String sql = "INSERT INTO rules (rule_string) VALUES (?)";
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ruleString);
            statement.executeUpdate();
            System.out.println("Rule inserted successfully: " + ruleString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllRules() {
        List<String> rules = new ArrayList<>();
        String sql = "SELECT rule_string FROM rules";
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                rules.add(resultSet.getString("rule_string"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rules;
    }
}
