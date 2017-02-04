package cn.com.duiba.controller;

import cn.com.duiba.domain.dto.WorksDto;
import cn.com.duiba.domain.param.WorksParams;
import cn.com.duiba.mvc.JsonRender;
import cn.com.duiba.mvc.RequestTool;
import cn.com.duiba.service.OSSFileService;
import cn.com.duiba.service.WorksService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * Created by liuyao on 2017/2/3.
 */
@Controller
@RequestMapping(value = "/works")
public class WorksController {
    @Autowired
    private WorksService worksService;
    @Autowired
    private OSSFileService oSSFileService;

    @ResponseBody
    @RequestMapping(value = "/details")
    public JsonRender details(Integer company){
        JSONObject model = new JSONObject();
        Long wid =  RequestTool.getWid();
        WorksDto dto = worksService.findByWriteIdAndCompany(wid,company);
        if(dto!=null){
            model.put("details",dto);
        }
        return JsonRender.successResult(model);
    }

    @ResponseBody
    @RequestMapping(value = "/saveWorks")
    public JsonRender saveWorks(@RequestBody WorksParams params){
        try{
            Long wid =  RequestTool.getWid();
            params.setWriterId(wid);
            worksService.saveWorks(params);
            return JsonRender.successResult();
        }catch (Exception e){
            return JsonRender.failResult(e);
        }
    }

    @ResponseBody
    @RequestMapping("/uploadImage")
    public JsonRender uploadImage(HttpServletRequest request) throws Exception{
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 先判断request中是否包涵multipart类型的数据，
        if (!multipartResolver.isMultipart(request)) {
            throw new Exception("提交类型不是Multipart");
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile f = multiRequest.getFile("file");
        if (f.getSize() > 2*1024 * 1024) {
            throw new Exception("文件大小不能超过2M");
        }
        if (Objects.equal(null, f) || f.isEmpty()) {
            throw new Exception("文件内容为空");
        }
        String fileType = f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf(".") + 1,f.getOriginalFilename().length());
        if(!StringUtils.equalsIgnoreCase(fileType, "jpg")
                && !StringUtils.equalsIgnoreCase(fileType, "jpeg")
                && !StringUtils.equalsIgnoreCase(fileType, "png")
                && !StringUtils.equalsIgnoreCase(fileType, "gif")){
            throw new Exception("只支持图片格式上传");
        }
        String name = new Date().getTime() + "." + fileType;
        String objectName = "ued/" + name;

        String webRootDir = RequestTool.getWebRootDir();
        File userDir = new File(webRootDir, "/upload/");
        if(!userDir.exists()){
            userDir.mkdirs();
        }
        File file = new File(userDir, objectName);
        try{
            file.createNewFile();
            f.transferTo(file);
            String url = oSSFileService.uploadOssImg(file,objectName);
            JSONObject json = new JSONObject();
            json.put("url",url);
            return JsonRender.successResult(json);
        }catch(Exception e){
            return JsonRender.failResult(e);
        }finally {
            if(file.exists()){
                file.delete();
            }
        }
    }
}
