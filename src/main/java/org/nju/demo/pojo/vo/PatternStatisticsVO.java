package org.nju.demo.pojo.vo;

public class PatternStatisticsVO {
    private String patternName;
    private int preViolationNum;
    private int nowViolationNum;

    public String getPatternName() {
        return patternName;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }

    public int getPreViolationNum() {
        return preViolationNum;
    }

    public void setPreViolationNum(int preViolationNum) {
        this.preViolationNum = preViolationNum;
    }

    public int getNowViolationNum() {
        return nowViolationNum;
    }

    public void setNowViolationNum(int nowViolationNum) {
        this.nowViolationNum = nowViolationNum;
    }
}
