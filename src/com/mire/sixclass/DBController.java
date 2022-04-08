package com.mire.sixclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DBController {

	// ��������Լ�(DB����)(���̺� �𵨰�ü ����)
	public static int StudentInsert(StudentModel studentModel) {

		int returnValue = 0; // ����Ÿ���̽� ��ɹ� ���ϰ�

		// 1.����Ÿ ���̽� ���ӿ�û
		Connection con = DBUtility.getConnection();

		if (con == null) {
			System.out.println("Mysql Connection Fail");
			return 0;
		}

		// 2.��ɹ� �ϴ�(���Ը�ɹ� �ϴ�: ���������� ��ɹ� �ϴ� : insert)
		String insertQuery = "insert into studentdb.studenttbl values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			// 2.1 insert query binding
			ps = (PreparedStatement) con.prepareStatement(insertQuery);
			ps.setString(1, studentModel.getName());
			ps.setString(2, studentModel.getYear());
			ps.setString(3, studentModel.getGender());		
			ps.setString(4, studentModel.getId());
			ps.setInt(5, studentModel.getKor());
			ps.setInt(6, studentModel.getMath());
			ps.setInt(7, studentModel.getEng());
			ps.setInt(8, studentModel.getTotal());
			ps.setDouble(9, studentModel.getAvr());
			ps.setInt(10, studentModel.getRank());
			// 2.2 query ��ɹ� ����
			// 3.���ϰ�(������ ������ �����Ѵ�.)
			returnValue = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// ps�� null�� �ƴϰ� ps�� �ȴݾ������� �ݾƶ�
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
				// con�� null�� �ƴϰ� con�� �ȴݾ������� �ݾƶ�
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 4.������� �뺸�Ѵ�.
		return returnValue;
	}

	// ��������Լ�(���̺� �𵨰�ü �����ֱ�)
	public static List<StudentModel> PhoneBookeSelect() {
		// ���̺� �ִ� ���ڵ� ���� �������� ���� ArrayList<StudentModel>
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// 1.����Ÿ ���̽� ���ӿ�û
		Connection con = DBUtility.getConnection();
		if (con == null) {
			System.out.println("Mysql Connection Fail");
			return null;
		}
		
		// 2.��ɹ� �ϴ�(select ������ ��ɹ� �ϴ�: ���������� ��ɹ� �ϴ� : select * from studentdb.studenttbl;)
		String selectQuery = "select * from studentdb.studenttbl";
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			// 2.1 insert query binding
			ps = (PreparedStatement) con.prepareStatement(selectQuery);

			// 2.2 query ��ɹ� ���� (select ������)
			// 3.���ϰ�(���ڵ� �� = resultSet�����Ѵ�.) ����: executeUpdata() �� executeQurey() ���� �Ҽ��ִ°�?
			resultSet = ps.executeQuery();

			// ���ڵ���� ����Ʈ�� �����´�
			while (resultSet.next()) {
				String name = resultSet.getString(1);
				String year = resultSet.getString(2);
				String gender = resultSet.getString(3);
				String id = resultSet.getString(4);
				int kor = resultSet.getInt(5);
				int math = resultSet.getInt(6);
				int eng = resultSet.getInt(7);
				int total = resultSet.getInt(8);
				double avr = resultSet.getDouble(9);
				int rank = resultSet.getInt(10);

				StudentModel studentModel = new StudentModel(name, year, gender, id, kor, math, eng, total, avr, rank);

				list.add(studentModel);
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// ps�� null�� �ƴϰ� ps�� �ȴݾ������� �ݾƶ�
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
				// con�� null�� �ƴϰ� con�� �ȴݾ������� �ݾƶ�
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 3.���ϰ�(������ ������ �����Ѵ�.)
		// 4.������� �뺸�Ѵ�.
		return list;

	}

	// ��������Լ�(���̺� �˻��ϱ�: �й�(�й�����,1) �̸�(�̸�����,2))
	public static List<StudentModel> studentSearch(String searchData, int number) {
		final int ID_NUM = 1, NAME_NUM = 2, GENDER_NUM = 3;
		
		// ���ڵ���� ������ List Collection
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// 1.����Ÿ ���̽� ���ӿ�û
		Connection con = DBUtility.getConnection();
		if (con == null) {
			System.out.println("Mysql Connection Fail");
			return null;
		}

		// ��ɹ� �ϴ�(�˻���ɹ��ϴ�: ���������� ��ɹ��ϴ� : select * from studentdb.studenttbl where ~)
		String searchQuery = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		switch (number) {
		case ID_NUM:
			searchQuery = "select * from studentdb.studenttbl where id like ?";
			break;
		case NAME_NUM:
			searchQuery = "select * from studentdb.studenttbl where name like ?";
			break;
		case GENDER_NUM:
			searchQuery = "select * from studentdb.studenttbl where gender like ?";
			break;
		}
		
		// 2.1 search query binding
		try {
			ps = (PreparedStatement) con.prepareStatement(searchQuery);
			searchData = "%" + searchData + "%";
			ps.setString(1, searchData);
			// 2.2 ��ɹ� ����
			// 3. ���ϰ� (���ڵ� �� = resultSet �����Ѵ�.) ����: executeUpdata() �� executeQurey() �����Ҽ��ִ°�?
			resultSet = ps.executeQuery();
			
			// 3.1 ���ϰ� ResultSet�� ArrayList<StudentModel> ��ȯ�Ѵ�.
			while (resultSet.next()) {
				String name = resultSet.getString(1);
				String year = resultSet.getString(2);
				String gender = resultSet.getString(3);
				String id = resultSet.getString(4);
				int kor = resultSet.getInt(5);
				int math = resultSet.getInt(6);
				int eng = resultSet.getInt(7);
				int total = resultSet.getInt(8);
				double avr = resultSet.getDouble(9);
				int rank = resultSet.getInt(10);

				StudentModel studentModel = new StudentModel(name, year, gender, id, kor, math, eng, total, avr, rank);

				list.add(studentModel);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				// ps�� null�� �ƴϰ� ps�� �ȴݾ������� �ݾƶ�
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
				// con�� null�� �ƴϰ� con�� �ȴݾ������� �ݾƶ�
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 4. ������� �뺸�Ѵ�.
		return list;
	}

	// ��������Լ�(���̺� �����ϱ�: �й�)
	public static int studentDelete(String deleteData, int number) {
		final int ID_NUM = 1;

		// 1.����Ÿ ���̽� ���ӿ�û
		Connection con = DBUtility.getConnection();
		if (con == null) {
			System.out.println("Mysql Connection Fail");
			return 0;
		}

		// ��ɹ� �ϴ�(������ɹ��ϴ�: ���������� ��ɹ��ϴ� : delete from studentdb.studenttbl where id like ?;)
		String deleteQuery = null;
		PreparedStatement ps = null;
		int resultNumber = 0;

		switch (number) {
		case ID_NUM:
			deleteQuery = "delete from studentdb.studenttbl where id like ?";
			break;

		}
		// 2.1 delete query binding
		try {
			ps = (PreparedStatement) con.prepareStatement(deleteQuery);
			deleteData = "%" + deleteData + "%";
			ps.setString(1, deleteData);
			// 2.2 ��ɹ� ����
			// 3. ���ϰ� (���ڵ� �� = resultNumber �����Ѵ�.) ����: executeUpdata() �� executeQurey() �����Ҽ��ִ°�?
			resultNumber = ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				// ps�� null�� �ƴϰ� ps�� �ȴݾ������� �ݾƶ�
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
				// con�� null�� �ƴϰ� con�� �ȴݾ������� �ݾƶ�
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 4. ������� �뺸�Ѵ�.
		return resultNumber;
	}

	public static int studentUpdata(String id, int kor, int math, int eng, int total, double avr, int rank) {
		final int ID_NUM = 1;

		// 1.����Ÿ ���̽� ���ӿ�û
		Connection con = DBUtility.getConnection();
		if (con == null) {
			System.out.println("Mysql Connection Fail");
			return 0;
		}

		// ��ɹ� �ϴ�(������ɹ��ϴ�: ���������� ��ɹ��ϴ� : update studentdb.studenttbl set kor = ?, math = ?, eng = ?, total = ?, avr = ?, rank = ? where id = ?)
		String updateQuery = null;
		PreparedStatement ps = null;
		int resultNumber = 0;

		switch (ID_NUM) {
		case ID_NUM:
			updateQuery = "update studentdb.studenttbl set kor = ?, math = ?, eng = ?, total = ?, avr = ?, rank = ? where id = ?";
			break;
		}
		// 2.1 update query binding
		try {
			ps = (PreparedStatement) con.prepareStatement(updateQuery);
			ps.setInt(1, kor);
			ps.setInt(2, math);
			ps.setInt(3, eng);
			ps.setInt(4, total);
			ps.setDouble(5, avr);
			ps.setInt(6, rank);
			ps.setString(7, id);
			// 2.2 ��ɹ� ����
			// 3. ���ϰ� (���ڵ� �� = resultNumber �����Ѵ�.) ����: executeUpdata() �� executeQurey() �����Ҽ��ִ°�?
			resultNumber = ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				// ps�� null�� �ƴϰ� ps�� �ȴݾ������� �ݾƶ�
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
				// con�� null�� �ƴϰ� con�� �ȴݾ������� �ݾƶ�
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 4. ������� �뺸�Ѵ�.
		return resultNumber;
	}

	public static List<StudentModel> StudentSort(int no) {
		final int ASC = 1, DESC = 2;
		
		// ���̺� �ִ� ���ڵ� ���� �������� ���� ArrayList<StudentModel>
		List<StudentModel> list = new ArrayList<StudentModel>();
		String sortQuery = null;
		
		// 1.����Ÿ ���̽� ���ӿ�û
		Connection con = DBUtility.getConnection();
		if (con == null) {
			System.out.println("Mysql Connection Fail");
			return null;
		}
		
		// 2.��ɹ� �ϴ�(select ������ ��ɹ� �ϴ�: ���������� ��ɹ� �ϴ� : select * from studentdb.studenttbl order by total ASC / DESC;)
		switch (no) {
		case ASC:
			sortQuery = "select * from studentdb.studenttbl order by total ASC";
			break;
		case DESC:
			sortQuery = "select * from studentdb.studenttbl order by total DESC";
			break;
		}

		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			// 2.1 sort query binding
			ps = (PreparedStatement) con.prepareStatement(sortQuery);

			// 2.2 query ��ɹ� ���� (sort ������)
			// 3.���ϰ�(���ڵ� �� = resultSet�����Ѵ�.) ����: executeUpdata() �� executeQurey() ���� �Ҽ��ִ°�?
			resultSet = ps.executeQuery();

			// ���ڵ���� ����Ʈ�� �����´�
			while (resultSet.next()) {
				String name = resultSet.getString(1);
				String year = resultSet.getString(2);
				String gender = resultSet.getString(3);			
				String id = resultSet.getString(4);
				int kor = resultSet.getInt(5);
				int math = resultSet.getInt(6);
				int eng = resultSet.getInt(7);
				int total = resultSet.getInt(8);
				double avr = resultSet.getDouble(9);
				int rank = resultSet.getInt(10);

				StudentModel studentModel = new StudentModel(name, year, gender, id, kor, math, eng, total, avr, rank);

				list.add(studentModel);
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// ps�� null�� �ƴϰ� ps�� �ȴݾ������� �ݾƶ�
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
				// con�� null�� �ƴϰ� con�� �ȴݾ������� �ݾƶ�
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 3.���ϰ�(������ ������ �����Ѵ�.)
		// 4.������� �뺸�Ѵ�.
		return list;
	}
}