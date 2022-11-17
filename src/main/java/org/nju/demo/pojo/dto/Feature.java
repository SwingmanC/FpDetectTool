package org.nju.demo.pojo.dto;

public class Feature {

    private ViolationFeature violationFeature;

    private SliceFeature sliceFeature;

    private MethodFeature methodFeature;

    private FileFeature fileFeature;

    public ViolationFeature getViolationFeature() {
        return violationFeature;
    }

    public void setViolationFeature(ViolationFeature violationFeature) {
        this.violationFeature = violationFeature;
    }

    public SliceFeature getSliceFeature() {
        return sliceFeature;
    }

    public void setSliceFeature(SliceFeature sliceFeature) {
        this.sliceFeature = sliceFeature;
    }

    public MethodFeature getMethodFeature() {
        return methodFeature;
    }

    public void setMethodFeature(MethodFeature methodFeature) {
        this.methodFeature = methodFeature;
    }

    public FileFeature getFileFeature() {
        return fileFeature;
    }

    public void setFileFeature(FileFeature fileFeature) {
        this.fileFeature = fileFeature;
    }
}
