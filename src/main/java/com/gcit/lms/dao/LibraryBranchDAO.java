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

import com.gcit.lms.entity.LibraryBranch;

/**
 * @author Incognito
 *
 */
@Repository
public class LibraryBranchDAO extends BaseDAO<LibraryBranch> implements ResultSetExtractor<List<LibraryBranch>> {

	public void addLibraryBranch(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("insert into tbl_library_branch (branchName, address) values (?, ?)",
				new Object[] { libraryBranch.getBranchName(), libraryBranch.getBranchAddress() });
	}
	
	public Integer addLibraryBranchGetPK(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		mysqlTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement("insert into tbl_library_branch (branchName, address) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, libraryBranch.getBranchName());
			ps.setString(2, libraryBranch.getBranchAddress());
			return ps;
		}, keyHolder);
		return ((BigInteger) keyHolder.getKey()).intValue();
	}

	public void addLibraryBranchAddress(Integer branchId, String branchAddress)
			throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("update tbl_publisher set branchAddress = ? where branchId = ?", new Object[] { branchAddress, branchId });
	}

	public void updateLibraryBranch(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {
		if (libraryBranch.getBranchName() != null && libraryBranch.getBranchAddress() != null) {
			mysqlTemplate.update("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?", new Object[] {
					libraryBranch.getBranchName(), libraryBranch.getBranchAddress(), libraryBranch.getBranchId() });
		} else if (libraryBranch.getBranchAddress() != null) {
			System.out.println(libraryBranch.getBranchAddress() + " " + libraryBranch.getBranchId());
			mysqlTemplate.update("update tbl_library_branch set branchAddress = ? where branchId = ?",
					new Object[] { libraryBranch.getBranchAddress(), libraryBranch.getBranchId() });
		} else if (libraryBranch.getBranchName() != null) {
			mysqlTemplate.update("update tbl_library_branch set branchName = ? where branchId = ?",
					new Object[] { libraryBranch.getBranchName(), libraryBranch.getBranchId() });
		}
	}

	public void deleteLibraryBranch(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException {
		mysqlTemplate.update("delete from tbl_library_branch where libraryBranch_id = ?", new Object[] { libraryBranch.getBranchId() });
	}

	public List<LibraryBranch> readAllLibraryBranches() throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query("select * from tbl_library_branch", this);
	}

	public LibraryBranch readLibraryBranchById(Integer branchId) throws ClassNotFoundException, SQLException {
		return mysqlTemplate.query("select * from tbl_library_branch where branchId = ?", new Object[] { branchId }, this).get(0);
	}
	
	public List<LibraryBranch> readLibraryBranchesByName(String branchName) throws ClassNotFoundException, SQLException {
		branchName = "%"+branchName+"%";
		return mysqlTemplate.query("select * from tbl_library_branch where branchName like ?", new Object[] { branchName }, this);
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> libraryBranches = new ArrayList<>();

		while (rs.next()) {
			LibraryBranch libraryBranch = new LibraryBranch();
			libraryBranch.setBranchId(rs.getInt("branchId"));
			libraryBranch.setBranchName(rs.getString("branchName"));
			libraryBranch.setBranchAddress(rs.getString("branchAddress"));
			libraryBranches.add(libraryBranch);
		}

		return libraryBranches;
	}

}
