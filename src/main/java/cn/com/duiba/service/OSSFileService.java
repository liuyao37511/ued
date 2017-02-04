package cn.com.duiba.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;

/**
 * Created by liuyao on 2017/2/3.
 */
@Service
public class OSSFileService {
    private static final Logger log = LoggerFactory.getLogger(OSSFileService.class);

    @Autowired
    private OSSClient ossClient;

    @Value("${ued.oss.bucketName}")
    private String bucketName;
    @Value("${ued.oss.accessPath}")
    private String accessPath;


    /**
     * 上传OssImg文件
     * @param file
     * @param objectName
     * @return xx
     */
    public String uploadOssImg(File file, String objectName) throws Exception {
        // 输出结果
        if (ossClient.doesBucketExist(bucketName)) {
            throw new Exception("Oss实例不存在");
        }
        try {
            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 必须设置ContentLength
            meta.setContentLength(file.length());
            meta.setContentType("image/jpeg");
            if (isFileExist(objectName)) {
                throw new Exception("Oss实例不存在");
            }
            // 上传Object.
            ossClient.putObject(bucketName, objectName, file, meta);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(accessPath).append(objectName);
            return stringBuilder.toString();
        } catch (OSSException | ClientException e) {
            log.error("上传OssImg文件出错", e);
            throw new Exception(e);
        }
    }


    /**
     * 验证文件是否存在
     *
     * @param objectName
     * @return
     */
    private boolean isFileExist(String objectName) {

        try {
            ossClient.getObjectMetadata(bucketName, objectName);
            return true;
        } catch (ClientException e) {
            log.error(e.getMessage(), e);
            return false;
        } catch (OSSException e) {
            if (!"NoSuchKey".equals(e.getErrorCode())) {
                log.error(e.getMessage(), e);
            }
            return false;
        }
    }

}
