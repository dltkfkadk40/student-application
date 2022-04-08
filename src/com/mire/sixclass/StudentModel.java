package com.mire.sixclass;

import java.io.Serializable;
import java.util.Objects;

public class StudentModel implements Comparable<Object>, Serializable {
	public static final int SUBJECT_COUNT = 3;

	// 멤버 변수 (이름, 학년, 성별, 학번, 국어, 수학, 영어, 총점, 평균, 등급)
	private String name;
	private String year;
	private String gender;
	private String id;
	private int kor;
	private int math;
	private int eng;
	private int total;
	private double avr;
	private int rank;
	
	//생성자 함수
	public StudentModel(String name, String year, String gender,String id, int kor, int math, int eng, int total, double avr,
			int rank) {
		super();
		this.name = name;
		this.year = year;
		this.gender = gender;
		this.id = id;
		this.kor = kor;
		this.math = math;
		this.eng = eng;
		this.total = total;
		this.avr = avr;
		this.rank = rank;
	}
	
	//총점 계산 함수(calTotal())
	public int calTotal() {
		return this.total = this.kor + this.math + this.eng;
	}

	//평균 계산 함수(calAverage())
	public double calAverage() {
		return this.avr = this.total / (double) SUBJECT_COUNT;
	}
	
	//등급 계산 함수 (calRank())
	public int calRank() {

		if (this.avr >= 90) {
			this.rank = 1;
		} else if (this.avr >= 80) {
			this.rank = 2;
		} else if (this.avr >= 70) {
			this.rank = 3;
		} else if (this.avr >= 60) {
			this.rank = 4;
		} else if (this.avr >= 50) {
			this.rank = 5;
		} else if (this.avr >= 40) {
			this.rank = 6;
		} else if (this.avr >= 30) {
			this.rank = 7;
		} else if (this.avr >= 20) {
			this.rank = 8;
		} else {
			this.rank = 9;
		}

		return rank;
	}
	
	// 해시코드(id)
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	// equals (id)
	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		if (!(obj instanceof StudentModel)) {
			throw new IllegalArgumentException("without StudentModel class");
		}
		StudentModel sm = (StudentModel) obj;

		return this.id.equals(sm.id);
	}
	
	// compareTo (total) 
	@Override
	public int compareTo(Object obj) {
		if (!(obj instanceof StudentModel)) {
			throw new IllegalArgumentException("without StudentModel class");
		}
		StudentModel sm = (StudentModel) obj;

		if (this.total < sm.total) {
			return -1;
		} else if (this.total > sm.total) {
			return 1;
		} else {
			return 0;
		}
	}
	
	// toString()
	@Override
	public String toString() {
		String year_data = year + "학년";
		String id_data = id + "번";
		String kor_data = kor + "점";
		String math_data = math + "점";
		String eng_data = eng + "점";
		String total_data = total + "점";
		String rank_data = rank + "등급";

		return name + "\t" + year_data + "\t" + id_data + "\t" + gender + "\t" + kor_data + "\t" + math_data + "\t" + eng_data + "\t"
				+ total_data + "\t" + String.format("%6.2f", avr) + "점    " + rank_data;
	}
	
	//getter, setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String id) {
		this.gender = gender;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public double getAvr() {
		return avr;
	}

	public void setAvr(double avr) {
		this.avr = avr;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}
