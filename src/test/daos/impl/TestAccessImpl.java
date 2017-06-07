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
import test.entities.Card;
import test.entities.County;
import test.entities.County_;
import test.entities.Education;
import test.entities.Education_;
import test.entities.Gender;
import test.entities.Gender_;
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
			criteria = criteria.select( root )
					.where( builder.equal( root.get( User_.status ), 'A' ) )
					.orderBy(builder.asc(root.get(User_.employeeId)));
			List<User> users = sharedManager.createQuery(criteria).getResultList();
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for(User user: users){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", user.getId());
					map.put("employeeId", user.getEmployeeId());
					map.put("name", user.getName());
					map.put("status", user.getStatus());
					map.put("genderId", user.getGender().getId());
					map.put("genderCode", user.getGender().getCode());
					map.put("genderName", user.getGender().getName());
					map.put("motherLanguageId", user.getMotherLanguage().getId());
					map.put("motherLanguageCode", user.getMotherLanguage().getCode());
					map.put("motherLanguageName", user.getMotherLanguage().getName());
					map.put("educationId", user.getEducation().getId());
					map.put("educationCode", user.getEducation().getCode());
					map.put("educationName", user.getEducation().getName());
					map.put("countyId", user.getCounty().getId());
					map.put("countyCode", user.getCounty().getCode());
					map.put("countyName", user.getCounty().getName());
					map.put("cardNumber", (user.getCard() == null)?
							"" : user.getCard().getId().toString());
					
					resultList.add(map);
			}
			
			return resultList;
	}

	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public void addUser(String employeeId, String name, char status, long genderId, 
			long motherLanguageId, long eductionId, long countyId){
			//Use the shared entity manager
			CriteriaBuilder builder = sharedManager.getCriteriaBuilder();
			//Gender
			CriteriaQuery<Gender> criteria1 = builder.createQuery( Gender.class );
			Root<Gender> root1 = criteria1.from(Gender.class);
			criteria1 = criteria1.select(root1)
					.where(builder.equal( root1.get(Gender_.id), genderId));
			Gender gender = sharedManager.createQuery(criteria1).getSingleResult();
			//Language
			CriteriaQuery<Language> criteria2 = builder.createQuery( Language.class );
			Root<Language> root2 = criteria2.from( Language.class );
			criteria2 = criteria2.select( root2 )
					.where( builder.equal(root2.get( Language_.id ), motherLanguageId));
			Language language = sharedManager.createQuery(criteria2).getSingleResult();
			//Education
			CriteriaQuery<Education> criteria3 = builder.createQuery(Education.class);
			Root<Education> root3 = criteria3.from(Education.class);
			criteria3 = criteria3.select( root3 )
					.where( builder.equal(root3.get(Education_.id), eductionId));
			Education education = sharedManager.createQuery(criteria3).getSingleResult();
			//County
			CriteriaQuery<County> criteria4 = builder.createQuery(County.class);
			Root<County> root4 = criteria4.from(County.class);
			criteria4 = criteria4.select( root4 )
					.where( builder.equal(root4.get(County_.id), countyId));
			County county = sharedManager.createQuery(criteria4).getSingleResult();
			
			User user = new User(employeeId, name, status, gender, language,
					education, county);
			sharedManager.persist(user);
			
			Card card = new Card();
			card.setUser(user);
			sharedManager.persist(card);
	}

	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public void deleteUser(long userId) {
			CriteriaBuilder builder = sharedManager.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder.createQuery( User.class );
			Root<User> root = criteria.from( User.class );
			criteria = criteria.select( root )
					.where( builder.equal( root.get( User_.id ), userId) );
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
			criteria = criteria.select( root )
					.where( builder.equal( root.get( User_.id ), userId) );
			User user = sharedManager.createQuery(criteria).getSingleResult();
			Map<String, Object> resultMap = null;
			if(user != null){
					resultMap = new HashMap<String, Object>();
					resultMap.put("id", user.getId());
					resultMap.put("employeeId", user.getEmployeeId());
					resultMap.put("name", user.getName());
					resultMap.put("status", user.getStatus());
					resultMap.put("genderId", user.getGender().getId());
					resultMap.put("genderCode", user.getGender().getCode());
					resultMap.put("genderName", user.getGender().getName());
					resultMap.put("motherLanguageId", user.getMotherLanguage().getId());
					resultMap.put("motherLanguageCode", user.getMotherLanguage().getCode());
					resultMap.put("motherLanguageName", user.getMotherLanguage().getName());
					resultMap.put("educationId", user.getEducation().getId());
					resultMap.put("educationCode", user.getEducation().getCode());
					resultMap.put("educationName", user.getEducation().getName());
					resultMap.put("countyId", user.getCounty().getId());
					resultMap.put("countyCode", user.getCounty().getCode());
					resultMap.put("countyName", user.getCounty().getName());
					resultMap.put("cardNumber", (user.getCard() == null)?
							"" : user.getCard().getId().toString());
			}
			
			return resultMap;
	}

	@Transactional(readOnly = false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public void updateUser(long userId, String employeeId, String name, long genderId, 
			long motherLanguageId, long eductionId, long countyId) {
		
			CriteriaBuilder builder = sharedManager.getCriteriaBuilder();
			//Gender
			CriteriaQuery<Gender> criteria1 = builder.createQuery( Gender.class );
			Root<Gender> root1 = criteria1.from(Gender.class);
			criteria1 = criteria1.select(root1)
					.where(builder.equal( root1.get(Gender_.id), genderId));
			Gender gender = sharedManager.createQuery(criteria1).getSingleResult();
			//Language
			CriteriaQuery<Language> criteria2 = builder.createQuery( Language.class );
			Root<Language> root2 = criteria2.from( Language.class );
			criteria2 = criteria2.select( root2 )
					.where( builder.equal(root2.get( Language_.id ), motherLanguageId));
			Language language = sharedManager.createQuery(criteria2).getSingleResult();
			//Education
			CriteriaQuery<Education> criteria3 = builder.createQuery(Education.class);
			Root<Education> root3 = criteria3.from(Education.class);
			criteria3 = criteria3.select( root3 )
					.where( builder.equal(root3.get(Education_.id), eductionId));
			Education education = sharedManager.createQuery(criteria3).getSingleResult();
			//County
			CriteriaQuery<County> criteria4 = builder.createQuery(County.class);
			Root<County> root4 = criteria4.from(County.class);
			criteria4 = criteria4.select( root4 )
					.where( builder.equal(root4.get(County_.id), countyId));
			County county = sharedManager.createQuery(criteria4).getSingleResult();
			
			CriteriaQuery<User> criteria5 = builder.createQuery( User.class );
			Root<User> root5 = criteria5.from( User.class );
			criteria5 = criteria5.select(root5)
					.where( builder.equal( root5.get( User_.id ), userId) );
			User user = sharedManager.createQuery(criteria5).getSingleResult();
			
			user.setEmployeeId(employeeId);
			user.setName(name);
			user.setGender(gender);
			user.setMotherLanguage(language);
			user.setEducation(education);
			user.setCounty(county);
			
			sharedManager.persist(user);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Map<String, Object>> findAllGenders() {
			CriteriaBuilder builder = sharedManager.getCriteriaBuilder();
			CriteriaQuery<Gender> criteria = builder.createQuery( Gender.class );
			Root<Gender> root = criteria.from( Gender.class );
			criteria = criteria.select( root )
					.where( builder.equal( root.get( Gender_.status ), 'A' ) );
			List<Gender> genders = sharedManager.createQuery(criteria).getResultList();
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for(Gender gender: genders){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", gender.getId());
					map.put("code", gender.getCode());
					map.put("name", gender.getName());
					map.put("status", gender.getStatus());
					resultList.add(map);
			}
			
			return resultList;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Map<String, Object>> findAllLanguages() {
			CriteriaBuilder builder = sharedManager.getCriteriaBuilder();
			CriteriaQuery<Language> criteria = builder.createQuery( Language.class );
			Root<Language> root = criteria.from( Language.class );
			criteria = criteria.select( root )
					.where( builder.equal( root.get( Language_.status ), 'A' ) );
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
	
	@Transactional(readOnly = true)
	@Override
	public List<Map<String, Object>> findAllEducations() {
			CriteriaBuilder builder = sharedManager.getCriteriaBuilder();
			CriteriaQuery<Education> criteria = builder.createQuery( Education.class );
			Root<Education> root = criteria.from( Education.class );
			criteria = criteria.select( root )
					.where( builder.equal( root.get( Education_.status ), 'A' ) );
			List<Education> educations = sharedManager.createQuery(criteria).getResultList();
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for(Education education: educations){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", education.getId());
					map.put("code", education.getCode());
					map.put("name", education.getName());
					map.put("status", education.getStatus());
					resultList.add(map);
			}
			
			return resultList;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Map<String, Object>> findAllCounties() {
			CriteriaBuilder builder = sharedManager.getCriteriaBuilder();
			CriteriaQuery<County> criteria = builder.createQuery( County.class );
			Root<County> root = criteria.from( County.class );
			criteria = criteria.select( root )
					.where( builder.equal( root.get(County_.status), 'A' ) );
			List<County> counties = sharedManager.createQuery(criteria).getResultList();
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for(County county: counties){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", county.getId());
					map.put("code", county.getCode());
					map.put("name", county.getName());
					map.put("status", county.getStatus());
					resultList.add(map);
			}
			
			return resultList;
	}
}
