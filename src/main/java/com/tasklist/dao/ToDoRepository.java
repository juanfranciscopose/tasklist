package com.tasklist.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tasklist.model.ToDo;

@Repository
public interface ToDoRepository extends JpaRepository <ToDo, Long>{

}
