package com.supproject.service;

import com.supproject.dao.TaskEntityFacadeLocal;
import com.supproject.dao.UserEntityFacadeLocal;
import com.supproject.entity.ProjectEntity;
import com.supproject.entity.TaskEntity;
import com.supproject.entity.UserEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class TaskService {
    @EJB
    private TaskEntityFacadeLocal taskDao;
    
    @EJB
    private UserEntityFacadeLocal userDao;
        
    /**
     * Find a taskt by its ID
     * @param id of the task
     * @return task found
     */
    public TaskEntity findOneById(long id){
        return taskDao.find(id);
    }
    
    /**
     * Create a new task
     * @param title
     * @param description
     * @param priority
     * @param dueTime
     * @param finished
     * @param project
     * @param user
     * @return newly created project
     */
    public TaskEntity create(
            String title, 
            String description,
            String priority,
            Date dueTime,
            ProjectEntity project,
            UserEntity user) {

            TaskEntity task = new TaskEntity();
            task.setTitle(title);
            task.setDescription(description);
            task.setDueTime(dueTime);
            task.setPriority(priority);
            task.setFinished(false);
            task.setProject(project);
            task.setUser(user);
            
            taskDao.create(task);
            return task;
    }
    
    /**
     * Create a new task
     * @param task
     * @return newly created task
     */
    public TaskEntity create(TaskEntity task){
        return this.create(
                task.getTitle(), 
                task.getDescription(), 
                task.getPriority(),
                task.getDueTime(),
                task.getProject(),
                task.getUser()
        );
    }
    
    public TaskEntity update(TaskEntity task){
        taskDao.edit(task);
        return task;
    }
    
    public TaskEntity editFinished(TaskEntity task, boolean finished){
        task.setFinished(finished);
        taskDao.edit(task);
        return task;
    }
    
    /**
     *
     * @param task
     * @return
     */
    public Boolean remove(TaskEntity task){
        taskDao.remove(task);
        
        return true;
    }
    
    /**
     * Find All tasks
     * @return
     */
    public List<TaskEntity> findAll(){
        return taskDao.findAll();
    }
    
    public List<TaskEntity> findAllByUser(UserEntity user){
        return taskDao.findAllByUser(user);
    }
    
        public List<TaskEntity> findAllByProject(ProjectEntity project){
        return taskDao.findAllByProject(project);
    }
    
}