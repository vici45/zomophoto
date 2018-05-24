package com.zomo.photo.service;

import com.zomo.photo.VO.UserVo;
import com.zomo.photo.common.ServiceResponse;
import com.zomo.photo.entity.Project;
import com.zomo.photo.entity.ProjectDetail;
import com.zomo.photo.entity.User;

public interface IProjectService {

    ServiceResponse findAll(UserVo userVo);

    ServiceResponse findById(Integer projectId,UserVo userVo);

    ServiceResponse findByName(String name,UserVo userVo);

    ServiceResponse addProject(Project project,UserVo userVo,Integer[] userIds);

    ServiceResponse deleteById(Integer id,User user);

    ServiceResponse updateProject(Project project,User user);

    ServiceResponse findAllDetailByProjectId(Integer projectId);

    ServiceResponse findDetailByDetailId(Integer detailId);

    ServiceResponse addDetail(ProjectDetail projectDetail,Integer projectId);

    ServiceResponse deleteDetail(Integer DetailId);

    ServiceResponse addPrivileges(Integer projectId,Integer[] userIds);
}
