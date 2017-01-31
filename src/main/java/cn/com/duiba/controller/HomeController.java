package cn.com.duiba.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liuyao on 2017/1/31.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String index(){

        System.out.println("进来了");
        return "index";
    }
}
