package test.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table( name = "EDUCATION" )
public class Education {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator="seq_education")
	@TableGenerator(name="seq_education", table="my_sequences", 
				pkColumnName="seq_name", valueColumnName="next_id",//default: sequence_name, next_val
				pkColumnValue="education",//default: same as table name 
				initialValue=1000, allocationSize=3) //default: 0, 50
	private long id;
	
	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "status", nullable = false)
	private char status;

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
