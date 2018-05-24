package com.zomo.photo.repository;

import com.zomo.photo.entity.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project,Integer> {

    Project findById(Integer id);

    List<Project> findByProjectNameLike(String name);

    List<Project> findByIdIn(Integer[] ids);
}
