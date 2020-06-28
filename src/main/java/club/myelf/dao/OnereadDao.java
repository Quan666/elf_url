package club.myelf.dao;

import club.myelf.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * dao层接口
 * OnereadDao
 * @author quan666
 * @date 2020/06/22
 */
@Mapper
public interface OnereadDao {

    /**
     * [新增]
     **/
    int insert(Oneread oneread);

    /**
     * [批量新增]
     **/
    int batchInsert(List<Oneread> list);

    /**
     * [更新]
     **/
    int update(Oneread oneread);

    /**
     * [删除]
     **/
    int delete(Object key);

    /**
     * [批量删除]
     **/
    int batchDelete(List<Object> list);

    /**
     * [主键查询]
     **/
    Oneread selectByKey(Object key);

    /**
     * [条件查询]
     **/
    List<Oneread> selectList (Oneread oneread);

    /**
     * [分页条件查询]
     **/
    List<Oneread> selectPage (@Param("oneread") Oneread oneread, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(Oneread oneread);
}
