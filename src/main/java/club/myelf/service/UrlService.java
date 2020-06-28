package club.myelf.service;

import java.util.Map;
import java.util.List;
import club.myelf.entity.*;
import club.myelf.common.PageList;

/**
 * 业务层
 * UrlService
 * @author quan666
 * @date 2020/06/21
 */
public interface UrlService {

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
    int batchDelete(List<Object> keys);

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
    PageList<Url> selectPage (Url url, Integer page, Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(Url url);
}
