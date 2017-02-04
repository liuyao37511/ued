package cn.com.duiba.dao;

import cn.com.duiba.domain.entity.WorksEntity;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyao on 2017/2/1.
 */
@Repository
public class WorksDao extends BaseDao {

    public List<WorksEntity> findAllWorks(){
        return selectList("WorksEntity");
    }

    public WorksEntity find(Long id){
        return selectOne("find",id);
    }

    public List<WorksEntity> findByIds(List<Long> ids){
        if(ids.isEmpty()){
            return Collections.emptyList();
        }
        return selectList("findByIds",ids);
    }

    public int incrScope(Long id){
        return update("incrScope",id);
    }

    public WorksEntity findByWriterIdAndCompany(Long writerId,Integer company){
        Map<String,Object> params = Maps.newHashMap();
        params.put("writerId",writerId);
        params.put("company",company);
        return selectOne("findByWriterIdAndCompany",params);
    }

    public int update(WorksEntity entity){
        return update("update",entity);
    }

    public int insert(WorksEntity entity){
        return insert("insert",entity);
    }

}
