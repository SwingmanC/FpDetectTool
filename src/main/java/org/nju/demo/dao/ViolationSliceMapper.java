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
import org.nju.demo.entity.ViolationSlice;
import org.nju.demo.entity.ViolationSliceExample;
import org.nju.demo.entity.ViolationSliceWithBLOBs;

public interface ViolationSliceMapper {
    @SelectProvider(type=ViolationSliceSqlProvider.class, method="countByExample")
    long countByExample(ViolationSliceExample example);

    @DeleteProvider(type=ViolationSliceSqlProvider.class, method="deleteByExample")
    int deleteByExample(ViolationSliceExample example);

    @Delete({
        "delete from violation_slice",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into violation_slice (violation_id, snippet, ",
        "snapshot)",
        "values (#{violationId,jdbcType=INTEGER}, #{snippet,jdbcType=LONGVARCHAR}, ",
        "#{snapshot,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(ViolationSliceWithBLOBs record);

    @InsertProvider(type=ViolationSliceSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(ViolationSliceWithBLOBs record);

    @SelectProvider(type=ViolationSliceSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="violation_id", property="violationId", jdbcType=JdbcType.INTEGER),
        @Result(column="snippet", property="snippet", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="snapshot", property="snapshot", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<ViolationSliceWithBLOBs> selectByExampleWithBLOBs(ViolationSliceExample example);

    @SelectProvider(type=ViolationSliceSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="violation_id", property="violationId", jdbcType=JdbcType.INTEGER)
    })
    List<ViolationSlice> selectByExample(ViolationSliceExample example);

    @Select({
        "select",
        "id, violation_id, snippet, snapshot",
        "from violation_slice",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="violation_id", property="violationId", jdbcType=JdbcType.INTEGER),
        @Result(column="snippet", property="snippet", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="snapshot", property="snapshot", jdbcType=JdbcType.LONGVARCHAR)
    })
    ViolationSliceWithBLOBs selectByPrimaryKey(Integer id);

    @UpdateProvider(type=ViolationSliceSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ViolationSliceWithBLOBs record, @Param("example") ViolationSliceExample example);

    @UpdateProvider(type=ViolationSliceSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") ViolationSliceWithBLOBs record, @Param("example") ViolationSliceExample example);

    @UpdateProvider(type=ViolationSliceSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ViolationSlice record, @Param("example") ViolationSliceExample example);

    @UpdateProvider(type=ViolationSliceSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ViolationSliceWithBLOBs record);

    @Update({
        "update violation_slice",
        "set violation_id = #{violationId,jdbcType=INTEGER},",
          "snippet = #{snippet,jdbcType=LONGVARCHAR},",
          "snapshot = #{snapshot,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(ViolationSliceWithBLOBs record);

    @Update({
        "update violation_slice",
        "set violation_id = #{violationId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ViolationSlice record);
}