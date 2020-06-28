package club.myelf.service;

import java.util.Map;
import java.util.List;
import club.myelf.entity.*;
import club.myelf.common.PageList;

/**
 * 业务层
 * CipherService
 * @author quan666
 * @date 2020/06/22
 */
public interface CipherService {

    /**
     * [新增]
     **/
    int insert(Cipher cipher);

    /**
     * [批量新增]
     **/
    int batchInsert(List<Cipher> list);

    /**
     * [更新]
     **/
    int update(Cipher cipher);

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
    Cipher selectByKey(Object key);

    /**
     * [条件查询]
     **/
    List<Cipher> selectList (Cipher cipher);

    /**
     * [分页条件查询]
     **/
    PageList<Cipher> selectPage (Cipher cipher, Integer page, Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(Cipher cipher);
}
