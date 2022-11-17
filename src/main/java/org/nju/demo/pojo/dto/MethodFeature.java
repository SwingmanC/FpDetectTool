package org.nju.demo.pojo.dto;

public class MethodFeature {

    /**
     * 警报所在方法的代码行数
     */
    private int codeLine;

    /**
     * 警报所在方法的条件分支数量
     */
    private int branchNum;

    /**
     * 警报所在方法的圈复杂度
     */
    private int cycNum;

    /**
     * 警报所在方法的标识符数量
     */
    private int tokenNum;

    /**
     * 警报所在方法的参数数量
     */
    private int paramNum;

    /**
     * 警报所在方法的警报数量
     */
    private int violationNum;

    public MethodFeature(){
        this.codeLine = 0;
        this.branchNum = 0;
        this.cycNum = 0;
        this.tokenNum = 0;
        this.paramNum = 0;
        this.violationNum = 0;
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

    public int getCycNum() {
        return cycNum;
    }

    public void setCycNum(int cycNum) {
        this.cycNum = cycNum;
    }

    public int getTokenNum() {
        return tokenNum;
    }

    public void setTokenNum(int tokenNum) {
        this.tokenNum = tokenNum;
    }

    public int getParamNum() {
        return paramNum;
    }

    public void setParamNum(int paramNum) {
        this.paramNum = paramNum;
    }

    public int getViolationNum() {
        return violationNum;
    }

    public void setViolationNum(int violationNum) {
        this.violationNum = violationNum;
    }
}
