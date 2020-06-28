package club.myelf.dao;

import club.myelf.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * dao层接口
 * UserDao
 * @author quan666
 * @date 2020/06/22
 */
@Mapper
@Component
public interface UserDao {

    /**
     * [新增]
     **/
    int insert(User user);

    /**
     * [批量新增]
     **/
    int batchInsert(List<User> list);

    /**
     * [更新]
     **/
    int update(User user);

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
    User selectByKey(Object key);

    /**
     * [条件查询]
     **/
    List<User> selectList (User user);

    /**
     * [分页条件查询]
     **/
    List<User> selectPage (@Param("user") User user, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(User user);
}
