package com.tasklist.service.imp;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tasklist.dao.TaskRepository;
import com.tasklist.dao.UserRepository;
import com.tasklist.dto.TaskRequest;
import com.tasklist.dto.ToDoRequest;
import com.tasklist.dto.UserRequest;
import com.tasklist.model.Task;
import com.tasklist.model.ToDo;
import com.tasklist.model.User;
import com.tasklist.service.TaskService;
import com.tasklist.util.exception.InternalServerErrorException;
import com.tasklist.util.exception.NotFoundException;

@Service
public class TaskServiceImp implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<TaskRequest> getAllPublicTask() throws InternalServerErrorException {
		try {
			List<Task> taskList = taskRepository.findByStatus(true);
			List<TaskRequest> taskRequestList = taskList.stream().map(task -> new TaskRequest(task.getId(),
					task.getTitle(), task.getDescription(), task.getTimeStamp(), task.isStatus(),
					new UserRequest(task.getAuthor().getId(), task.getAuthor().getName(), task.getAuthor().getSurname(),
							task.getAuthor().getEmail(), null, task.getAuthor().getTelephone()),
					null)).collect(Collectors.toList());
			Collections.sort(taskRequestList);
			return taskRequestList;
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	public void storeTask(TaskRequest taskRequest) throws NotFoundException, InternalServerErrorException {
		try {
			User user = userRepository.findById(taskRequest.getAuthor().getId()).get();
			Task task = new Task(taskRequest.getTitle(), taskRequest.getDescription(), taskRequest.getTimeStamp(),
					taskRequest.isStatus());
			user.addTask(task);
			userRepository.save(user);
		} catch (NoSuchElementException e) {
			throw new NotFoundException("User (author) with id '" + taskRequest.getAuthor().getId() + "' not exist");
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	public Task getTaskById(long id) throws NotFoundException, InternalServerErrorException {
		try {
			Task task = taskRepository.findById(id).get();
			return task;
		} catch (NoSuchElementException e) {
			throw new NotFoundException("task with id '" + id + "' not exist");
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	// refactor-> no need to search task
	public void deleteTask(long id) throws InternalServerErrorException, NotFoundException {
		try {
			Task task = this.getTaskById(id);
			User user = userRepository.findDistinctById(task.getAuthor().getId()).get();
			user.removeTask(task);
			userRepository.save(user);
		} catch (NoSuchElementException e) {
			throw new NotFoundException("user not exist");
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	public void updateTask(TaskRequest taskRequest) throws InternalServerErrorException {
		try {
			Task task = getTaskById(taskRequest.getId());
			task.setTitle(taskRequest.getTitle());
			task.setStatus(taskRequest.isStatus());
			task.setDescription(taskRequest.getDescription());
			taskRepository.save(task);
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	private List<ToDoRequest> buildToDoRequestList(List<ToDo> toDoList) throws InternalServerErrorException {
		try {
			List<ToDoRequest> toDoRequestList = toDoList.stream().map(toDo -> new ToDoRequest(toDo.getId(),
					toDo.getDescription(), toDo.getTimeStamp(), null, toDo.isStatus())).collect(Collectors.toList());
			return toDoRequestList;
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}
	
	@Override
	public List<ToDoRequest> getAllToDo(long taskId) throws InternalServerErrorException {
		try {
			Task task = this.getTaskById(taskId);
			List<ToDoRequest> toDoRequestList = this.buildToDoRequestList(task.getList());
			return toDoRequestList;
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

	@Override
	public TaskRequest getTask(long taskId) throws InternalServerErrorException {
		try {
			Task task = this.getTaskById(taskId);
			List<ToDoRequest> toDoRequestListResult = this.buildToDoRequestList(task.getList());

			User author = task.getAuthor();
			UserRequest userRequest = new UserRequest(author.getId(), null, null, null, null, 0);
			TaskRequest taskRequest = new TaskRequest(taskId, task.getTitle(), task.getDescription(),
					task.getTimeStamp(), task.isStatus(), userRequest, toDoRequestListResult);
			return taskRequest;
		} catch (Exception e) {
			throw new InternalServerErrorException(e.toString());
		}
	}

}
