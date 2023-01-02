package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.FoodKcal;
import DTO.FoodRecode;
import DTO.Member;

public class KcalDAO {
	final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	
	public Connection open() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, "test", "test1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
		// 회원목록생성
	public ArrayList<Member> getList() throws Exception {
		ArrayList<Member> list = new ArrayList<>();
		
		Connection conn = open();
		PreparedStatement ps = conn.prepareStatement("SELECT MEMBER_NO, MNAME, HEIGHT, NEEDKCAL || ' Kcal' FROM MEMBER_TBL");
		ResultSet rs = ps.executeQuery();
		
		try (conn; ps; rs) {
			while (rs.next()) {
				Member m = new Member();
				
				m.setMember_no(rs.getInt(1));
				m.setMname(rs.getString(2));
				m.setHeight(rs.getInt(3));
				m.setNeedKcal(rs.getString(4));
				
				list.add(m);
			}
		}
		
		return list;
	}
	
	// 회원번호생성
	public Member getAdd() throws Exception {
		Member member_no = new Member();
		
		Connection conn = open();
		PreparedStatement ps = conn.prepareStatement("SELECT MAX(MEMBER_NO) + 1 FROM MEMBER_TBL");
		ResultSet rs = ps.executeQuery();
		
		try (conn; ps; rs) {
			if (rs.next()) {
				
				member_no.setMember_no(rs.getInt(1));
				
			}
		}
		
		return member_no;
	}
	
		// 회원별 칼로리 조회
	public ArrayList<FoodRecode> getTotalKcal() throws Exception {
		ArrayList<FoodRecode> list = new ArrayList<>();
		
		Connection conn = open();
		PreparedStatement ps = conn.prepareStatement("SELECT A.MNAME, SUM(C.KCAL) KCAL "
													+ "FROM MEMBER_TBL A, FOOD_RECORD B, FOOD_KCAL C "
													+ "WHERE A.MEMBER_NO = B.MEMBER_NO AND B.FOOD = C.FOOD "
													+ "GROUP BY A.MNAME ORDER BY KCAL DESC");
		ResultSet rs = ps.executeQuery();
		
		try (conn; ps; rs) {
			while (rs.next()) {
				FoodRecode f = new FoodRecode();
				
				f.setMname(rs.getString(1));
				f.setKcal(rs.getString(2) + " Kcal");
				
				
				list.add(f);
			}
		}
		
		return list;
	}
	
		// 식단 추가시 select에 음식 종류 생성
	public ArrayList<FoodKcal> getFood() throws Exception {
		ArrayList<FoodKcal> foodList = new ArrayList<>();
		
		Connection conn = open();
		PreparedStatement ps = conn.prepareStatement("SELECT FOOD FROM FOOD_KCAL");
		ResultSet rs = ps.executeQuery();
		
		try (conn; ps; rs) {
			while (rs.next()) {
				FoodKcal f = new FoodKcal();
				
				f.setFood(rs.getString(1));
				
				foodList.add(f);
			}
		}
		
		return foodList;
	}
	
	public Member getName(int member_no) throws Exception {
		Member mname = new Member();
		
		Connection conn = open();
		PreparedStatement ps = conn.prepareStatement("SELECT MNAME FROM MEMBER_TBL WHERE MEMBER_NO = " + member_no);
		ResultSet rs = ps.executeQuery();
		
		try (conn; ps; rs) {
				if (rs.next()) {
					mname.setMname(rs.getString(1));
				}
			}
		
		return mname;
	}
	
