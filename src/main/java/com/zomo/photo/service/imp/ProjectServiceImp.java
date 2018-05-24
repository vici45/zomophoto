package com.zomo.photo.service.imp;

import com.google.common.collect.Lists;
import com.zomo.photo.VO.ProjectDetailVo;
import com.zomo.photo.VO.ProjectVo;
import com.zomo.photo.VO.UserVo;
import com.zomo.photo.common.Const;
import com.zomo.photo.common.ServiceResponse;
import com.zomo.photo.entity.Project;
import com.zomo.photo.entity.ProjectDetail;
import com.zomo.photo.entity.ProjectPrivilege;
import com.zomo.photo.entity.User;
import com.zomo.photo.repository.ProjectDetailRepository;
import com.zomo.photo.repository.ProjectPrivilegeRepository;
import com.zomo.photo.repository.ProjectRepository;
import com.zomo.photo.service.IProjectService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImp implements IProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectDetailRepository projectDetailRepository;

    @Autowired
    private ProjectPrivilegeRepository projectPrivilegeRepository;
    @Override
    public ServiceResponse findAll(UserVo userVo) {
        Integer userId=userVo.getId();
        List<ProjectPrivilege> projectPrivilegeList=projectPrivilegeRepository.findByUserId(userId);
        if (CollectionUtils.isEmpty(projectPrivilegeList)){
            return ServiceResponse.createBySuccess("null project");
        }
        Integer[] projectIds=new Integer[projectPrivilegeList.size()];
        for (int i=0;i<projectIds.length;i++){
            projectIds[i]=projectPrivilegeList.get(i).getProjectId();
        }
        List<Project> projectList= projectRepository.findByIdIn(projectIds);
        return ServiceResponse.createBySuccess(projectList);
    }

    @Override
    public ServiceResponse findById(Integer projectId,UserVo userVo) {

        ProjectPrivilege projectPrivilege=projectPrivilegeRepository.findByUserIdAndProjectId(userVo.getId(),projectId);
        if (projectPrivilege==null){
            return ServiceResponse.createByErrorMessage("You have no authority to view this project or this project not exist");
        }
        Project project=projectRepository.findOne(projectId);
        if (project==null){
            return ServiceResponse.createByErrorMessage("project not exist");
        }
        return ServiceResponse.createBySuccess(project);
    }

//    @Override
//    public ServiceResponse findByName(String name,UserVo userVo) {
//        List<Project> projectList=projectRepository.findByProjectNameLike(name);
//        Integer userId=userVo.getId();
//        if (CollectionUtils.isEmpty(projectList)) {
//            return ServiceResponse.createBySuccessMessage("can't find project");
//        }
//        return ServiceResponse.createBySuccess(projectList);
//    }

    @Override
    public ServiceResponse addProject(Project project, UserVo userVo,Integer[] userIds) {
        project.setProjectCreateUserId(userVo.getId());
        project=projectRepository.save(project);
        return addPrivileges(project.getId(),userIds);
    }

    @Override
    public ServiceResponse deleteById(Integer projectId, UserVo userVo) {
        ServiceResponse response=checkPrivilege(projectId,userVo);
        if (!response.isSuccess()) {
            return response;
        }
        projectRepository.delete(projectId);
        return ServiceResponse.createBySuccessMessage("delete project success");
    }

    @Override
    public ServiceResponse updateProject(Project project, UserVo userVo) {
        ServiceResponse response=checkPrivilege(project.getId(),userVo);
        if (!response.isSuccess()) {
            return response;
        }
        projectRepository.save(project);//todo 验证更新成功
        return ServiceResponse.createBySuccessMessage("update project success");
    }

    @Override
    public ServiceResponse findAllDetailByProjectId(Integer projectId,UserVo userVo) {
        ServiceResponse response=checkPrivilege(projectId,userVo);
        if (!response.isSuccess()){
            return ServiceResponse.createByErrorMessage("you no have authority or project not exist");
        }
        Project project=projectRepository.findOne(projectId);
        if (project==null){
            return ServiceResponse.createByErrorMessage("project not exist");
        }
        List<ProjectDetail> projectDetailList=projectDetailRepository.findByProjectId(projectId);
        ProjectVo projectVo=new ProjectVo();
        BeanUtils.copyProperties(project,projectVo);
        if (projectDetailList.size()==0){
            return ServiceResponse.createBySuccess(projectVo);
        }
        List<ProjectDetailVo> projectDetailVoList=Lists.newArrayList();
        for (ProjectDetail detail : projectDetailList) {
            ProjectDetailVo projectDetailVo=new ProjectDetailVo();
            BeanUtils.copyProperties(detail,projectDetailVo);
            projectDetailVoList.add(projectDetailVo);
        }
        projectVo.setProjectDetailVoList(projectDetailVoList);
        return ServiceResponse.createBySuccess(projectVo);
    }

    @Override
    public ServiceResponse findDetailByDetailId(Integer detailId,UserVo userVo) {
        return null;
    }

    @Override
    public ServiceResponse addDetail(ProjectDetail projectDetail, Integer projectId,UserVo userVo) {
        checkPrivilege(projectId,userVo);

        return null;
    }

    @Override
    public ServiceResponse deleteDetail(Integer DetailId,UserVo userVo) {
        return null;
    }

    @Override
    public ServiceResponse addPrivileges(Integer projectId, Integer[] userIds) {
        for (int i=0;i<userIds.length;i++){
            ProjectPrivilege projectPrivilege=new ProjectPrivilege();
            projectPrivilege.setProjectId(projectId);
            projectPrivilege.setUserId(userIds[i]);
            projectPrivilegeRepository.save(projectPrivilege);
        }
        return ServiceResponse.createBySuccess();
    }

    @Override
    public ServiceResponse checkPrivilege(Integer projectId, UserVo userVo) {
        if (!userVo.getRoleId().equals(Const.Role.MANAGER.getCode())){
            return ServiceResponse.createByErrorMessage("You no have authority");
        }
        ProjectPrivilege projectPrivilege=projectPrivilegeRepository.findByUserIdAndProjectId(userVo.getId(),projectId);
        if (projectPrivilege==null){
            return ServiceResponse.createByErrorMessage("You no have authority or project not exist");
        }
        return ServiceResponse.createBySuccess();
    }

}
