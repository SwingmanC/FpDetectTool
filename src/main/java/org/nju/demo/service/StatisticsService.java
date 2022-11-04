package org.nju.demo.service;

import org.nju.demo.entity.PatternStatistics;
import org.nju.demo.entity.Violation;
import org.nju.demo.pojo.dto.PatternStatisticsDTO;

import java.util.List;
import java.util.Map;

public interface StatisticsService {

    List<PatternStatisticsDTO> getPatternStatisticsByVersionId(String versionId);

    int getViolationNumByRelation(String versionId,int patternId);

    int addPatternStatistics(PatternStatistics patternStatistics);

    Map<String,Integer> countViolationByPattern(List<Violation> violationList);

}
