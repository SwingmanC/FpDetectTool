package org.nju.demo.service.impl;

import org.nju.demo.config.Constants;
import org.nju.demo.dao.VersionPatternRelMapper;
import org.nju.demo.dao.ViolationCodeMapper;
import org.nju.demo.dao.ViolationMapper;
import org.nju.demo.entity.*;
import org.nju.demo.service.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViolationServiceImpl implements ViolationService {

    @Autowired
    private ViolationMapper violationMapper;

    @Autowired
    private VersionPatternRelMapper versionPatternRelMapper;

    @Autowired
    private ViolationCodeMapper violationCodeMapper;

    @Override
    public List<Violation> getViolationList(String versionId, int priority, String type, int state) {
        ViolationExample example = new ViolationExample();
        ViolationExample.Criteria criteria = example.createCriteria();

        criteria.andVersionIdEqualTo(versionId);
        if (priority>0) criteria.andPriorityEqualTo(priority);
        if (!type.equals("")) criteria.andTypeEqualTo(type);
        if (state>=-1) criteria.andStateEqualTo(state);

        return violationMapper.selectByExample(example);
    }

    @Override
    public List<Violation> getViolationsByVersionId(String versionId) {
        ViolationExample violationExample = new ViolationExample();
        ViolationExample.Criteria criteria = violationExample.createCriteria();

        criteria.andVersionIdEqualTo(versionId);

        return violationMapper.selectByExample(violationExample);
    }

    @Override
    public List<Violation> getTrueViolations(String versionId) {
        ViolationExample violationExample = new ViolationExample();
        ViolationExample.Criteria criteria = violationExample.createCriteria();

        criteria.andVersionIdEqualTo(versionId).andStateEqualTo(Constants.ViolationState.TRUE);

        return violationMapper.selectByExample(violationExample);
    }

    @Override
    public Violation getViolation(int id) {
        return violationMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Violation> getClassifiedViolations() {
        ViolationExample example = new ViolationExample();
        ViolationExample.Criteria criteria = example.createCriteria();

        criteria.andStateNotEqualTo(Constants.ViolationState.UNCLASSIFIED)
                .andStateNotEqualTo(Constants.ViolationState.UNKNOWN);

        return violationMapper.selectByExample(example);
    }

    @Override
    public int countTrueViolationByPattern(String versionId, String type) {
        ViolationExample violationExample = new ViolationExample();
        ViolationExample.Criteria criteria = violationExample.createCriteria();

        criteria.andVersionIdEqualTo(versionId)
                .andTypeEqualTo(type)
                .andStateEqualTo(Constants.ViolationState.TRUE);

        return (int) violationMapper.countByExample(violationExample);
    }

    @Override
    public int addViolation(Violation violation) {
        violationMapper.insert(violation);
        return violationMapper.selectLastId();
    }

    @Override
    public int addRelation(VersionPatternRel versionPatternRel) {
        VersionPatternRelExample example = new VersionPatternRelExample();
        VersionPatternRelExample.Criteria criteria = example.createCriteria();

        criteria.andVersionIdEqualTo(versionPatternRel.getVersionId())
                .andPatternIdEqualTo(versionPatternRel.getPatternId());

        List<VersionPatternRel> versionPatternRelList = versionPatternRelMapper.selectByExample(example);
        if (versionPatternRelList.size() > 0) return 0;

        VersionPatternRel last = versionPatternRelMapper.selectLastRecord();
        if(last==null) versionPatternRel.setId(1);
        else versionPatternRel.setId(last.getId()+1);
        versionPatternRelMapper.insert(versionPatternRel);
        return versionPatternRel.getId();
    }

    @Override
    public int updateViolation(Violation violation) {
        return violationMapper.updateByPrimaryKeySelective(violation);
    }

    @Override
    public int deleteViolationByVersionId(String versionId) {
        ViolationExample violationExample = new ViolationExample();
        ViolationExample.Criteria criteria = violationExample.createCriteria();

        criteria.andVersionIdEqualTo(versionId);
        return violationMapper.deleteByExample(violationExample);
    }

    @Override
    public ViolationCode getViolationCodeByViolationId(int violationId) {
        ViolationCodeExample example = new ViolationCodeExample();
        ViolationCodeExample.Criteria criteria = example.createCriteria();

        criteria.andViolationIdEqualTo(violationId);

        List<ViolationCode> violationCodeList = violationCodeMapper.selectByExample(example);
        if (violationCodeList.size() > 0) return violationCodeList.get(0);
        else return null;
    }

    @Override
    public int addViolationCode(ViolationCode violationCode) {
        return violationCodeMapper.insert(violationCode);
    }

    @Override
    public int deleteViolationCodeByViolationId(int violationId) {
        ViolationCodeExample example = new ViolationCodeExample();
        ViolationCodeExample.Criteria criteria = example.createCriteria();

        criteria.andViolationIdEqualTo(violationId);

        return violationCodeMapper.deleteByExample(example);
    }
}
