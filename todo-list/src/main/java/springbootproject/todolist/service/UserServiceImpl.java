package springbootproject.todolist.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springbootproject.todolist.createuser.CreateUser;
import springbootproject.todolist.dao.RoleDao;
import springbootproject.todolist.dao.UserDao;
import springbootproject.todolist.entity.Role;
import springbootproject.todolist.entity.User;

@Service
public class UserServiceImpl implements UserService  {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	@Override
	@Transactional	
	public User findByUsername(String username) {
	
		return userDao.findByUsername(username);

	}

	@Override
	@Transactional
	public void save(CreateUser cuser) {
		
		User theUser = new User();
		
		theUser.setUsername(cuser.getUserName());
		theUser.setPassword(passwordEncoder.encode(cuser.getPassword()));
		theUser.setFirstname(cuser.getFirstName());
		theUser.setLastname(cuser.getLastName());
		theUser.setEmail(cuser.getEmail());	
		
		theUser.setRoles( Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));
		
		userDao.save(theUser);
		
	}
	
	@Override
	@Transactional	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		User result = userDao.findByUsername(username);	
		
		if(result == null)
		{
			throw new UsernameNotFoundException("Invalid Username or Password!!!");
		}
			
		 return new org.springframework.security.core.userdetails.User(result.getUsername(), result.getPassword(), mapRolesToAuthorities(result.getRoles()));
			
	}
		
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public List<User> getAllUsers() {
		
		return userDao.getAllUsers();
	}
		
}

	