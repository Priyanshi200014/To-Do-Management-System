package springbootproject.todolist.dao;

import java.util.List;
import java.util.Optional;

import springbootproject.todolist.entity.Task;

public interface TaskDao {
	
	// add a task
	public void addTask(Task newtask);
	
	// delete a task
	public void deleteTask(int taskId);
	
	// get all tasks
	public List<Task> getUserTasks(int userId);
	
	// get by task id
	public Optional<Task> getByTaskId(int taskId);
	
}
