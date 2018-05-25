package com.zomo.photo.service;

import com.zomo.photo.VO.UserVo;
import com.zomo.photo.common.ServiceResponse;
import com.zomo.photo.entity.Project;
import com.zomo.photo.entity.ProjectDetail;
import com.zomo.photo.entity.User;

public interface IProjectService {

    ServiceResponse findAll(UserVo userVo);

    ServiceResponse findById(Integer projectId,UserVo userVo);

//    ServiceResponse findByName(String name,UserVo userVo);

    ServiceResponse addProject(Project project,UserVo userVo,Integer[] userIds);

    ServiceResponse deleteById(Integer projectId,UserVo userVo);

    ServiceResponse updateProject(Project project,UserVo userVo);

    ServiceResponse findAllDetailByProjectId(Integer projectId,UserVo userVo);

    ServiceResponse findDetailByDetailId(Integer detailId,UserVo userVo);

    ServiceResponse addDetail(ProjectDetail projectDetail,Integer projectId,UserVo userVo);

    ServiceResponse deleteDetail(Integer DetailId,Integer projectId,UserVo userVo);

    ServiceResponse addPrivileges(Integer projectId,Integer[] userIds);

    ServiceResponse checkPrivilege(Integer projectId,UserVo userVo);

}
