package club.myelf.web;

import club.myelf.annotation.IsURL;
import club.myelf.dao.OnereadDao;
import club.myelf.dao.UrlDao;
import club.myelf.entity.Oneread;
import club.myelf.entity.Url;
import club.myelf.service.OnereadService;
import club.myelf.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class RedirectRestController {
    @Autowired
    private UrlService urlService;
    @Autowired
    OnereadService onereadService;

    //短链接跳转
    @IsURL
    @RequestMapping(value = {"/s/{shortURL}"})
    public String RedirectRestURL(@PathVariable String shortURL){
        Url url = new Url();
        url.setShortUrl(shortURL);
        url.setUrl(urlService.selectByKey(url).getUrl());
        if(url.getUrl()!=null){
            System.out.println(url.getUrl());
            return "redirect:"+url.getUrl();
        }else {
            return "redirect:/404.html";
        }
//        return "redirect:/";
    }
    //跳转到管理页面
    @RequestMapping(value = {"/admin"})
    public String RedirectRestAdmin(){
        return "redirect:/admin.html";
    }
//    @RequestMapping(value = {"/"})
//    public String RedirectRestIndex(){
//        return "redirect:/index.html";
//    }


}
