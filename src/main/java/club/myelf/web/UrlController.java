package club.myelf.web;

import club.myelf.annotation.Forbid;
import club.myelf.annotation.NeedLogin;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import club.myelf.entity.*;
import club.myelf.common.ApiResult;
import club.myelf.common.PageList;
import club.myelf.common.ResultCode;
import club.myelf.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 控制层
 * UrlController
 * @author quan666
 * @date 2020/06/21
 */
@Slf4j
@Controller
@RequestMapping(value = "/url")
public class UrlController {

    @Autowired
    UrlService service;

    /**
     * 具体字段请根据实际情况处理
     * 参数请求报文:
     *
     * {
     *   "paramOne": 1,
     *   "paramTwo": "xxx"
     * }
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public ApiResult insert (@RequestBody Url url, HttpServletRequest request) {
        if(null==url.getUrl()||("").equals(url.getUrl())){
            return new ApiResult<>(ResultCode.failed.getCode(),"输入链接！", ResultCode.failed.getDescr(), request.getRequestURI());
        }
        int affectRows = service.insert(url);
        StringBuffer baseUri = request.getRequestURL();
        String uri = baseUri.substring(0, baseUri.indexOf("/",8)+1);
        Url ResultUrl = service.selectByKey(url);
        ResultUrl.setShortUrl(uri+ResultUrl.getShortUrl());
        return new ApiResult<>(ResultCode.success.getCode(),ResultUrl, ResultCode.success.getDescr(), request.getRequestURI());
    }

    /**
     * 参数请求报文:
     *
     * [
     *     {
     *       "paramOne": 1,
     *       "paramTwo": "xxx"
     *     },
     *     {
     *       "paramOne": 1,
     *       "paramTwo": "xxx"
     *     }
     * ]
     */
    @Forbid
    @RequestMapping(value = "/batchInsert")
    @ResponseBody
    public ApiResult batchInsert (@RequestBody List<Url> list, HttpServletRequest request) {
        int affectRows = service.batchInsert(list);
        if(affectRows>0){
            List<Url> resultList = new ArrayList<>();
            StringBuffer baseUri = request.getRequestURL();
            String uri = baseUri.substring(0, baseUri.indexOf("/",8)+1);
            for(Url url:list){
                Url ResultUrl = service.selectByKey(url);
                ResultUrl.setShortUrl(uri+ResultUrl.getShortUrl());
                resultList.add(ResultUrl);
            }
            return new ApiResult<>(ResultCode.success.getCode(), resultList, ResultCode.success.getDescr(), request.getRequestURI());
        }else {
            return new ApiResult<>(ResultCode.failed.getCode(), affectRows, ResultCode.failed.getDescr(), request.getRequestURI());
        }

    }

    /**
     * 参数请求报文:
     *
     * {
     *   "paramOne": 1,
     *   "paramTwo": "xxx"
     * }
     */
    @Forbid
    @RequestMapping(value = "/update")
    @ResponseBody
    public ApiResult update (@RequestBody Url url, HttpServletRequest request) {
        int affectRows = service.update(url);
        return new ApiResult<>(ResultCode.success.getCode(), affectRows, ResultCode.success.getDescr(), request.getRequestURI());
    }

    /**
     * 参数请求报文:
     *
     * {
     *   "key":1
     * }
     */
    @Forbid
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ApiResult delete (@RequestBody Object key, HttpServletRequest request) {
        int affectRows = service.delete(key);
        return new ApiResult<>(ResultCode.success.getCode(), affectRows, ResultCode.success.getDescr(), request.getRequestURI());
    }

    /**
     * 参数请求报文:
     *
     * [
     *     9,
     *     11
     * ]
     */
    @NeedLogin
    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public ApiResult batchDelete (@RequestBody List<Object> keys, HttpServletRequest request) {
        int affectRows = service.batchDelete(keys);
        log.info(""+affectRows);
        return new ApiResult<>(ResultCode.success.getCode(), affectRows, ResultCode.success.getDescr(), request.getRequestURI());
    }

    /**
     * 参数请求报文:
     *
     * {
     *   "key":1
     * }
     */
    @RequestMapping(value = "/selectByKey")
    @ResponseBody
    public ApiResult selectByKey (@RequestBody Object key, HttpServletRequest request) {
        Url url = service.selectByKey(key);
        return new ApiResult<>(ResultCode.success.getCode(), url, ResultCode.success.getDescr(), request.getRequestURI());
    }

