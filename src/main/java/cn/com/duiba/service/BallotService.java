package cn.com.duiba.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

import cn.com.duiba.dao.BallotDao;
import cn.com.duiba.dao.VoteLogDao;
import cn.com.duiba.dao.WorksDao;
import cn.com.duiba.domain.entity.BallotEntity;
import cn.com.duiba.domain.entity.WorksEntity;

import javax.annotation.PostConstruct;

/**
 * Created by liuyao on 2017/2/1.
 */
@Service
public class BallotService {

    private static final Integer Ren_Number = 260;

    @Autowired
    private BallotDao ballotDao;
    @Autowired
    private VoteLogDao voteLogDao;
    @Autowired
    private WorksDao worksDao;

    public void jiaoyangToken(String token) throws Exception {
        if(StringUtils.isBlank(token)){
            throw new Exception("选票码无效");
        }
        BallotEntity entity = ballotDao.findByCode(token);
        if(entity==null){
            throw new Exception("选票码无效");
        }
    }

    @Transactional
    public void toupiao(String token,Long worksId) throws Exception {
        BallotEntity entity = ballotDao.findByCode(token);
        if(entity==null){
            throw new Exception("选票码无效");
        }
        List<Long> list = voteLogDao.findWorksIdByBallotId(entity.getId());
        if(list.contains(worksId)){
            throw new Exception("选票已经投出");
        }
        WorksEntity works = worksDao.find(worksId);
        if(works==null){
            throw new Exception("无效投票");
        }
        List<WorksEntity> worksEntityList = worksDao.findByIds(list);
        Set<Integer> companySet = Sets.newHashSet();
        for(WorksEntity it : worksEntityList){
            companySet.add(it.getCompany());
        }
        if(companySet.contains(works.getCompany())){
            throw new Exception("每个栏目只能投出一票");
        }
        //插入投票记录
        voteLogDao.insertLog(worksId,entity.getId());
        //票数加1
        worksDao.incrScope(entity.getId());

    }
}
