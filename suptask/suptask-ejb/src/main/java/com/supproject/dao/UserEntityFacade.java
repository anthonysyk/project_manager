package com.supproject.dao;

import com.supproject.entity.ProjectEntity;
import com.supproject.entity.UserEntity;
import com.supproject.entity.UserEntity_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class UserEntityFacade extends AbstractFacade<UserEntity> implements UserEntityFacadeLocal {
    @PersistenceContext(unitName = "SupTask1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserEntityFacade() {
        super(UserEntity.class);
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public UserEntity findOneByUsername(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        CriteriaQuery<UserEntity> criteriaQuery = cb.createQuery(UserEntity.class);
        Root<UserEntity> userRoot = criteriaQuery.from(UserEntity.class);
        criteriaQuery.where(cb.equal(userRoot.get(UserEntity_.username), username));
        
        try {
            return em.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
    
    @Override
    public List<UserEntity> findProjectMembers(ProjectEntity project){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        // Création de la requête avec Criteria
        CriteriaQuery<UserEntity> criteriaQuery = cb.createQuery(UserEntity.class);
        Root<UserEntity> userRoot = criteriaQuery.from(UserEntity.class);
        criteriaQuery.where(cb.equal(userRoot.get(UserEntity_.projects), project));
        
        return (List<UserEntity>) em.createQuery(criteriaQuery.select(userRoot)).getResultList();
    }

    @Override
    public void remove(UserEntity userEntity) {
        UserEntity user = em.merge(userEntity);
        /*
        for(ProjectEntity project : user.getProject()){
            em.remove(project);
        }
        */
        
        em.remove(user);
    }
}
