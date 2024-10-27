package com.ruleengines;

public class Node {
    private String type;
    private Node left;
    private Node right;
    private String value;

    public Node(String type, Node left, Node right, String value) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getValue() {
        return value;
    }
}