		// 회원별 먹은 식단 목록 생성
	public ArrayList<FoodRecode> getAloneList(int member_no) throws Exception {
		ArrayList<FoodRecode> list = new ArrayList<>();
		
		Connection conn = open();
		PreparedStatement ps = conn.prepareStatement("SELECT A.FOOD_NO, A.MEMBER_NO, TO_CHAR(A.EAT_DATE, 'YYYY-MM-DD') EAT_DATE, A.TIME, A.FOOD, B.KCAL || ' Kcal' "
													+ "FROM FOOD_RECORD A, FOOD_KCAL B "
													+ "WHERE A.FOOD = B.FOOD AND A.MEMBER_NO =" + member_no + " "
													+ "GROUP BY (A.FOOD_NO, A.MEMBER_NO, A.EAT_DATE, A.TIME, A.FOOD, B.KCAL ) "
													+ "ORDER BY EAT_DATE DESC");
		ResultSet rs = ps.executeQuery();
		
		try (conn; ps; rs) {
			while (rs.next()) {
				FoodRecode f = new FoodRecode();
				
				f.setFood_no(rs.getInt(1));
				f.setMember_no(rs.getInt(2));
				f.setEat_date(rs.getString(3));
				f.setTime(rs.getString(4));
				f.setFood(rs.getString(5));
				f.setKcal(rs.getString(6));
				
				list.add(f);
			}
		}
		
		return list;
	}
	
		// 수정할 때 기존정보 가져옴
	public FoodRecode getEditList(String food_no) throws Exception {
		FoodRecode f = new FoodRecode();
		
		Connection conn = open();
		PreparedStatement ps = conn.prepareStatement("SELECT FOOD_NO, MEMBER_NO, TO_CHAR(EAT_DATE, 'YYMMDD'), TIME, FOOD "
													+ "FROM FOOD_RECORD WHERE FOOD_NO = " + food_no);
		ResultSet rs = ps.executeQuery();
		
		try (conn; ps; rs) {
			while (rs.next()) {
				
				f.setFood_no(rs.getInt(1));
				f.setMember_no(rs.getInt(2));
				f.setEat_date(rs.getString(3));
				f.setTime(rs.getString(4));
				f.setFood(rs.getString(5));
			}
		}
		
		return f;
	}
	
		// 회원별 먹은식단 중 한줄 삭제
	public void deleteFood(int food_no) throws Exception {
		Connection conn = open();
		PreparedStatement ps = conn.prepareStatement("DELETE FROM FOOD_RECORD WHERE FOOD_NO = ?");
		
		try (conn; ps) {
			ps.setInt(1, food_no);
			
			if (ps.executeUpdate() != 1) {
				throw new Exception("삭제된 목록이 없습니다.");
			}
		}
		
	}
	
		// 식단추가
	public void insertFood(FoodRecode f) throws Exception {
		Connection conn = open();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO FOOD_RECORD VALUES (FOOD_RECORD_SEQ.NEXTVAL, ?, ?, ?, ?)");
		
		try (conn; ps) {
			ps.setInt(1, f.getMember_no());
			ps.setString(2, f.getEat_date());
			ps.setString(3, f.getTime());
			ps.setString(4, f.getFood());
			
			
			if (ps.executeUpdate() != 1) {
				throw new Exception("입력된 음식이 없습니다.");
			}
		}
		
	}
	
	
	// 회원추가
	public void addInsert(Member m) throws Exception {
		Connection conn = open();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO MEMBER_TBL VALUES (?, ?, ?, ?)");
		
		try (conn; ps) {
			ps.setInt(1, m.getMember_no());
			ps.setString(2, m.getMname());
			ps.setInt(3, m.getHeight());
//			String needKcal = "" + ((m.getHeight() - 100) * 0.9 ) * 25;
			ps.setString(4, String.valueOf(((m.getHeight() - 100) * 0.9 ) * 25));
			
			
			if (ps.executeUpdate() != 1) {
				throw new Exception("입력된 회원이 없습니다.");
			}
		}
		
	}
	
		// 회원별 식단 한줄 수정
	public void updateFood(FoodRecode f) throws Exception {
		Connection conn = open();
		PreparedStatement ps = conn.prepareStatement("UPDATE FOOD_RECORD SET MEMBER_NO = ?, EAT_DATE = ?, TIME = ?, FOOD = ? WHERE FOOD_NO= ?");
		
		try (conn; ps) {
			ps.setInt(1, f.getMember_no());
			ps.setString(2, f.getEat_date());
			ps.setString(3, f.getTime());
			ps.setString(4, f.getFood());
			ps.setInt(5, f.getFood_no());
			
			
			if (ps.executeUpdate() != 1) {
				throw new Exception("입력된 음식이 없습니다.");
			}
		}
		
	}
	
}
