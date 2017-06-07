package test.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table( name = "LANGUAGE" )
public class Language {
		@Id
		@Column(name = "id")
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_language")
		@SequenceGenerator(name="seq_language", sequenceName = "seq_language", initialValue=300, allocationSize=2)
		private long id;
		
		@Column(name = "code", nullable = false)
		private String code;
		
		@Column(name = "name", nullable = false)
		private String name;
		
		@Column(name = "status", nullable = false)
		private char status;
		
		public Language(){
		}

		public Language(String code, String name, char status) {
				this.code = code;
				this.name = name;
				this.status = status;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public char getStatus() {
			return status;
		}

		public void setStatus(char status) {
			this.status = status;
		}
}
