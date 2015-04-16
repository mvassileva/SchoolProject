package edu.spsu.swe2313.group7.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Robert Whitaker
 */
@Entity
@Table(name="AUTHOR")
public class Author {
	//Id column, should be setup as an auto generated
	// identity column(auto incrementation is implied)
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Basic
	private String lastName;
	
	@Basic
	private String firstName;
	
	@Basic
	@JsonIgnore
	private String bio;
	
	@Basic
	@JsonIgnore
	private Date dateOfBirth;
	
	@Basic
	@JsonIgnore
	private Date dateOfDeath;

	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="authors")
	private List<Book> booksbyAuthor;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

}
