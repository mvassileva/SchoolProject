package edu.spsu.swe2313.group7.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.spsu.swe2313.group7.library.model.serializers.JsonDateDeserializer;
import edu.spsu.swe2313.group7.library.model.serializers.JsonDateSerializer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 *
 * @author Robert Whitaker
 */

@Entity
@Table(name="BOOK")
public class Book {
	
	//Id column, should be setup as an auto generated
	// identity column(auto incrementation is implied)
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	
	
	private String title;
	private String ISBN10;
	private String ISBN13;
	private String shelf;
	private Date publishDate;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="BOOK_AUTHORS", 
		joinColumns = { @JoinColumn(name= "BOOK_id") }, 
		inverseJoinColumns = {@JoinColumn(name="AUTHOR_id") } )
	private List<Author> authors;
	
	private int checkOutDuration;
	private BookStatus status;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="USERS_BOOKS",
		joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
		inverseJoinColumns={ @JoinColumn(name="BOOK_ID", referencedColumnName="ID") } )
	private User checkedOutBy;
	
	
	private Date dueDate;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="BOOK_WAITING_USERS",
		joinColumns = { @JoinColumn(name= "BOOK_ID") }, 
		inverseJoinColumns = {@JoinColumn(name="USER_ID")})
	private List<User> waitingList;

	@JsonIgnore
	public Author getAuthor() {
		if (this.authors != null) {
			if (authors.get(0) != null) {
				return authors.get(0);
			}
		}
		return null;
	}

	public void setAuthor(Author author) {
		if (this.authors != null) {
			authors.add(author);
		}
		else {
			authors = new ArrayList<>();
			authors.add(author);
		}	
	}

	public String getShelf() {
		return shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getPublishDate() {
		return publishDate;
	}
	
	@JsonDeserialize(using = JsonDateDeserializer.class)
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public int getCheckOutDuration() {
		return checkOutDuration;
	}

	public void setCheckOutDuration(int checkOutDuration) {
		this.checkOutDuration = checkOutDuration;
	}

	public BookStatus getStatus() {
		return status;
	}

	public void setStatus(BookStatus status) {
		this.status = status;
	}

	public User getCheckedOutBy() {
		return checkedOutBy;
	}

	public void setCheckedOutBy(User checkedOutBy) {
		this.checkedOutBy = checkedOutBy;
		/*if (!checkedOutBy.getBooksCheckedOut().contains(this)) {
			checkedOutBy.getBooksCheckedOut().add(this);
		}*/
	}
	
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getDueDate() {
		return dueDate;
	}
	
	@JsonDeserialize(using = JsonDateDeserializer.class)
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public List<User> getWaitingList() {
		return waitingList;
	}

	public void setWaitingList(List<User> waitingList) {
		this.waitingList = waitingList;
	}

	public String getISBN10() {
		return ISBN10;
	}

	public void setISBN10(String ISBN10) {
		this.ISBN10 = ISBN10;
	}

	public String getISBN13() {
		return ISBN13;
	}

	public void setISBN13(String ISBN13) {
		this.ISBN13 = ISBN13;
	}
	
	public boolean copyFromBook(Book oldBook) {
		// Refactor? Probably
		//TODO: Refactor, is this even needed?
		//
/*
		this.setAuthors(oldBook.getAuthors());
		this.setCheckOutDuration(oldBook.getCheckOutDuration());
		this.setCheckedOutBy(oldBook.getCheckedOutBy());
		this.setDueDate(oldBook.getDueDate());
		this.setISBN10(oldBook.getISBN10());
		this.setISBN13(oldBook.getISBN13());
		this.setId(oldBook.getId());
		this.setPublishDate(oldBook.getPublishDate());
		this.setStatus(oldBook.getStatus());
		this.setTitle(oldBook.getTitle());
		this.setWaitingList(oldBook.getWaitingList());
		*/
		return true;
		
	} 
	
}
