package cn.com.duiba.controller;

import cn.com.duiba.domain.dto.WorksJsonDto;
import cn.com.duiba.domain.entity.WorksEntity;
import cn.com.duiba.domain.enumeration.CompanyEnum;
import cn.com.duiba.domain.param.ToupiaoParams;
import cn.com.duiba.mvc.JsonRender;
import cn.com.duiba.service.BallotService;
import cn.com.duiba.service.WorksService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by liuyao on 2017/1/31.
 */
@Controller
public class HomeController {
    @Autowired
    private WorksService worksService;
    @Autowired
    private BallotService ballotService;

    @RequestMapping(value = "/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @ResponseBody
    @RequestMapping(value = "/getWorksDate")
    public JsonRender getWorksDate(@RequestParam String token){
        try{
            ArrayListMultimap<Integer,JSONObject> data = worksService.getWorksDate(token);
            JSONObject model = new JSONObject();
            model.put(CompanyEnum.DUIBA.getCode().toString(),data.get(CompanyEnum.DUIBA.getCode()));
//            model.put(CompanyEnum.TUI_A.getCode().toString(),data.get(CompanyEnum.TUI_A.getCode()));
//            model.put(CompanyEnum.MAI_LA.getCode().toString(),data.get(CompanyEnum.MAI_LA.getCode()));
            return JsonRender.successResult(model);
        }catch (Exception e){
            e.printStackTrace();
            return JsonRender.failResult(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getOneWorksContext")
    public JsonRender getOneWorksContext(@RequestParam Long id){
        try{
            WorksJsonDto dto = worksService.getOneJsonContext(id);
            JSONObject model = new JSONObject();
            model.put("item",dto);
            return JsonRender.successResult(model);
        }catch (Exception e){
            return JsonRender.failResult(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/yangzhengToken")
    public JsonRender yangzhengToken(@RequestParam String token){
        try{
            String yangzhengtoken = ballotService.jiaoyangToken(token);
            JsonRender json = JsonRender.successResult();
            json.put("token",yangzhengtoken);
            return json;
        }catch (Exception e){
            return JsonRender.failResult(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/toupiao")
    public JsonRender toupiao(@RequestBody ToupiaoParams params){
        try{
            ballotService.toupiao(params);
            return JsonRender.successResult();
        }catch (Exception e){
            return JsonRender.failResult(e);
        }
    }

}
