package controler;

import java.util.List;

import persistence.PersonDao;
import model.Person;

public class PersonController {

	// ATRIBUTOS
	private PersonDao dao;

	// CONSTRUTOR
	public PersonController() {
		dao = new PersonDao();
	}

	public void insert(Person person) throws BusinessException{
		Person aux = new Person();
		aux.setuId(person.getuId());
		if (dao.find(aux) != null) {
			throw new BusinessException("Uid já está cadastrado.");
		}
		dao.insert(person);
	}

	public void update(Person person) {
		dao.update(person);
	}

	public void delete(Person person) {
		dao.delete(person);
	}

	public Person find(Person person) {
		return dao.find(person);
	}

	public List<Person> search(Person person) {
		return dao.search(person);
	}
}
