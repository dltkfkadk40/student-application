package com.mire.sixclass;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentMain {
	//��� ���ϸ�ġ
	public static final int NAME = 1, YEAR = 2, GROUP = 3, ID = 4;
	//��� ���� �޴�
	public static final int INSERT = 1, SEARCH = 2, DELETE = 3, UPDATE = 4, PRINT = 5, SORT = 6, EXIT = 7;
	//��� �����
	private static final double SUBJECT_COUNT = 3;
	//scan ��ü ����
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		boolean flag = false;

		while (!flag) {
			int no = selectMenu(); // �޴� ���� �Լ�

			switch (no) {
			case INSERT:
				StudentInsert(); // ����
				break;
			case SEARCH:
				StudentSearch(); // �˻�
				break;
			case DELETE:
				StudentDelete(); // ����
				break;
			case UPDATE:
				StudentUpdate(); // ����
				break;
			case PRINT:
				StudentPrint(); // ���
				break;
			case SORT:
				StudentSort(); // ����
				break;
			case EXIT:
				flag = true; // ����
				System.out.println("���� �Ǿ����ϴ�!");
				break;
			default:
				System.out.println("�ٽ� �Է¿��!");
				break;
			}
		}
	}

	// �л� ���� ���� �Լ� 
	private static void StudentInsert() {
		
		// �Է� �޾ƾ� �� �������� ����
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

		// �̸�
		while (true) {
			System.out.print("�̸�(ȫ�浿) �Է� >> ");
			name = scan.nextLine();

			if (patternCheck(name, NAME)) {
				break;
			} else {
				System.out.println("�ٽ� �Է����ּ���!");
			}
		}
		// �г�
		while (true) {
			System.out.print("�e��(1~4�г�) �Է� >> ");
			year = scan.nextLine();

			if (patternCheck(year, YEAR)) {
				break;
			} else {
				System.out.println("�ٽ� �Է����ּ���!");
			}
		}
		
		while (true) {
			System.out.print("����(����, ����) �Է� >> ");
			gender = scan.nextLine();

			if (gender.equals("����") || gender.equals("����")) {
				break;
			} else {
				System.out.println("�ٽ� �Է� �ٶ��ϴ�");
			}
		}

		// �й�
		while (true) {
			System.out.print("�й� (1000~1999) �Է� >> ");
			id = scan.nextLine();

			if (patternCheck(id, ID)) {
				break;
			} else {
				System.out.println("�ٽ� �Է����ּ���!");
			}
		}

		// ���� ����
		while (true) {
			System.out.print("���� ����(0~100) �Է� >> ");
			kor = scan.nextInt();

			if (kor >= 0 && kor <= 100) {
				break;
			} else {
				System.out.println("�ٽ� �Է� �ٶ��ϴ�");
			}
		}

		// ���� ����
		while (true) {
			System.out.print("���� ����(0~100) �Է� >> ");
			math = scan.nextInt();

			if (math >= 0 && math <= 100) {
				break;
			} else {
				System.out.println("�ٽ� �Է� �ٶ��ϴ�");
			}
		}
		// ���� ����
		while (true) {
			System.out.print("���� ����(0~100) �Է� >> ");
			eng = scan.nextInt();
			scan.nextLine();

			if (eng >= 0 && eng <= 100) {
				break;
			} else {
				System.out.println("�ٽ� �Է� �ٶ��ϴ�");
			}
		}
		
		//StudentModel ��ü ����
		StudentModel sm = new StudentModel(name, year, gender, id, kor, math, eng, total, avr, rank);

		total = sm.calTotal();
		avr = sm.calAverage();
		rank = sm.calRank();

		int returnValue = DBController.StudentInsert(sm);
		
		if (returnValue != 0 ) {
			System.out.println(sm.getName() + "�� ���� �����Ͽ����ϴ�");
		} else {
			System.out.println(sm.getName() + "�� ���� �����Ͽ����ϴ�.(����: �ߺ��� �й�)");
		}
		
	}
	
	//�л� ���� �˻� �Լ�
	private static void StudentSearch() {
		//��� �˻��޴�
		final int ID_NUM = 1, NAME_NUM = 2, GENDER_NUM = 3,  EXIT = 4;
		
		//ArrayList<StudentModel> ��ü ����
		List<StudentModel> list = new ArrayList<StudentModel>();

		// �˻��� ������ ���ÿ�û(��ȭ��ȣ, �̸�, ����)
		String id = null;
		String name = null;
		String searchData = null;
		String gender = null;
		boolean flag = false;
		int number = 0;
		int no = 0;
		
		//�˻� �޴� �Լ�
		no = searchMenu();

		switch (no) {
		case ID_NUM:
			// �й�
			while (true) {
				System.out.print("�й�(1000 ~ 1999) �Է� >> ");
				id = scan.nextLine();

				if (patternCheck(id, ID)) {
					break;
				} else {
					System.out.println("�ٽ� �Է����ּ���!");
				}

			}
			searchData = id;
			number = ID_NUM;
			break;

		case NAME_NUM:
			// �̸�
			while (true) {
				System.out.print("�̸� (ȫ�浿) �Է�>> ");
				name = scan.nextLine();

				if (patternCheck(name, NAME)) {
					break;
				} else {
					System.out.println("�ٽ� �Է����ּ���!");
				}
			}
			searchData = name;
			number = NAME_NUM;
			break;
			
		case GENDER_NUM:
			// ����
			while (true) {
				System.out.print("���� (����, ����)�Է�>> ");
				gender = scan.nextLine();

				if (gender.equals("����") || gender.equals("����")) {
					break;
				} else {
					System.out.println("�ٽ� �Է����ּ���!");
				}
			}
			searchData = gender;
			number = GENDER_NUM;
			break;

		case EXIT:
			System.out.println("�˻��� ��� �Ǿ����ϴ�");
			flag = true;
			break;
		}

		if (flag) {
			return;
		}
		
		// DBController list���� �޾� �л� ���� �˻�
		list = DBController.studentSearch(searchData, number);

		if (list.size() <= 0) {
			System.out.println(searchData + " �O�� ���߽��ϴ�.");
			return;
		}
		
		//�л����� ���
		for (StudentModel data : list) {
			System.out.println(data);
		}
	}
	// �л� �������� �Լ�  
	private static void StudentDelete() {
		final int PHONE_NUM = 1;
		String id = null;
		String deleteData = null;
		int number = 0;
		int resultNumber = 0;

		// �й�
		while (true) {
			System.out.print("�й�(1000-1999) �Է� >> ");
			id = scan.nextLine();

			if (patternCheck(id, ID)) {
				break;
			} else {
				System.out.println("�ٽ� �Է����ּ���!");
			}
		}
		deleteData = id;
		number = PHONE_NUM;
		
		//DBController���� ���� �޾� �л���������
		resultNumber = DBController.studentDelete(deleteData, number);

		if (resultNumber != 0) {
			System.out.println(id + "��" + " �ش� �л����� �����Ϸ��߽��ϴ�.");
		} else {
			System.out.println(id + "��" + " �ش� �л����� ���������߽��ϴ�.");
		}
		return;
	}
	
	// �л� ���� ���� �Լ� (StudentUpdate())
	private static void StudentUpdate() {
		final int ID_NUM = 1;
		
		//ArrayList<StudentModel> ��ü ����
		List<StudentModel> list = new ArrayList<StudentModel>();

		// ������ ������ ���ÿ�û(�й�)
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

		// �й�
		while (true) {
			System.out.print("������ �й�(1000~1999) �Է� >> ");
			id = scan.nextLine();

			if (patternCheck(id, ID)) {
				break;
			} else {
				System.out.println("�ٽ� �Է����ּ���!");
			}
		}
		searchData = id;
		number = ID_NUM;
		
		//DBController list���� �޾� �л� ���� �˻�
		list = DBController.studentSearch(searchData, number);

		if (list.size() <= 0) {
			System.out.println(searchData + " �O�� ���߽��ϴ�.");
			return;
		}
		StudentModel data_buffer = null;
		for (StudentModel data : list) {
			System.out.println(data);
			data_buffer = data;
		}

		// 2. �й��� ������ ������ ������ �޾Ƽ� ������û
		while (true) {
			System.out.print("���� ��������: [" + data_buffer.getKor() + "] \n������ �������� �Է�: ");
			kor = scan.nextInt();

			if (kor >= 0 && kor <= 100) {
				break;
			} else {
				System.out.println("�ٽ� �Է� �ٶ��ϴ�");
			}
		}
		while (true) {
			System.out.print("���� ��������: [" + data_buffer.getMath() + "] \n������ ���� ���� �Է�: ");
			math = scan.nextInt();

			if (math >= 0 && math <= 100) {
				break;
			} else {
				System.out.println("�ٽ� �Է� �ٶ��ϴ�");
			}
		}
		while (true) {
			System.out.print("���� ��������: [" + data_buffer.getKor() + "] \n������ ���� ���� �Է�: ");
			eng = scan.nextInt();
			scan.nextLine();

			if (eng >= 0 && eng <= 100) {
				break;
			} else {
				System.out.println("�ٽ� �Է� �ٶ��ϴ�");
			}
		}
		
		//StudentModel ��ü ����
		StudentModel sm = new StudentModel(name, year, gender, id, kor, math, eng, total, avr, rank);

		total = sm.calTotal();
		avr = sm.calAverage();
		rank = sm.calRank();

		// 3. ����� Ȯ��4
		resultNumber = DBController.studentUpdata(id, kor, math, eng, total, avr, rank);

		if (resultNumber != 0) {
			System.out.println(id + " ��ȣ �����Ϸ��߽��ϴ�.");
		} else {
			System.out.println(id + " ��ȣ ���������߽��ϴ�.");
		}
		return;
	}
	
	//�л� ���� ��� (StudentPrint())
	private static void StudentPrint() {
		
		//ArrayList<StudentModel> ��ü ����
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		//DBController���� list���� ���޹޾� �л� ���� �����ش�
		list = DBController.PhoneBookeSelect();

		if (list.size() <= 0) {
			System.out.println("����� �л� ���� ������ �����ϴ�.");
			return;
		}
		for (StudentModel data : list) {
			System.out.println(data.toString());
		}
	}
	
	//�л� ���� ����
	private static void StudentSort() {
		//ArrayList<StudentModel> ��ü ����
		List<StudentModel> list = new ArrayList<StudentModel>();
		int no = 0;
		boolean flag = false;

		// 1. ���Ĺ��(�������� , ��������)
		while (!flag) {
			System.out.println("***************************");
			System.out.println("1. ��������(����), 2. ��������(����)");
			System.out.println("***************************");
			System.out.print("���Ĺ�� ����>> ");
			try {
				no = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("���: ���� �Է����ּ���!");
				continue;
			} catch (Exception e) {
				System.out.println("���: ���� �Է����ּ���!");
				continue;
			}
			if (no >= 1 && no <= 2) {
				flag = true;
			} else {
				System.out.println("���: (1~2)���� �Է����ּ���!");
			}
		} // end of while

		// 2. ��¹��� �����´�.
		list = DBController.StudentSort(no);

		if (list.size() <= 0) {
			System.out.println("���ĵ� ������ �����ϴ�.");
			return;
		}
		for (StudentModel data : list) {
			System.out.println(data.toString());
		}

		return;

	}
	
	//���� ��Ī (patternCheck()) 
	private static boolean patternCheck(String patternData, int patternType) {

		String filter = null;

		switch (patternType) {
		case NAME:
			filter = "^[��-�R]{2,5}$";
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
	
	// ���� �޴� ���� (selectMenu())
	private static int selectMenu() {
		boolean flag = false;
		int no = 0;

		while (!flag) {

			System.out.println("**************************************");
			System.out.println("1.�Է� 2.��ȸ 3.���� 4.���� 5.��� 6.���� 7.����");
			System.out.println("**************************************");
			System.out.print("��ȣ ���� >> ");
			try {
				no = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("���: ���� �Է����ּ���!");
				continue;
			} catch (Exception e) {
				System.out.println("���: ���� �Է����ּ���!");
				continue;
			}
			if (no >= 1 && no <= 7) {
				flag = true;
			} else {
				System.out.println("���: (1~7)���� �Է����ּ���!");
			}
		} // end of while

		return no;
	}
	
	// �˻� �޴� ���� (searchMenu())
	private static int searchMenu() {
		boolean flag = false;
		int no = 0;

		while (!flag) {
			System.out.println("************************");
			System.out.println("1.��ȣ 2.�̸� 3.���� 4.���� ");
			System.out.println("************************");
			System.out.print("��ȣ ���� >> ");
			try {
				no = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("���: ���� �Է����ּ���!");
				continue;
			} catch (Exception e) {
				System.out.println("���: ���� �Է����ּ���!");
				continue;
			}
			if (no >= 1 && no <= 3) {
				flag = true;
			} else {
				System.out.println("���: (1~4)���� �Է����ּ���!");
			}
		} // end of while

		return no;
	}
}