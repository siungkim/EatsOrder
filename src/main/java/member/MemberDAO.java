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

	// 로그인
	public boolean login(String email, String password) {
		boolean result = false;

		try {
			connection = connectionMgr.getConnection();
			pStatement = connection
					.prepareStatement("select * from member_login where email='" + email + "' and password='" + password + "'");
			resultSet = pStatement.executeQuery();

			result = resultSet.next();
			System.out.println("로그인 결과 : " + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionMgr.freeConnection(connection, pStatement, resultSet);
		}

		return result;
	}

	// 중복값 체크
	public boolean checkMemberInfo(String data, int type) {
		// data 1: 이메일, 2: 전화번호, 3: 닉네임 중복값 체크
		boolean result = false;
		String checkType = "";

		switch (type) {
		case 1:
			checkType = "email";
			break;
		case 2:
			checkType = "phone";
			break;
		case 3:
			checkType = "nickname";
			break;
		default:
			break;
		}

		try {
			connection = connectionMgr.getConnection();
			pStatement = connection
					.prepareStatement("select " + checkType + " from member_info where " + checkType + "='" + data + "'");
			resultSet = pStatement.executeQuery();
			result = resultSet.next();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionMgr.freeConnection(connection, pStatement, resultSet);
		}
		return result;
	}

	// 회원가입
	public int insertMember(String email, String password, String phone, String nickname, char receive_marketing) {
		// 1 : 가입 성공, 0 : 가입 실패
		int result = 0;

		try {
			connection = connectionMgr.getConnection();

			pStatement = connection.prepareStatement("insert into member_info(email, phone, nickname, receive_marketing) values('"
					+ email + "', '" + phone + "', '" + nickname + "', '" + receive_marketing + "')");

			connection.setAutoCommit(false);

			result = pStatement.executeUpdate();
			if (result > 0) {
				connection.commit();
				pStatement = connection.prepareStatement("insert into member_login values('" + email + "', '" + password + "')");
				result = pStatement.executeUpdate();
				if (result > 0) {
					connection.commit();
				}
			}

			if (result > 0) {
				System.out.println("회원가입 성공");
			} else {
				System.out.println("회원가입 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionMgr.freeConnection(connection, pStatement);
		}

		return result;
	}

	// 회원탈퇴
	public int deleteMember(String email, String password, String reason_withdraw) {
		// result가 0보다 크면 탈퇴 성공
		int[] resultArray = {};
		int result = 0;

		try {
			connection = connectionMgr.getConnection();
			pStatement = connection.prepareStatement("select password from member_login where email='" + email + "'");
			resultSet = pStatement.executeQuery();

			connection.setAutoCommit(false);
			if (resultSet.next()) {
				if (password.equals(resultSet.getString("password"))) {
					if (password.equals(resultSet.getString("password"))) {
						pStatement = connection.prepareStatement("delete from member_login where email='" + email + "'");
						result = pStatement.executeUpdate();
						if (result > 0) {
							pStatement = connection.prepareStatement(
									"update member_info set withdraw_date=sysdate where email='" + email + "'");
							result = pStatement.executeUpdate();
							if (result > 0) {
								pStatement = connection.prepareStatement("insert into withdraw_member values('" + email
										+ "', sysdate, '" + reason_withdraw + "')");
								result = pStatement.executeUpdate();
							}
						}
					}
				}
			}

			if (result > 0) {
				connection.commit();
				System.out.println("회원탈퇴 성공");
			} else {
				System.out.println("회원탈퇴 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionMgr.freeConnection(connection, pStatement, resultSet);
		}

		return result;
	}

	// 회원수정
	public int updateMember(String email, String password, String phone, String nickname, char receive_marketing) {
		// result가 0보다 크면 회원수정 성공
		int result = -1;

		try {
			connection = connectionMgr.getConnection();
			pStatement = connection.prepareStatement("select password from member_login where email='" + email + "'");
			resultSet = pStatement.executeQuery();

			connection.setAutoCommit(false);
			if (resultSet.next()) {
				pStatement = connection
						.prepareStatement("update member_login set password='" + password + "' where email='" + email + "'");
				result = pStatement.executeUpdate();
				if (result > 0) {
					pStatement = connection.prepareStatement("update member_info set phone='" + phone + "', nickname='" + nickname
							+ "', receive_marketing='" + receive_marketing + "' where email='" + email + "'");
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
