package club.myelf.web;

import club.myelf.annotation.Forbid;
import club.myelf.annotation.NeedLogin;
import club.myelf.url.ShortUrlGenerator;
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
import java.util.Date;
import java.util.List;

/**
 * 控制层
 * OnereadController
 * @author quan666
 * @date 2020/06/22
 */
@Slf4j
@Controller
@RequestMapping(value = "/oneread")
public class OnereadController {

    @Autowired
    OnereadService onereadService;
    @Autowired
    UrlService urlService;

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
    public ApiResult insert (@RequestBody Oneread oneread, HttpServletRequest request) {
        if(null==oneread.getMessage()||("").equals(oneread.getMessage())){
            return new ApiResult<>(ResultCode.failed.getCode(),"输入内容！", ResultCode.failed.getDescr(), request.getRequestURI());
        }
        String code = ShortUrlGenerator.shortUrl((""+oneread.getMessage()+(new Date()).hashCode()))[0];
        oneread.setCode(code);
        int affectRows = onereadService.insert(oneread);
        StringBuffer baseUri = request.getRequestURL();
        String uri = baseUri.substring(0, baseUri.indexOf("/",8)+1);
        Url url = new Url();
        url.setUrl(uri+"oneread.html?code="+ code);
        log.info(url.getUrl());
        urlService.insert(url);
        Url ResultUrl = urlService.selectByKey(url);
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
    public ApiResult batchInsert (@RequestBody List<Oneread> list, HttpServletRequest request) {
        int affectRows = onereadService.batchInsert(list);
        return new ApiResult<>(ResultCode.success.getCode(), affectRows, ResultCode.success.getDescr(), request.getRequestURI());
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
    public ApiResult update (@RequestBody Oneread oneread, HttpServletRequest request) {
        int affectRows = onereadService.update(oneread);
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
        int affectRows = onereadService.delete(key);
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
        int affectRows = onereadService.batchDelete(keys);
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
    public ApiResult selectByKey (@RequestBody Oneread key, HttpServletRequest request) {
        Oneread oneread = onereadService.selectByKey(key);
        if(oneread==null){
            oneread = new Oneread();
            oneread.setMessage("信息不存在！");
        }
        if(oneread.getStatus()==0){
            oneread.setStatus(1);
            onereadService.update(oneread);
        }else{
            oneread.setMessage("信息已销毁！");
        }
        return new ApiResult<>(ResultCode.success.getCode(), oneread, ResultCode.success.getDescr(), request.getRequestURI());
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
    public ApiResult selectList (@RequestBody Oneread oneread, HttpServletRequest request) {
        List<Oneread> result = onereadService.selectList(oneread);
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

        Oneread oneread = object.toJavaObject(Oneread.class);
        PageList<Oneread> pageList = onereadService.selectPage(oneread, page, pageSize);
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
        Oneread query = new Oneread();
        if (StringUtils.isNotBlank(searchParams)) {
            JSONObject object = JSON.parseObject(searchParams);
            query = object.toJavaObject(Oneread.class);
        }

        PageList<Oneread> pageList = onereadService.selectPage(query, page, limit);
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
    public Oneread formSelectByKey(@RequestParam(value = "key", required = false) long  key) {
        Oneread oneread = new Oneread();
        oneread.setId(key);
        return onereadService.selectByKey(oneread);
    }

    /***
     * 表单插入
     * @param params Bean对象JSON字符串
     */
    @NeedLogin
    @RequestMapping(value = "/formInsert")
    @ResponseBody
    public String formInsert(@RequestParam(value = "params", required = false) String  params, HttpServletRequest request) {
        Oneread oneread = null;
        if (StringUtils.isNotBlank(params)) {
            JSONObject object = JSON.parseObject(params);
            oneread = object.toJavaObject(Oneread.class);
        }

        int rows = 0;


        if(null==oneread.getMessage()||("").equals(oneread.getMessage())){
            rows=0;
        }else{
            String code = ShortUrlGenerator.shortUrl((""+oneread.getMessage()+(new Date()).hashCode()))[0];
            oneread.setCode(code);
            rows = onereadService.insert(oneread);
            StringBuffer baseUri = request.getRequestURL();
            String uri = baseUri.substring(0, baseUri.indexOf("/",8)+1);
            Url url = new Url();
            url.setUrl(uri+"oneread.html?code="+ code);
            log.info(url.getUrl());
            urlService.insert(url);
        }



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
        Oneread update = null;
        if (StringUtils.isNotBlank(params)) {
            JSONObject object = JSON.parseObject(params);
            update = object.toJavaObject(Oneread.class);
        }

        int rows = onereadService.update(update);

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
        return onereadService.delete(key);
    }
}
