package com.brijesh.bookstore.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BookIssued {

	@Id
	@GeneratedValue
	private Long isbnId;

	@Column(name = "BORROWED_BY")
	private String borrower;

	@Column(name = "ISSUED_DATE")
	private Date issuedOn;

	@Column(name = "DAYS_BORROWED_FOR")
	private int daysToReturn;

	private boolean isReturned = Boolean.FALSE;

	public Long getIsbnId() {
		return isbnId;
	}

	public void setIsbnId(final Long isbnId) {
		this.isbnId = isbnId;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(final String borrower) {
		this.borrower = borrower;
	}

	public Date getIssuedOn() {
		return issuedOn;
	}

	public void setIssuedOn(final Date issuedOn) {
		this.issuedOn = issuedOn;
	}

	public int getDaysToReturn() {
		return daysToReturn;
	}

	public void setDaysToReturn(final int daysToReturn) {
		this.daysToReturn = daysToReturn;
	}

	public boolean isReturned() {
		return isReturned;
	}

	public void setIsReturned(final Boolean isReturned) {
		this.isReturned = isReturned;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((borrower == null) ? 0 : borrower.hashCode());
		result = (prime * result) + daysToReturn;
		result = (prime * result) + ((isbnId == null) ? 0 : isbnId.hashCode());
		result = (prime * result) + ((issuedOn == null) ? 0 : issuedOn.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BookIssued other = (BookIssued) obj;
		if (borrower == null) {
			if (other.borrower != null) {
				return false;
			}
		} else if (!borrower.equals(other.borrower)) {
			return false;
		}
		if (daysToReturn != other.daysToReturn) {
			return false;
		}
		if (isbnId == null) {
			if (other.isbnId != null) {
				return false;
			}
		} else if (!isbnId.equals(other.isbnId)) {
			return false;
		}
		if (issuedOn == null) {
			if (other.issuedOn != null) {
				return false;
			}
		} else if (!issuedOn.equals(other.issuedOn)) {
			return false;
		}
		return true;
	}

}
