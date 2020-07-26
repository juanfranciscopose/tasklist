package com.tasklist.dto;

import java.util.Date;

public class ToDoRequest {
	private long id;
	private String description;
	private Date timeStamp;
	private TaskRequest task;
	private boolean status;
	
	public ToDoRequest() {}
	
	public ToDoRequest(long id, String description, Date timeStamp, TaskRequest task, boolean status) {
		this.id = id;
		this.description = description;
		this.timeStamp = timeStamp;
		this.task = task;
		this.status = status;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public TaskRequest getTask() {
		return task;
	}
	public void setTask(TaskRequest task) {
		this.task = task;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
