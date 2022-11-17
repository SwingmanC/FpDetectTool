package org.nju.demo.service;

import org.nju.demo.entity.Violation;
import org.nju.demo.pojo.dto.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FeatureService {

    List<Feature> getFeatureList(List<Violation> violationList) throws IOException;

    Feature getFeature(String sourceFilePath,Violation violation) throws IOException;

    ViolationFeature getViolationFeature(Violation violation);

    SliceFeature getSliceFeature(Violation violation);

    MethodFeature getMethodFeature(String className,Violation violation);

    FileFeature getFileFeature(String className,Violation violation);

}
