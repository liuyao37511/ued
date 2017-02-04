package cn.com.duiba.mvc;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by liuyao on 2017/2/1.
 */
public class JsonRender extends JSONObject {
    private static final Logger log = LoggerFactory.getLogger(JsonRender.class);

    private static String SUCCESS = "success";
    private static String MESSAGE = "message";
    private static String CALLBACK = "callback";

    private JsonRender(){}

    public static JsonRender successResult(){
        JsonRender resp = new JsonRender();
        resp.put("success",true);
        return resp;
    }

    public static JsonRender successResult(JSONObject model){
        JsonRender resp = new JsonRender();
        resp.put(JsonRender.SUCCESS,true);
        resp.putAll(model);
        return resp;
    }

    public static JsonRender failResult(String message){
        JsonRender resp = new JsonRender();
        resp.put(JsonRender.SUCCESS,false);
        resp.put(JsonRender.MESSAGE,message);
        return resp;
    }

    public static JsonRender failResult(Throwable e){
        JsonRender resp = new JsonRender();
        resp.put(JsonRender.SUCCESS,false);
        resp.put(JsonRender.MESSAGE,e.getMessage());
        return resp;
    }


    /**
     * jsonp响应时传入回调函数名称
     * @param callback
     * @return
     */
    public JsonRender setJSONPCallback(String callback){
        this.put(JsonRender.CALLBACK,callback);
        return this;
    }

    /**
     * 请求处理结果
     * @return
     */
    public boolean isSuccess() {
        return this.getBoolean(JsonRender.SUCCESS);
    }

    /**
     * 设置响应提示信息
     * @param message
     */
    public JsonRender setMessage(String message){
        this.put(JsonRender.MESSAGE,message);
        return this;
    }

    /**
     * 写入返回值
     * @param key
     * @param result
     * @return
     */
    public JsonRender addResult(String key,Object result){
        this.put(key,result);
        return this;
    }

    /**
     * jsonp处理
     * @param response
     * @param callback
     */
    public void forJsonpReturn(HttpServletResponse response, String callback){
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(callback + "(" + this.toJSONString() + ")");
        }catch(IOException e){
            log.error("e={}",e);
        }finally {
            if(out!=null) out.close();
        }
    }
}
