package club.myelf.service;

import java.util.Map;
import java.util.List;
import club.myelf.entity.*;
import club.myelf.common.PageList;

/**
 * 业务层
 * OnereadService
 * @author quan666
 * @date 2020/06/22
 */
public interface OnereadService {

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
    int batchDelete(List<Object> keys);

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
    PageList<Oneread> selectPage (Oneread oneread, Integer page, Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(Oneread oneread);
}
