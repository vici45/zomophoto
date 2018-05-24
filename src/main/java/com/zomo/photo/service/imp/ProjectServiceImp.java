package com.zomo.photo.service.imp;

import com.zomo.photo.VO.UserVo;
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

    @Override
    public ServiceResponse findByName(String name,UserVo userVo) {
        List<Project> projectList=projectRepository.findByProjectNameLike(name);
        Integer userId=userVo.getId();
        if (CollectionUtils.isEmpty(projectList)) {
            return ServiceResponse.createBySuccessMessage("can't find project");
        }
        return ServiceResponse.createBySuccess(projectList);
    }

    @Override
    public ServiceResponse addProject(Project project, UserVo userVo,Integer[] userIds) {
        project.setProjectCreateUserId(userVo.getId());
        project=projectRepository.save(project);
        return addPrivileges(project.getId(),userIds);
    }

    @Override
    public ServiceResponse deleteById(Integer id, User user) {
        //todo
        return null;
    }

    @Override
    public ServiceResponse updateProject(Project project, User user) {
        //todo
        return null;
    }

    @Override
    public ServiceResponse findAllDetailByProjectId(Integer projectId) {
        return null;
    }

    @Override
    public ServiceResponse findDetailByDetailId(Integer detailId) {
        return null;
    }

    @Override
    public ServiceResponse addDetail(ProjectDetail projectDetail, Integer projectId) {
        return null;
    }

    @Override
    public ServiceResponse deleteDetail(Integer DetailId) {
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

}
