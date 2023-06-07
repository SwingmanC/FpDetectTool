package org.nju.demo.service;

import org.nju.demo.entity.VersionPatternRel;
import org.nju.demo.entity.Violation;
import org.nju.demo.entity.ViolationCode;
import org.nju.demo.entity.ViolationExample;

import java.util.List;

public interface ViolationService {

    List<Violation> getViolationList(String versionId,int priority,String type,int state);

    List<Violation> getViolationsByVersionId(String versionId);

    List<Violation> getTrueViolations(String versionId);

    Violation getViolation(int id);

    List<Violation> getClassifiedViolations();

    List<Violation> getViolationByRange(int start,int end);

    ViolationCode getViolationCodeByViolationId(int violationId);

    int countTrueViolationByPattern(String versionId,String type);

    int addViolation(Violation violation);

    int addViolationCode(ViolationCode violationCode);

    int addRelation(VersionPatternRel versionPatternRel);

    int updateViolation(Violation violation);

    int deleteViolationByVersionId(String versionId);

    int deleteViolationCodeByViolationId(int violationId);

    int deleteViolation(int id);

}
