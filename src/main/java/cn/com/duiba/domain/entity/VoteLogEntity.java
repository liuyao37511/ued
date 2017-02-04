package cn.com.duiba.domain.entity;

import java.util.Date;

/**
 * Created by liuyao on 2017/2/1.
 * 投票日志
 */
public class VoteLogEntity {
    private Long id;
    private Long worksId;//作品id
    private Long ballotId;//选票Id
    private Date gmtCreate;
    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public Long getBallotId() {
        return ballotId;
    }

    public void setBallotId(Long ballotId) {
        this.ballotId = ballotId;
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
