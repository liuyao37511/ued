package cn.com.duiba.dao;

import cn.com.duiba.domain.entity.BallotEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by liuyao on 2017/2/1.
 */
@Repository
public class BallotDao extends BaseDao{

    public BallotEntity findByCode(String code){
        return selectOne("findByCode",code);
    }




}
