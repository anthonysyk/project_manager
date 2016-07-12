package com.supproject.dao;

import com.supproject.entity.ProjectEntity;
import com.supproject.entity.TaskEntity;
import com.supproject.entity.UserEntity;
import java.util.List;
import javax.ejb.Local;

@Local
public interface TaskEntityFacadeLocal {

    void create(TaskEntity taskEntity);

    void edit(TaskEntity taskEntity);

    void remove(TaskEntity taskEntity);

    TaskEntity find(Object id);

    List<TaskEntity> findAll();
    
    List<TaskEntity> findAllByUser(UserEntity user);
    
    List<TaskEntity> findAllByProject(ProjectEntity project);

    List<TaskEntity> findRange(int[] range);

    int count();
}
