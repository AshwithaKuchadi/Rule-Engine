package com.ruleengines;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleEngine {

    public Node createRule(String ruleString) {
        return parseExpression(ruleString);
    }

    private Node parseExpression(String expression) {
        expression = expression.trim();
        if (expression.startsWith("(") && expression.endsWith(")")) {
            expression = expression.substring(1, expression.length() - 1).trim();
        }

        List<Node> nodes = new ArrayList<>();
        String operator = null;

        Pattern pattern = Pattern.compile("(\\w+\\s*[<>=!]+\\s*[^\\s()]+|\\([^()]*\\))\\s*(AND|OR)?");
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {
            String matched = matcher.group(1).trim();
            nodes.add(new Node("operand", null, null, matched));

            if (matcher.group(2) != null) {
                operator = matcher.group(2).trim();
            }
        }

        if (operator != null && nodes.size() == 2) {
            return new Node("operator", nodes.get(0), nodes.get(1), operator);
        } else if (nodes.size() == 1) {
            return nodes.get(0);
        }

        return null;
    }

    public boolean evaluateRule(Node ast, Map<String, Object> userData) {
        if (ast == null) {
            return false;
        }

        switch (ast.getType()) {
            case "operand":
                return evaluateOperand(ast.getValue(), userData);
            case "operator":
                return evaluateOperator(ast.getValue(), ast.getLeft(), ast.getRight(), userData);
            default:
                return false;
        }
    }

    private boolean evaluateOperand(String operand, Map<String, Object> userData) {
        String[] parts = operand.split(" ");
        String attribute = parts[0];
        String operator = parts[1];
        String value = parts[2].replace("'", "");

        Object userValue = userData.get(attribute);
        if (userValue == null) {
            return false;
        }

        switch (operator) {
            case ">":
                return userValue instanceof Number && ((Number) userValue).intValue() > Integer.parseInt(value);
            case "<":
                return userValue instanceof Number && ((Number) userValue).intValue() < Integer.parseInt(value);
            case "=":
            case "==":
                return userValue.toString().equals(value);
            default:
                return false;
        }
    }

    private boolean evaluateOperator(String operator, Node left, Node right, Map<String, Object> userData) {
        boolean leftResult = evaluateRule(left, userData);
        boolean rightResult = evaluateRule(right, userData);

        return operator.equals("AND") ? leftResult && rightResult : leftResult || rightResult;
    }
}
