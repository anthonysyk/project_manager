package com.supproject.dao;

import com.supproject.entity.UserEntity;
import com.supproject.entity.ProjectEntity;
import com.supproject.entity.ProjectEntity_;
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
public class ProjectEntityFacade extends AbstractFacade<ProjectEntity> 
        implements ProjectEntityFacadeLocal {
    @PersistenceContext(unitName = "SupTask1PU")
    private EntityManager em;
    
    @EJB
    ProjectEntityFacadeLocal projectDao;

    public ProjectEntityFacade() {
        super(ProjectEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

  
    @Override
    public List<ProjectEntity> findAll(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        // Création de la requête avec Criteria
        CriteriaQuery<ProjectEntity> criteriaQuery = cb.createQuery(ProjectEntity.class);
        Root<ProjectEntity> projectRoot = criteriaQuery.from(ProjectEntity.class);
        
        return (List<ProjectEntity>) em.createQuery(criteriaQuery.select(projectRoot)).getResultList();
    }
    
    @Override
    public List<ProjectEntity> findAllByUser(UserEntity creator){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        // Création de la requête avec Criteria
        CriteriaQuery<ProjectEntity> criteriaQuery = cb.createQuery(ProjectEntity.class);
        Root<ProjectEntity> projectRoot = criteriaQuery.from(ProjectEntity.class);
        criteriaQuery.where(cb.equal(projectRoot.get(ProjectEntity_.creator), creator));
        
        return (List<ProjectEntity>) em.createQuery(criteriaQuery.select(projectRoot)).getResultList();
    }
    
    @Override
    public void remove(ProjectEntity entity) {
        ProjectEntity project = em.merge(entity);
        project.getCreator().removeProject(project);
        em.remove(project);
    }

    @Override
    public void create(ProjectEntity entity) {
        UserEntity user = em.merge(entity.getCreator());
        super.create(entity);
        user.addProject(entity);
        em.flush();
        em.refresh(user);
    }
    

}