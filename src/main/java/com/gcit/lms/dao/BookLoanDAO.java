/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.gcit.lms.entity.BookLoan;

/**
 * @author Incognito
 *
 */
@Repository
public class BookLoanDAO extends BaseDAO<BookLoan> implements ResultSetExtractor<List<BookLoan>> {

	public void addBookLoan(BookLoan bookLoan) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) values (?,?,?,now(),now() + INTERVAL 7 DAY)",
				new Object[] { bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo() });
		updateSubtractCopies(bookLoan);
	}

	// updates time
	public void updateBookLoan(BookLoan bookLoan) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_book_loans set dateIn = now() where bookId = ? and branchId = ? and cardNo = ?",
				new Object[] { bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo() });
	}

	public void updateAddCopies(BookLoan bookloan) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_book_copies set noOfCopies = noOfCopies+1 where branchId = ? and bookId = ?",
				new Object[] { bookloan.getBranchId(), bookloan.getBookId() });
		updateBookLoan(bookloan);
	}

	public void override(BookLoan bookLoan) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_book_loans set dueDate = now() + INTERVAL 7 DAY  where bookId = ? and branchId = ? and cardNo = ?",
				new Object[] { bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo() });
	}

	public void updateSubtractCopies(BookLoan bookloan) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_book_copies set noOfCopies = noOfCopies-1 where branchId = ? and bookId = ?",
				new Object[] { bookloan.getBranchId(), bookloan.getBookId() });
	}

	public void deleteBookLoan(BookLoan bookLoan) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("delete from tbl_book_loans where bookId = ? and branchId = ? and cardNo = ?", new Object[] {
				bookLoan.getDateIn(), bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo() });
	}

	public List<BookLoan> readAllBookLoans() throws ClassNotFoundException {
		return mysqlTemplate.query("select * from tbl_book_loans", this);
	}

	public BookLoan readBookLoan(BookLoan bookLoan) throws ClassNotFoundException {
		return mysqlTemplate.query("select * from tbl_book_loans where bookId = ? and branchId = ? and cardNo = ?",
				new Object[] { bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo() }, this).get(0);
	}
	
	public List<BookLoan> readBookLoansForBranch(Integer branchId) throws ClassNotFoundException {
		return mysqlTemplate.query("select * from tbl_book_loans", this);
	}

	@Override
	public List<BookLoan> extractData(ResultSet rs) throws SQLException {
		List<BookLoan> bookLoans = new ArrayList<>();

		while (rs.next()) {
			BookLoan bookLoan = new BookLoan();
			bookLoan.setBookId(rs.getInt("bookId"));
			bookLoan.setBranchId(rs.getInt("branchId"));
			bookLoan.setCardNo(rs.getInt("cardNo"));
			bookLoan.setDateIn(rs.getString("dateIn"));
			bookLoan.setDateOut(rs.getString("dateOut"));
			bookLoan.setDueDate(rs.getString("dueDate"));
			bookLoans.add(bookLoan);
		}
		return bookLoans;
	}

}
