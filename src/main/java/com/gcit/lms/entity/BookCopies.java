/**
 * 
 */
package com.gcit.lms.entity;

import java.util.List;

/**
 * @author Incognito
 *
 */
public class BookCopies {
	
	private Integer bookId;
	private Integer branchId;
	private Integer noOfCopies;
	private Book book;
	private List<LibraryBranch> libraryBranch;
	
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public Integer getNoOfCopies() {
		return noOfCopies;
	}
	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	
	public List<LibraryBranch> getLibraryBranch() {
		return libraryBranch;
	}
	public void setLibraryBranch(List<LibraryBranch> libraryBranch) {
		this.libraryBranch = libraryBranch;
	}
	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}
	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}
	@Override
	public String toString() {
		return "BookCopies [bookId=" + bookId + ", branchId=" + branchId + ", noOfCopies=" + noOfCopies + ", book="
				+ book + ", libraryBranch=" + libraryBranch + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((libraryBranch == null) ? 0 : libraryBranch.hashCode());
		result = prime * result + ((noOfCopies == null) ? 0 : noOfCopies.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookCopies other = (BookCopies) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (libraryBranch == null) {
			if (other.libraryBranch != null)
				return false;
		} else if (!libraryBranch.equals(other.libraryBranch))
			return false;
		if (noOfCopies == null) {
			if (other.noOfCopies != null)
				return false;
		} else if (!noOfCopies.equals(other.noOfCopies))
			return false;
		return true;
	}
}
