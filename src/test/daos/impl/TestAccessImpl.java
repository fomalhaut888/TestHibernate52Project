package test.daos.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Transactional(readOnly = true)
	@Override
	public List<Map<String, Object>> findAllUsers() {
			//Use the shared entity manager
			CriteriaBuilder builder = sharedManager.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder.createQuery( User.class );
			Root<User> root = criteria.from( User.class );
			criteria.select( root );
			criteria.where( builder.equal( root.get( User_.status ), 'A' ) );
			criteria.orderBy(builder.asc(root.get(User_.employeeId)));
			List<User> users = sharedManager.createQuery(criteria).getResultList();
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
			
			//Use the extended entity manager
			/*EntityManager manager = null;
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
			}*/
	}

	@Transactional(readOnly = true)
	@Override
	public List<Map<String, Object>> findAllLanguages() {
			CriteriaBuilder builder = sharedManager.getCriteriaBuilder();
			CriteriaQuery<Language> criteria = builder.createQuery( Language.class );
			Root<Language> root = criteria.from( Language.class );
			criteria.select( root );
			criteria.where( builder.equal( root.get( Language_.status ), 'A' ) );
			List<Language> languages = sharedManager.createQuery(criteria).getResultList();
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
	}

	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public void addUser(String employeeId, String name, char status, long motherLanguageId){
			//Use the shared entity manager
			CriteriaBuilder builder = sharedManager.getCriteriaBuilder();
			CriteriaQuery<Language> criteria = builder.createQuery( Language.class );
			Root<Language> root = criteria.from( Language.class );
			criteria.select( root );
			criteria.where( builder.equal( root.get( Language_.id ), motherLanguageId) );
			Language language = sharedManager.createQuery(criteria).getSingleResult();
			User user = new User(employeeId, name, status, language);
			sharedManager.persist(user);
			
			//Use the extended entity manager
			/*EntityManager manager = null;
			EntityTransaction tx = null;
			try{
					manager = this.getExtendedManager();
					tx = manager.getTransaction();
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
	}

	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public void deleteUser(long userId) {
			CriteriaBuilder builder = sharedManager.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder.createQuery( User.class );
			Root<User> root = criteria.from( User.class );
			criteria.select( root );
			criteria.where( builder.equal( root.get( User_.id ), userId) );
			User user = sharedManager.createQuery(criteria).getSingleResult();
			//delete this directly
			//sharedManager.remove(user);
			//set a delete mark
			user.setStatus('D');
			sharedManager.persist(user);
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findUserById(long userId) {
			CriteriaBuilder builder = sharedManager.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder.createQuery( User.class );
			Root<User> root = criteria.from( User.class );
			criteria.select( root );
			criteria.where( builder.equal( root.get( User_.id ), userId) );
			User user = sharedManager.createQuery(criteria).getSingleResult();
			Map<String, Object> resultMap = null;
			if(user != null){
					resultMap = new HashMap<String, Object>();
					resultMap.put("id", user.getId());
					resultMap.put("employeeId", user.getEmployeeId());
					resultMap.put("name", user.getName());
					resultMap.put("status", user.getStatus());
					if(user.getMotherLanguage() != null){
							resultMap.put("motherLanguageId", user.getMotherLanguage().getId());
							resultMap.put("motherLanguageCode", user.getMotherLanguage().getCode());
							resultMap.put("motherLanguageName", user.getMotherLanguage().getName());
					}else{
							resultMap.put("motherLanguageName", "Unknown");
					}
			}
			
			return resultMap;
	}

	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public void updateUser(long userId, String employeeId, String name, long motherLanguageId) {
			CriteriaBuilder builder = sharedManager.getCriteriaBuilder();
			CriteriaQuery<Language> criteria = builder.createQuery( Language.class );
			Root<Language> root = criteria.from( Language.class );
			criteria.select( root );
			criteria.where( builder.equal( root.get( Language_.id ), motherLanguageId) );
			Language language = sharedManager.createQuery(criteria).getSingleResult();
			
			CriteriaQuery<User> criteria2 = builder.createQuery( User.class );
			Root<User> root2 = criteria2.from( User.class );
			criteria2.select( root2 );
			criteria2.where( builder.equal( root2.get( User_.id ), userId) );
			User user = sharedManager.createQuery(criteria2).getSingleResult();
			
			user.setEmployeeId(employeeId);
			user.setName(name);
			user.setMotherLanguage(language);
			
			sharedManager.persist(user);
	}

}
