package test.daos;

import java.util.List;
import java.util.Map;

public interface TestAccess {
		public List<Map<String, Object>> findAllUsers();

		public void addUser(String employeeId, String name, char status, long genderId, 
				long motherLanguageId, long eductionId, long countyId);

		public void deleteUser(long userId);

		public Map<String, Object> findUserById(long userId);

		public void updateUser(long userId, String employeeId, String name, long genderId, 
				long motherLanguageId, long eductionId, long countyId);
		
		public List<Map<String, Object>> findAllGenders();
		
		public List<Map<String, Object>> findAllLanguages();
		
		public List<Map<String, Object>> findAllEducations();
		
		public List<Map<String, Object>> findAllCounties();
}
