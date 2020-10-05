import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConn {
	
	// jdbc 드라이브 로딩
	static { 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");		
		} catch (ClassNotFoundException e ) {
			System.out.println("JDBC 드라이브 에러 >> " + e.getMessage());
		}
	}
	
	Connection con ;
	ResultSet rs ; 
	PreparedStatement prs;
	
	String url = "jdbc:oracle:thin:@192.168.0.216:1521:xe";
	String userId = "scott";
	String pw = "tiger";

	public DBConn() {
	}
	
	// DB 연결
	public void getConnect() {
		try {
			con = DriverManager.getConnection(url,  userId,  pw);
			
		} catch (SQLException e) {
			System.out.println("DB 연결 에러 발생 --> " + e.getMessage());
		}
	}
	
	// DB 종료
	public void disConnect() {
		try {
			if (rs!= null ) rs.close();
			if (prs != null) prs.close();
			if (con != null) con.close();			
		} catch (SQLException e) {
			System.out.println("DB 닫기 에러 발생 --> " + e.getMessage());
		}
	}
}
