package com.zbay.eventsjava.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="events")
public class Event {
	 @Id
	 @GeneratedValue
	 private Long id;
	 
	 @Column
	 @Size(min=1)
	 private String name;
	 
	 @Column
	 private Date date;
	 
	 @Column
	 @Size(min=1)
	 private String city;
	 
	@Column
	@Size(min=2, max=2)
	private String state;
		 
	@Column(updatable=false)
	private Date createdAt;
		 
	@Column
	private Date updatedAt;
		 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="host_id")
	private User host;
		 
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="events_users",
			joinColumns = @JoinColumn(name="event_id"),
			inverseJoinColumns = @JoinColumn(name="user_id"))
	private List<User> joiners;
	
	 @OneToMany(mappedBy="event", fetch=FetchType.LAZY)
	 private List<Message> messages;
	 
	 public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public List<User> getJoiners() {
		return joiners;
	}

	public void setJoiners(List<User> joiners) {
		this.joiners = joiners;
	}
	
	public boolean equals(Event other) {
		return this.getId() == other.getId();
	}
	
	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	 
	 public Event() {}
}
