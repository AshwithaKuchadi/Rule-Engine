package com.ruleengine.RuleEngine;
import com.ruleengines.RuleEngine;
import com.ruleengines.Node;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AppTest {

    private RuleEngine ruleEngine;

    @BeforeEach
    public void setUp() {
        // Initialize the RuleEngine instance before each test
        ruleEngine = new RuleEngine();
    }

    @Test
    public void testCreateRule() {
        // Test case for creating a rule
        String ruleString = "age > 30 AND department = 'Sales'";
        Node ast = ruleEngine.createRule(ruleString); // Create AST from rule string
        assertNotNull(ast, "AST should not be null"); // Assert that the AST is created
    }

    @Test
    public void testEvaluateRuleTrue() {
        // Test case for evaluating a rule that should return true
        String ruleString = "age > 30 AND department = 'Sales'";
        Node ast = ruleEngine.createRule(ruleString); // Create AST

        // Create user data that meets the rule criteria
        Map<String, Object> userData = new HashMap<>();
        userData.put("age", 35);
        userData.put("department", "Sales");

        // Evaluate the rule
        boolean result = ruleEngine.evaluateRule(ast, userData);
        assertTrue(result, "User should be eligible"); // Assert that the user is eligible
    }

    @Test
    public void testEvaluateRuleFalse() {
        // Test case for evaluating a rule that should return false
        String ruleString = "age > 30 AND department = 'Sales'";
        Node ast = ruleEngine.createRule(ruleString); // Create AST

        // Create user data that does not meet the rule criteria
        Map<String, Object> userData = new HashMap<>();
        userData.put("age", 25);
        userData.put("department", "Marketing");

        // Evaluate the rule
        boolean result = ruleEngine.evaluateRule(ast, userData);
        assertFalse(result, "User should not be eligible"); // Assert that the user is not eligible
    }

    // Additional tests can be added for more scenarios and edge cases
}
