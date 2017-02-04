package cn.com.duiba.service;

import cn.com.duiba.dao.BallotDao;
import cn.com.duiba.dao.VoteLogDao;
import cn.com.duiba.dao.WorksDao;
import cn.com.duiba.domain.dto.WorksDto;
import cn.com.duiba.domain.dto.WorksJsonDto;
import cn.com.duiba.domain.entity.BallotEntity;
import cn.com.duiba.domain.entity.WorksEntity;
import cn.com.duiba.domain.enumeration.CompanyEnum;
import cn.com.duiba.domain.param.WorksParams;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by liuyao on 2017/2/1.
 */
@Service
public class WorksService {
    @Autowired
    private WorksDao worksDao;
    @Autowired
    private BallotDao ballotDao;
    @Autowired
    private VoteLogDao voteLogDao;

    public ArrayListMultimap<Integer,JSONObject> getWorksDate(String token) throws Exception {
        List<WorksEntity> list = worksDao.findAllWorks();
        ArrayListMultimap<Integer,WorksEntity> multimap = ArrayListMultimap.create();
        for (WorksEntity it : list){
            multimap.put(it.getCompany(),it);
        }
        Set<Long> isTouSet = Sets.newHashSet();
        if(StringUtils.isNotBlank(token)){
            BallotEntity ballot = ballotDao.findByCode(token);
            if(ballot==null){
                throw new Exception("选票的号码无效");
            }
            List<Long> ids = voteLogDao.findWorksIdByBallotId(ballot.getId());
            isTouSet.addAll(ids);
        }

        Transform transform = new Transform();
        transform.setIsTouSet(isTouSet);

        ArrayListMultimap<Integer,JSONObject> dataMap = ArrayListMultimap.create();
        for(CompanyEnum company : CompanyEnum.values()){
            List<WorksEntity> companylist = multimap.get(company.getCode());
            dataMap.putAll(company.getCode(), Lists.transform(companylist,transform));
        }
        return dataMap;
    }

    public WorksJsonDto getOneJsonContext(Long id){
        WorksEntity worksEntity =  worksDao.find(id);
        if (worksEntity==null){
            return null;
        }
        return JSONObject.parseObject(worksEntity.getJsonContext(),WorksJsonDto.class);
    }

    public WorksDto findByWriteIdAndCompany(Long writerId,Integer company){
        WorksEntity worksEntity =  worksDao.findByWriterIdAndCompany(writerId,company);
        if(worksEntity==null){
            return null;
        }
        WorksDto dto = new WorksDto();
        dto.setId(worksEntity.getId());
        dto.setCompany(company);
        dto.setTitle(worksEntity.getTitle());
        dto.setWriterId(writerId);
        dto.setScore(worksEntity.getScore());
        WorksJsonDto json = JSONObject.parseObject(worksEntity.getJsonContext(),WorksJsonDto.class);
        dto.setJson(json);
        return dto;
    }

    public void saveWorks(WorksParams params){
        WorksEntity worksEntity =  worksDao.findByWriterIdAndCompany(params.getWriterId(),params.getCompany());
        WorksEntity save = new WorksEntity();
        save.setWriterId(params.getWriterId());
        save.setCompany(params.getCompany());
        save.setTitle(params.getTitle());

        WorksJsonDto json = new WorksJsonDto();
        json.setMainImage(params.getMainImage());
        json.setDescription(params.getDescription());
        json.setDefimage(params.getDefimage());
        String jsonStr = JSONObject.toJSONString(json);
        save.setJsonContext(jsonStr);
        if (worksEntity == null) {
            worksDao.insert(save);
        }else{
            save.setId(worksEntity.getId());
            worksDao.update(save);
        }
    }


    private class Transform implements Function<WorksEntity,JSONObject> {

        private Set<Long> isTouSet;

        public void setIsTouSet(Set<Long> isTouSet) {
            this.isTouSet = isTouSet;
        }

        @Override
        public JSONObject apply(WorksEntity worksEntity) {
            JSONObject json = new JSONObject();
            json.put("id",worksEntity.getId());
            WorksJsonDto jsonContext = JSONObject.parseObject(worksEntity.getJsonContext(),WorksJsonDto.class);
            json.put("image",jsonContext.getMainImage());
            json.put("integral",worksEntity.getScore());
            json.put("title",worksEntity.getTitle());
            json.put("isTou",isTouSet.contains(worksEntity.getId()));
            return json;
        }
    }

}
