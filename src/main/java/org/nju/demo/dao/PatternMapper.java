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
import org.nju.demo.entity.Pattern;
import org.nju.demo.entity.PatternExample;

public interface PatternMapper {
    @SelectProvider(type=PatternSqlProvider.class, method="countByExample")
    long countByExample(PatternExample example);

    @DeleteProvider(type=PatternSqlProvider.class, method="deleteByExample")
    int deleteByExample(PatternExample example);

    @Delete({
        "delete from pattern",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into pattern (pattern_name, category_id, ",
        "t_num, f_num)",
        "values (#{patternName,jdbcType=VARCHAR}, #{categoryId,jdbcType=INTEGER}, ",
        "#{tNum,jdbcType=INTEGER}, #{fNum,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Pattern record);

    @InsertProvider(type=PatternSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(Pattern record);

    @SelectProvider(type=PatternSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="pattern_name", property="patternName", jdbcType=JdbcType.VARCHAR),
        @Result(column="category_id", property="categoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="t_num", property="tNum", jdbcType=JdbcType.INTEGER),
        @Result(column="f_num", property="fNum", jdbcType=JdbcType.INTEGER)
    })
    List<Pattern> selectByExample(PatternExample example);

    @Select({
        "select",
        "id, pattern_name, category_id, t_num, f_num",
        "from pattern",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="pattern_name", property="patternName", jdbcType=JdbcType.VARCHAR),
        @Result(column="category_id", property="categoryId", jdbcType=JdbcType.INTEGER),
        @Result(column="t_num", property="tNum", jdbcType=JdbcType.INTEGER),
        @Result(column="f_num", property="fNum", jdbcType=JdbcType.INTEGER)
    })
    Pattern selectByPrimaryKey(Integer id);

    @UpdateProvider(type=PatternSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Pattern record, @Param("example") PatternExample example);

    @UpdateProvider(type=PatternSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Pattern record, @Param("example") PatternExample example);

    @UpdateProvider(type=PatternSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Pattern record);

    @Update({
        "update pattern",
        "set pattern_name = #{patternName,jdbcType=VARCHAR},",
          "category_id = #{categoryId,jdbcType=INTEGER},",
          "t_num = #{tNum,jdbcType=INTEGER},",
          "f_num = #{fNum,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Pattern record);

    @Select({
            "select",
            "id, pattern_name, category_id, t_num, f_num",
            "from pattern",
            "where pattern_name like concat('%',#{keyword,jdbcType=VARCHAR},'%')"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="pattern_name", property="patternName", jdbcType=JdbcType.VARCHAR),
            @Result(column="category_id", property="categoryId", jdbcType=JdbcType.INTEGER),
            @Result(column="t_num", property="tNum", jdbcType=JdbcType.INTEGER),
            @Result(column="f_num", property="fNum", jdbcType=JdbcType.INTEGER)
    })
    List<Pattern> selectByKeyword(String keyword);

    @Select({
            "select",
            "id, pattern_name, category_id, t_num, f_num",
            "from pattern",
            "where pattern_name = #{patternName,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="pattern_name", property="patternName", jdbcType=JdbcType.VARCHAR),
            @Result(column="category_id", property="categoryId", jdbcType=JdbcType.INTEGER),
            @Result(column="t_num", property="tNum", jdbcType=JdbcType.INTEGER),
            @Result(column="f_num", property="fNum", jdbcType=JdbcType.INTEGER)
    })
    Integer getPatternIdByPatternName(String patternName);
}