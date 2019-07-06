/**
 * 
 */
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
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;

/**
 * @author Incognito
 *
 */
@RestController
public class BorrowerService extends GeneralService {

	@Transactional
	@RequestMapping(value = "/borrower/getBorrowerName", method = RequestMethod.GET, produces = "application/json")
	public String getBorrowerName(@RequestParam(value = "cardNo") Integer cardNo) {

		try {
			return brwDao.readBorrowerByCardNo(cardNo);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@RequestMapping(value = "/borrower/validateBorrower", method = RequestMethod.POST, produces = "application/json")
	public boolean validateBorrower(@RequestBody Borrower borrower) {

		try {
			if (brwDao.validateBorrower(borrower)) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	@RequestMapping(value = "/borrower/addBookLoan", method = RequestMethod.POST, produces = "application/json")
	public boolean addBookLoan(@RequestBody BookLoan bookLoan) {

		try {
			blDao.addBookLoan(bookLoan);
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	@RequestMapping(value = "/borrower/removeBookLoan", method = RequestMethod.POST, produces = "application/json")
	public boolean removeBookLoan(@RequestBody BookLoan bookLoan) {

		try {
			blDao.updateAddCopies(bookLoan);
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	@RequestMapping(value = "/borrower/updateBookLoan", method = RequestMethod.POST, produces = "application/json")
	public boolean updateBookLoan(@RequestBody BookLoan bookLoan) {

		try {
			blDao.updateBookLoan(bookLoan);
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	@RequestMapping(value = "/borrower/getBooksBorrowed", method = RequestMethod.GET, produces = "application/json")
	public List<Book> getBooksBorrowed(@RequestParam(value = "cardNo") Integer cardNo,
			@RequestParam(value = "branchNo") Integer branchNo) {

		try {
			return bDao.readAllBooksBorrowedByBranch(cardNo, branchNo);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
