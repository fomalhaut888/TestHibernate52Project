package test.daos;

import java.util.List;
import java.util.Map;

public interface TestAccess {
		public List<Map<String, Object>> findAllUsers();
		
		public List<Map<String, Object>> findAllLanguages();

		public void addUser(String employeeId, String name, char status, long motherLanguageId) throws Exception;
}
