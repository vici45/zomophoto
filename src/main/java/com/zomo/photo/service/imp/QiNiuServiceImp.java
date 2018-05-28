package com.zomo.photo.service.imp;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.zomo.photo.service.IQiNiuService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
@Service
public class QiNiuServiceImp implements IQiNiuService,InitializingBean {
    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    @Value("${qiniu.Bucket}")
    private String bucket;

    private StringMap putPolicy;
    @Override
    public Response fileUpload(File file) throws QiniuException {
        Response response=this.uploadManager.put(file,null,getUploadToken());
        int retry=0;
        while (response.needRetry()&&retry<3){
            response=this.uploadManager.put(file,null,getUploadToken());
            retry++;
        }
        return response;
    }

    @Override
    public Response fileUpload(InputStream inputStream) throws QiniuException {
        Response response=this.uploadManager.put(inputStream,null,getUploadToken(),null,null);
        int retry=0;
        while (response.needRetry()&&retry<3){
            response=this.uploadManager.put(inputStream,null,getUploadToken(),null,null);
            retry++;
        }
        return response;
    }

    @Override
    public Response delete(String key) throws QiniuException {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy=new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width),\"height\":$(imageInfo.height)}");
    }

    private String getUploadToken(){
        return this.auth.uploadToken(bucket,null,3600,putPolicy);
    }
}
