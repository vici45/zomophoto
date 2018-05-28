package com.zomo.photo.controller;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.zomo.photo.VO.UserVo;
import com.zomo.photo.common.Const;
import com.zomo.photo.common.ServiceResponse;
import com.zomo.photo.dto.QiNiuPutRet;
import com.zomo.photo.entity.Project;
import com.zomo.photo.service.IFileService;
import com.zomo.photo.service.IProjectService;
import com.zomo.photo.service.IQiNiuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;


@Controller
@RequestMapping("/project/")
public class ProjectController {

    @Autowired
    private IProjectService projectService;
    @Autowired
    private IFileService fileService;
    @Autowired
    private IQiNiuService qiNiuService;
    @Autowired
    private Gson gson;

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
    @RequestMapping("upload")
    @ResponseBody
    public ServiceResponse upload(@RequestParam(value = "upload_file",required = false) MultipartFile file,
                                  @RequestParam(value = "projectId") Integer projectId){
        if (file.isEmpty()){
            return ServiceResponse.createByErrorMessage("file is empty");
        }
        String fileName=file.getOriginalFilename();
        try {
            InputStream inputStream=file.getInputStream();
            Response response=qiNiuService.fileUpload(inputStream);
            if (response.isOK()){
                QiNiuPutRet ret=gson.fromJson(response.bodyString(),QiNiuPutRet.class);
                return ServiceResponse.createBySuccess(ret);
            }else {
                return ServiceResponse.createByerrorCodeMessage(response.statusCode,response.getInfo());
            }
        }catch (QiniuException e) {
                Response response=e.response;
                return  ServiceResponse.createByerrorCodeMessage(response.statusCode,response.getInfo());
        } catch (IOException e) {
            e.printStackTrace();
            return ServiceResponse.createByErrorMessage("ioException");
        }
    }

}
