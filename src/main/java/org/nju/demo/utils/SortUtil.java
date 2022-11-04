package org.nju.demo.utils;

import org.nju.demo.entity.Violation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortUtil {

    public static Map<String,Integer> countNum(List<Violation> violationList, int state){
        Map<String,Integer> res = new HashMap<>();
        for(Violation violation:violationList){
            if (violation.getState() == state){
                res.put(violation.getType(),res.getOrDefault(violation.getType(),0)+1);
            }
        }
        return res;
    }

}
