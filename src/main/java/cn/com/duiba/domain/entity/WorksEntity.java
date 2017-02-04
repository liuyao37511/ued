package cn.com.duiba.domain.entity;

import java.util.Date;

/**
 * Created by liuyao on 2017/2/1. 参赛作品项
 */
public class WorksEntity {

    private Long    id;
    private Long    writerId;
    private Integer company;    // 公司编号
    private String  title;
    private String  jsonContext;
    private Integer score;      // 票数
    private Date    gmtCreate;
    private Date    gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWriterId() {
        return writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJsonContext() {
        return jsonContext;
    }

    public void setJsonContext(String jsonContext) {
        this.jsonContext = jsonContext;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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
