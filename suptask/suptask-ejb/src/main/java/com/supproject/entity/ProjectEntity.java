package com.supproject.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@XmlRootElement
public class ProjectEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    @ManyToOne
    private UserEntity creator;
    @OneToMany(mappedBy="project", cascade=CascadeType.REMOVE)
    private List<TaskEntity> task = new ArrayList<>();
    @ManyToMany(cascade=CascadeType.REMOVE)
    private List<UserEntity> member = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity user) {
        this.creator = user;
    }
    
    @XmlTransient
    public List<TaskEntity> getTask() {
        return task;
    }

    public void setTask(List<TaskEntity> tasks) {
        this.task = tasks;
    }
    
    public void addProject(TaskEntity task){
        this.task.add(task);
    }
    
    public void removeTask(TaskEntity task){
        this.task.remove(task);
    }
    
    public List<UserEntity> getMembers() {
        return member;
    }

    public void setMembers(List<UserEntity> members) {
        this.member = members;
    }
    
    public void addMember(UserEntity member){
        if(!this.member.contains(member)){
            this.member.add(member);
        }
    }
    
    public void removeMember(UserEntity user){
        this.member.remove(user);
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
        if (!(object instanceof ProjectEntity)) {
            return false;
        }
        ProjectEntity other = (ProjectEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supproject.entity.ProjectEntity[ id=" + id + " ]";
    }
    
}
