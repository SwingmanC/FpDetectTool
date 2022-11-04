package org.nju.demo.entity;

public class PatternStatistics {
    private Integer id;

    private Integer violationNum;

    private Integer vPId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getViolationNum() {
        return violationNum;
    }

    public void setViolationNum(Integer violationNum) {
        this.violationNum = violationNum;
    }

    public Integer getvPId() {
        return vPId;
    }

    public void setvPId(Integer vPId) {
        this.vPId = vPId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", violationNum=").append(violationNum);
        sb.append(", vPId=").append(vPId);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PatternStatistics other = (PatternStatistics) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getViolationNum() == null ? other.getViolationNum() == null : this.getViolationNum().equals(other.getViolationNum()))
            && (this.getvPId() == null ? other.getvPId() == null : this.getvPId().equals(other.getvPId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getViolationNum() == null) ? 0 : getViolationNum().hashCode());
        result = prime * result + ((getvPId() == null) ? 0 : getvPId().hashCode());
        return result;
    }
}