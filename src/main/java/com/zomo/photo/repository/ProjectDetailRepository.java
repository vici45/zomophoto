package com.zomo.photo.repository;

import com.zomo.photo.entity.ProjectDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectDetailRepository extends CrudRepository<ProjectDetail,Integer> {

    List<ProjectDetail> findByProjectId(Integer projectId);
}
