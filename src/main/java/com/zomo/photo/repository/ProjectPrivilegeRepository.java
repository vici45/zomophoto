package com.zomo.photo.repository;

import com.zomo.photo.entity.ProjectPrivilege;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectPrivilegeRepository extends CrudRepository<ProjectPrivilege,Integer> {

    List<ProjectPrivilege> findByUserId(Integer userId);

    ProjectPrivilege findByUserIdAndProjectId(Integer userId,Integer projectId);

}
