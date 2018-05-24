package com.zomo.photo.service.imp;

import com.zomo.photo.ApplicationTests;
import com.zomo.photo.VO.UserVo;
import com.zomo.photo.common.ServiceResponse;
import com.zomo.photo.entity.Project;
import com.zomo.photo.entity.User;
import com.zomo.photo.repository.ProjectRepository;
import com.zomo.photo.repository.UserRepository;
import com.zomo.photo.service.IProjectService;
import com.zomo.photo.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ProjectServiceImpTest extends ApplicationTests {

    @Autowired
    private IProjectService projectService;
    @Autowired
    private UserRepository userRepository;
    @Test
    public void addPrivileges() {
        Integer[] userIds=new Integer[]{1,2,3,4,5,6,7};
        projectService.addPrivileges(1,userIds);

        Project project=new Project();
        project.setProjectName("project1");
        project.setProjectBannerHost("xxx");
        project.setProjectContent("first test");
        project.setProjectCreateUserId(11);
        project.setProjectKeyImageHost("null");
        project.setProjectSit("bj");
        User user=userRepository.findOne(12);

    }

    @Test
    public void findAll(){
        UserVo userVo=new UserVo();
        userVo.setId(12);
        ServiceResponse response=projectService.findAll(userVo);
    }
}