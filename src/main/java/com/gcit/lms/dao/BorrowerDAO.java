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

import com.gcit.lms.entity.Borrower;

/**
 * @borrower Incognito
 *
 */
@Repository
public class BorrowerDAO extends BaseDAO<Borrower> implements ResultSetExtractor<List<Borrower>> {

	public void addBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("insert into tbl_borrower (borrower_name) values (?)",
				new Object[] { borrower.getName() });
	}
	
	public Integer addBorrowerGetPK(Borrower borrower) throws ClassNotFoundException, SQLException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		mysqlTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement("insert into tbl_borrower (borrower_name) values (?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, borrower.getName());
			return ps;
		}, keyHolder);
		return ((BigInteger) keyHolder.getKey()).intValue();
	}

	public void addBorrowerAddress(Integer borrowerId, String borrowerAddress)
			throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_publisher set borrowerAddress = ? where borrowerId = ?",
				new Object[] { borrowerAddress, borrowerId });
	}

	public void addBorrowerPhone(Integer borrowerId, String phone) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_publisher set borrowerPhone = ? where borrowerId = ?",
				new Object[] { phone, borrowerId });
	}

	public void updateBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_borrower set borrower_name = ? where borrower_id = ?",
				new Object[] { borrower.getName(), borrower.getCardNo() });
	}

	public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("delete from tbl_borrower where borrower_id = ?", new Object[] { borrower.getCardNo() });
	}

	public boolean validateBorrower(Borrower borrower) throws SQLException {
		List<Borrower> brw =  mysqlTemplate.query("select * from tbl_borrower where cardNo = ?",
				new Object[] { borrower.getCardNo() }, this);
		if(brw != null && brw.size() > 0) {
			return true;
		}
		return false;
	}

	public List<Borrower> readAllBorrowers() throws  SQLException {
		return mysqlTemplate.query("select * from tbl_borrower", this);
	}
	
	public List<Borrower> readBorrowerByName(String name) throws ClassNotFoundException, SQLException {
		name = "%"+name+"%";
		return mysqlTemplate.query("select * from tbl_borrower where name like ? ",new Object[] { name }, this);
	}
	
	public String readBorrowerByCardNo(Integer cardNo) throws ClassNotFoundException, SQLException {
		List<Borrower> brw =  mysqlTemplate.query("select * from tbl_borrower where cardNo = ?",
				new Object[] { cardNo }, this);
		if(brw != null && brw.size() > 0) {
			return brw.get(0).getName();
		}
		return null;
	}
	
	public List<Borrower> readBorrowersForBranch(Integer branchId) throws ClassNotFoundException {
		return mysqlTemplate.query("select * from tbl_borrower where cardNo in (select cardNo from tbl_book_loans where branchId = ?)",new Object[] { branchId }, this);
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			borrowers.add(borrower);
		}
		return borrowers;
	}

	
}
