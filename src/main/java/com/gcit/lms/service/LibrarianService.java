package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.LibraryBranch;

@RestController
public class LibrarianService extends GeneralService {

	@Transactional
	@RequestMapping(value = "/librarian/readLibraryBranches", method = RequestMethod.GET, produces = "application/json")
	public List<LibraryBranch> readLibraryBranches(@RequestParam(value = "branchName") String branchName) {
		return readBranchesGS(branchName);
	}
	
	@Transactional 
	@RequestMapping(value = "/librarian/updateLibraryBranch", method = RequestMethod.POST, consumes = "application/json")
	public boolean updateBranch(@RequestBody LibraryBranch libraryBranch) {

		try {
			lbDao.updateLibraryBranch(libraryBranch);
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional 
	@RequestMapping(value = "/librarian/updateBranchCopies", method = RequestMethod.POST, consumes = "application/json")
	public boolean updateBranchCopies(@RequestBody BookCopies bookCopies) {

		try {
			bcDao.updateBookCopies(bookCopies);
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional 
	@RequestMapping(value = "/librarian/addBookCopy", method = RequestMethod.POST, consumes = "application/json")
	public boolean addBookCopy(@RequestBody BookCopies bookCopies) {

		try {
			bcDao.addBookCopies(bookCopies);
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional 
	@RequestMapping(value = "/librarian/getBookCopiesForBranch", method = RequestMethod.GET, produces = "application/json")
	public List<Book> getBookCopiesForBranch(@RequestParam(value = "branchId") Integer branchId) {

		try {
			List<Book> bc = bDao.readAllBooksByBranch(branchId);
			return bc;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Something went wrong. Failed to get books from branch");
		}
		return null;
	}

	@Transactional 
	@RequestMapping(value = "/librarian/getNoOfCopiesForBranch", method = RequestMethod.GET, produces = "application/json")
	public BookCopies getNoOfCopiesForBranch(@RequestParam(value = "bookId") Integer bookId, @RequestParam(value = "branchId") Integer branchId) {

		try {
			List<BookCopies> bc = bcDao.readNoOfCopiesForBranch(bookId, branchId);
			return bc.get(0);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
