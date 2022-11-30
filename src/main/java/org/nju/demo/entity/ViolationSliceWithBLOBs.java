package org.nju.demo.entity;

public class ViolationSliceWithBLOBs extends ViolationSlice {
    private String snippet;

    private String snapshot;

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", snippet=").append(snippet);
        sb.append(", snapshot=").append(snapshot);
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
        ViolationSliceWithBLOBs other = (ViolationSliceWithBLOBs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getViolationId() == null ? other.getViolationId() == null : this.getViolationId().equals(other.getViolationId()))
            && (this.getSnippet() == null ? other.getSnippet() == null : this.getSnippet().equals(other.getSnippet()))
            && (this.getSnapshot() == null ? other.getSnapshot() == null : this.getSnapshot().equals(other.getSnapshot()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getViolationId() == null) ? 0 : getViolationId().hashCode());
        result = prime * result + ((getSnippet() == null) ? 0 : getSnippet().hashCode());
        result = prime * result + ((getSnapshot() == null) ? 0 : getSnapshot().hashCode());
        return result;
    }
}