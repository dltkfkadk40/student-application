package com.mire.sixclass;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentMain {
	//»ó¼ö ÆÐÅÏ¸ÅÄ¡
	public static final int NAME = 1, YEAR = 2, GROUP = 3, ID = 4;
	//»ó¼ö ¸ÞÀÎ ¸Þ´º
	public static final int INSERT = 1, SEARCH = 2, DELETE = 3, UPDATE = 4, PRINT = 5, SORT = 6, EXIT = 7;
	//»ó¼ö °ú¸ñ¼ö
	private static final double SUBJECT_COUNT = 3;
	//scan °´Ã¼ »ý¼º
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		boolean flag = false;

		while (!flag) {
			int no = selectMenu(); // ¸Þ´º ¼±ÅÃ ÇÔ¼ö

			switch (no) {
			case INSERT:
				StudentInsert(); // »ðÀÔ
				break;
			case SEARCH:
				StudentSearch(); // °Ë»ö
				break;
			case DELETE:
				StudentDelete(); // »èÁ¦
				break;
			case UPDATE:
				StudentUpdate(); // ¼öÁ¤
				break;
			case PRINT:
				StudentPrint(); // Ãâ·Â
				break;
			case SORT:
				StudentSort(); // Á¤·Ä
				break;
			case EXIT:
				flag = true; // Á¾·á
				System.out.println("Á¾·á µÇ¾ú½À´Ï´Ù!");
				break;
			default:
				System.out.println("´Ù½Ã ÀÔ·Â¿ä¸Á!");
				break;
			}
		}
	}

	// ÇÐ»ý Á¤º¸ »ðÀÔ ÇÔ¼ö 
	private static void StudentInsert() {
		
		// ÀÔ·Â ¹Þ¾Æ¾ß ÇÒ Áö¿ªº¯¼ö ¼±¾ð
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

		// ÀÌ¸§
		while (true) {
			System.out.print("ÀÌ¸§(È«±æµ¿) ÀÔ·Â >> ");
			name = scan.nextLine();

			if (patternCheck(name, NAME)) {
				break;
			} else {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!");
			}
		}
		// ÇÐ³â
		while (true) {
			System.out.print("Áe³â(1~4ÇÐ³â) ÀÔ·Â >> ");
			year = scan.nextLine();

			if (patternCheck(year, YEAR)) {
				break;
			} else {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!");
			}
		}
		
		while (true) {
			System.out.print("¼ºº°(³²ÀÚ, ¿©ÀÚ) ÀÔ·Â >> ");
			gender = scan.nextLine();

			if (gender.equals("³²ÀÚ") || gender.equals("¿©ÀÚ")) {
				break;
			} else {
				System.out.println("´Ù½Ã ÀÔ·Â ¹Ù¶ø´Ï´Ù");
			}
		}

		// ÇÐ¹ø
		while (true) {
			System.out.print("ÇÐ¹ø (1000~1999) ÀÔ·Â >> ");
			id = scan.nextLine();

			if (patternCheck(id, ID)) {
				break;
			} else {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!");
			}
		}

		// ±¹¾î Á¡¼ö
		while (true) {
			System.out.print("±¹¾î Á¡¼ö(0~100) ÀÔ·Â >> ");
			kor = scan.nextInt();

			if (kor >= 0 && kor <= 100) {
				break;
			} else {
				System.out.println("´Ù½Ã ÀÔ·Â ¹Ù¶ø´Ï´Ù");
			}
		}

		// ¼öÇÐ Á¡¼ö
		while (true) {
			System.out.print("¼öÇÐ Á¡¼ö(0~100) ÀÔ·Â >> ");
			math = scan.nextInt();

			if (math >= 0 && math <= 100) {
				break;
			} else {
				System.out.println("´Ù½Ã ÀÔ·Â ¹Ù¶ø´Ï´Ù");
			}
		}
		// ¿µ¾î Á¡¼ö
		while (true) {
			System.out.print("¿µ¾î Á¡¼ö(0~100) ÀÔ·Â >> ");
			eng = scan.nextInt();
			scan.nextLine();

			if (eng >= 0 && eng <= 100) {
				break;
			} else {
				System.out.println("´Ù½Ã ÀÔ·Â ¹Ù¶ø´Ï´Ù");
			}
		}
		
		//StudentModel °´Ã¼ »ý¼º
		StudentModel sm = new StudentModel(name, year, gender, id, kor, math, eng, total, avr, rank);

		total = sm.calTotal();
		avr = sm.calAverage();
		rank = sm.calRank();

		int returnValue = DBController.StudentInsert(sm);
		
		if (returnValue != 0 ) {
			System.out.println(sm.getName() + "´Ô »ðÀÔ ¼º°øÇÏ¿´½À´Ï´Ù");
		} else {
			System.out.println(sm.getName() + "´Ô »ðÀÔ ½ÇÆÐÇÏ¿´½À´Ï´Ù.(ÀÌÀ¯: Áßº¹µÈ ÇÐ¹ø)");
		}
		
	}
	
	//ÇÐ»ý Á¤º¸ °Ë»ö ÇÔ¼ö
	private static void StudentSearch() {
		//»ó¼ö °Ë»ö¸Þ´º
		final int ID_NUM = 1, NAME_NUM = 2, GENDER_NUM = 3,  EXIT = 4;
		
		//ArrayList<StudentModel> °´Ã¼ »ý¼º
		List<StudentModel> list = new ArrayList<StudentModel>();

		// °Ë»öÇÒ ³»¿ëÀ» ¼±ÅÃ¿äÃ»(ÀüÈ­¹øÈ£, ÀÌ¸§, ¼ºº°)
		String id = null;
		String name = null;
		String searchData = null;
		String gender = null;
		boolean flag = false;
		int number = 0;
		int no = 0;
		
		//°Ë»ö ¸Þ´º ÇÔ¼ö
		no = searchMenu();

		switch (no) {
		case ID_NUM:
			// ÇÐ¹ø
			while (true) {
				System.out.print("ÇÐ¹ø(1000 ~ 1999) ÀÔ·Â >> ");
				id = scan.nextLine();

				if (patternCheck(id, ID)) {
					break;
				} else {
					System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!");
				}

			}
			searchData = id;
			number = ID_NUM;
			break;

		case NAME_NUM:
			// ÀÌ¸§
			while (true) {
				System.out.print("ÀÌ¸§ (È«±æµ¿) ÀÔ·Â>> ");
				name = scan.nextLine();

				if (patternCheck(name, NAME)) {
					break;
				} else {
					System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!");
				}
			}
			searchData = name;
			number = NAME_NUM;
			break;
			
		case GENDER_NUM:
			// ¼ºº°
			while (true) {
				System.out.print("¼ºº° (³²ÀÚ, ¿©ÀÚ)ÀÔ·Â>> ");
				gender = scan.nextLine();

				if (gender.equals("³²ÀÚ") || gender.equals("¿©ÀÚ")) {
					break;
				} else {
					System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!");
				}
			}
			searchData = gender;
			number = GENDER_NUM;
			break;

		case EXIT:
			System.out.println("°Ë»öÀÌ Ãë¼Ò µÇ¾ú½À´Ï´Ù");
			flag = true;
			break;
		}

		if (flag) {
			return;
		}
		
		// DBController list°ªÀ» ¹Þ¾Æ ÇÐ»ý Á¤º¸ °Ë»ö
		list = DBController.studentSearch(searchData, number);

		if (list.size() <= 0) {
			System.out.println(searchData + " ªOÁö ¸øÇß½À´Ï´Ù.");
			return;
		}
		
		//ÇÐ»ýÁ¤º¸ Ãâ·Â
		for (StudentModel data : list) {
			System.out.println(data);
		}
	}
	// ÇÐ»ý Á¤º¸»èÁ¦ ÇÔ¼ö  
	private static void StudentDelete() {
		final int PHONE_NUM = 1;
		String id = null;
		String deleteData = null;
		int number = 0;
		int resultNumber = 0;

		// ÇÐ¹ø
		while (true) {
			System.out.print("ÇÐ¹ø(1000-1999) ÀÔ·Â >> ");
			id = scan.nextLine();

			if (patternCheck(id, ID)) {
				break;
			} else {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!");
			}
		}
		deleteData = id;
		number = PHONE_NUM;
		
		//DBController¿¡¼­ °ªÀ» ¹Þ¾Æ ÇÐ»ýÁ¤º¸»èÁ¦
		resultNumber = DBController.studentDelete(deleteData, number);

		if (resultNumber != 0) {
			System.out.println(id + "¹ø" + " ÇØ´ç ÇÐ»ýÁ¤º¸ »èÁ¦¿Ï·áÇß½À´Ï´Ù.");
		} else {
			System.out.println(id + "¹ø" + " ÇØ´ç ÇÐ»ýÁ¤º¸ »èÁ¦½ÇÆÐÇß½À´Ï´Ù.");
		}
		return;
	}
	
	// ÇÐ»ý Á¤º¸ ¼öÁ¤ ÇÔ¼ö (StudentUpdate())
	private static void StudentUpdate() {
		final int ID_NUM = 1;
		
		//ArrayList<StudentModel> °´Ã¼ »ý¼º
		List<StudentModel> list = new ArrayList<StudentModel>();

		// ¼öÁ¤ÇÒ ³»¿ëÀ» ¼±ÅÃ¿äÃ»(ÇÐ¹ø)
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

		// ÇÐ¹ø
		while (true) {
			System.out.print("¼öÁ¤ÇÒ ÇÐ¹ø(1000~1999) ÀÔ·Â >> ");
			id = scan.nextLine();

			if (patternCheck(id, ID)) {
				break;
			} else {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!");
			}
		}
		searchData = id;
		number = ID_NUM;
		
		//DBController list°ªÀ» ¹Þ¾Æ ÇÐ»ý Á¤º¸ °Ë»ö
		list = DBController.studentSearch(searchData, number);

		if (list.size() <= 0) {
			System.out.println(searchData + " ªOÁö ¸øÇß½À´Ï´Ù.");
			return;
		}
		StudentModel data_buffer = null;
		for (StudentModel data : list) {
			System.out.println(data);
			data_buffer = data;
		}

		// 2. ÇÐ¹ø°¡ ÀÖÀ¸¸é ¼öÁ¤ÇÒ Á¡¼ö¸¦ ¹Þ¾Æ¼­ ¼öÁ¤¿äÃ»
		while (true) {
			System.out.print("±âÁ¸ ±¹¾îÁ¡¼ö: [" + data_buffer.getKor() + "] \n¼öÁ¤ÇÒ ±¹¾îÁ¡¼ö ÀÔ·Â: ");
			kor = scan.nextInt();

			if (kor >= 0 && kor <= 100) {
				break;
			} else {
				System.out.println("´Ù½Ã ÀÔ·Â ¹Ù¶ø´Ï´Ù");
			}
		}
		while (true) {
			System.out.print("±âÁ¸ ¼öÇÐÁ¡¼ö: [" + data_buffer.getMath() + "] \n¼öÁ¤ÇÒ ¼öÇÐ Á¡¼ö ÀÔ·Â: ");
			math = scan.nextInt();

			if (math >= 0 && math <= 100) {
				break;
			} else {
				System.out.println("´Ù½Ã ÀÔ·Â ¹Ù¶ø´Ï´Ù");
			}
		}
		while (true) {
			System.out.print("±âÁ¸ ¿µ¾îÁ¡¼ö: [" + data_buffer.getKor() + "] \n¼öÁ¤ÇÒ ¿µ¾î Á¡¼ö ÀÔ·Â: ");
			eng = scan.nextInt();
			scan.nextLine();

			if (eng >= 0 && eng <= 100) {
				break;
			} else {
				System.out.println("´Ù½Ã ÀÔ·Â ¹Ù¶ø´Ï´Ù");
			}
		}
		
		//StudentModel °´Ã¼ »ý¼º
		StudentModel sm = new StudentModel(name, year, gender, id, kor, math, eng, total, avr, rank);

		total = sm.calTotal();
		avr = sm.calAverage();
		rank = sm.calRank();

		// 3. °á°ú°ª È®ÀÎ4
		resultNumber = DBController.studentUpdata(id, kor, math, eng, total, avr, rank);

		if (resultNumber != 0) {
			System.out.println(id + " ¹øÈ£ ¼öÁ¤¿Ï·áÇß½À´Ï´Ù.");
		} else {
			System.out.println(id + " ¹øÈ£ ¼öÁ¤½ÇÆÐÇß½À´Ï´Ù.");
		}
		return;
	}
	
	//ÇÐ»ý Á¤º¸ Ãâ·Â (StudentPrint())
	private static void StudentPrint() {
		
		//ArrayList<StudentModel> °´Ã¼ »ý¼º
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		//DBController¿¡¼­ list°ªÀ» Àü´Þ¹Þ¾Æ ÇÐ»ý Á¤º¸ º¸¿©ÁØ´Ù
		list = DBController.PhoneBookeSelect();

		if (list.size() <= 0) {
			System.out.println("Ãâ·ÂÇÒ ÇÐ»ý Á¤º¸ ³»¿ëÀÌ ¾ø½À´Ï´Ù.");
			return;
		}
		for (StudentModel data : list) {
			System.out.println(data.toString());
		}
	}
	
	//ÇÐ»ý Á¤º¸ Á¤·Ä
	private static void StudentSort() {
		//ArrayList<StudentModel> °´Ã¼ »ý¼º
		List<StudentModel> list = new ArrayList<StudentModel>();
		int no = 0;
		boolean flag = false;

		// 1. Á¤·Ä¹æ½Ä(¿À¸§Â÷¼ø , ³»¸²Â÷¼ø)
		while (!flag) {
			System.out.println("***************************");
			System.out.println("1. ¿À¸§Â÷¼ø(ÃÑÁ¡), 2. ³»¸²Â÷¼ø(ÃÑÁ¡)");
			System.out.println("***************************");
			System.out.print("Á¤·Ä¹æ½Ä ¼±ÅÃ>> ");
			try {
				no = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("°æ°í: ¼ýÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
				continue;
			} catch (Exception e) {
				System.out.println("°æ°í: ¼ýÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
				continue;
			}
			if (no >= 1 && no <= 2) {
				flag = true;
			} else {
				System.out.println("°æ°í: (1~2)¼ýÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
			}
		} // end of while

		// 2. Ãâ·Â¹®À» °¡Á®¿Â´Ù.
		list = DBController.StudentSort(no);

		if (list.size() <= 0) {
			System.out.println("Á¤·ÄµÈ ³»¿ëÀÌ ¾ø½À´Ï´Ù.");
			return;
		}
		for (StudentModel data : list) {
			System.out.println(data.toString());
		}

		return;

	}
	
	//ÆÐÅÏ ¸ÅÄª (patternCheck()) 
	private static boolean patternCheck(String patternData, int patternType) {

		String filter = null;

		switch (patternType) {
		case NAME:
			filter = "^[°¡-ÆR]{2,5}$";
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
	
	// ¸ÞÀÎ ¸Þ´º ¼±ÅÃ (selectMenu())
	private static int selectMenu() {
		boolean flag = false;
		int no = 0;

		while (!flag) {

			System.out.println("**************************************");
			System.out.println("1.ÀÔ·Â 2.Á¶È¸ 3.»èÁ¦ 4.¼öÁ¤ 5.Ãâ·Â 6.Á¤·Ä 7.Á¾·á");
			System.out.println("**************************************");
			System.out.print("¹øÈ£ ¼±ÅÃ >> ");
			try {
				no = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("°æ°í: ¼ýÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
				continue;
			} catch (Exception e) {
				System.out.println("°æ°í: ¼ýÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
				continue;
			}
			if (no >= 1 && no <= 7) {
				flag = true;
			} else {
				System.out.println("°æ°í: (1~7)¼ýÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
			}
		} // end of while

		return no;
	}
	
	// °Ë»ö ¸Þ´º ¼±ÅÃ (searchMenu())
	private static int searchMenu() {
		boolean flag = false;
		int no = 0;

		while (!flag) {
			System.out.println("************************");
			System.out.println("1.¹øÈ£ 2.ÀÌ¸§ 3.¼ºº° 4.Á¾·á ");
			System.out.println("************************");
			System.out.print("¹øÈ£ ¼±ÅÃ >> ");
			try {
				no = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("°æ°í: ¼ýÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
				continue;
			} catch (Exception e) {
				System.out.println("°æ°í: ¼ýÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
				continue;
			}
			if (no >= 1 && no <= 3) {
				flag = true;
			} else {
				System.out.println("°æ°í: (1~4)¼ýÀÚ ÀÔ·ÂÇØÁÖ¼¼¿ä!");
			}
		} // end of while

		return no;
	}
}