package org.nju.demo.pojo.dto;

public class FileFeature {

    /**
     * 警报所在文件的代码行数
     */
    private int codeLine;

    /**
     * 警报所在文件的圈复杂度
     */
    private double cycNum;

    /**
     * 警报所在文件的标识符数量
     */
    private int tokenNum;

    /**
     * 警报所在文件中的方法个数
     */
    private int methodNum;

    /**
     * 警报所在文件中的警报个数
     */
    private int violationNum;

    public FileFeature(){
        this.codeLine = 0;
        this.cycNum = 0;
        this.tokenNum = 0;
        this.methodNum = 0;
        this.violationNum = 0;
    }

    public int getCodeLine() {
        return codeLine;
    }

    public void setCodeLine(int codeLine) {
        this.codeLine = codeLine;
    }

    public double getCycNum() {
        return cycNum;
    }

    public void setCycNum(double cycNum) {
        this.cycNum = cycNum;
    }

    public int getTokenNum() {
        return tokenNum;
    }

    public void setTokenNum(int tokenNum) {
        this.tokenNum = tokenNum;
    }

    public int getMethodNum() {
        return methodNum;
    }

    public void setMethodNum(int methodNum) {
        this.methodNum = methodNum;
    }

    public int getViolationNum() {
        return violationNum;
    }

    public void setViolationNum(int violationNum) {
        this.violationNum = violationNum;
    }
}
