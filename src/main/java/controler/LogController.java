package controler;

import java.util.List;

import model.Log;
import model.Person;
import persistence.LogDao;

public class LogController {

	// ATRIBUTOS
	private LogDao dao;

	// CONSTRUTOR
	public LogController() {
		dao = new LogDao();
	}

	public void insert(Log log) {
		dao.insert(log);
	}

	public List<Log> search(String  uid) {
		return dao.search(uid);
	}

	public void delete(Log log){
		dao.delete(log);
	}
	
}
