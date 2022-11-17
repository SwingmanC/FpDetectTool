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
import org.nju.demo.entity.ViolationCode;
import org.nju.demo.entity.ViolationCodeExample;

public interface ViolationCodeMapper {
    @SelectProvider(type=ViolationCodeSqlProvider.class, method="countByExample")
    long countByExample(ViolationCodeExample example);

    @DeleteProvider(type=ViolationCodeSqlProvider.class, method="deleteByExample")
    int deleteByExample(ViolationCodeExample example);

    @Delete({
        "delete from violation_code",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into violation_code (violation_id, snippet)",
        "values (#{violationId,jdbcType=INTEGER}, #{snippet,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(ViolationCode record);

    @InsertProvider(type=ViolationCodeSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(ViolationCode record);

    @SelectProvider(type=ViolationCodeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="violation_id", property="violationId", jdbcType=JdbcType.INTEGER),
        @Result(column="snippet", property="snippet", jdbcType=JdbcType.VARCHAR)
    })
    List<ViolationCode> selectByExample(ViolationCodeExample example);

    @Select({
        "select",
        "id, violation_id, snippet",
        "from violation_code",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="violation_id", property="violationId", jdbcType=JdbcType.INTEGER),
        @Result(column="snippet", property="snippet", jdbcType=JdbcType.VARCHAR)
    })
    ViolationCode selectByPrimaryKey(Integer id);

    @UpdateProvider(type=ViolationCodeSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ViolationCode record, @Param("example") ViolationCodeExample example);

    @UpdateProvider(type=ViolationCodeSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ViolationCode record, @Param("example") ViolationCodeExample example);

    @UpdateProvider(type=ViolationCodeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ViolationCode record);

    @Update({
        "update violation_code",
        "set violation_id = #{violationId,jdbcType=INTEGER},",
          "snippet = #{snippet,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ViolationCode record);

    @Select({
            "select",
            "id, violation_id, snippet",
            "from violation_code",
            "where violation_id = #{violationId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="violation_id", property="violationId", jdbcType=JdbcType.INTEGER),
            @Result(column="snippet", property="snippet", jdbcType=JdbcType.VARCHAR)
    })
    ViolationCode selectCodeByViolationId(Integer violationId);
}