    /***
    * 参数请求报文:
    *
    * {
    *   "paramOne": 1,
    *   "paramTwo": "xxx"
    * }
    */
    @NeedLogin
    @RequestMapping(value = "/selectList")
    @ResponseBody
    public ApiResult selectList (@RequestBody Url url, HttpServletRequest request) {
        List<Url> result = service.selectList(url);
        return new ApiResult<>(ResultCode.success.getCode(), result, ResultCode.success.getDescr(), request.getRequestURI());
    }

    /***
     * 参数请求报文:
     *
     * {
     *   "paramOne": 1,
     *   "paramTwo": "xxx"
     * }
     */
    @NeedLogin
    @RequestMapping(value = "/selectPage")
    @ResponseBody
    public ApiResult selectPage (@RequestBody JSONObject object, HttpServletRequest request) {
        Integer page     = (Integer) object.getOrDefault("page"    , 1);
        Integer pageSize = (Integer) object.getOrDefault("pageSize", 15);

        // 剔除page, pageSize参数
        object.remove("page");
        object.remove("pageSize");

        Url url = object.toJavaObject(Url.class);
        PageList<Url> pageList = service.selectPage(url, page, pageSize);
        return new ApiResult<>(ResultCode.success.getCode(), pageList, ResultCode.success.getDescr(), request.getRequestURI());
    }

    /***
     * 表单查询请求
     * @param searchParams Bean对象JSON字符串
     * @param page         页码
     * @param limit        每页显示数量
     */
    @NeedLogin
    @RequestMapping(value = "/formPage")
    @ResponseBody
    public String formPage (@RequestParam(value = "searchParams", required = false) String  searchParams,
                            @RequestParam(value = "page"        , required = false, defaultValue = "1") Integer page,
                            @RequestParam(value = "limit"       , required = false, defaultValue = "15") Integer limit) {
        Url query = new Url();
        if (StringUtils.isNotBlank(searchParams)) {
            JSONObject object = JSON.parseObject(searchParams);
            query = object.toJavaObject(Url.class);
        }

        PageList<Url> pageList = service.selectPage(query, page, limit);
        JSONObject response = new JSONObject();
        response.put("code" , 0);
        response.put("msg"  , "");
        response.put("data" , null != pageList.getList() ? pageList.getList() : new JSONArray());
        response.put("count", pageList.getTotalCount());
        return response.toString();
    }

    /***
     * 表单查询
     */
    @NeedLogin
    @RequestMapping(value = "/formSelectByKey")
    @ResponseBody
    public Url formSelectByKey(@RequestParam(value = "key", required = false) long key) {
        Url url = new Url();
        url.setId(key);
        return service.selectByKey(url);
    }

    /***
     * 表单插入
     * @param params Bean对象JSON字符串
     */
    @NeedLogin
    @RequestMapping(value = "/formInsert")
    @ResponseBody
    public String formInsert(@RequestParam(value = "params", required = false) String  params) {
        Url insert = null;
        if (StringUtils.isNotBlank(params)) {
            JSONObject object = JSON.parseObject(params);
            insert = object.toJavaObject(Url.class);
        }

        int rows = service.insert(insert);

        JSONObject response = new JSONObject();
        response.put("code" , rows);
        response.put("msg"  , rows > 0 ? "添加成功" : "添加失败");
        return response.toString();
    }

    /***
     * 表单修改
     * @param params Bean对象JSON字符串
     */
    @NeedLogin
    @RequestMapping(value = "/formUpdate")
    @ResponseBody
    public String formUpdate(@RequestParam(value = "params", required = false) String  params) {
        Url update = null;
        if (StringUtils.isNotBlank(params)) {
            JSONObject object = JSON.parseObject(params);
            update = object.toJavaObject(Url.class);
        }

        int rows = service.update(update);

        JSONObject response = new JSONObject();
        response.put("code" , rows);
        response.put("msg"  , rows > 0 ? "修改成功" : "修改失败");
        return response.toString();
    }

    /***
     * 表单删除
     */
    @NeedLogin
    @RequestMapping(value = "/formDelete")
    @ResponseBody
    public int formDelete(@RequestParam(value = "key", required = false) String  key) {
        return service.delete(key);
    }
}
