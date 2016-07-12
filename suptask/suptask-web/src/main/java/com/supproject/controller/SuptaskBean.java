package com.supproject.controller;

import com.supproject.entity.ProjectEntity;
import com.supproject.entity.TaskEntity;
import com.supproject.entity.UserEntity;
import com.supproject.exception.MissingInformationException;
import com.supproject.exception.UserDuplicationException;
import com.supproject.service.ProjectService;
import com.supproject.service.TaskService;
import com.supproject.service.UserService;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

@Named(value = "suptaskBean")
@SessionScoped
public class SuptaskBean implements Serializable {

    public SuptaskBean() {
    }

    @EJB
    private UserService userService;

    private UserEntity user = new UserEntity();
    private String username;
    private String password;
    private boolean isLogged;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<UserEntity> findAll() {
        return this.userService.findAll();
    }

    public String register() throws UserDuplicationException, MissingInformationException {
        userService.create(this.user);
        this.user = new UserEntity();
        return "/site/home.xhtml?faces-redirect=true";
    }

    public String delete() {
        this.userService.remove(user);
        return logout();
    }

    public String editProfile() {
        this.userService.update(this.user, false);
        return "/site/home.xhtml?faces-redirect=true";
    }

    public String editPassword() {
        this.userService.update(this.user, true);
        return "/site/home.xhtml?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsLogged() {
        return isLogged;
    }

    public void setIsLogged(Boolean isLogged) {
        this.isLogged = isLogged;
    }

    public String login() {
        this.user = userService.authenticate(username, password);
        if (user != null) {
            isLogged = true;
            return "/site/home.xhtml";
        } else {
            return "/site/login.xhtml?faces-redirect=true";
        }
    }

    public String logout() {
        this.user = null;
        isLogged = false;
        return "/site/home.xhtml?faces-redirect=true";
    }

    /////////////////////////////////// Projets ////////////////////////////
    @EJB
    private ProjectService projectService;

    private ProjectEntity project = new ProjectEntity();

    private String memberName;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public List<ProjectEntity> findAllProjects() {
        return projectService.findAll();
    }

    public List<ProjectEntity> findUsersProjects() {
        return projectService.findAllByUser(user);
    }

    public String addProject() {
        projectService.create(project.getTitle(), project.getDescription(), userService.findOneById(user.getId()));
        return "project.xhtml?faces-redirect=true";
    }

    public String deleteProject() {
        this.projectService.remove(project);
        return "project.xhtml?faces-redirect=true";
    }

    public String manageProject(ProjectEntity project) {
        this.project = project;
        return "projectManage.xhtml?faces-redirect=true";
    }

    public String editProject() {
        this.projectService.update(project);
        return "projectManage.xhtml?faces-redirect=true";
    }

    public String addMember() {
        this.projectService.addMemberToProject(
                userService.findOneByUsername(memberName),
                this.project);
        return "projectManage.xhtml?faces-redirect=true";
    }

    public List<UserEntity> findProjectMembers() {
        return userService.findProjectMembers(project);
    }

    /////////////////////////////////// Tasks ////////////////////////////
    @EJB
    private TaskService taskService;

    private TaskEntity task = new TaskEntity();
    private String attributedUsername;

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public String getAttributedUsername() {
        return attributedUsername;
    }

    public void setAttributedUsername(String attributedUsername) {
        this.attributedUsername = attributedUsername;
    }

    public List<TaskEntity> findAllTasks() {
        return taskService.findAll();
    }

    public List<TaskEntity> findUserTasks() {
        return taskService.findAllByUser(user);
    }

    public List<TaskEntity> findProjectTasks() {
        return taskService.findAllByProject(project);
    }

    public String addTask() {
        taskService.create(task.getTitle(), task.getDescription(), task.getPriority(), task.getDueTime(), project, userService.findOneByUsername(attributedUsername));
        return "projectManage.xhtml?faces-redirect=true";
    }

    public String deleteTask(TaskEntity task) {
        this.taskService.remove(task);
        return "projectManage.xhtml?faces-redirect=true";
    }

    public String editTask() {
        task.setUser(userService.findOneByUsername(attributedUsername));
        this.taskService.update(task);
        return "projectManage.xhtml?faces-redirect=true";
    }

    public String editTask(TaskEntity task) {
        this.task = task;
        attributedUsername = task.getUser().getUsername();
        return "taskEdit.xhtml?faces-redirect=true";
    }

    public String editFinished(TaskEntity task) {
        if (task.getFinished() == false) {
            this.taskService.editFinished(task, true);
        } else if (task.getFinished() == true) {
            this.taskService.editFinished(task, false);
        }
        return "projectManage.xhtml?faces-redirect=true";
    }
}
