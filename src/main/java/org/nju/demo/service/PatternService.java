package org.nju.demo.service;

import org.nju.demo.entity.Pattern;

import java.util.List;

public interface PatternService {

    Pattern getPatternByPatternName(String patternName);

    Pattern getPattern(int id);

    List<Pattern> getFalsePatterns();

    List<Pattern> getPatternListByKeyword(String keyword);

    int updatePattern(Pattern pattern);

    long countByCategoryId(int categoryId);

}
