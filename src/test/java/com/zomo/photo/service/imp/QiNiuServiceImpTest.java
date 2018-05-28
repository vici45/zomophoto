package com.zomo.photo.service.imp;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.zomo.photo.ApplicationTests;
import com.zomo.photo.dto.QiNiuPutRet;
import com.zomo.photo.entity.ProjectDetail;
import com.zomo.photo.repository.ProjectDetailRepository;
import com.zomo.photo.service.IQiNiuService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

import static org.junit.Assert.*;

public class QiNiuServiceImpTest extends ApplicationTests {

    @Autowired
    private IQiNiuService qiNiuService;
    @Autowired
    private Gson gson;
    @Autowired
    private ProjectDetailRepository projectDetailRepository;
    @Value("${qiniu.cdn.prefix}")
    private String prefix;
    @Test
    public void fileUpload() {
        String fileName="D:\\project\\zomo\\photo\\tmp\\451A2117.JPG";
        File file=new File(fileName);
        Assert.assertTrue(file.exists());
        try {
            Response response=qiNiuService.fileUpload(file);
            Assert.assertTrue(response.isOK());
            QiNiuPutRet ret=gson.fromJson(response.bodyString(),QiNiuPutRet.class);
            System.out.println(ret);
            String host=prefix+"/"+ret.getKey();
            ProjectDetail detail=new ProjectDetail();
            detail.setImageHost(host);
            detail.setProjectId(1);
            projectDetailRepository.save(detail);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }
}