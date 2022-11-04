package org.nju.demo.entity;

import java.util.Date;

public class AVersion {
    private String versionId;

    private String versionName;

    private String classFilePath;

    private String javaFilePath;

    private Date createTime;

    private String projectId;

    private String lastId;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getClassFilePath() {
        return classFilePath;
    }

    public void setClassFilePath(String classFilePath) {
        this.classFilePath = classFilePath;
    }

    public String getJavaFilePath() {
        return javaFilePath;
    }

    public void setJavaFilePath(String javaFilePath) {
        this.javaFilePath = javaFilePath;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", versionId=").append(versionId);
        sb.append(", versionName=").append(versionName);
        sb.append(", classFilePath=").append(classFilePath);
        sb.append(", javaFilePath=").append(javaFilePath);
        sb.append(", createTime=").append(createTime);
        sb.append(", projectId=").append(projectId);
        sb.append(", lastId=").append(lastId);
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
        AVersion other = (AVersion) that;
        return (this.getVersionId() == null ? other.getVersionId() == null : this.getVersionId().equals(other.getVersionId()))
            && (this.getVersionName() == null ? other.getVersionName() == null : this.getVersionName().equals(other.getVersionName()))
            && (this.getClassFilePath() == null ? other.getClassFilePath() == null : this.getClassFilePath().equals(other.getClassFilePath()))
            && (this.getJavaFilePath() == null ? other.getJavaFilePath() == null : this.getJavaFilePath().equals(other.getJavaFilePath()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getLastId() == null ? other.getLastId() == null : this.getLastId().equals(other.getLastId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVersionId() == null) ? 0 : getVersionId().hashCode());
        result = prime * result + ((getVersionName() == null) ? 0 : getVersionName().hashCode());
        result = prime * result + ((getClassFilePath() == null) ? 0 : getClassFilePath().hashCode());
        result = prime * result + ((getJavaFilePath() == null) ? 0 : getJavaFilePath().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getLastId() == null) ? 0 : getLastId().hashCode());
        return result;
    }
}