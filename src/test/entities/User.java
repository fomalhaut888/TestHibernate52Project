package test.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "USER" )
public class User {		
		@Id
		@Column(name = "id")
		@GeneratedValue
		private long id;
		
		@Column(name = "employee_id")
		private String employeeId;
		
		@Column(name = "name", nullable = false)
		private String name;
		
		@Column(name = "status", nullable = false)
		private char status;
		
		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="mother_language", foreignKey = @ForeignKey(name = "fk_user_to_mother_language"))
		private Language motherLanguage;
		
		public User(){
		}
		
		public User(String employeeId, String name, char status, Language motherLanguage){
				this.employeeId = employeeId;
				this.name = name;
				this.status = status;
				this.motherLanguage = motherLanguage;
		}

		public long getId() {
			return id;
		}

		public String getEmployeeId() {
			return employeeId;
		}

		public String getName() {
			return name;
		}

		public char getStatus() {
			return status;
		}

		public void setId(long id) {
			this.id = id;
		}

		public void setEmployeeId(String employeeId) {
			this.employeeId = employeeId;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setStatus(char status) {
			this.status = status;
		}

		public Language getMotherLanguage() {
			return motherLanguage;
		}

		public void setMotherLanguage(Language motherLanguage) {
			this.motherLanguage = motherLanguage;
		}
}
