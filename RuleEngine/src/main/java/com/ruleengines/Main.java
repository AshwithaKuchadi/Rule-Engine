package com.ruleengines;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        RuleEngine ruleEngine = new RuleEngine();
        RuleDAO ruleDAO = new RuleDAO();

        // Insert rule only if it doesn't already exist
        String ruleString = "age > 30 AND department = 'Sales'";
        ruleDAO.insertRule(ruleString);

        List<User> users = readUsersFromJson("resources/data.json");

        for (User user : users) {
            Node ast = ruleEngine.createRule(ruleString);
            boolean isEligible = ruleEngine.evaluateRule(ast, user.toMap());
            System.out.println("Is user eligible? " + isEligible);
        }
    }

    private static List<User> readUsersFromJson(String filePath) {
        List<User> userList = new ArrayList<>();
        Gson gson = new Gson();

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                System.err.println("File not found: " + filePath);
                return userList;
            }

            try (Reader reader = new InputStreamReader(inputStream)) {
                JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
                JsonArray usersArray = jsonObject.getAsJsonArray("users");

                for (int i = 0; i < usersArray.size(); i++) {
                    User user = gson.fromJson(usersArray.get(i), User.class);
                    userList.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }
}