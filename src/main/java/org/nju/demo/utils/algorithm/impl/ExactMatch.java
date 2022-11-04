package org.nju.demo.utils.algorithm.impl;

import org.nju.demo.entity.Violation;
import org.nju.demo.utils.algorithm.Match;

import java.util.ArrayList;
import java.util.List;

public class ExactMatch implements Match {

    @Override
    public List<Violation> mark1(List<Violation> v1, List<Violation> v2) {
        if(v1 == null) return null;

        List<Violation> violationList = new ArrayList<>();

        for(int j = 0;j < v1.size();++j){
            Violation violation = v1.get(j);
            int is_exist = 1;
            for(int k = 0;k < v2.size() && is_exist == 1;++k){
                Violation compare = v2.get(k);
                if(violation.getType().equals(compare.getType()) &&
                        violation.getPriority().equals(compare.getPriority()) &&
                        violation.getSourcePath().equals(compare.getSourcePath()) &&
                        violation.getMethodName().equals(compare.getMethodName()) &&
                        violation.getEndLine() - violation.getStartLine() == compare.getEndLine() - compare.getStartLine()){
                    violation.setState(0);
                    is_exist = 0;
                }
            }
            if(is_exist == 1){
                int is_file_exist = 1;
                String file = violation.getSourcePath();
                for(int k=0;k<v2.size() && is_file_exist == 1;++k){
                    Violation compare = v2.get(k);
                    if(compare.getSourcePath().equals(file)){
                        is_file_exist = 0;
                    }
                }
                if(is_file_exist == 1) violation.setState(2);
                else violation.setState(1);
            }
            violationList.add(violation);
        }

        return violationList;
    }

}
