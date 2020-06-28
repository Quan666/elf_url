package club.myelf.service;

import java.util.Map;
import java.util.List;
import club.myelf.entity.*;
import club.myelf.common.PageList;

/**
 * 业务层
 * UserService
 * @author quan666
 * @date 2020/06/22
 */
public interface UserService {

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
    int batchDelete(List<Object> keys);

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
    PageList<User> selectPage (User user, Integer page, Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(User user);
}
