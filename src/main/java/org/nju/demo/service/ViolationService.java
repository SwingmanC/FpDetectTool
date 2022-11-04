package org.nju.demo.service;

import org.nju.demo.entity.VersionPatternRel;
import org.nju.demo.entity.Violation;

import java.util.List;

public interface ViolationService {

    List<Violation> getViolationList(String versionId,int priority,String type,int state);

    List<Violation> getViolationsByVersionId(String versionId);

    List<Violation> getTrueViolations(String versionId);

    Violation getViolation(int id);

    int countTrueViolationByPattern(String versionId,String type);

    int addViolation(Violation violation);

    int addRelation(VersionPatternRel versionPatternRel);

    int updateViolation(Violation violation);

    int deleteViolationByVersionId(String versionId);

}
