package com.mire.sixclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DBController {

	// 정적멤버함수(DB삽입)(테이블 모델객체 삽입)
	public static int StudentInsert(StudentModel studentModel) {

		int returnValue = 0; // 데이타베이스 명령문 리턴값

		// 1.데이타 베이스 접속요청
		Connection con = DBUtility.getConnection();

		if (con == null) {
			System.out.println("Mysql Connection Fail");
			return 0;
		}

		// 2.명령문 하달(삽입명령문 하달: 쿼리문으로 명령문 하달 : insert)
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
			// 2.2 query 명령문 실행
			// 3.리턴값(삽입한 갯수를 리턴한다.)
			returnValue = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// ps가 null이 아니고 ps가 안닫아졌으면 닫아라
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
				// con이 null이 아니고 con이 안닫아졌으면 닫아라
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 4.결과값을 통보한다.
		return returnValue;
	}

	// 정적멤버함수(테이블 모델객체 보여주기)
	public static List<StudentModel> PhoneBookeSelect() {
		// 테이블에 있는 레코드 셋을 가져오기 위한 ArrayList<StudentModel>
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// 1.데이타 베이스 접속요청
		Connection con = DBUtility.getConnection();
		if (con == null) {
			System.out.println("Mysql Connection Fail");
			return null;
		}
		
		// 2.명령문 하달(select 쿼리문 명령문 하달: 쿼리문으로 명령문 하달 : select * from studentdb.studenttbl;)
		String selectQuery = "select * from studentdb.studenttbl";
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			// 2.1 select query binding
			ps = (PreparedStatement) con.prepareStatement(selectQuery);

			// 2.2 query 명령문 실행 (select 쿼리문)
			// 3.리턴값(레코드 셋 = resultSet리턴한다.)
			resultSet = ps.executeQuery();

			// 레코드셋을 리스트가 가져온다
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
				// ps가 null이 아니고 ps가 안닫아졌으면 닫아라
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
				// con이 null이 아니고 con이 안닫아졌으면 닫아라
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 3.리턴값(삽입한 갯수를 리턴한다.)
		// 4.결과값을 통보한다.
		return list;

	}

	// 정적멤버함수(테이블 검색하기: 학번(학번내용,1) 이름(이름내용,2) 성별(성별내용,3))
	public static List<StudentModel> studentSearch(String searchData, int number) {
		final int ID_NUM = 1, NAME_NUM = 2, GENDER_NUM = 3;
		
		// 레코드셋을 저장을 List Collection
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// 1.데이타 베이스 접속요청
		Connection con = DBUtility.getConnection();
		if (con == null) {
			System.out.println("Mysql Connection Fail");
			return null;
		}

		// 명령문 하달(검색명령문하달: 쿼리문으로 명령문하달 : select * from studentdb.studenttbl where ~)
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
			// 2.2 명령문 실행
			// 3. 리턴값 (레코드 셋 = resultSet 리턴한다.) 
			resultSet = ps.executeQuery();
			
			// 3.1 리턴값 ResultSet을 ArrayList<StudentModel> 변환한다.
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
				// ps가 null이 아니고 ps가 안닫아졌으면 닫아라
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
				// con이 null이 아니고 con이 안닫아졌으면 닫아라
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 4. 결과값을 통보한다.
		return list;
	}

	// 정적멤버함수(테이블 삭제하기: 학번)
	public static int studentDelete(String deleteData, int number) {
		final int ID_NUM = 1;

		// 1.데이타 베이스 접속요청
		Connection con = DBUtility.getConnection();
		if (con == null) {
			System.out.println("Mysql Connection Fail");
			return 0;
		}

		// 명령문 하달(삭제명령문하달: 쿼리문으로 명령문하달 : delete from studentdb.studenttbl where id like ?;)
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
			// 2.2 명령문 실행
			// 3. 리턴값 (레코드 셋 = resultNumber 리턴한다.) 
			resultNumber = ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				// ps가 null이 아니고 ps가 안닫아졌으면 닫아라
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
				// con이 null이 아니고 con이 안닫아졌으면 닫아라
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 4. 결과값을 통보한다.
		return resultNumber;
	}

	public static int studentUpdata(String id, int kor, int math, int eng, int total, double avr, int rank) {
		final int ID_NUM = 1;

		// 1.데이타 베이스 접속요청
		Connection con = DBUtility.getConnection();
		if (con == null) {
			System.out.println("Mysql Connection Fail");
			return 0;
		}

		// 명령문 하달(수정명령문하달: 쿼리문으로 명령문하달 : update studentdb.studenttbl set kor = ?, math = ?, eng = ?, total = ?, avr = ?, rank = ? where id = ?)
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
			// 2.2 명령문 실행
			// 3. 리턴값 (레코드 셋 = resultNumber 리턴한다.)
			resultNumber = ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				// ps가 null이 아니고 ps가 안닫아졌으면 닫아라
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
				// con이 null이 아니고 con이 안닫아졌으면 닫아라
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 4. 결과값을 통보한다.
		return resultNumber;
	}

	public static List<StudentModel> StudentSort(int no) {
		final int ASC = 1, DESC = 2;
		
		// 테이블에 있는 레코드 셋을 가져오기 위한 ArrayList<StudentModel>
		List<StudentModel> list = new ArrayList<StudentModel>();
		String sortQuery = null;
		
		// 1.데이타 베이스 접속요청
		Connection con = DBUtility.getConnection();
		if (con == null) {
			System.out.println("Mysql Connection Fail");
			return null;
		}
		
		// 2.명령문 하달(sort 쿼리문 명령문 하달: 쿼리문으로 명령문 하달 : select * from studentdb.studenttbl order by total ASC / DESC;)
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

			// 2.2 query 명령문 실행 (sort 쿼리문)
			// 3.리턴값(레코드 셋 = resultSet리턴한다.)
			resultSet = ps.executeQuery();

			// 레코드셋을 리스트가 가져온다
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
				// ps가 null이 아니고 ps가 안닫아졌으면 닫아라
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
				// con이 null이 아니고 con이 안닫아졌으면 닫아라
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 3.리턴값(삽입한 갯수를 리턴한다.)
		// 4.결과값을 통보한다.
		return list;
	}
}
