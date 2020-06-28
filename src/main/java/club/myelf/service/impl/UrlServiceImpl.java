package club.myelf.service.impl;

import club.myelf.entity.*;
import club.myelf.common.PageList;
import club.myelf.dao.*;
import club.myelf.service.*;
import club.myelf.url.CMyEncrypt;
import club.myelf.url.ShortUrlGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 业务层实现类
 * UrlServiceImpl
 * @author quan666
 * @date 2020/06/21
 */
@Slf4j
@Service
public class UrlServiceImpl implements UrlService {

	@Autowired
	private UrlDao dao;

	//增加短链接信息
    @Override
    public int insert(Url url) {
		boolean isMatch = Pattern.matches("^[Hh][Tt][Tt][Pp][Ss]*://(.*)", url.getUrl());
		if(!isMatch) {
			url.setUrl("http://" + url.getUrl());
		}
    	Url UrlLoc = dao.selectByKey(url);
    	if(UrlLoc==null){
			url.setMD5((new CMyEncrypt()).md5("myelf" + url.getUrl()));
			url.setShortUrl(ShortUrlGenerator.shortUrl(url.getUrl())[0]);
			return dao.insert(url);
		}else {
    		return -1;
		}
    }

    //批量增加
    @Override
    public int batchInsert(List<Url> list) {
    	//去除重复元素
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		log.info(""+list.size());
		for(int i=0;i<list.size();i++){
    		Url url = list.get(i);
			boolean isMatch = Pattern.matches("^[Hh][Tt][Tt][Pp][Ss]*://(.*)", url.getUrl());
			if(!isMatch) {
				url.setUrl("http://" + url.getUrl());
			}
			Url UrlLoc = dao.selectByKey(url);
			if(UrlLoc==null){
				url.setMD5((new CMyEncrypt()).md5("myelf" + url.getUrl()));
				url.setShortUrl(ShortUrlGenerator.shortUrl(url.getUrl())[0]);
				list.set(i,url);
			}else {
				list.remove(url);
				i--;
			}
		}
		if(list.size()<=0){
			return -1;
		}
    	return dao.batchInsert(list);
    }

    @Override
    public int update(Url url) {
    	return dao.update(url);
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
	public Url selectByKey(Object key) {
		return dao.selectByKey(key);
	}

	@Override
	public List<Url> selectList(Url url) {
		return dao.selectList(url);
	}

	@Override
	public PageList<Url> selectPage(Url url, Integer offset, Integer pageSize) {
		PageList<Url> pageList = new PageList<>();

		int total = this.total(url);

		Integer totalPage;
		if (total % pageSize != 0) {
			totalPage = (total /pageSize) + 1;
		} else {
			totalPage = total /pageSize;
		}

		int page = (offset - 1) * pageSize;

		List<Url> list = dao.selectPage(url, page, pageSize);

		pageList.setList(list);
		pageList.setStartPageNo(offset);
		pageList.setPageSize(pageSize);
		pageList.setTotalCount(total);
		pageList.setTotalPageCount(totalPage);
		return pageList;
	}

	@Override
	public int total(Url url) {
		return dao.total(url);
	}
}