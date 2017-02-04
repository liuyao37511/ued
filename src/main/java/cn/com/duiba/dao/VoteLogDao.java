package cn.com.duiba.dao;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuyao on 2017/2/1.
 */
@Repository
public class VoteLogDao extends BaseDao {

    public List<Long> findWorksIdByBallotId(Long ballotId){
        return selectList("findWorksIdByBallotId",ballotId);
    }

    public int insertLog(Long worksId,Long ballotId){
        Map<String,Object> params = Maps.newHashMap();
        params.put("worksId",worksId);
        params.put("ballotId",ballotId);
        return insert("insertLog",params);
    }
}
