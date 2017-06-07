package test.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table( name = "USER" )
public class User {		
		@Id
		@Column(name = "id")
		@GeneratedValue
		private long id;
		
		@Column(name = "employee_id", nullable = false)
		@GeneratedValue
		
		private String employeeId;
		
		@Column(name = "name", nullable = false)
		private String name;
		
		@Column(name = "status", nullable = false)
		private char status;
		
		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="gender", foreignKey = @ForeignKey(name = "fk_user_to_gender"), nullable = false)
		private Gender gender;
		
		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="mother_language", foreignKey = @ForeignKey(name = "fk_user_to_mother_language"), nullable = false)
		private Language motherLanguage;
		
		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="education", foreignKey = @ForeignKey(name = "fk_user_to_education"), nullable = false)
		private Education education;
		
		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="county", foreignKey = @ForeignKey(name = "fk_user_to_county"), nullable = false)
		private County county;
		
		@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	    private Card card;
		
		public User(){
		}
		
		public User(String employeeId, String name, char status, Gender gender,
				Language motherLanguage, Education education, County county){
				this.employeeId = employeeId;
				this.name = name;
				this.status = status;
				this.gender = gender;
				this.motherLanguage = motherLanguage;
				this.education = education;
				this.county = county;
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

		public Gender getGender() {
			return gender;
		}

		public void setGender(Gender gender) {
			this.gender = gender;
		}

		public Education getEducation() {
			return education;
		}

		public void setEducation(Education education) {
			this.education = education;
		}

		public County getCounty() {
			return county;
		}

		public void setCounty(County county) {
			this.county = county;
		}

		public Card getCard() {
			return card;
		}

		public void setCard(Card card) {
			this.card = card;
		}
}
