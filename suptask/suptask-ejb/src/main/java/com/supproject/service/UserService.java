package com.supproject.service;

import com.supproject.dao.ProjectEntityFacade;
import com.supproject.dao.ProjectEntityFacadeLocal;
import com.supproject.dao.TaskEntityFacadeLocal;
import com.supproject.dao.UserEntityFacadeLocal;
import com.supproject.entity.ProjectEntity;
import com.supproject.entity.TaskEntity;
import com.supproject.entity.UserEntity;
import com.supproject.exception.MissingInformationException;
import com.supproject.exception.UserDuplicationException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class UserService {
    @EJB
    private UserEntityFacadeLocal userDao;
    
    @EJB
    private ProjectEntityFacadeLocal projectDao;
    
    @EJB
    private TaskEntityFacadeLocal taskDao;
    
    private static final Logger _logger = 
            Logger.getLogger(ProjectService.class.getName());
    
    /**
     * Find a user by his ID
     * @param id of the user
     * @return user found
     */
    public UserEntity findOneById(long id){
        return userDao.find(id);
    }
    
    /**
     * Find a user by his username
     * @param username of the user
     * @return user found
     */
    public UserEntity findOneByUsername(String username){
        return userDao.findOneByUsername(username);
    }
    
    public List<UserEntity> findProjectMembers(ProjectEntity project){
        return userDao.findProjectMembers(project);
    }
    
    /**
     *Find all users
     * @return
     */
    public List<UserEntity> findAll(){
        return userDao.findAll();
    }
    
    /**
     * Create a new user
     * @param username
     * @param email
     * @param phoneNumber
     * @param lastName
     * @param firstName
     * @param address
     * @param password (only for non-OpenID signing)
     * @return newly created user
     * @throws com.supproject.exception.UserDuplicationException
     * @throws com.supproject.exception.MissingInformationException
     */
    public UserEntity create(String username, 
            String email, 
            String phoneNumber,
            String lastName,
            String firstName,
            String address,
            String password) 
            throws UserDuplicationException, MissingInformationException{
        // Prevent username duplication
        if(this.findOneByUsername(username) == null){
            UserEntity user = new UserEntity();
            user.setUsername(username);
            user.setMail(email);
            user.setPhoneNumber(phoneNumber);
            user.setLastName(lastName);
            user.setFirstName(firstName);
            user.setAddress(address);

            // Select password
            if(password != null){
                user.setSalt(UUID.randomUUID().toString());
                user.setPassword(hashPassword(password, user.getSalt()));
            } 
            else {
                throw new MissingInformationException(
                        "password missing");
            }

            userDao.create(user);
            return user;
        } else {
            throw new UserDuplicationException(
                    "username already used");
        }
    }
    
    /**
     * Create a new user
     * @param user providing parameters
     * @return newly created user
     * @throws UserDuplicationException
     * @throws MissingInformationException 
     */
    public UserEntity create(UserEntity user) 
            throws UserDuplicationException, MissingInformationException{
        return this.create(
                user.getUsername(), 
                user.getMail(), 
                user.getPhoneNumber(),
                user.getLastName(), 
                user.getFirstName(), 
                user.getAddress(), 
                user.getPassword());
    }
    
    /**
     * Try to authenticate a user
     * @param username
     * @param password user clear password
     * @return user if authenticate otherwise null
     */
    public UserEntity authenticate(String username, String password){
        UserEntity user = userDao.findOneByUsername(username);
        
        return (user != null 
                && hashPassword(password, user.getSalt())
                    .equals(user.getPassword())) 
                ? user 
                : null;
    }
    
    public UserEntity update(UserEntity user, boolean password){
        UserEntity originalUser = this.findOneById(user.getId());
        if(password){
            user.setPassword(hashPassword(user.getPassword(), user.getSalt()));
        } else {
            user.setPassword(originalUser.getPassword());
        }
        userDao.edit(user);
        
        return user;
    }
    
    public Boolean remove(UserEntity user){
        for(TaskEntity task : user.getTask()){
            taskDao.remove(task);
        }
        
        for(ProjectEntity project : user.getProjects()){
            project.removeMember(user);
            
            if(project.getCreator().getId().equals(user.getId())){
                projectDao.remove(project);
            } else {
                projectDao.edit(project);
            }
        }
        
        userDao.remove(user);        
        return true;
    }
    
    /**
     * Hash a password with a salt
     * @param password password to hash
     * @param salt salt to use
     * @return hashed password
     */
    private static String hashPassword(String password, String salt){
        String encoded = "";
        try {
            MessageDigest encoder = MessageDigest.getInstance("SHA-512");
            encoder.update((password 
                    + "DIU37D87SIU2OJS9O827XA" 
                    + salt).getBytes());
            
            // Hash to String
            byte[] byteEncoded = encoder.digest();
            for (int i=0; i < byteEncoded.length; i++) {
                encoded += Integer
                        .toString((byteEncoded[i] & 0xff)+0x100, 16)
                        .substring(1);
            }
            
        } catch (NoSuchAlgorithmException e) {
            _logger.log(Level.SEVERE, 
                    "Hash algorithme SHA-512 is unavailable", e);
        }
        
        return encoded;
    }
}
