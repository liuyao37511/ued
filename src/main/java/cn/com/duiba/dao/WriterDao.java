package cn.com.duiba.dao;

import cn.com.duiba.domain.entity.WriterEntity;
import org.springframework.stereotype.Repository;

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
}
