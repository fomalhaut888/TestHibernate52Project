package test.daos.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import test.daos.BaseDao;
import test.daos.TestAccess;
import test.entities.Language;
import test.entities.Language_;
import test.entities.User;
import test.entities.User_;

public class TestAccessImpl extends BaseDao implements TestAccess {

	@Override
	public List<Map<String, Object>> findAllUsers() {
			EntityManager manager = null;
			try{
					manager = this.getExtendedManager();
					CriteriaBuilder builder = manager.getCriteriaBuilder();
					CriteriaQuery<User> criteria = builder.createQuery( User.class );
					Root<User> root = criteria.from( User.class );
					criteria.select( root );
					criteria.where( builder.equal( root.get( User_.status ), 'A' ) );
					List<User> users = manager.createQuery(criteria).getResultList();
					List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
					for(User user: users){
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("id", user.getId());
							map.put("employeeId", user.getEmployeeId());
							map.put("name", user.getName());
							map.put("status", user.getStatus());
							if(user.getMotherLanguage() != null){
									map.put("motherLanguageName", user.getMotherLanguage().getName());
							}else{
									map.put("motherLanguageName", "Unknown");
							}
							resultList.add(map);
					}
					
					return resultList;
			}catch(Exception e){
					e.printStackTrace();
					throw e;
			}finally{
					if(manager != null){
							manager.close();
					}
			}
	}

	@Override
	public List<Map<String, Object>> findAllLanguages() {
			EntityManager manager = null;
			try{
					manager = this.getExtendedManager();
					CriteriaBuilder builder = manager.getCriteriaBuilder();
					CriteriaQuery<Language> criteria = builder.createQuery( Language.class );
					Root<Language> root = criteria.from( Language.class );
					criteria.select( root );
					criteria.where( builder.equal( root.get( Language_.status ), 'A' ) );
					List<Language> languages = manager.createQuery(criteria).getResultList();
					List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
					for(Language language: languages){
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("id", language.getId());
							map.put("code", language.getCode());
							map.put("name", language.getName());
							map.put("status", language.getStatus());
							resultList.add(map);
					}
					
					return resultList;
			}catch(Exception e){
					e.printStackTrace();
					throw e;
			}finally{
					if(manager != null){
							manager.close();
					}
			}
	}

	@Override
	public void addUser(String employeeId, String name, char status, long motherLanguageId) throws Exception {
			/*EntityManager manager = null;
			EntityTransaction tx = null;
			try{
					manager = this.getExtendedManager();
					tx = manager.getTransaction();
					tx.begin();
					
					CriteriaBuilder builder = manager.getCriteriaBuilder();
					CriteriaQuery<Language> criteria = builder.createQuery( Language.class );
					Root<Language> root = criteria.from( Language.class );
					criteria.select( root );
					criteria.where( builder.equal( root.get( Language_.id ), motherLanguageId) );
					Language language = manager.createQuery(criteria).getSingleResult();
					User user = new User(employeeId, name, status, language);
					manager.persist(user);					
					tx.commit();
			}catch(Exception e){
					e.printStackTrace();
					if(tx != null){
							tx.rollback();
					}
					throw e;
			}finally{
					if(manager != null){
							manager.close();
					}
			}*/
		
			//CriteriaBuilder builder = sharedManager.getCriteriaBuilder();
			EntityManager manager = this.getExtendedManager();
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Language> criteria = builder.createQuery( Language.class );
			Root<Language> root = criteria.from( Language.class );
			criteria.select( root );
			criteria.where( builder.equal( root.get( Language_.id ), motherLanguageId) );
			Language language = manager.createQuery(criteria).getSingleResult();
			logger.debug("language=" + language);
			User user = new User(employeeId, name, status, language);
			manager.persist(user);
			
			//throw new Exception("An intentional exception thrown!");
	}

}
