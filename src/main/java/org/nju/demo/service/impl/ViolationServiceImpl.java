package org.nju.demo.service.impl;

import org.nju.demo.config.Constants;
import org.nju.demo.dao.VersionPatternRelMapper;
import org.nju.demo.dao.ViolationMapper;
import org.nju.demo.entity.VersionPatternRel;
import org.nju.demo.entity.Violation;
import org.nju.demo.entity.ViolationExample;
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
    public int countTrueViolationByPattern(String versionId, String type) {
        ViolationExample violationExample = new ViolationExample();
        ViolationExample.Criteria criteria = violationExample.createCriteria();

        criteria.andVersionIdEqualTo(versionId)
                .andTypeEqualTo(type)
                .andStateEqualTo(Constants.ViolationState.TRUE);

        return 0;
    }

    @Override
    public int addViolation(Violation violation) {
        return violationMapper.insert(violation);
    }

    @Override
    public int addRelation(VersionPatternRel versionPatternRel) {
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
}
