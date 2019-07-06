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

import com.gcit.lms.entity.Author;

/**
 * @author Incognito
 *
 */
@Repository
public class AuthorDAO extends BaseDAO<Author> implements ResultSetExtractor<List<Author>> {

	public void addAuthor(Author author) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("insert into tbl_author (authorName) values (?)", new Object[] { author.getAuthorName() });
	}

	public Integer addAuthorGetPK(Author author) throws ClassNotFoundException, SQLException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		mysqlTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement("insert into tbl_author (authorName) values (?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, author.getAuthorName());
			return ps;
		}, keyHolder);
		return ((BigInteger) keyHolder.getKey()).intValue();
	}

	public void addBook(Integer bookId, Integer authorId) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("insert into tbl_book_authors values (?, ?)", new Object[] { bookId, authorId });
	}

	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("delete from tbl_author where authorId = ?", new Object[] { author.getAuthorId() });
	}

	public List<Author> readAllAuthors() throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query("select * from tbl_author", this);
	}

	public Author readAuthorById(Integer authorId) throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query("select * from tbl_author where authorId = ?", new Object[] { authorId }, this)
				.get(0);
	}

	public List<Author> readAllAuthorsByName(String authorName) throws ClassNotFoundException, SQLException {
		authorName = "%" + authorName + "%";
		return mysqlTemplate.query("select * from tbl_author where authorName like ?", new Object[] { authorName },
				this);
	}

	public List<Author> readBookAuthors(Integer bookId) throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query(
				"select * from tbl_author where authorId in (select authorId from tbl_book_authors where bookId = ?)",
				new Object[] { bookId }, this);
	}

	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			authors.add(author);
		}
		return authors;
	}

}
