package org.nju.demo.service.impl;

import org.nju.demo.dao.PatternStatisticsMapper;
import org.nju.demo.entity.PatternStatistics;
import org.nju.demo.entity.Violation;
import org.nju.demo.pojo.dto.PatternStatisticsDTO;
import org.nju.demo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private PatternStatisticsMapper patternStatisticsMapper;

    @Override
    public List<PatternStatisticsDTO> getPatternStatisticsByVersionId(String versionId) {
        return patternStatisticsMapper.selectPatternStatisticsByVersionId(versionId);
    }

    @Override
    public int getViolationNumByRelation(String versionId, int patternId) {
        return patternStatisticsMapper.selectViolationNumByRelation(versionId,patternId);
    }

    @Override
    public int addPatternStatistics(PatternStatistics patternStatistics) {
        return patternStatisticsMapper.insert(patternStatistics);
    }

    @Override
    public Map<String, Integer> countViolationByPattern(List<Violation> violationList) {
        HashMap<String,Integer> hm = new HashMap<>();
        for(Violation violation : violationList) hm.put(violation.getType(),hm.getOrDefault(violation.getType(),0)+1);
        return hm;
    }
}
