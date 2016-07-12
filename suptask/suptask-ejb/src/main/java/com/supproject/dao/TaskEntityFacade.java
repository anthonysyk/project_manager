package com.supproject.dao;

import com.supproject.entity.ProjectEntity;
import com.supproject.entity.TaskEntity;
import com.supproject.entity.TaskEntity_;
import com.supproject.entity.UserEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Anthony
 */
@Stateless
public class TaskEntityFacade extends AbstractFacade<TaskEntity> 
        implements TaskEntityFacadeLocal {
    @PersistenceContext(unitName = "SupTask1PU")
    private EntityManager em;
    
    @EJB
    ProjectEntityFacadeLocal projectDao;

    public TaskEntityFacade() {
        super(TaskEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

  
    @Override
    public List<TaskEntity> findAll(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        // Création de la requête avec Criteria
        CriteriaQuery<TaskEntity> criteriaQuery = cb.createQuery(TaskEntity.class);
        Root<TaskEntity> taskRoot = criteriaQuery.from(TaskEntity.class);
        
        return (List<TaskEntity>) em.createQuery(criteriaQuery.select(taskRoot)).getResultList();
    }

    @Override
    public List<TaskEntity> findAllByUser(UserEntity user){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        // Création de la requête avec Criteria
        CriteriaQuery<TaskEntity> criteriaQuery = cb.createQuery(TaskEntity.class);
        Root<TaskEntity> taskRoot = criteriaQuery.from(TaskEntity.class);
        criteriaQuery.where(cb.equal(taskRoot.get(TaskEntity_.user), user));
        
        return (List<TaskEntity>) em.createQuery(criteriaQuery.select(taskRoot)).getResultList();
    }
    
    @Override
    public List<TaskEntity> findAllByProject(ProjectEntity project){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        // Création de la requête avec Criteria
        CriteriaQuery<TaskEntity> criteriaQuery = cb.createQuery(TaskEntity.class);
        Root<TaskEntity> taskRoot = criteriaQuery.from(TaskEntity.class);
        criteriaQuery.where(cb.equal(taskRoot.get(TaskEntity_.project), project));
        
        return (List<TaskEntity>) em.createQuery(criteriaQuery.select(taskRoot)).getResultList();
    }
    
    
    @Override
    public void remove(TaskEntity entity) {
        TaskEntity task = em.merge(entity);
        task.getProject().removeTask(task);
        em.remove(task);
    }

    @Override
    public void create(TaskEntity entity) {
        ProjectEntity project = em.merge(entity.getProject());
        super.create(entity);
        project.addProject(entity);
        em.flush();
        em.refresh(project);
    }
    

}