package cn.com.duiba.service;

import cn.com.duiba.domain.dto.WriterDto;
import cn.com.duiba.utils.BlowfishUtils;
import cn.com.duiba.utils.MessageDigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.duiba.dao.WriterDao;
import cn.com.duiba.domain.entity.WriterEntity;

/**
 * Created by liuyao on 2017/2/3.
 */
@Service
public class WriterService {
    @Autowired
    private WriterDao writerDao;

    @Value("${ued.login.key}")
    private String loginEncryptKey;


    public void doSign(WriterDto params) throws Exception {
        WriterEntity writer = writerDao.findByAccount(params.getAccount());
        if(writer!=null){
            throw new Exception("此账号已经被注册");
        }
        //密码加密
        String password = BlowfishUtils.encryptBlowfish(MessageDigestUtils.SHA(params.getPassword()), loginEncryptKey);
        WriterEntity insert = new WriterEntity();
        insert.setAccount(params.getAccount());
        insert.setPassword(password);
        insert.setWriterName(params.getName());
        writerDao.insert(insert);
    }

    public WriterEntity findWriterByAccount(String account){
        return writerDao.findByAccount(account);
    }

}
