/**
 * 
 */
package com.gcit.lms.entity;

import java.util.List;

/**
 * @author incognito
 *
 */
public class LibraryBranch {
	
	private Integer branchId;
	private String branchName;
	private String branchAddress;
	private List<Book> books;
	private List<Borrower> borrowers;
	private List<BookLoan> bookLoans;
	private List<BookCopies> bookCopies;
	/**
	 * @return the branchId
	 */
	public Integer getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}
	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	/**
	 * @return the branchAddress
	 */
	public String getBranchAddress() {
		return branchAddress;
	}
	/**
	 * @param branchAddress the branchAddress to set
	 */
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	/**
	 * @return the bookLoans
	 */
	public List<BookLoan> getBookLoans() {
		return bookLoans;
	}
	/**
	 * @param bookLoans the bookLoans to set
	 */
	public void setBookLoans(List<BookLoan> bookLoans) {
		this.bookLoans = bookLoans;
	}
	/**
	 * @return the bookCopies
	 */
	public List<BookCopies> getBookCopies() {
		return bookCopies;
	}
	/**
	 * @param bookCopies the bookCopies to set
	 */
	public void setBookCopies(List<BookCopies> bookCopies) {
		this.bookCopies = bookCopies;
	}
	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}
	/**
	 * @param books the books to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	/**
	 * @return the borrowers
	 */
	public List<Borrower> getBorrowers() {
		return borrowers;
	}
	/**
	 * @param borrowers the borrowers to set
	 */
	public void setBorrowers(List<Borrower> borrowers) {
		this.borrowers = borrowers;
	}
	@Override
	public String toString() {
		return "LibraryBranch [branchId=" + branchId + ", branchName=" + branchName + ", branchAddress=" + branchAddress
				+ ", books=" + books + ", bookLoans=" + bookLoans + ", bookCopies=" + bookCopies + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookCopies == null) ? 0 : bookCopies.hashCode());
		result = prime * result + ((bookLoans == null) ? 0 : bookLoans.hashCode());
		result = prime * result + ((books == null) ? 0 : books.hashCode());
		result = prime * result + ((branchAddress == null) ? 0 : branchAddress.hashCode());
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((branchName == null) ? 0 : branchName.hashCode());
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
		LibraryBranch other = (LibraryBranch) obj;
		if (bookCopies == null) {
			if (other.bookCopies != null)
				return false;
		} else if (!bookCopies.equals(other.bookCopies))
			return false;
		if (bookLoans == null) {
			if (other.bookLoans != null)
				return false;
		} else if (!bookLoans.equals(other.bookLoans))
			return false;
		if (books == null) {
			if (other.books != null)
				return false;
		} else if (!books.equals(other.books))
			return false;
		if (branchAddress == null) {
			if (other.branchAddress != null)
				return false;
		} else if (!branchAddress.equals(other.branchAddress))
			return false;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (branchName == null) {
			if (other.branchName != null)
				return false;
		} else if (!branchName.equals(other.branchName))
			return false;
		return true;
	}
}
