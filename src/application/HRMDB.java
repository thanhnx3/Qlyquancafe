package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HRMDB {
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	
	private Connection conn;
	
	public HRMDB(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}
	
	public boolean addCafe(Cafe cafe) {
		conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
		String sqlCommand = "INSERT INTO tblcafe VALUES(?, ?, ?)";
		PreparedStatement pst = null;
		boolean result = false;
		try {
			pst = conn.prepareStatement(sqlCommand);
			
			pst.setString(1, cafe.getId());
			pst.setString(2, cafe.getName());
			pst.setString(3, cafe.getSize());
			pst.setString(4, cafe.getPrice());
			pst.setString(5, cafe.getNote());
			
			int i = pst.executeUpdate();
			if(i == 1) {
				result = true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally { 
			DBConnection.closePreparedStatement(pst);
			DBConnection.closeConnect(conn);
		}
		return result;
	}
	
	//update Cafe
	public boolean updateCafe(Cafe cafe) {
		conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
		String sqlCommand = "UPDATE tblcafe SET name = ?, size = ?, price = ?, note = ? " + "WHERE cafeId = ?";
		PreparedStatement pst = null;
		boolean result = false;
		try {
			pst = conn.prepareStatement(sqlCommand);
			pst.setString(1, cafe.getName());
			pst.setString(2, cafe.getSize());
			pst.setString(3, cafe.getPrice());
			pst.setString(4, cafe.getNote());
			pst.setString(5, cafe.getId());
			int i = pst.executeUpdate();
			if(i == 1) {
				result = true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnection.closePreparedStatement(pst);
			DBConnection.closeConnect(conn);
		}
		return result;
	}
	
	public boolean deleteCafe(String cafeId) {
		conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
		String sqlCommand = "DELETE FROM tblCafe WHERE cafeId = ?";
		PreparedStatement pst = null;
		
		boolean result = false;
		try {
			pst = conn.prepareStatement(sqlCommand);
			pst.setString(1, cafeId);
			int i = pst.executeUpdate();
			if(i == 1) {
				result = true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnection.closePreparedStatement(pst);
			DBConnection.closeConnect(conn);
		}
		return result;
	}
	
	public List<Cafe> getCafeList() {
		List<Cafe> cafeList = new ArrayList<Cafe>();
		Statement statement = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.create(jdbcURL, jdbcUsername, jdbcPassword);
			statement = conn.createStatement();
			rs = statement.executeQuery("Select * from tblcafe");
			String cafeId;
			String name;
			String size;
			String price;
			String note;
			while(rs.next()) {
				cafeId = rs.getString(5);
				name = rs.getString(1);
				size = rs.getString(2);
				price = rs.getString(3);
				note = rs.getString(4);
				cafeList.add(new Cafe(cafeId, name, size, price, note));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeResultSet(rs);
			DBConnection.closeStatement(statement);
			DBConnection.closeConnect(conn);
		}
		return cafeList;
	}
}
