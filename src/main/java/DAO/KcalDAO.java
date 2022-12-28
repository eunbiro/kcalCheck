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
	
	
		// 데이터베이스와의 연결 수행 메소드
	public Connection open() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, "test", "test1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;		// 데이터베이스의 연결 객체를 리턴
	}
	
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
	
	public ArrayList<FoodRecode> getAloneList(int member_no) throws Exception {
		ArrayList<FoodRecode> list = new ArrayList<>();
		
		Connection conn = open();
		PreparedStatement ps = conn.prepareStatement("SELECT A.FOOD_NO, A.MEMBER_NO, TO_CHAR(A.EAT_DATE, 'YYYY-MM-DD') EAT_DATE, A.TIME, A.FOOD, B.KCAL || ' Kcal' "
													+ "FROM FOOD_RECORD A, FOOD_KCAL B "
													+ "WHERE A.FOOD = B.FOOD AND MEMBER_NO =" + member_no + " "
													+ "GROUP BY (A.FOOD_NO, A.MEMBER_NO, A.EAT_DATE, A.TIME, A.FOOD, B.KCAL) "
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
	
	public FoodRecode getEditList(String food_no) throws Exception {
		FoodRecode f = new FoodRecode();
		
		Connection conn = open();
		PreparedStatement ps = conn.prepareStatement("SELECT FOOD_NO, MEMBER_NO, EAT_DATE, TIME, FOOD "
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
	
}
