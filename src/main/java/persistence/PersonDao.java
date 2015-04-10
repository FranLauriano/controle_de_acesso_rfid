package persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import model.Log;
import model.Person;

import comunication.ManagerFactorySingleton;

import controler.BusinessException;
import controler.LogController;

public class PersonDao {

	private EntityManagerFactory factory = ManagerFactorySingleton
			.getInstance();

	public void insert(Person person) throws BusinessException {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();

			manager.persist(person);

			trx.commit();

		} catch (Exception e) {
			throw new BusinessException("ERRO: Não foi possível realizar o cadastro! Tente novamente.");
		} finally {
			manager.close();
		}

	}

	public void update(Person person) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trx = manager.getTransaction();

		trx.begin();

		manager.merge(person);

		trx.commit();

		manager.close();
	}

	public void delete(Person person) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trx = manager.getTransaction();
		
		LogController controllerLog = new LogController();

		trx.begin();
		
		List<Log> logs = controllerLog.search(person.getuId());
		
		for(Log log: logs){
			controllerLog.delete(log);
		}
		
		Person personRemoved = manager.find(Person.class, person.getId());
		manager.remove(personRemoved);

		trx.commit();

		manager.close();
	}

	public Person find(Person person) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trx = manager.getTransaction();
		trx.begin();
		StringBuilder predicate = new StringBuilder("1 = 1");
		if (person.getuId() != null && person.getuId().length() > 1) {
			predicate.append(" and person.uId = :userId");
		}
		if (person.getName() != null && person.getName().length() > 1) {
			predicate.append(" and person.name like :userName");
		}
		if (person.getCpf() != null && person.getCpf().length() > 1) {
			predicate.append(" and person.cpf like :userCpf");
		}
		String jpql = "Select person from Person person where " + predicate;
		System.out.println(jpql);
		TypedQuery<Person> query = manager.createQuery(jpql, Person.class);
		if (person.getuId() != null && person.getuId().length() > 1) {
			query.setParameter("userId", person.getuId());
		}
		if (person.getName() != null && person.getName().length() > 1) {
			query.setParameter("userName", "%" + person.getName() + "%");
		}
		if (person.getCpf() != null && person.getCpf().length() > 1) {
			query.setParameter("userCpf", "%" + person.getCpf() + "%");
		}

		trx.commit();

		List<Person> result = query.getResultList();
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}

	public List<Person> search(Person person) {

		EntityManager manager = factory.createEntityManager();
		EntityTransaction trx = manager.getTransaction();
		trx.begin();

		StringBuilder predicate = new StringBuilder("1 = 1");
		if (person.getuId() != null && person.getuId().length() > 1) {
			predicate.append(" and person.uId = :userId");
		}
		if (person.getName() != null && person.getName().length() > 1) {
			predicate.append(" and person.name like :userName");
		}
		if (person.getCpf() != null && person.getCpf().length() > 1) {
			predicate.append(" and person.cpf like :userCpf");
		}
		String jpql = "Select person from Person person where " + predicate;
		System.out.println(jpql);
		TypedQuery<Person> query = manager.createQuery(jpql, Person.class);
		if (person.getuId() != null && person.getuId().length() > 1) {
			query.setParameter("userId", person.getuId());
		}
		if (person.getName() != null && person.getName().length() > 1) {
			query.setParameter("userName", "%" + person.getName() + "%");
		}
		if (person.getCpf() != null && person.getCpf().length() > 1) {
			query.setParameter("userCpf", "%" + person.getCpf() + "%");
		}

		trx.commit();

		List<Person> result = query.getResultList();

		return result;
	}

}
