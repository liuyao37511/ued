package cn.com.duiba.domain.entity;

import java.util.Date;

/**
 * Created by liuyao on 2017/2/1.
 * 选票
 */
public class BallotEntity {
    private Long id;
    private String code;//唯一键
    private Date gmtCreate;
    private Date gmtModified;

    public void init(){
        System.out.println("起作用了");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
