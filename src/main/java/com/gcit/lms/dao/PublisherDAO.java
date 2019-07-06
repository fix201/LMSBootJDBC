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

import com.gcit.lms.entity.Publisher;

/**
 * @author Incognito
 *
 */
@Repository
public class PublisherDAO extends BaseDAO<Publisher> implements ResultSetExtractor<List<Publisher>> {

	public void addPublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("insert into tbl_publisher (publisherName) values (?)",
				new Object[] { publisher.getPublisherName() });
	}

	public Integer addPublisherGetPK(Publisher publisher) throws ClassNotFoundException, SQLException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		mysqlTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement("insert into tbl_publisher (publisherName) values (?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, publisher.getPublisherName());
			return ps;
		}, keyHolder);
		return ((BigInteger) keyHolder.getKey()).intValue();
	}

	public void addPublisherAddress(Integer publisherId, String publisherAddress)
			throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_publisher set publisherAddress = ? where publisherId = ?",
				new Object[] { publisherAddress, publisherId });
	}

	public void addPublisherPhone(Integer publisherId, String publisherPhone)
			throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_publisher set publisherPhone = ? where publisherId = ?",
				new Object[] { publisherPhone, publisherId });
	}

	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_publisher set publisherName = ? where publisherId = ?",
				new Object[] { publisher.getPublisherId(), publisher.getPublisherId() });
	}

	public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("delete from tbl_publisher where publisherId = ?",
				new Object[] { publisher.getPublisherId() });
	}

	public List<Publisher> readAllPublishers() throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query("select * from tbl_publisher", this);
	}

	public List<Publisher> readAllPublishersByName(String publisherName) throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query("select * from tbl_publisher where publisherName like ?",
				new Object[] { "%" + publisherName + "%" }, this);
	}

	public Publisher readPublisher(Integer publisherId) throws ClassNotFoundException, SQLException {
		return mysqlTemplate
				.query("select * from tbl_publisher where publisherId = ?", new Object[] { publisherId }, this).get(0);
	}

	public Publisher readBookPublisher(Integer bookId) throws ClassNotFoundException, SQLException {
		List<Publisher> publishers = mysqlTemplate.query(
				"select * from tbl_publisher where publisherId in (select pubId from tbl_book where bookId = ?)",
				new Object[] { bookId }, this);
		if(publishers != null && publishers.size() > 0) {
			return publishers.get(0);
		}
		return null;
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher publisher = new Publisher();
			publisher.setPublisherId(rs.getInt("publisherId"));
			publisher.setPublisherName(rs.getString("publisherName"));
			publisher.setPublisherAddress(rs.getString("publisherAddress"));
			publisher.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(publisher);
		}
		return publishers;
	}

}
