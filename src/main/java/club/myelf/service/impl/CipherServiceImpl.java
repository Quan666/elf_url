package club.myelf.service.impl;

import club.myelf.entity.*;
import club.myelf.common.PageList;
import club.myelf.dao.*;
import club.myelf.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层实现类
 * CipherServiceImpl
 * @author quan666
 * @date 2020/06/22
 */
@Service
public class CipherServiceImpl implements CipherService {

    @Autowired
	CipherDao dao;

    @Override
    public int insert(Cipher cipher) {

        return dao.insert(cipher);
    }

    @Override
    public int batchInsert(List<Cipher> list) {
    	return dao.batchInsert(list);
    }

    @Override
    public int update(Cipher cipher) {
    	return dao.update(cipher);
    }

    @Override
    public int delete(Object key) {
    	return dao.delete(key);
    }

    @Override
    public int batchDelete(List<Object> keys) {
        return dao.batchDelete(keys);
    }

	@Override
	public Cipher selectByKey(Object key) {
		return dao.selectByKey(key);
	}

	@Override
	public List<Cipher> selectList(Cipher cipher) {
		return dao.selectList(cipher);
	}

	@Override
	public PageList<Cipher> selectPage(Cipher cipher, Integer offset, Integer pageSize) {
		PageList<Cipher> pageList = new PageList<>();

		int total = this.total(cipher);

		Integer totalPage;
		if (total % pageSize != 0) {
			totalPage = (total /pageSize) + 1;
		} else {
			totalPage = total /pageSize;
		}

		int page = (offset - 1) * pageSize;

		List<Cipher> list = dao.selectPage(cipher, page, pageSize);

		pageList.setList(list);
		pageList.setStartPageNo(offset);
		pageList.setPageSize(pageSize);
		pageList.setTotalCount(total);
		pageList.setTotalPageCount(totalPage);
		return pageList;
	}

	@Override
	public int total(Cipher cipher) {
		return dao.total(cipher);
	}
}