package com.stuartyee.javabelt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=2, max=100, message="Name must be at least 2 characters")
	@NotBlank
	private String name;
	
	@Email(message="Please enter using the standard email format")
	private String email;
	
	@Size(min=8, message="Please enter a password at least 8 characters long")
	private String password;
	
	@Transient
	private String passwordConfirm; //transient string value used to confirm password by matching at time of creation
	
	@OneToMany(mappedBy="creator", fetch=FetchType.LAZY)
	private List<Idea> ideas;
	
//	@OneToMany(mappedBy="liker", fetch=FetchType.LAZY)
//	private List<Like> likes;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "likes", 
			joinColumns = @JoinColumn(name = "user_id"), 
			inverseJoinColumns = @JoinColumn(name = "idea_id")
	)
	private List<Idea> likedIdeas;

	@Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
    //If adding new class variables, remember to add appropriate getters and setters
    
    public User () { //empty constructor for Java beans compliance
    }
    
    //PrePersist and PreUpdate methods for created date and updated date
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	
	//Getters and setters appear here

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<Idea> getIdeas() {
		return ideas;
	}

	public void setIdeas(List<Idea> ideas) {
		this.ideas = ideas;
	}

	public List<Idea> getLikedIdeas() {
		return likedIdeas;
	}

	public void setLikedIdeas(List<Idea> likedIdeas) {
		this.likedIdeas = likedIdeas;
	}

//	public List<Like> getLikes() {
//		return likes;
//	}
//
//	public void setLikes(List<Like> likes) {
//		this.likes = likes;
//	}
	
	
    
    
	
	

}