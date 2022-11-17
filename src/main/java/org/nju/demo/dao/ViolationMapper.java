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
import org.nju.demo.entity.Violation;
import org.nju.demo.entity.ViolationExample;

public interface ViolationMapper {
    @SelectProvider(type=ViolationSqlProvider.class, method="countByExample")
    long countByExample(ViolationExample example);

    @DeleteProvider(type=ViolationSqlProvider.class, method="deleteByExample")
    int deleteByExample(ViolationExample example);

    @Delete({
        "delete from violation",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into violation (version_id, `type`, ",
        "category, priority, ",
        "class_name, source_path, ",
        "method_name, signature, ",
        "start_line, end_line, ",
        "`state`)",
        "values (#{versionId,jdbcType=VARCHAR}, #{type,jdbcType=VARCHAR}, ",
        "#{category,jdbcType=VARCHAR}, #{priority,jdbcType=INTEGER}, ",
        "#{className,jdbcType=VARCHAR}, #{sourcePath,jdbcType=VARCHAR}, ",
        "#{methodName,jdbcType=VARCHAR}, #{signature,jdbcType=VARCHAR}, ",
        "#{startLine,jdbcType=INTEGER}, #{endLine,jdbcType=INTEGER}, ",
        "#{state,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Violation record);

    @InsertProvider(type=ViolationSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(Violation record);

    @SelectProvider(type=ViolationSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="version_id", property="versionId", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="category", property="category", jdbcType=JdbcType.VARCHAR),
        @Result(column="priority", property="priority", jdbcType=JdbcType.INTEGER),
        @Result(column="class_name", property="className", jdbcType=JdbcType.VARCHAR),
        @Result(column="source_path", property="sourcePath", jdbcType=JdbcType.VARCHAR),
        @Result(column="method_name", property="methodName", jdbcType=JdbcType.VARCHAR),
        @Result(column="signature", property="signature", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_line", property="startLine", jdbcType=JdbcType.INTEGER),
        @Result(column="end_line", property="endLine", jdbcType=JdbcType.INTEGER),
        @Result(column="state", property="state", jdbcType=JdbcType.INTEGER)
    })
    List<Violation> selectByExample(ViolationExample example);

    @Select({
        "select",
        "id, version_id, `type`, category, priority, class_name, source_path, method_name, ",
        "signature, start_line, end_line, `state`",
        "from violation",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="version_id", property="versionId", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="category", property="category", jdbcType=JdbcType.VARCHAR),
        @Result(column="priority", property="priority", jdbcType=JdbcType.INTEGER),
        @Result(column="class_name", property="className", jdbcType=JdbcType.VARCHAR),
        @Result(column="source_path", property="sourcePath", jdbcType=JdbcType.VARCHAR),
        @Result(column="method_name", property="methodName", jdbcType=JdbcType.VARCHAR),
        @Result(column="signature", property="signature", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_line", property="startLine", jdbcType=JdbcType.INTEGER),
        @Result(column="end_line", property="endLine", jdbcType=JdbcType.INTEGER),
        @Result(column="state", property="state", jdbcType=JdbcType.INTEGER)
    })
    Violation selectByPrimaryKey(Integer id);

    @UpdateProvider(type=ViolationSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Violation record, @Param("example") ViolationExample example);

    @UpdateProvider(type=ViolationSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Violation record, @Param("example") ViolationExample example);

    @UpdateProvider(type=ViolationSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Violation record);

    @Update({
        "update violation",
        "set version_id = #{versionId,jdbcType=VARCHAR},",
          "`type` = #{type,jdbcType=VARCHAR},",
          "category = #{category,jdbcType=VARCHAR},",
          "priority = #{priority,jdbcType=INTEGER},",
          "class_name = #{className,jdbcType=VARCHAR},",
          "source_path = #{sourcePath,jdbcType=VARCHAR},",
          "method_name = #{methodName,jdbcType=VARCHAR},",
          "signature = #{signature,jdbcType=VARCHAR},",
          "start_line = #{startLine,jdbcType=INTEGER},",
          "end_line = #{endLine,jdbcType=INTEGER},",
          "`state` = #{state,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Violation record);

    @Select({
        "select id from violation order by id desc limit 1"
    })
    Integer selectLastId();
}