package org.nju.demo.pojo.dto;

public class SliceFeature {

    /**
     * 警报切片的代码行数
     */
    private int codeLine;

    /**
     * 警报切片的条件分支数量
     */
    private int branchNum;

    /**
     * 警报切片的警报数量
     */
    private int violationNum;

    public SliceFeature(){
        this.codeLine = 0;
        this.branchNum = 0;
        this.violationNum = 1;
    }

    public int getCodeLine() {
        return codeLine;
    }

    public void setCodeLine(int codeLine) {
        this.codeLine = codeLine;
    }

    public int getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(int branchNum) {
        this.branchNum = branchNum;
    }

    public int getViolationNum() {
        return violationNum;
    }

    public void setViolationNum(int violationNum) {
        this.violationNum = violationNum;
    }
}
