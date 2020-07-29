package com.tasklist.service.imp;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tasklist.dao.TaskRepository;
import com.tasklist.dao.ToDoRepository;
import com.tasklist.dto.ToDoRequest;
import com.tasklist.model.Task;
import com.tasklist.model.ToDo;
import com.tasklist.service.ToDoService;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;

@Service
public class ToDoServiceImp implements ToDoService{

	@Autowired
	private ToDoRepository toDoRepository;
	
	@Autowired
	private TaskRepository taskRepository;

	@Override
	public void storeToDo(ToDoRequest toDoRequest) throws NotFoundException, InternalServerErrorException {
		try {
			Task task = taskRepository.findById(toDoRequest.getTask().getId()).get();
			ToDo toDo = new ToDo(toDoRequest.getDescription(), toDoRequest.getTimeStamp());
			task.addToDo(toDo);
			taskRepository.save(task);
		} catch (NoSuchElementException  e) {
			throw new NotFoundException("task with id '"+toDoRequest.getTask().getId()+"' not exist"); 
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	public ToDo getToDoById(long id) throws NotFoundException, InternalServerErrorException {
		try {
			ToDo toDo = toDoRepository.findById(id).get();
			return toDo;
		} catch (NoSuchElementException  e) {
			throw new NotFoundException("'to do' with id '"+id+"' not exist"); 
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	public void deleteToDo(long id) throws InternalServerErrorException, NotFoundException {
		try {
			ToDo toDo = this.getToDoById(id);
			Task task = taskRepository.findById(toDo.getTask().getId()).get();
			task.removeToDo(toDo);
			taskRepository.save(task);
		} catch (NoSuchElementException  e) {
			throw new NotFoundException("task not exist"); 
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	public void updateToDo(ToDoRequest toDoRequest) throws InternalServerErrorException {
		try {
			ToDo toDo = this.getToDoById(toDoRequest.getId());
			toDo.setDescription(toDoRequest.getDescription());
			toDoRepository.save(toDo);
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	public void changeStatus(ToDoRequest toDoRequest) throws InternalServerErrorException {
		try {
			ToDo toDo = this.getToDoById(toDoRequest.getId());
			toDo.setStatus(!toDo.isStatus());
			toDoRepository.save(toDo);
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}
}
