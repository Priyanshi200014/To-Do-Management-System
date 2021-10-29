package springbootproject.todolist.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springbootproject.todolist.entity.Role;

@Repository
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private EntityManager entityManager;
	
	
	@Override
	public Role findRoleByName(String name) {
		
		Session session = entityManager.unwrap(Session.class);
		
		Query<Role> theQuery = session.createQuery("from Role where name=:rname",Role.class);
		
		theQuery.setParameter("rname",name);
		
		Role role = null;
		
		try {
			
			role = theQuery.getSingleResult();
		}
		catch(Exception ex)
		{
			role = null;
		}
		
		return role;
	}
	
}
