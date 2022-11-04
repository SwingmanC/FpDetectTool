package org.nju.demo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AVersionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AVersionExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andVersionIdIsNull() {
            addCriterion("version_id is null");
            return (Criteria) this;
        }

        public Criteria andVersionIdIsNotNull() {
            addCriterion("version_id is not null");
            return (Criteria) this;
        }

        public Criteria andVersionIdEqualTo(String value) {
            addCriterion("version_id =", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdNotEqualTo(String value) {
            addCriterion("version_id <>", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdGreaterThan(String value) {
            addCriterion("version_id >", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdGreaterThanOrEqualTo(String value) {
            addCriterion("version_id >=", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdLessThan(String value) {
            addCriterion("version_id <", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdLessThanOrEqualTo(String value) {
            addCriterion("version_id <=", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdLike(String value) {
            addCriterion("version_id like", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdNotLike(String value) {
            addCriterion("version_id not like", value, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdIn(List<String> values) {
            addCriterion("version_id in", values, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdNotIn(List<String> values) {
            addCriterion("version_id not in", values, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdBetween(String value1, String value2) {
            addCriterion("version_id between", value1, value2, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionIdNotBetween(String value1, String value2) {
            addCriterion("version_id not between", value1, value2, "versionId");
            return (Criteria) this;
        }

        public Criteria andVersionNameIsNull() {
            addCriterion("version_name is null");
            return (Criteria) this;
        }

        public Criteria andVersionNameIsNotNull() {
            addCriterion("version_name is not null");
            return (Criteria) this;
        }

        public Criteria andVersionNameEqualTo(String value) {
            addCriterion("version_name =", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameNotEqualTo(String value) {
            addCriterion("version_name <>", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameGreaterThan(String value) {
            addCriterion("version_name >", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameGreaterThanOrEqualTo(String value) {
            addCriterion("version_name >=", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameLessThan(String value) {
            addCriterion("version_name <", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameLessThanOrEqualTo(String value) {
            addCriterion("version_name <=", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameLike(String value) {
            addCriterion("version_name like", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameNotLike(String value) {
            addCriterion("version_name not like", value, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameIn(List<String> values) {
            addCriterion("version_name in", values, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameNotIn(List<String> values) {
            addCriterion("version_name not in", values, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameBetween(String value1, String value2) {
            addCriterion("version_name between", value1, value2, "versionName");
            return (Criteria) this;
        }

        public Criteria andVersionNameNotBetween(String value1, String value2) {
            addCriterion("version_name not between", value1, value2, "versionName");
            return (Criteria) this;
        }

        public Criteria andClassFilePathIsNull() {
            addCriterion("class_file_path is null");
            return (Criteria) this;
        }

        public Criteria andClassFilePathIsNotNull() {
            addCriterion("class_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andClassFilePathEqualTo(String value) {
            addCriterion("class_file_path =", value, "classFilePath");
            return (Criteria) this;
        }

        public Criteria andClassFilePathNotEqualTo(String value) {
            addCriterion("class_file_path <>", value, "classFilePath");
            return (Criteria) this;
        }

        public Criteria andClassFilePathGreaterThan(String value) {
            addCriterion("class_file_path >", value, "classFilePath");
            return (Criteria) this;
        }

        public Criteria andClassFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("class_file_path >=", value, "classFilePath");
            return (Criteria) this;
        }

        public Criteria andClassFilePathLessThan(String value) {
            addCriterion("class_file_path <", value, "classFilePath");
            return (Criteria) this;
        }

        public Criteria andClassFilePathLessThanOrEqualTo(String value) {
            addCriterion("class_file_path <=", value, "classFilePath");
            return (Criteria) this;
        }

        public Criteria andClassFilePathLike(String value) {
            addCriterion("class_file_path like", value, "classFilePath");
            return (Criteria) this;
        }

        public Criteria andClassFilePathNotLike(String value) {
            addCriterion("class_file_path not like", value, "classFilePath");
            return (Criteria) this;
        }

        public Criteria andClassFilePathIn(List<String> values) {
            addCriterion("class_file_path in", values, "classFilePath");
            return (Criteria) this;
        }

        public Criteria andClassFilePathNotIn(List<String> values) {
            addCriterion("class_file_path not in", values, "classFilePath");
            return (Criteria) this;
        }

        public Criteria andClassFilePathBetween(String value1, String value2) {
            addCriterion("class_file_path between", value1, value2, "classFilePath");
            return (Criteria) this;
        }

        public Criteria andClassFilePathNotBetween(String value1, String value2) {
            addCriterion("class_file_path not between", value1, value2, "classFilePath");
            return (Criteria) this;
        }

        public Criteria andJavaFilePathIsNull() {
            addCriterion("java_file_path is null");
            return (Criteria) this;
        }

        public Criteria andJavaFilePathIsNotNull() {
            addCriterion("java_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andJavaFilePathEqualTo(String value) {
            addCriterion("java_file_path =", value, "javaFilePath");
            return (Criteria) this;
        }

        public Criteria andJavaFilePathNotEqualTo(String value) {
            addCriterion("java_file_path <>", value, "javaFilePath");
            return (Criteria) this;
        }

        public Criteria andJavaFilePathGreaterThan(String value) {
            addCriterion("java_file_path >", value, "javaFilePath");
            return (Criteria) this;
        }

        public Criteria andJavaFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("java_file_path >=", value, "javaFilePath");
            return (Criteria) this;
        }

        public Criteria andJavaFilePathLessThan(String value) {
            addCriterion("java_file_path <", value, "javaFilePath");
            return (Criteria) this;
        }

        public Criteria andJavaFilePathLessThanOrEqualTo(String value) {
            addCriterion("java_file_path <=", value, "javaFilePath");
            return (Criteria) this;
        }

        public Criteria andJavaFilePathLike(String value) {
            addCriterion("java_file_path like", value, "javaFilePath");
            return (Criteria) this;
        }

        public Criteria andJavaFilePathNotLike(String value) {
            addCriterion("java_file_path not like", value, "javaFilePath");
            return (Criteria) this;
        }

        public Criteria andJavaFilePathIn(List<String> values) {
            addCriterion("java_file_path in", values, "javaFilePath");
            return (Criteria) this;
        }

        public Criteria andJavaFilePathNotIn(List<String> values) {
            addCriterion("java_file_path not in", values, "javaFilePath");
            return (Criteria) this;
        }

        public Criteria andJavaFilePathBetween(String value1, String value2) {
            addCriterion("java_file_path between", value1, value2, "javaFilePath");
            return (Criteria) this;
        }

        public Criteria andJavaFilePathNotBetween(String value1, String value2) {
            addCriterion("java_file_path not between", value1, value2, "javaFilePath");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(String value) {
            addCriterion("project_id =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(String value) {
            addCriterion("project_id <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(String value) {
            addCriterion("project_id >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("project_id >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(String value) {
            addCriterion("project_id <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(String value) {
            addCriterion("project_id <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLike(String value) {
            addCriterion("project_id like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotLike(String value) {
            addCriterion("project_id not like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<String> values) {
            addCriterion("project_id in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<String> values) {
            addCriterion("project_id not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(String value1, String value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(String value1, String value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andLastIdIsNull() {
            addCriterion("last_id is null");
            return (Criteria) this;
        }

        public Criteria andLastIdIsNotNull() {
            addCriterion("last_id is not null");
            return (Criteria) this;
        }

        public Criteria andLastIdEqualTo(String value) {
            addCriterion("last_id =", value, "lastId");
            return (Criteria) this;
        }

        public Criteria andLastIdNotEqualTo(String value) {
            addCriterion("last_id <>", value, "lastId");
            return (Criteria) this;
        }

        public Criteria andLastIdGreaterThan(String value) {
            addCriterion("last_id >", value, "lastId");
            return (Criteria) this;
        }

        public Criteria andLastIdGreaterThanOrEqualTo(String value) {
            addCriterion("last_id >=", value, "lastId");
            return (Criteria) this;
        }

        public Criteria andLastIdLessThan(String value) {
            addCriterion("last_id <", value, "lastId");
            return (Criteria) this;
        }

        public Criteria andLastIdLessThanOrEqualTo(String value) {
            addCriterion("last_id <=", value, "lastId");
            return (Criteria) this;
        }

        public Criteria andLastIdLike(String value) {
            addCriterion("last_id like", value, "lastId");
            return (Criteria) this;
        }

        public Criteria andLastIdNotLike(String value) {
            addCriterion("last_id not like", value, "lastId");
            return (Criteria) this;
        }

        public Criteria andLastIdIn(List<String> values) {
            addCriterion("last_id in", values, "lastId");
            return (Criteria) this;
        }

        public Criteria andLastIdNotIn(List<String> values) {
            addCriterion("last_id not in", values, "lastId");
            return (Criteria) this;
        }

        public Criteria andLastIdBetween(String value1, String value2) {
            addCriterion("last_id between", value1, value2, "lastId");
            return (Criteria) this;
        }

        public Criteria andLastIdNotBetween(String value1, String value2) {
            addCriterion("last_id not between", value1, value2, "lastId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}