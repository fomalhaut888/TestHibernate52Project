package test.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.apache.log4j.Logger;

public class BaseDao {
	
	@PersistenceUnit
	private EntityManagerFactory factory;
	
	@PersistenceContext
	protected EntityManager sharedManager;
	
	protected Logger logger;
	
	public BaseDao(){
			logger = Logger.getLogger(this.getClass());
	}
	
	protected EntityManager getExtendedManager(){
			return factory.createEntityManager();
	}
}
