package com.zomo.photo.controller;

import com.zomo.photo.VO.UserVo;
import com.zomo.photo.common.Const;
import com.zomo.photo.common.ServiceResponse;
import com.zomo.photo.entity.Project;
import com.zomo.photo.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/project/")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @RequestMapping(value = "add_project.do",method = RequestMethod.POST)
    public ServiceResponse addProject(Project project, Integer[] userIds, HttpSession session){
        UserVo userVo= (UserVo) session.getAttribute(Const.CURRENT_USER);
        return projectService.addProject(project,userVo,userIds);
    }

    @RequestMapping(value = "find_all.do",method = RequestMethod.GET)
    public ServiceResponse findAll(HttpSession session){
        UserVo userVo= (UserVo) session.getAttribute(Const.CURRENT_USER);
        return projectService.findAll(userVo);
    }

}
