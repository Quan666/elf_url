package club.myelf.dao;

import club.myelf.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * dao层接口
 * UrlDao
 * @author quan666
 * @date 2020/06/21
 */
@Mapper
@Component
public interface UrlDao {

    /**
     * [新增]
     **/
    int insert(Url url);

    /**
     * [批量新增]
     **/
    int batchInsert(List<Url> list);

    /**
     * [更新]
     **/
    int update(Url url);

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
    Url selectByKey(Object key);

    /**
     * [条件查询]
     **/
    List<Url> selectList (Url url);

    /**
     * [分页条件查询]
     **/
    List<Url> selectPage (@Param("url") Url url, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(Url url);
}
