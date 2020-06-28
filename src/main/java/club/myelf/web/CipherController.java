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
 * CipherController
 * @author quan666
 * @date 2020/06/22
 */
@Slf4j
@Controller
@RequestMapping(value = "/cipher")
public class CipherController {

    @Autowired
    CipherService cipherService;
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
    public ApiResult insert (@RequestBody Cipher cipher, HttpServletRequest request) {

        if(null==cipher.getMessage()||("").equals(cipher.getMessage())){
            return new ApiResult<>(ResultCode.failed.getCode(),"输入内容！", ResultCode.failed.getDescr(), request.getRequestURI());
        }
        String code = ShortUrlGenerator.shortUrl((""+cipher.getMessage()+(new Date()).hashCode()))[0];
        cipher.setCode(code);
        int affectRows = cipherService.insert(cipher);
        StringBuffer baseUri = request.getRequestURL();
        String uri = baseUri.substring(0, baseUri.indexOf("/",8)+1);
        Url url = new Url();
        url.setUrl(uri+ "cipher.html?code=" + code);
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
    public ApiResult batchInsert (@RequestBody List<Cipher> list, HttpServletRequest request) {
        int affectRows = cipherService.batchInsert(list);
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
    public ApiResult update (@RequestBody Cipher cipher, HttpServletRequest request) {
        int affectRows = cipherService.update(cipher);
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
        int affectRows = cipherService.delete(key);
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
        int affectRows = cipherService.batchDelete(keys);
        return new ApiResult<>(ResultCode.success.getCode(), affectRows, ResultCode.success.getDescr(), request.getRequestURI());
    }

    /**
     * 参数请求报文:
     *
     * {
     *   "code":"asda",
     *   "passwd":"113"
     * }
     */
    @RequestMapping(value = "/selectByKey")
    @ResponseBody
    public ApiResult selectByKey (@RequestBody Cipher key, HttpServletRequest request) {
        Cipher cipher = cipherService.selectByKey(key);
        if(cipher==null){
            return new ApiResult<>(ResultCode.wrong.getCode(), "密语不存在！", ResultCode.wrong.getDescr(), request.getRequestURI());
        }else{
            if(cipher.getPasswd().equals(key.getPasswd())){
                cipher.setPasswd(null);
                cipher.setId(null);
                return new ApiResult<>(ResultCode.success.getCode(), cipher, ResultCode.success.getDescr(), request.getRequestURI());
            }else{
                cipher.setMessage("密码错误！");
                cipher.setPasswd(null);
                cipher.setId(null);
                return new ApiResult<>(ResultCode.failed.getCode(), cipher, ResultCode.failed.getDescr(), request.getRequestURI());
            }
        }
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
    public ApiResult selectList (@RequestBody Cipher cipher, HttpServletRequest request) {
        List<Cipher> result = cipherService.selectList(cipher);
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

        Cipher cipher = object.toJavaObject(Cipher.class);
        PageList<Cipher> pageList = cipherService.selectPage(cipher, page, pageSize);
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
        Cipher query = new Cipher();
        if (StringUtils.isNotBlank(searchParams)) {
            JSONObject object = JSON.parseObject(searchParams);
            query = object.toJavaObject(Cipher.class);
        }

        PageList<Cipher> pageList = cipherService.selectPage(query, page, limit);
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
    public Cipher formSelectByKey(@RequestParam(value = "key", required = false) Long  key) {
        Cipher cipher = new Cipher();
        cipher.setId(key);
        return cipherService.selectByKey(cipher);
    }

    /***
     * 表单插入
     * @param params Bean对象JSON字符串
     */
    @NeedLogin
    @RequestMapping(value = "/formInsert")
    @ResponseBody
    public String formInsert(@RequestParam(value = "params", required = false) String  params, HttpServletRequest request) {
        Cipher cipher = null;
        if (StringUtils.isNotBlank(params)) {
            JSONObject object = JSON.parseObject(params);
            cipher = object.toJavaObject(Cipher.class);
        }

        int rows = 0;


        if(null==cipher.getMessage()||("").equals(cipher.getMessage())){
            rows=0;
        }else{
            String code = ShortUrlGenerator.shortUrl((""+cipher.getMessage()+(new Date()).hashCode()))[0];
            cipher.setCode(code);
            rows = cipherService.insert(cipher);
            StringBuffer baseUri = request.getRequestURL();
            String uri = baseUri.substring(0, baseUri.indexOf("/",8)+1);
            Url url = new Url();
            url.setUrl(uri+ "cipher.html?code=" + code);
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
        Cipher update = null;
        if (StringUtils.isNotBlank(params)) {
            JSONObject object = JSON.parseObject(params);
            update = object.toJavaObject(Cipher.class);
        }

        int rows = cipherService.update(update);

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
        return cipherService.delete(key);
    }
}
