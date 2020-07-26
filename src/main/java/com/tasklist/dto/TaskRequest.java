package com.tasklist.dto;

import java.util.Date;
import java.util.List;

public class TaskRequest {
	private long id;
	private String title;
	private String description;
	private Date timeStamp;
	private UserRequest author;
	private boolean status;
	private List<ToDoRequest> toDo;
	
	public TaskRequest() {}
	
	public TaskRequest(long id, String title, String description, Date timeStamp, boolean status, UserRequest author, List<ToDoRequest> toDo) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.timeStamp = timeStamp;
		this.status = status;
		this.author = author;
		this.toDo = toDo;
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
	public UserRequest getAuthor() {
		return author;
	}
	public void setAuthor(UserRequest author) {
		this.author = author;
	}

	public List<ToDoRequest> getToDo() {
		return toDo;
	}

	public void setToDo(List<ToDoRequest> toDo) {
		this.toDo = toDo;
	}
}
