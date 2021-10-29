package springbootproject.todolist.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springbootproject.todolist.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public User findByUsername(String username) {
		
		Session session = entityManager.unwrap(Session.class);
		
		Query<User> theQuery = session.createQuery("from User where username=:uname" , User.class);
		
		theQuery.setParameter("uname" , username);
		
		User theUser = null;
		
		try {
			
			theUser = theQuery.getSingleResult();
		}
		catch(Exception ex)
		{
			theUser = null;
		}
		
		
		return theUser;
	}
	
	

	@Override
	public void save(User theUser) {

		Session session = entityManager.unwrap(Session.class);
		
		session.saveOrUpdate(theUser);
		
	}



	@Override
	public List<User> getAllUsers() {
		
		Session session = entityManager.unwrap(Session.class);
		
		Query theQuery = session.createQuery("from User", User.class);
		
		List<User> users = theQuery.getResultList();
		
		return users;
	}

}
