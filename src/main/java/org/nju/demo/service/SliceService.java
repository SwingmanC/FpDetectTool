package org.nju.demo.service;

import org.nju.demo.entity.AVersion;
import org.nju.demo.entity.Violation;
import org.nju.demo.entity.ViolationSlice;
import org.nju.demo.entity.ViolationSliceWithBLOBs;

import java.util.List;

public interface SliceService {

    ViolationSliceWithBLOBs getViolationSliceByViolationId(int violationId);

    ViolationSliceWithBLOBs getViolationSlice(int sliceId);

    int addViolationSlice(ViolationSliceWithBLOBs violationSlice);

    int addViolationSlice(AVersion version,Violation violation);

    int updateViolationSlice(ViolationSliceWithBLOBs violationSlice);

    int deleteViolationSliceByViolationId(int violationId);

    int deleteViolationSliceById(int sliceId);

}
