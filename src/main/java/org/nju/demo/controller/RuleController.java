package org.nju.demo.controller;

import org.nju.demo.entity.*;
import org.nju.demo.service.PatternService;
import org.nju.demo.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @Autowired
    private PatternService patternService;

    @Autowired
    private HttpSession session;

    @ResponseBody
    @RequestMapping("/rules")
    public List<ARule> getRules(){
        AVersion version = (AVersion) session.getAttribute("version");
        return ruleService.getRules(version.getVersionId());
    }

    @ResponseBody
    @RequestMapping("/rule/{ruleId}")
    public ARule getRule(@PathVariable("ruleId") int ruleId){
        return ruleService.getRule(ruleId);
    }

    @RequestMapping(value = "/addRule",method = RequestMethod.POST)
    public String addRule(@RequestParam("ruleName") String ruleName,
                          @RequestParam(value = "typeName",required = false) String typeName,
                          @RequestParam(value = "category",required = false) String category,
                          @RequestParam(value = "priority",required = false) int priority,
                          @RequestParam(value = "className",required = false) String className,
                          @RequestParam(value = "methodName",required = false) String methodName,
                          @RequestParam(value = "lineLength",required = false) int lineLength){
        ARule rule = new ARule();
        AVersion version = (AVersion) session.getAttribute("version");
        rule.setRuleName(ruleName);
        if (typeName == null) rule.setTypeName("");
        else rule.setTypeName(typeName);

        if (category == null) rule.setCategory("");
        else rule.setCategory(category);

        if (priority == 0) rule.setPriority(0);
        else rule.setPriority(priority);

        if (className == null) rule.setClassName("");
        else rule.setClassName(className);

        if (methodName == null) rule.setMethodName("");
        else rule.setMethodName(methodName);

        if (lineLength == 0) rule.setLineLength(0);
        else rule.setLineLength(lineLength);

        rule.setVersionId(version.getVersionId());
        rule.setCreateTime(new Date());
        ruleService.addRule(rule);
        return "redirect:/view/rules";
    }

    @ResponseBody
    @RequestMapping(value = "/editRule",method = RequestMethod.POST)
    public int editRule(@RequestParam("ruleId") int ruleId,
                        @RequestParam("ruleName") String ruleName,
                        @RequestParam(value = "typeName",required = false) String typeName,
                        @RequestParam(value = "category",required = false) String category,
                        @RequestParam(value = "priority",required = false) int priority,
                        @RequestParam(value = "className",required = false) String className,
                        @RequestParam(value = "methodName",required = false) String methodName,
                        @RequestParam(value = "lineLength",required = false) int lineLength){
        ARule rule = ruleService.getRule(ruleId);
        rule.setRuleName(ruleName);
        if (typeName != null) rule.setTypeName(typeName);

        if (category != null) rule.setCategory(category);

        if (priority != 0) rule.setPriority(0);

        if (className != null) rule.setClassName(className);

        if (methodName != null) rule.setMethodName(methodName);

        if (lineLength != 0) rule.setLineLength(lineLength);

        return ruleService.updateRule(rule);
    }

}
