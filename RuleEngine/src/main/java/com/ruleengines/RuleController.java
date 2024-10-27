package com.ruleengines;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;


@RestController
@RequestMapping("/api/rules")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React (running on port 3000)
public class RuleController {

    private final RuleDAO ruleDAO = new RuleDAO();
    private final RuleEngine ruleEngine = new RuleEngine();

    @PostMapping("/create")
    public ResponseEntity<String> createRule(@RequestBody String rule) {
        try {
            ruleDAO.insertRule(rule);
            return ResponseEntity.status(HttpStatus.CREATED).body("Rule created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create rule");
        }
    }

    @PostMapping("/evaluate")
    public ResponseEntity<Boolean> evaluateRule(@RequestBody User user) {
        try {
            List<String> rules = ruleDAO.getAllRules();
            if (rules.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(false);
            }

            boolean isEligible = false;
            for (String ruleString : rules) {
                Node ast = ruleEngine.createRule(ruleString);
                if (ruleEngine.evaluateRule(ast, user.toMap())) {
                    isEligible = true; // User is eligible if any rule evaluates to true
                    break;
                }
            }
            return ResponseEntity.ok(isEligible);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }
}
