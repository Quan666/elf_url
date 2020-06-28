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
 * OnereadServiceImpl
 * @author quan666
 * @date 2020/06/22
 */
@Service
public class OnereadServiceImpl implements OnereadService {

    @Autowired
	OnereadDao dao;

    @Override
    public int insert(Oneread oneread) {
        return dao.insert(oneread);
    }

    @Override
    public int batchInsert(List<Oneread> list) {
    	return dao.batchInsert(list);
    }

    @Override
    public int update(Oneread oneread) {
    	return dao.update(oneread);
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
	public Oneread selectByKey(Object key) {
		return dao.selectByKey(key);
	}

	@Override
	public List<Oneread> selectList(Oneread oneread) {
		return dao.selectList(oneread);
	}

	@Override
	public PageList<Oneread> selectPage(Oneread oneread, Integer offset, Integer pageSize) {
		PageList<Oneread> pageList = new PageList<>();

		int total = this.total(oneread);

		Integer totalPage;
		if (total % pageSize != 0) {
			totalPage = (total /pageSize) + 1;
		} else {
			totalPage = total /pageSize;
		}

		int page = (offset - 1) * pageSize;

		List<Oneread> list = dao.selectPage(oneread, page, pageSize);

		pageList.setList(list);
		pageList.setStartPageNo(offset);
		pageList.setPageSize(pageSize);
		pageList.setTotalCount(total);
		pageList.setTotalPageCount(totalPage);
		return pageList;
	}

	@Override
	public int total(Oneread oneread) {
		return dao.total(oneread);
	}
}