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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 控制层
 * UserController
 * @author quan666
 * @date 2020/06/22
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService service;

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
    public ApiResult insert (@RequestBody User user, HttpServletRequest request) {
        int affectRows = service.insert(user);
        return new ApiResult<>(ResultCode.success.getCode(), affectRows, ResultCode.success.getDescr(), request.getRequestURI());
    }

    /**
     * 参数请求报文:
     *
     * {
     *   "key":1
     * }
     */
    //登录接口
    @RequestMapping(value = "/login")
    @ResponseBody
    public ApiResult login (@RequestBody User key, HttpServletRequest request, HttpSession session) {
        User user = service.selectByKey(key);
        if(user==null){
            return new ApiResult<>(ResultCode.wrong.getCode(), key, ResultCode.wrong.getDescr(), request.getRequestURI());
        }else{
            if(key.getPasswd().equals(user.getPasswd())){
                user.setPasswd(null);
                session.setAttribute("USER",user);
                return new ApiResult<>(ResultCode.success.getCode(), user, ResultCode.success.getDescr(), request.getRequestURI());
            }
        }
        return new ApiResult<>(ResultCode.failed.getCode(), key, ResultCode.failed.getDescr(), request.getRequestURI());
    }
    //登出接口
    @RequestMapping(value = "/logout")
    @ResponseBody
    public ApiResult logout (HttpServletRequest request, HttpSession session) {
        session.setAttribute("USER",null);
        return new ApiResult<>(ResultCode.success.getCode(), "登出成功！", ResultCode.success.getDescr(), request.getRequestURI());
    }
    //登信息获取
    @NeedLogin
    @RequestMapping(value = "/loginStatus")
    @ResponseBody
    public ApiResult loginStatus ( HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("USER");
        user.setPasswd(null);
        return new ApiResult<>(ResultCode.success.getCode(), user, ResultCode.success.getDescr(), request.getRequestURI());
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
    public ApiResult batchInsert (@RequestBody List<User> list, HttpServletRequest request) {
        int affectRows = service.batchInsert(list);
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
    public ApiResult update (@RequestBody User user, HttpServletRequest request) {
        int affectRows = service.update(user);
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
    @Forbid
    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public ApiResult batchDelete (@RequestBody List<Object> keys, HttpServletRequest request) {
        int affectRows = service.batchDelete(keys);
        return new ApiResult<>(ResultCode.success.getCode(), affectRows, ResultCode.success.getDescr(), request.getRequestURI());
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
    public ApiResult selectList (@RequestBody User user, HttpServletRequest request) {
        List<User> result = service.selectList(user);
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

        User user = object.toJavaObject(User.class);
        PageList<User> pageList = service.selectPage(user, page, pageSize);
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
        User query = new User();
        if (StringUtils.isNotBlank(searchParams)) {
            JSONObject object = JSON.parseObject(searchParams);
            query = object.toJavaObject(User.class);
        }

        PageList<User> pageList = service.selectPage(query, page, limit);
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
    public User formSelectByKey(@RequestParam(value = "key", required = false) long  key) {
        User user = new User();
        user.setId(key);
        return service.selectByKey(user);
    }

    /***
     * 表单插入
     * @param params Bean对象JSON字符串
     */
    @NeedLogin
    @RequestMapping(value = "/formInsert")
    @ResponseBody
    public String formInsert(@RequestParam(value = "params", required = false) String  params) {
        User insert = null;
        if (StringUtils.isNotBlank(params)) {
            JSONObject object = JSON.parseObject(params);
            insert = object.toJavaObject(User.class);
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
        User update = null;
        if (StringUtils.isNotBlank(params)) {
            JSONObject object = JSON.parseObject(params);
            update = object.toJavaObject(User.class);
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
