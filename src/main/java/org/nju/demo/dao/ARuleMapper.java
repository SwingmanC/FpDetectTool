package org.nju.demo.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.nju.demo.entity.ARule;
import org.nju.demo.entity.ARuleExample;

public interface ARuleMapper {
    @SelectProvider(type=ARuleSqlProvider.class, method="countByExample")
    long countByExample(ARuleExample example);

    @DeleteProvider(type=ARuleSqlProvider.class, method="deleteByExample")
    int deleteByExample(ARuleExample example);

    @Delete({
        "delete from a_rule",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into a_rule (rule_name, type_name, ",
        "priority, category, ",
        "class_name, method_name, ",
        "line_length, create_time, ",
        "`state`, version_id)",
        "values (#{ruleName,jdbcType=VARCHAR}, #{typeName,jdbcType=VARCHAR}, ",
        "#{priority,jdbcType=INTEGER}, #{category,jdbcType=VARCHAR}, ",
        "#{className,jdbcType=VARCHAR}, #{methodName,jdbcType=VARCHAR}, ",
        "#{lineLength,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{state,jdbcType=INTEGER}, #{versionId,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(ARule record);

    @InsertProvider(type=ARuleSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(ARule record);

    @SelectProvider(type=ARuleSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="rule_name", property="ruleName", jdbcType=JdbcType.VARCHAR),
        @Result(column="type_name", property="typeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="priority", property="priority", jdbcType=JdbcType.INTEGER),
        @Result(column="category", property="category", jdbcType=JdbcType.VARCHAR),
        @Result(column="class_name", property="className", jdbcType=JdbcType.VARCHAR),
        @Result(column="method_name", property="methodName", jdbcType=JdbcType.VARCHAR),
        @Result(column="line_length", property="lineLength", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="state", property="state", jdbcType=JdbcType.INTEGER),
        @Result(column="version_id", property="versionId", jdbcType=JdbcType.VARCHAR)
    })
    List<ARule> selectByExample(ARuleExample example);

    @Select({
        "select",
        "id, rule_name, type_name, priority, category, class_name, method_name, line_length, ",
        "create_time, `state`, version_id",
        "from a_rule",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="rule_name", property="ruleName", jdbcType=JdbcType.VARCHAR),
        @Result(column="type_name", property="typeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="priority", property="priority", jdbcType=JdbcType.INTEGER),
        @Result(column="category", property="category", jdbcType=JdbcType.VARCHAR),
        @Result(column="class_name", property="className", jdbcType=JdbcType.VARCHAR),
        @Result(column="method_name", property="methodName", jdbcType=JdbcType.VARCHAR),
        @Result(column="line_length", property="lineLength", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="state", property="state", jdbcType=JdbcType.INTEGER),
        @Result(column="version_id", property="versionId", jdbcType=JdbcType.VARCHAR)
    })
    ARule selectByPrimaryKey(Integer id);

    @UpdateProvider(type=ARuleSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ARule record, @Param("example") ARuleExample example);

    @UpdateProvider(type=ARuleSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ARule record, @Param("example") ARuleExample example);

    @UpdateProvider(type=ARuleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ARule record);

    @Update({
        "update a_rule",
        "set rule_name = #{ruleName,jdbcType=VARCHAR},",
          "type_name = #{typeName,jdbcType=VARCHAR},",
          "priority = #{priority,jdbcType=INTEGER},",
          "category = #{category,jdbcType=VARCHAR},",
          "class_name = #{className,jdbcType=VARCHAR},",
          "method_name = #{methodName,jdbcType=VARCHAR},",
          "line_length = #{lineLength,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "`state` = #{state,jdbcType=INTEGER},",
          "version_id = #{versionId,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ARule record);
}