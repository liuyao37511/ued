package cn.com.duiba.service;

import java.util.List;
import java.util.Set;

import cn.com.duiba.domain.param.ToupiaoParams;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private BallotDao ballotDao;
    @Autowired
    private VoteLogDao voteLogDao;
    @Autowired
    private WorksDao worksDao;
    @Value("${ued.canTouNum}")
    private Integer canTouNum;

    public String jiaoyangToken(String token) throws Exception {
        if(StringUtils.isBlank(token)){
            throw new Exception("选票码无效");
        }
        BallotEntity entity = ballotDao.findByCode(token);
        if(entity!=null){
            throw new Exception("选票码已经存在");
        }
        ballotDao.insert(token);
        return token;
    }

    @Transactional
    public void toupiao(ToupiaoParams params) throws Exception {
        BallotEntity entity = ballotDao.findByCode(params.getToken());
        if(entity==null){
            throw new Exception("选票码无效");
        }
        List<Long> list = voteLogDao.findWorksIdByBallotId(entity.getId());
        if(list.contains(params.getWorksId())){
            throw new Exception("选票已经投出");
        }
        WorksEntity works = worksDao.find(params.getWorksId());
        if(works==null){
            throw new Exception("无效投票");
        }
        List<WorksEntity> worksEntityList = worksDao.findByIds(list);
        Multiset<Integer> companySet = HashMultiset.create();
        for(WorksEntity it : worksEntityList){
            companySet.add(it.getCompany());
        }
        if(companySet.count(works.getCompany())>=canTouNum){
            throw new Exception("每个公司栏目只能投出"+canTouNum+"票");
        }
        //插入投票记录
        voteLogDao.insertLog(params.getWorksId(),entity.getId());
        //票数加1
        worksDao.incrScope(params.getWorksId());

    }
}
