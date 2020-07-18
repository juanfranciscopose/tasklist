package com.tasklist.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="title", nullable = false, length = 100)
	private String title;
	
	@Column(name="description", length = 255)
	private String description;
	
	@Column(name = "time_stamp", nullable = false)
	private Date timeStamp;
	
	@Column(name="status", nullable = false)
	private boolean status;//public-> true, private-> false?
	
	@ManyToOne(optional = false)
	@JoinColumn(name="author")
	private User author;

	@OneToMany(mappedBy = "task", cascade = {CascadeType.PERSIST, CascadeType.MERGE ,CascadeType.REMOVE}, orphanRemoval = true)
	private List<ToDo> toDoList ;
	
	public Task() {}
	
	public Task(long id, String title, String description, Date timeStamp, boolean status, User author) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.timeStamp = timeStamp;
		this.status = status;
		this.author = author;
		this.toDoList = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public User getAuthor() {
		return author;
	}

	public List<ToDo> getList() {
		return toDoList;
	}

	public void setList(List<ToDo> list) {
		this.toDoList = list;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	public boolean addToDo(ToDo toDo) {
		if (!this.toDoList.contains(toDo)) {
			this.toDoList.add(toDo);
			return true;
		}
		return false;
	}
	
	public boolean removeToDo(ToDo toDo) {
		if (this.toDoList.contains(toDo)) {
			this.toDoList.remove(toDo);
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (status ? 1231 : 1237);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (status != other.status)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
