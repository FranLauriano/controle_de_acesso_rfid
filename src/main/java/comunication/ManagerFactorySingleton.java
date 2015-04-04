package comunication;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ManagerFactorySingleton {

	private static EntityManagerFactory instance;
	
	public static EntityManagerFactory getInstance() {
		if(instance == null){
			instance = Persistence.createEntityManagerFactory("rfid");
		}
		
		return instance;
	}
	
	private ManagerFactorySingleton() {
		
	}
	
}
