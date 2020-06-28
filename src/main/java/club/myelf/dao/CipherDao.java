package club.myelf.dao;

import club.myelf.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * dao层接口
 * CipherDao
 * @author quan666
 * @date 2020/06/22
 */
@Mapper
@Component
public interface CipherDao {

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
    int batchDelete(List<Object> list);

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
    List<Cipher> selectPage (@Param("cipher") Cipher cipher, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

    /**
     * [总量查询]
     **/
    int total(Cipher cipher);
}
