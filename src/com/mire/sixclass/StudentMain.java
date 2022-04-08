package com.mire.sixclass;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentMain {
	//상수 패턴매치
	public static final int NAME = 1, YEAR = 2, GROUP = 3, ID = 4;
	//상수 메인 메뉴
	public static final int INSERT = 1, SEARCH = 2, DELETE = 3, UPDATE = 4, PRINT = 5, SORT = 6, EXIT = 7;
	//상수 과목수
	private static final double SUBJECT_COUNT = 3;
	//scan 객체 생성
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		boolean flag = false;

		while (!flag) {
			int no = selectMenu(); // 메뉴 선택 함수

			switch (no) {
			case INSERT:
				StudentInsert(); // 삽입
				break;
			case SEARCH:
				StudentSearch(); // 검색
				break;
			case DELETE:
				StudentDelete(); // 삭제
				break;
			case UPDATE:
				StudentUpdate(); // 수정
				break;
			case PRINT:
				StudentPrint(); // 출력
				break;
			case SORT:
				StudentSort(); // 정렬
				break;
			case EXIT:
				flag = true; // 종료
				System.out.println("종료 되었습니다!");
				break;
			default:
				System.out.println("다시 입력요망!");
				break;
			}
		}
	}

	// 학생 정보 삽입 함수 (StudentInsert())
	private static void StudentInsert() {
		
		// 입력 받아야 할 지역변수 선언
		String name = null;
		String year = null;
		String gender = null;
		String id = null;
		int kor = 0;
		int math = 0;
		int eng = 0;
		int total = 0;
		double avr = 0.0;
		int rank = 0;

		// 이름
		while (true) {
			System.out.print("이름(홍길동) 입력 >> ");
			name = scan.nextLine();

			if (patternCheck(name, NAME)) {
				break;
			} else {
				System.out.println("다시 입력해주세요!");
			}
		}
		// 학년
		while (true) {
			System.out.print("햑년(1~4학년) 입력 >> ");
			year = scan.nextLine();

			if (patternCheck(year, YEAR)) {
				break;
			} else {
				System.out.println("다시 입력해주세요!");
			}
		}
		
		//성별
		while (true) {
			System.out.print("성별(남자, 여자) 입력 >> ");
			gender = scan.nextLine();

			if (gender.equals("남자") || gender.equals("여자")) {
				break;
			} else {
				System.out.println("다시 입력 바랍니다");
			}
		}

		// 학번
		while (true) {
			System.out.print("학번 (1000~1999) 입력 >> ");
			id = scan.nextLine();

			if (patternCheck(id, ID)) {
				break;
			} else {
				System.out.println("다시 입력해주세요!");
			}
		}

		// 국어 점수
		while (true) {
			System.out.print("국어 점수(0~100) 입력 >> ");
			kor = scan.nextInt();

			if (kor >= 0 && kor <= 100) {
				break;
			} else {
				System.out.println("다시 입력 바랍니다");
			}
		}

		// 수학 점수
		while (true) {
			System.out.print("수학 점수(0~100) 입력 >> ");
			math = scan.nextInt();

			if (math >= 0 && math <= 100) {
				break;
			} else {
				System.out.println("다시 입력 바랍니다");
			}
		}
		// 영어 점수
		while (true) {
			System.out.print("영어 점수(0~100) 입력 >> ");
			eng = scan.nextInt();
			scan.nextLine();

			if (eng >= 0 && eng <= 100) {
				break;
			} else {
				System.out.println("다시 입력 바랍니다");
			}
		}
		
		//StudentModel 객체 생성
		StudentModel sm = new StudentModel(name, year, gender, id, kor, math, eng, total, avr, rank);

		total = sm.calTotal();
		avr = sm.calAverage();
		rank = sm.calRank();
		
		int returnValue = DBController.StudentInsert(sm);
		
		if (returnValue != 0 ) {
			System.out.println(sm.getName() + "님 삽입 성공하였습니다");
		} else {
			System.out.println(sm.getName() + "님 삽입 실패하였습니다.(이유: 중복된 학번)");
		}
		
	}
	
	//학생 정보 검색 함수 (StudentSearch())
	private static void StudentSearch() {
		//상수 검색메뉴
		final int ID_NUM = 1, NAME_NUM = 2, GENDER_NUM = 3,  EXIT = 4;
		
		//ArrayList<StudentModel> 객체 생성
		List<StudentModel> list = new ArrayList<StudentModel>();

		// 검색할 내용을 선택요청(학번, 이름, 성별)
		String id = null;
		String name = null;
		String searchData = null;
		String gender = null;
		boolean flag = false;
		int number = 0;
		int no = 0;
		
		//검색 메뉴 함수
		no = searchMenu();

		switch (no) {
		case ID_NUM:
			// 학번
			while (true) {
				System.out.print("학번(1000 ~ 1999) 입력 >> ");
				id = scan.nextLine();

				if (patternCheck(id, ID)) {
					break;
				} else {
					System.out.println("다시 입력해주세요!");
				}

			}
			searchData = id;
			number = ID_NUM;
			break;

		case NAME_NUM:
			// 이름
			while (true) {
				System.out.print("이름 (홍길동) 입력>> ");
				name = scan.nextLine();

				if (patternCheck(name, NAME)) {
					break;
				} else {
					System.out.println("다시 입력해주세요!");
				}
			}
			searchData = name;
			number = NAME_NUM;
			break;
			
		case GENDER_NUM:
			// 성별
			while (true) {
				System.out.print("성별 (남자, 여자)입력>> ");
				gender = scan.nextLine();

				if (gender.equals("남자") || gender.equals("여자")) {
					break;
				} else {
					System.out.println("다시 입력해주세요!");
				}
			}
			searchData = gender;
			number = GENDER_NUM;
			break;

		case EXIT:
			System.out.println("검색이 취소 되었습니다");
			flag = true;
			break;
		}

		if (flag) {
			return;
		}
		
		// DBController list값을 받아 학생 정보 검색
		list = DBController.studentSearch(searchData, number);

		if (list.size() <= 0) {
			System.out.println(searchData + " 찿지 못했습니다.");
			return;
		}
		
		//학생정보 출력
		for (StudentModel data : list) {
			System.out.println(data);
		}
	}
	// 학생 정보삭제 함수(학번) 
	private static void StudentDelete() {
		final int PHONE_NUM = 1;
		String id = null;
		String deleteData = null;
		int number = 0;
		int resultNumber = 0;

		// 학번
		while (true) {
			System.out.print("학번(1000-1999) 입력 >> ");
			id = scan.nextLine();

			if (patternCheck(id, ID)) {
				break;
			} else {
				System.out.println("다시 입력해주세요!");
			}
		}
		deleteData = id;
		number = PHONE_NUM;
		
		//DBController에서 값을 받아 학생정보삭제
		resultNumber = DBController.studentDelete(deleteData, number);

		if (resultNumber != 0) {
			System.out.println(id + "번" + " 해당 학생정보 삭제완료했습니다.");
		} else {
			System.out.println(id + "번" + " 해당 학생정보 삭제실패했습니다.");
		}
		return;
	}
	
	// 학생 정보 수정 함수 (StudentUpdate())
	private static void StudentUpdate() {
		final int ID_NUM = 1;
		
		//ArrayList<StudentModel> 객체 생성
		List<StudentModel> list = new ArrayList<StudentModel>();

		// 수정할 내용을 선택요청(학번)
		String name = null;
		String year = null;
		String gender = null;
		String id = null;
		String searchData = null;
		int kor = 0;
		int math = 0;
		int eng = 0;
		int total = 0;
		double avr = 0.0;
		int rank = 0;
		boolean flag = false;
		int number = 0;
		int no = 0;
		int resultNumber = 0;

		// 학번
		while (true) {
			System.out.print("수정할 학번(1000~1999) 입력 >> ");
			id = scan.nextLine();

			if (patternCheck(id, ID)) {
				break;
			} else {
				System.out.println("다시 입력해주세요!");
			}
		}
		searchData = id;
		number = ID_NUM;
		
		//DBController list값을 받아 학생 정보 검색
		list = DBController.studentSearch(searchData, number);

		if (list.size() <= 0) {
			System.out.println(searchData + " 찿지 못했습니다.");
			return;
		}
		StudentModel data_buffer = null;
		for (StudentModel data : list) {
			System.out.println(data);
			data_buffer = data;
		}

		// 2. 학번가 있으면 수정할 점수를 받아서 수정요청
		while (true) {
			System.out.print("기존 국어점수: [" + data_buffer.getKor() + "] \n수정할 국어점수 입력: ");
			kor = scan.nextInt();

			if (kor >= 0 && kor <= 100) {
				break;
			} else {
				System.out.println("다시 입력 바랍니다");
			}
		}
		while (true) {
			System.out.print("기존 수학점수: [" + data_buffer.getMath() + "] \n수정할 수학 점수 입력: ");
			math = scan.nextInt();

			if (math >= 0 && math <= 100) {
				break;
			} else {
				System.out.println("다시 입력 바랍니다");
			}
		}
		while (true) {
			System.out.print("기존 영어점수: [" + data_buffer.getKor() + "] \n수정할 영어 점수 입력: ");
			eng = scan.nextInt();
			scan.nextLine();

			if (eng >= 0 && eng <= 100) {
				break;
			} else {
				System.out.println("다시 입력 바랍니다");
			}
		}
		
		//StudentModel 객체 생성
		StudentModel sm = new StudentModel(name, year, gender, id, kor, math, eng, total, avr, rank);

		total = sm.calTotal();
		avr = sm.calAverage();
		rank = sm.calRank();

		// 3. 결과값 확인4
		resultNumber = DBController.studentUpdata(id, kor, math, eng, total, avr, rank);

		if (resultNumber != 0) {
			System.out.println(id + " 번호 수정완료했습니다.");
		} else {
			System.out.println(id + " 번호 수정실패했습니다.");
		}
		return;
	}
	
	//학생 정보 출력 (StudentPrint())
	private static void StudentPrint() {
		
		//ArrayList<StudentModel> 객체 생성
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		//DBController에서 list값을 전달받는다
		list = DBController.PhoneBookeSelect();

		if (list.size() <= 0) {
			System.out.println("출력할 학생 정보 내용이 없습니다.");
			return;
		}
		for (StudentModel data : list) {
			System.out.println(data.toString());
		}
	}
	
	//학생 정보 정렬(StudentSort())
	private static void StudentSort() {
		//ArrayList<StudentModel> 객체 생성
		List<StudentModel> list = new ArrayList<StudentModel>();
		int no = 0;
		boolean flag = false;

		// 1. 정렬방식(오름차순 , 내림차순)
		while (!flag) {
			System.out.println("***************************");
			System.out.println("1. 오름차순(총점), 2. 내림차순(총점)");
			System.out.println("***************************");
			System.out.print("정렬방식 선택>> ");
			try {
				no = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("경고: 숫자 입력해주세요!");
				continue;
			} catch (Exception e) {
				System.out.println("경고: 숫자 입력해주세요!");
				continue;
			}
			if (no >= 1 && no <= 2) {
				flag = true;
			} else {
				System.out.println("경고: (1~2)숫자 입력해주세요!");
			}
		} // end of while

		// 2. 출력문을 가져온다.
		list = DBController.StudentSort(no);

		if (list.size() <= 0) {
			System.out.println("정렬된 내용이 없습니다.");
			return;
		}
		for (StudentModel data : list) {
			System.out.println(data.toString());
		}

		return;

	}
	
	//패턴 매칭 (patternCheck()) 
	private static boolean patternCheck(String patternData, int patternType) {

		String filter = null;

		switch (patternType) {
		case NAME:
			filter = "^[가-힣]{2,5}$";
			break;
		case YEAR:
			filter = "[1-4]";
			break;
		case ID:
			filter = "[1]\\d{3}";
			break;
		}

		Pattern pattern = Pattern.compile(filter);
		Matcher matcher = pattern.matcher(patternData);

		return matcher.matches();
	}
	
	// 메인 메뉴 선택 (selectMenu())
	private static int selectMenu() {
		boolean flag = false;
		int no = 0;

		while (!flag) {

			System.out.println("**************************************");
			System.out.println("1.입력 2.조회 3.삭제 4.수정 5.출력 6.정렬 7.종료");
			System.out.println("**************************************");
			System.out.print("번호 선택 >> ");
			try {
				no = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("경고: 숫자 입력해주세요!");
				continue;
			} catch (Exception e) {
				System.out.println("경고: 숫자 입력해주세요!");
				continue;
			}
			if (no >= 1 && no <= 7) {
				flag = true;
			} else {
				System.out.println("경고: (1~7)숫자 입력해주세요!");
			}
		} // end of while

		return no;
	}
	
	// 검색 메뉴 선택 (searchMenu())
	private static int searchMenu() {
		boolean flag = false;
		int no = 0;

		while (!flag) {
			System.out.println("************************");
			System.out.println("1.번호 2.이름 3.성별 4.종료 ");
			System.out.println("************************");
			System.out.print("번호 선택 >> ");
			try {
				no = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("경고: 숫자 입력해주세요!");
				continue;
			} catch (Exception e) {
				System.out.println("경고: 숫자 입력해주세요!");
				continue;
			}
			if (no >= 1 && no <= 3) {
				flag = true;
			} else {
				System.out.println("경고: (1~4)숫자 입력해주세요!");
			}
		} // end of while

		return no;
	}
}
