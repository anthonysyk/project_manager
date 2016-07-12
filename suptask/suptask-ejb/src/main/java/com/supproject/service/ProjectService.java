package com.supproject.service;

import com.supproject.dao.ProjectEntityFacadeLocal;
import com.supproject.dao.UserEntityFacadeLocal;
import com.supproject.entity.UserEntity;
import com.supproject.entity.ProjectEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ProjectService {
    @EJB
    private ProjectEntityFacadeLocal projectDao;
    
    @EJB
    private UserEntityFacadeLocal userDao;
        
    /**
     * Find a project by its ID
     * @param id of the project
     * @return project found
     */
    public ProjectEntity findOneById(long id){
        return projectDao.find(id);
    }
    
    /**
     * Create a new project
     * @param title
     * @param description
     * @param creator
     * @return newly created project
     */
    public ProjectEntity create(
            String title, 
            String description,
            UserEntity creator) {

            ProjectEntity project= new ProjectEntity();
            project.setTitle(title);
            project.setDescription(description);
            project.setDate(new Date());
            // UserEntity creator = userDao.find(creatorId);
            project.setCreator(creator);
            
            projectDao.create(project);
            return project;
    }
    
    /**
     * Create a new project
     * @param project
     * @return newly created project
     */

    public ProjectEntity create(ProjectEntity project){
        return this.create(
                project.getTitle(), 
                project.getDescription(), 
                project.getCreator()
        );
    }

    
    public ProjectEntity update(ProjectEntity project){
        projectDao.edit(project);
        
        return project;
    }
    
    /**
     *
     * @param project
     * @return
     */
    public Boolean remove(ProjectEntity project){
        projectDao.remove(project);
        
        return true;
    }
    
    /**
     * Find All projects
     * @return
     */
    public List<ProjectEntity> findAll(){
        return projectDao.findAll();
    }
    
    public List<ProjectEntity> findAllByUser(UserEntity creator){
        return projectDao.findAllByUser(creator);
    }
    
    public void addMemberToProject(UserEntity user, ProjectEntity project){
        project.addMember(user);
        projectDao.edit(project);
    }
    
}