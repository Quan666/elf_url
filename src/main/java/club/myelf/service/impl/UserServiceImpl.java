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
 * UserServiceImpl
 * @author quan666
 * @date 2020/06/22
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
	UserDao dao;

    @Override
    public int insert(User user) {
        return dao.insert(user);
    }

    @Override
    public int batchInsert(List<User> list) {
    	return dao.batchInsert(list);
    }

    @Override
    public int update(User user) {
    	return dao.update(user);
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
	public User selectByKey(Object key) {
		return dao.selectByKey(key);
	}

	@Override
	public List<User> selectList(User user) {
		return dao.selectList(user);
	}

	@Override
	public PageList<User> selectPage(User user, Integer offset, Integer pageSize) {
		PageList<User> pageList = new PageList<>();

		int total = this.total(user);

		Integer totalPage;
		if (total % pageSize != 0) {
			totalPage = (total /pageSize) + 1;
		} else {
			totalPage = total /pageSize;
		}

		int page = (offset - 1) * pageSize;

		List<User> list = dao.selectPage(user, page, pageSize);

		pageList.setList(list);
		pageList.setStartPageNo(offset);
		pageList.setPageSize(pageSize);
		pageList.setTotalCount(total);
		pageList.setTotalPageCount(totalPage);
		return pageList;
	}

	@Override
	public int total(User user) {
		return dao.total(user);
	}
}