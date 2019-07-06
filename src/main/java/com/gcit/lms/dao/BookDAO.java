/**
 * 
 */
package com.gcit.lms.dao;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gcit.lms.entity.Book;

/**
 * @book Incognito
 *
 */
@Repository
public class BookDAO extends BaseDAO<Book> implements ResultSetExtractor<List<Book>> {

	public void addBook(Book book) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("insert into tbl_book (title) values (?)", new Object[] { book.getTitle() });
	}

	public Integer addBookGetPK(Book book) throws ClassNotFoundException, SQLException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		mysqlTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement("insert into tbl_book (title) values (?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, book.getTitle());
			return ps;
		}, keyHolder);
		return ((BigInteger) keyHolder.getKey()).intValue();
	}

	public void addBookAuthors(Integer bookId, Integer authorId) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("insert into tbl_book_authors values (?, ?)", new Object[] { bookId, authorId });
	}

	public void addGenres(Integer genreId, Integer bookId) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("insert into tbl_book_genres values (?, ?)", new Object[] { genreId, bookId });
	}

	public void addPublisher(Integer pubId, Integer bookId) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_book set pubId = ? where bookId = ?", new Object[] { pubId, bookId });
	}

	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_book set title = ? where bookId = ?",
				new Object[] { book.getTitle(), book.getBookId() });
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("delete from tbl_book where bookId = ?", new Object[] { book.getBookId() });
	}

	public List<Book> readAllBooks() throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query("select * from tbl_book", this);
	}

	public List<Book> readAllBooksByAuthor(String authorName) throws ClassNotFoundException, SQLException {
		authorName = "%" + authorName + "%";
		return mysqlTemplate.query(
				"select * from tbl_book where bookId in (select bookId from tbl_book_authors where authorId in (select authorId from tbl_author where authorName like ?))",
				new Object[] { authorName }, this);
	}

	public List<Book> readAllBooksByTitle(String title) throws ClassNotFoundException, SQLException {
		title = "%" + title + "%";
		return mysqlTemplate.query("select * from tbl_book where title like ?", new Object[] { title }, this);
	}

	public List<Book> readAllBooksByBranch(Integer branchId) throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query(
				"select * from tbl_book where tbl_book.bookId IN (select tbl_book_copies.bookId from tbl_book_copies where branchId = ?)",
				new Object[] { branchId }, this);
	}

	public List<Book> readAllBooksByAuthor(Integer authorId) throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query(
				"select * from tbl_book where bookId in (select bookId from tbl_book_authors where authorId = ?)",
				new Object[] { authorId }, this);
	}

	public List<Book> readAllBooksByGenre(Integer genreId) throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query(
				"select * from tbl_book where bookId in (select bookId from tbl_book_genres where genre_id = ?)",
				new Object[] { genreId }, this);
	}

	public List<Book> readAllBooksBorrowed(Integer cardNo) throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query(
				"select * from tbl_book where bookId IN (select bookId from tbl_book_loans where cardNo = ? and dateIn is null)",
				new Object[] { cardNo }, this);
	}

	public List<Book> readAllBooksBorrowedByBranch(Integer cardNo, Integer branchId)
			throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query(
				"select * from tbl_book where bookId IN (select bookId from tbl_book_loans where cardNo = ? and branchId = ? and dateIn is null)",
				new Object[] { cardNo, branchId }, this);
	}

	public List<Book> readAllBooksByPublisher(Integer publisherId) throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query(
				"select * from tbl_book where pubId IN (select publisherId from tbl_publisher where publisherId = ?)",
				new Object[] { publisherId }, this);
	}

	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			books.add(book);
		}
		return books;
	}

}
