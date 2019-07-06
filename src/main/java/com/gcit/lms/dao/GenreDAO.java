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

import com.gcit.lms.entity.Genre;

/**
 * @author Incognito
 *
 */
@Repository
public class GenreDAO extends BaseDAO<Genre> implements ResultSetExtractor<List<Genre>>{


	public void addGenre(Genre genre) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("insert into tbl_genre (genre_name) values (?)", new Object[] { genre.getGenreName() });
	}
	
	public Integer addGenreGetPK(Genre genre) throws ClassNotFoundException, SQLException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		mysqlTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement("insert into tbl_genre (genre_name) values (?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, genre.getGenreName());
			return ps;
		}, keyHolder);
		return ((BigInteger) keyHolder.getKey()).intValue();
	}
	
	public void updateGenre(Genre genre) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_genre set genre_name = ? where genre_id = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("delete from tbl_genre where genre_id = ?", new Object[] { genre.getGenreId() });
	}

	public List<Genre> readAllGenres() throws  SQLException {
		return mysqlTemplate.query("select * from tbl_genre", this);
	}
	
	public Genre readGenreById(Integer genreId) throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query("select * from tbl_genre where genre_id = ?", new Object[] {genreId}, this).get(0);
	}
	
	public List<Genre> readGenresByName(String genreName) throws ClassNotFoundException, SQLException {
		genreName = "%" + genreName + "%";
		return mysqlTemplate.query("select * from tbl_genre where genre_name like ?", new Object[] {genreName}, this);
	}
	
	public List<Genre> readBookGenres(Integer genreId) throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query("select * from tbl_genre where genre_id in (select genre_id from tbl_book_genres where bookId = ?)", new Object[] {genreId}, this);
	}

	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<>();
		
		while (rs.next()) {
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genre_id"));
			genre.setGenreName(rs.getString("genre_name"));
			genres.add(genre);
		}
		return genres;
	}
}
