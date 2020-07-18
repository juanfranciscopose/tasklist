package com.tasklist.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tasklist.model.Task;

@Repository
public interface TaskRepository extends JpaRepository <Task, Long>{
	//returns a task list with status passed by parameter
	public List<Task> findByStatus(Boolean status);
}
