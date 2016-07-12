package com.supproject.dao;

import com.supproject.entity.ProjectEntity;
import com.supproject.entity.UserEntity;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ProjectEntityFacadeLocal {

    void create(ProjectEntity videoEntity);

    void edit(ProjectEntity videoEntity);

    void remove(ProjectEntity videoEntity);

    ProjectEntity find(Object id);

    List<ProjectEntity> findAll();
    
    List<ProjectEntity> findAllByUser(UserEntity creator);
    
    List<ProjectEntity> findRange(int[] range);
    
    int count();

}
