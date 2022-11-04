package org.nju.demo.service;

import org.nju.demo.entity.ARule;

import java.util.List;

public interface RuleService {

    List<ARule> getRules(String versionId);

    ARule getRule(int ruleId);

    int addRule(ARule rule);

    int updateRule(ARule rule);

    int deleteRule(int ruleId);
}
