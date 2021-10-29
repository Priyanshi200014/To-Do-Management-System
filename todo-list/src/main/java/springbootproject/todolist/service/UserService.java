package springbootproject.todolist.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import springbootproject.todolist.createuser.CreateUser;
import springbootproject.todolist.entity.User;

public interface UserService extends UserDetailsService {

	public User findByUsername(String username);
	
	public void save(CreateUser cuser);
	
	public List<User> getAllUsers();
	
}
