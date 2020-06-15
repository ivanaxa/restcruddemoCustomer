package com.lunarllc.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lunarllc.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {

	//define field for entitymanager
	private EntityManager entityManager;
	
	//set up constructor injection
	//"thEntityManager" is automatically created by Spring boot
	@Autowired
	public EmployeeDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager=theEntityManager;
	}
	
	@Override
	public List<Employee> findAll() {
		//get current hibernate session
		Session currentSession=entityManager.unwrap(Session.class);
		//create query
		Query<Employee> theQ=
				currentSession.createQuery("from Employee", Employee.class);
		//execute query and get results
		List<Employee> employees = theQ.getResultList();
		//return the results
		
		return employees;
	}

	@Override
	public Employee findById(int theId) {
		//get current hibernate session
		Session currentSession=entityManager.unwrap(Session.class);
		//create query
		Query<Employee> theQ=
				currentSession.createQuery("from Employee e where e.id="+theId , Employee.class);
		//execute query and get results
		Employee employee = theQ.getSingleResult();
		//return the results
		return employee;
	}

	@Override
	public void save(Employee theEmployee) {
		//get current hibernate session
		Session currentSession=entityManager.unwrap(Session.class);
		//create query
		currentSession.saveOrUpdate(theEmployee);
		
	}

	@Override
	public void deleteById(int theId) {
		//get current hibernate session
		Session currentSession=entityManager.unwrap(Session.class);
		//create query
		Query theQuery = 
				currentSession.createQuery(
						"delete from Employee where id=:employeeId");
		theQuery.setParameter("employeeId", theId);
		theQuery.executeUpdate();
	}

}
