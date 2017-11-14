package com.zbay.eventsjava.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="messages")
public class Message {
	 @Id
	 @GeneratedValue
	 private Long id;
	 
		@Column
		@Size(min=1)
		private String text;
		 
		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="author_id")
		private User author;
		 
		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="event_id")
		private Event event;
		 
		@Column(updatable=false)
		private Date createdAt;
		 
		@Column
		private Date updatedAt;
	 
	 public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
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
	 
	 public Message() {}
}
