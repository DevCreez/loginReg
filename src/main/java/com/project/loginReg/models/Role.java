package com.project.loginReg.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Role{
	@Id
	@GeneratedValue
	private long id;

	private String name;

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	
	@ManyToMany(mappedBy = "roles")
    private List<User> users;

	public void setUsers(List<User> users){
		this.users=users;
	}
	public List<User> getUsers(){
		return users;
	}

	@DateTimeFormat(pattern="MM:dd:yyyy HH:mm:ss")
	private Date createdAt;
	
	@DateTimeFormat(pattern="MM:dd:yyyy HH:mm:ss")
	private Date updatedAt;

	@PrePersist
	public void onCreate(){this.createdAt = new Date();}
	@PreUpdate
	public void onUpdate(){this.updatedAt = new Date();}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public Role(){
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}
}

// after models are created go into mysql workbench and insert into schema:
// INSERT INTO `role` (name) VALUES ('ROLE_USER');
// INSERT INTO `role` (name) VALUES ('ROLE_ADMIN');
//this will create the user and admin rows for the role table in mysql