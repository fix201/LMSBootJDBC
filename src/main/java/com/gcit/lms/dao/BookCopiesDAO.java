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

import com.gcit.lms.entity.BookCopies;

/**
 * @author Incognito
 *
 */
@Repository
public class BookCopiesDAO extends BaseDAO<BookCopies> implements ResultSetExtractor<List<BookCopies>> {

	public void addBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("insert into tbl_book_copies values (?, ?, ?)",
				new Object[] { bookCopies.getBookId(), bookCopies.getBranchId(), bookCopies.getNoOfCopies() });
	}

	public void updateBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?",
				new Object[] { bookCopies.getNoOfCopies(), bookCopies.getBookId(), bookCopies.getBranchId() });
	}

	public void deleteBookCopies(BookCopies bookCopies) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("delete from tbl_book_copies where bookId = ? and branchId = ?",
				new Object[] { bookCopies.getBookId(), bookCopies.getBranchId(), bookCopies.getNoOfCopies() });
	}

	public List<BookCopies> readAllBookCopies() throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query("select * from tbl_book_copies", this);
	}

	public List<BookCopies> readAllBooksForBranch(Integer branchId) throws SQLException, ClassNotFoundException {
		return mysqlTemplate.query("select * from tbl_book_copies where branchId = ? and noOfCopies > 0",
				new Object[] { branchId }, this);
	}

	public List<BookCopies> readNoOfCopiesForBranch(Integer bookId, Integer branchId)
			throws SQLException, ClassNotFoundException {
		return mysqlTemplate.query("select * from tbl_book_copies where bookId = ? and branchId = ?",
				new Object[] { bookId, branchId }, this);
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {

		List<BookCopies> bookCopies = new ArrayList<>();

		while (rs.next()) {
			BookCopies bookCopy = new BookCopies();
			bookCopy.setBookId(rs.getInt("bookId"));
			bookCopy.setBranchId(rs.getInt("branchId"));
			bookCopy.setNoOfCopies(rs.getInt("noOfCopies"));
			bookCopies.add(bookCopy);
		}
		return bookCopies;
	}
}
