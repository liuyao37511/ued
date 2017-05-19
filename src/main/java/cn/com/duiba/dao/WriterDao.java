package cn.com.duiba.dao;

import cn.com.duiba.domain.entity.WriterEntity;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyao on 2017/2/2.
 */
@Repository
public class WriterDao extends BaseDao {

    public WriterEntity find(Long id){
        return selectOne("find",id);
    }

    public WriterEntity findByAccount(String account){
        return selectOne("findByAccount",account);
    }

    public int insert(WriterEntity writer){
        return insert("insert",writer);
    }

    public List<WriterEntity> findByIds(List<Long> ids){
        if(ids.isEmpty()){
            return Collections.emptyList();
        }
        Map<String,Object> params = Maps.newHashMap();
        params.put("ids",ids);
        return selectList("findByIds",params);
    }
}
