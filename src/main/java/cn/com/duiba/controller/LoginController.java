package cn.com.duiba.controller;

import cn.com.duiba.mvc.SecureTool;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import cn.com.duiba.domain.dto.WriterDto;
import cn.com.duiba.domain.entity.WriterEntity;
import cn.com.duiba.mvc.JsonRender;
import cn.com.duiba.service.WriterService;
import cn.com.duiba.utils.BlowfishUtils;
import cn.com.duiba.utils.MessageDigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

/**
 * Created by liuyao on 2017/2/3.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    private WriterService writerService;
    @Value("${ued.login.key}")
    private String loginEncryptKey;

    @RequestMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("login");
    }

    @ResponseBody
    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public JsonRender doLogin(HttpServletResponse response,@RequestBody WriterEntity params){
        try{
            WriterEntity writer = writerService.findWriterByAccount(params.getAccount());
            if(writer==null){
                throw new Exception("账号或者密码错误");
            }
            String newPassword = BlowfishUtils.encryptBlowfish(MessageDigestUtils.SHA(params.getPassword()), loginEncryptKey);
            if(!StringUtils.equals(newPassword,writer.getPassword())){
                throw new Exception("账号或者密码错误");
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("time", new Date().getTime());
            jsonObject.put("wid", writer.getId());
            String envalue = SecureTool.encryptWriterCookie(jsonObject.toJSONString());

            Cookie cookie = new Cookie("wdata3", envalue);
            cookie.setPath("/");
            response.addCookie(cookie);

            return JsonRender.successResult();
        }catch(Exception e){
            return JsonRender.failResult(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/doSign",method = RequestMethod.POST)
    public JsonRender doSign(@RequestBody @Valid WriterDto writer){
        try{
            writerService.doSign(writer);
            return JsonRender.successResult();
        }catch(Exception e){
            return JsonRender.failResult(e);
        }
    }
}
