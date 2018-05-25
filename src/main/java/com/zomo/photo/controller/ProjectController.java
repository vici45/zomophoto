package com.zomo.photo.controller;

import com.zomo.photo.VO.UserVo;
import com.zomo.photo.common.Const;
import com.zomo.photo.common.ServiceResponse;
import com.zomo.photo.entity.Project;
import com.zomo.photo.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/project/")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @RequestMapping(value = "add_project.do",method = RequestMethod.POST)
    public ServiceResponse addProject(Project project, Integer[] userIds, HttpSession session){
        UserVo userVo= (UserVo) session.getAttribute(Const.CURRENT_USER);
        return projectService.addProject(project,userVo,userIds);
    }

    @RequestMapping(value = "addProject",method = RequestMethod.POST)
    public String addProject(Project project, Integer[] userIds, HttpSession session,Model model){
        UserVo userVo= (UserVo) session.getAttribute(Const.CURRENT_USER);
        ServiceResponse response=projectService.addProject(project,userVo,userIds);
        if (!response.isSuccess()){
            model.addAttribute("msg",response.getMsg());
            return "error";
        }
        return "success";
    }

    @RequestMapping(value = "find_all.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse findAll(HttpSession session){
        UserVo userVo= (UserVo) session.getAttribute(Const.CURRENT_USER);
        return projectService.findAll(userVo);
    }

    @RequestMapping(value = "findAll",method = RequestMethod.GET)
    public String findAll(HttpSession session, Model model){
        UserVo userVo= (UserVo) session.getAttribute(Const.CURRENT_USER);
        if (userVo==null){
            model.addAttribute("msg","please logged in");
            return "error";
        }
        ServiceResponse response=projectService.findAll(userVo);
        if (!response.isSuccess()){
            model.addAttribute("msg",response.getMsg());
            return "error";
        }
        model.addAttribute("list",response.getData());
        return "admin/admin-table";
    }

    @RequestMapping(value = "findDetailByProjectId",method = RequestMethod.GET)
    public String findDetailByProjectId(Integer projectId,HttpSession session,Model model){
        UserVo userVo= (UserVo) session.getAttribute(Const.CURRENT_USER);
        if (userVo==null){
            model.addAttribute("msg","please logged in");
            return "error";
        }
        if (projectId==null){
            model.addAttribute("msg","project id error");
            return "error";
        }
        ServiceResponse response=projectService.findAllDetailByProjectId(projectId,userVo);
        if (!response.isSuccess()){
            model.addAttribute("msg",response.getMsg());
            return "error";
        }
        model.addAttribute("list",response.getData());
        return "admin/admin-table";
    }

    public ServiceResponse upload(MultipartFile file, HttpServletRequest request){
        String path=request.getServletContext().getRealPath("upload");
        return null;
    }

}
