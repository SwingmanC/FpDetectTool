package org.nju.demo.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.nju.demo.entity.ARule;
import org.nju.demo.entity.ARuleExample.Criteria;
import org.nju.demo.entity.ARuleExample.Criterion;
import org.nju.demo.entity.ARuleExample;

public class ARuleSqlProvider {

    public String countByExample(ARuleExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("a_rule");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(ARuleExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("a_rule");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(ARule record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("a_rule");
        
        if (record.getRuleName() != null) {
            sql.VALUES("rule_name", "#{ruleName,jdbcType=VARCHAR}");
        }
        
        if (record.getTypeName() != null) {
            sql.VALUES("type_name", "#{typeName,jdbcType=VARCHAR}");
        }
        
        if (record.getPriority() != null) {
            sql.VALUES("priority", "#{priority,jdbcType=INTEGER}");
        }
        
        if (record.getCategory() != null) {
            sql.VALUES("category", "#{category,jdbcType=VARCHAR}");
        }
        
        if (record.getClassName() != null) {
            sql.VALUES("class_name", "#{className,jdbcType=VARCHAR}");
        }
        
        if (record.getMethodName() != null) {
            sql.VALUES("method_name", "#{methodName,jdbcType=VARCHAR}");
        }
        
        if (record.getLineLength() != null) {
            sql.VALUES("line_length", "#{lineLength,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getState() != null) {
            sql.VALUES("`state`", "#{state,jdbcType=INTEGER}");
        }
        
        if (record.getVersionId() != null) {
            sql.VALUES("version_id", "#{versionId,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectByExample(ARuleExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("rule_name");
        sql.SELECT("type_name");
        sql.SELECT("priority");
        sql.SELECT("category");
        sql.SELECT("class_name");
        sql.SELECT("method_name");
        sql.SELECT("line_length");
        sql.SELECT("create_time");
        sql.SELECT("`state`");
        sql.SELECT("version_id");
        sql.FROM("a_rule");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        ARule record = (ARule) parameter.get("record");
        ARuleExample example = (ARuleExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("a_rule");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getRuleName() != null) {
            sql.SET("rule_name = #{record.ruleName,jdbcType=VARCHAR}");
        }
        
        if (record.getTypeName() != null) {
            sql.SET("type_name = #{record.typeName,jdbcType=VARCHAR}");
        }
        
        if (record.getPriority() != null) {
            sql.SET("priority = #{record.priority,jdbcType=INTEGER}");
        }
        
        if (record.getCategory() != null) {
            sql.SET("category = #{record.category,jdbcType=VARCHAR}");
        }
        
        if (record.getClassName() != null) {
            sql.SET("class_name = #{record.className,jdbcType=VARCHAR}");
        }
        
        if (record.getMethodName() != null) {
            sql.SET("method_name = #{record.methodName,jdbcType=VARCHAR}");
        }
        
        if (record.getLineLength() != null) {
            sql.SET("line_length = #{record.lineLength,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getState() != null) {
            sql.SET("`state` = #{record.state,jdbcType=INTEGER}");
        }
        
        if (record.getVersionId() != null) {
            sql.SET("version_id = #{record.versionId,jdbcType=VARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("a_rule");
        
        sql.SET("id = #{record.id,jdbcType=INTEGER}");
        sql.SET("rule_name = #{record.ruleName,jdbcType=VARCHAR}");
        sql.SET("type_name = #{record.typeName,jdbcType=VARCHAR}");
        sql.SET("priority = #{record.priority,jdbcType=INTEGER}");
        sql.SET("category = #{record.category,jdbcType=VARCHAR}");
        sql.SET("class_name = #{record.className,jdbcType=VARCHAR}");
        sql.SET("method_name = #{record.methodName,jdbcType=VARCHAR}");
        sql.SET("line_length = #{record.lineLength,jdbcType=INTEGER}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("`state` = #{record.state,jdbcType=INTEGER}");
        sql.SET("version_id = #{record.versionId,jdbcType=VARCHAR}");
        
        ARuleExample example = (ARuleExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(ARule record) {
        SQL sql = new SQL();
        sql.UPDATE("a_rule");
        
        if (record.getRuleName() != null) {
            sql.SET("rule_name = #{ruleName,jdbcType=VARCHAR}");
        }
        
        if (record.getTypeName() != null) {
            sql.SET("type_name = #{typeName,jdbcType=VARCHAR}");
        }
        
        if (record.getPriority() != null) {
            sql.SET("priority = #{priority,jdbcType=INTEGER}");
        }
        
        if (record.getCategory() != null) {
            sql.SET("category = #{category,jdbcType=VARCHAR}");
        }
        
        if (record.getClassName() != null) {
            sql.SET("class_name = #{className,jdbcType=VARCHAR}");
        }
        
        if (record.getMethodName() != null) {
            sql.SET("method_name = #{methodName,jdbcType=VARCHAR}");
        }
        
        if (record.getLineLength() != null) {
            sql.SET("line_length = #{lineLength,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getState() != null) {
            sql.SET("`state` = #{state,jdbcType=INTEGER}");
        }
        
        if (record.getVersionId() != null) {
            sql.SET("version_id = #{versionId,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, ARuleExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}