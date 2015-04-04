package persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import model.Log;
import model.Person;


import comunication.ManagerFactorySingleton;

public class LogDao {
	
private EntityManagerFactory factory = ManagerFactorySingleton.getInstance();
	
	
	public void insert(Log log){
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trx = manager.getTransaction();
		
		trx.begin();
		
		manager.persist(log);
		
		trx.commit();
		
		manager.close();
	}
	
	
	public List<Log> search(String uid){
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trx = manager.getTransaction();
		trx.begin();
		StringBuilder predicate = new StringBuilder("1 = 1");
		if(uid != null && uid.length() > 1){
			predicate.append("and log.uid = :uid");
		}
		System.out.println("teste");
		String jpql = "Select log from Log log where " + predicate;
		System.out.println(jpql);
		TypedQuery<Log> query = manager.createQuery(jpql, Log.class);
		if(uid!= null && uid.length() > 1){
			query.setParameter("uid", uid);
		}
		
		trx.commit();
		
		List<Log> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result;
		}
	}

}
