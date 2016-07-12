package com.supproject.dao;

import com.supproject.entity.ProjectEntity;
import com.supproject.entity.UserEntity;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserEntityFacadeLocal {

    void create(UserEntity userEntity);

    void edit(UserEntity userEntity);

    void remove(UserEntity userEntity);

    UserEntity find(Object id);

    List<UserEntity> findAll();
    
    List<UserEntity> findProjectMembers(ProjectEntity project);

    List<UserEntity> findRange(int[] range);

    int count();

    UserEntity findOneByUsername(String username);
    
}
