package member;

import java.sql.*;

import connectionMgr.DBConnectionMgr;

public class MemberDAO {
	private DBConnectionMgr connectionMgr;
	private Connection connection;
	private PreparedStatement pStatement;
	private ResultSet resultSet;
	
	public MemberDAO() {
		try {
			connectionMgr = DBConnectionMgr.getInstance();
			System.out.println("connectionMgr : " + connectionMgr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public boolean login(String email, String password) {
		boolean result = false;
		String sql = "select * from member_login where email='" + email + "' and password='" + password + "'";

		try {
			connection = connectionMgr.getConnection();
			pStatement = connection.prepareStatement(sql);
			resultSet = pStatement.executeQuery();

			result = resultSet.next();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionMgr.freeConnection(connection, pStatement, resultSet);
		}

		return result;
	}

	public int regist(String email, String password) {
		// 1 : 가입 성공, 0 : 가입 실패
		int result = 0;

		try {
			connection = connectionMgr.getConnection();
			pStatement = connection.prepareStatement("insert into member_login value('" + email + "', '" + password + "')");
			
			connection.setAutoCommit(false);
			
			result = pStatement.executeUpdate();
			if (result > 0) {
				connection.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionMgr.freeConnection(connection, pStatement, resultSet);
		}

		return result;
	}
	
	public int withdraw(String email, String password) {
		int result = -1;
		
		try {
			connection = connectionMgr.getConnection();
			pStatement = connection.prepareStatement("select password from member_login where id='" + email + "'");
			resultSet = pStatement.executeQuery(); 

			connection.setAutoCommit(false);
			if (resultSet.next()) {
				if (password.equals(resultSet.getString("password"))) {
					pStatement = connection.prepareStatement("delete from member_login where id='" + email + "'");
					result = pStatement.executeUpdate();
					connection.commit();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionMgr.freeConnection(connection, pStatement, resultSet);
		}
		
		return result;
	}
}
