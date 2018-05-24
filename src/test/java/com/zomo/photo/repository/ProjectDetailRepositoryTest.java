package com.zomo.photo.repository;

import com.zomo.photo.ApplicationTests;
import com.zomo.photo.entity.ProjectDetail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ProjectDetailRepositoryTest extends ApplicationTests {
    @Autowired
    private ProjectDetailRepository projectDetailRepository;

    @Test
    public void fun1(){
        ProjectDetail projectDetail=new ProjectDetail();
        projectDetail.setImageHost("www.baidu.com/hahaah");
        projectDetail.setProjectId(1);
        projectDetailRepository.save(projectDetail);
    }

}