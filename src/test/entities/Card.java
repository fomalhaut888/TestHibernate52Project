package test.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table( name = "CARD" )
public class Card {
	@Id
	@Column(name = "id")
	@GeneratedValue
	private UUID id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user", foreignKey = @ForeignKey(name = "fk_card_to_user"))
	private User user;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
