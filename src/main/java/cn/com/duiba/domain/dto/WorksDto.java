package cn.com.duiba.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuyao on 2017/2/3.
 */
public class WorksDto implements Serializable{

    private static final long serialVersionUID = 7225324578700286409L;
    private Long    id;
    private Long    writerId;
    private Integer company;    // 公司编号
    private String  title;
    private WorksJsonDto json;
    private Integer score;      // 票数

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

    public WorksJsonDto getJson() {
        return json;
    }

    public void setJson(WorksJsonDto json) {
        this.json = json;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
