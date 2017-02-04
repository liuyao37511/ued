package cn.com.duiba.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by liuyao on 2017/2/1.
 */
public class BaseDao implements ApplicationContextAware{

    private SqlSessionTemplate sqlSessionTemplate;

    public int insert(String statement) {
        return sqlSessionTemplate.insert(addNameSpace(statement));
    }

    public int delete(String statement) {
        return sqlSessionTemplate.delete(addNameSpace(statement));
    }

    public int update(String statement) {
        return sqlSessionTemplate.update(addNameSpace(statement));
    }

    public int delete(String statement, Object parameter) {
        return sqlSessionTemplate.delete(addNameSpace(statement), parameter);
    }

    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        return sqlSessionTemplate.selectList(addNameSpace(statement), parameter, rowBounds);
    }

    public void select(String statement, ResultHandler handler) {
        sqlSessionTemplate.select(addNameSpace(statement), handler);
    }

    public <T> T selectOne(String statement, Object parameter) {
        return sqlSessionTemplate.selectOne(addNameSpace(statement), parameter);
    }

    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        return sqlSessionTemplate.selectMap(addNameSpace(statement), parameter, mapKey);
    }

    public int insert(String statement, Object parameter) {
        return sqlSessionTemplate.insert(addNameSpace(statement), parameter);
    }

    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
        return sqlSessionTemplate.selectMap(addNameSpace(statement), parameter, mapKey, rowBounds);
    }

    public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
        sqlSessionTemplate.select(addNameSpace(statement), parameter, rowBounds, handler);
    }

    public <E> List<E> selectList(String statement) {
        return sqlSessionTemplate.selectList(addNameSpace(statement));
    }

    public void select(String statement, Object parameter, ResultHandler handler) {
        sqlSessionTemplate.select(addNameSpace(statement), parameter, handler);
    }

    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return sqlSessionTemplate.selectMap(addNameSpace(statement), mapKey);
    }

    public int update(String statement, Object parameter) {
        return sqlSessionTemplate.update(addNameSpace(statement), parameter);
    }

    public <T> T selectOne(String statement) {
        return sqlSessionTemplate.selectOne(addNameSpace(statement));
    }

    public <E> List<E> selectList(String statement, Object parameter) {
        return sqlSessionTemplate.selectList(addNameSpace(statement), parameter);
    }

    /**
     * 获取mybatis命名空间
     * @param method
     * @return
     */
    protected String addNameSpace(String method){
        return getClass().getName()+"."+method;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //this.sqlSessionTemplate = applicationContext.getBean(SqlSessionTemplate.class);
    }
}
