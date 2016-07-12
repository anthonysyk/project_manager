package com.supproject.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String mail;
    private String phoneNumber;
    private String lastName;
    private String firstName;
    private String address;
    private String password;
    private String salt;
    @OneToMany(mappedBy="creator", cascade=CascadeType.REMOVE)
    private List<ProjectEntity> project = new ArrayList<>();
    @OneToMany(mappedBy="user", cascade=CascadeType.REMOVE)
    private List<TaskEntity> task = new ArrayList<>();
    @ManyToMany(mappedBy="member", cascade=CascadeType.REMOVE)
    private List<ProjectEntity> projects = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    // Liste des projets créés
    public List<ProjectEntity> getProject() {
        return project;
    }

    public void setProject(List<ProjectEntity> projects) {
        this.project = projects;
    }
    
    public void addProject(ProjectEntity project){
        this.project.add(project);
    }
    
    public void removeProject(ProjectEntity project){
        this.project.remove(project);
    }
    
    // Liste des taches attribuées
    public List<TaskEntity> getTask() {
        return task;
    }

    public void setTask(List<TaskEntity> tasks) {
        this.task = tasks;
    }
    
    public void addTask(TaskEntity task){
        this.task.add(task);
    }
    
    public void removeTask(TaskEntity task){
        this.task.remove(task);
    }
    
    
    // Liste des projets dont on est membre
    public List<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }
    
    public void addProjects(ProjectEntity project){
        if(!this.projects.contains(project)){
            this.projects.add(project);
        }
    }
    
    public void removeProjects(ProjectEntity project){
        this.projects.remove(project);
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supproject.entity.UserEntity[ id=" + id + " ]";
    }
    
}
