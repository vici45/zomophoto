package com.zomo.photo.repository;

import com.zomo.photo.ApplicationTests;
import com.zomo.photo.entity.Project;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class ProjectRepositoryTest extends ApplicationTests {

    @Autowired
    private ProjectRepository projectRepository;
    @Test
    public void fun1(){
        Project project=new Project();
        project.setProjectName("project1");
        project.setProjectBannerHost("xxx");
        project.setProjectContent("first test");
        project.setProjectCreateUserId(11);
        project.setProjectKeyImageHost("null");
        project.setProjectSit("bj");

        projectRepository.save(project);
    }

    @Test
    public void fun2(){
        Integer[] integers=new Integer[]{1,2};
        List<Project> projectList=projectRepository.findByIdIn(integers);
        System.out.println(projectList.size());
    }
}