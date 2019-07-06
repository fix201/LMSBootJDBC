/**
 * 
 */
package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoanDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;

/**
 * @author Incognito
 *
 */
@RestController
@RequestMapping(value="/lms")
public abstract class GeneralService {

	@Autowired
	BookDAO bDao;
	@Autowired
	AuthorDAO aDao;
	@Autowired
	GenreDAO gDao;
	@Autowired
	PublisherDAO pDao;
	@Autowired
	LibraryBranchDAO lbDao;
	@Autowired
	BorrowerDAO brwDao;
	@Autowired
	BookLoanDAO blDao;
	@Autowired
	BookCopiesDAO bcDao;

	public List<Author> readAuthorsGS(String authorName) {
		List<Author> authors = new ArrayList<>();
		try {
			if (authorName != null && authorName.length() > 0) {
				authors = aDao.readAllAuthorsByName(authorName);
			} else {
				authors = aDao.readAllAuthors();
			}
			for (Author a : authors) {
				a.setBooks(bDao.readAllBooksByAuthor(a.getAuthorId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong. Failed to get all authors");
		}
		return authors;
	}

	public List<Book> readBooksGS(String bookTitle) {
		List<Book> books = new ArrayList<>();
		try {
			if (bookTitle != null && bookTitle.length() > 0) {
				books = bDao.readAllBooksByTitle(bookTitle);
			} else {
				books = bDao.readAllBooks();
			}
			for (Book b : books) {
				b.setAuthors(aDao.readBookAuthors(b.getBookId()));
				b.setGenres(gDao.readBookGenres(b.getBookId()));
				b.setPublisher(pDao.readBookPublisher(b.getBookId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong. Failed to get all books");
		}
		return books;
	}

	public List<LibraryBranch> readBranchesGS(String branchName) {
		System.out.println("here2");
		List<LibraryBranch> libraryBranches = new ArrayList<>();
		try {
			if (branchName != null && branchName.length() > 0) {
				libraryBranches = lbDao.readLibraryBranchesByName(branchName);
			} else {
				libraryBranches = lbDao.readAllLibraryBranches();
			}
			for (LibraryBranch lb : libraryBranches) {
				lb.setBooks(bDao.readAllBooksByBranch(lb.getBranchId()));
				lb.setBorrowers(brwDao.readBorrowersForBranch(lb.getBranchId()));
				lb.setBookCopies(bcDao.readAllBooksForBranch(lb.getBranchId()));
				lb.setBookLoans(blDao.readBookLoansForBranch(lb.getBranchId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return libraryBranches;
	}

	public List<Genre> readGenresGS(String genreName) {
		List<Genre> genres = new ArrayList<>();
		try {
			if (genreName != null && genreName.length() > 0) {
				genres = gDao.readGenresByName(genreName);
			} else {
				genres = gDao.readAllGenres();
			}
			for (Genre g : genres) {
				g.setBooks(bDao.readAllBooksByGenre(g.getGenreId()));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return genres;
	}

	public List<Borrower> readBorrowersGS(String name) {
		List<Borrower> borrowers = new ArrayList<>();
		try {
			if (name != null && name.length() > 0) {
				borrowers = brwDao.readBorrowerByName(name);
			} else {
				borrowers = brwDao.readAllBorrowers();
			}
			for (Borrower brw : borrowers) {
				brw.setBooks(bDao.readAllBooksBorrowed(brw.getCardNo()));
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return borrowers;
	}

	public List<Publisher> readPublishersGS(String publisherName) {
		List<Publisher> publishers = new ArrayList<>();
		try {
			if (publisherName != null && publisherName.length() > 0) {
				publishers = pDao.readAllPublishersByName(publisherName);
			} else {
				publishers = pDao.readAllPublishers();
			}
			for(Publisher p : publishers) {
				 p.setBooks(bDao.readAllBooksByPublisher(p.getPublisherId()));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return publishers;
	}

	public List<Book> readAllBooksForBranchGS(Integer branchId) {

		BookCopies books = new BookCopies();
		books.setBranchId(branchId);
		try {
			return bDao.readAllBooksByBranch(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
