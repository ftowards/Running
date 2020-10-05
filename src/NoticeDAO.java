import java.util.ArrayList;
import java.util.List;

public class NoticeDAO extends DBConn{
	List<NoticeVO> notice = new ArrayList<NoticeVO>();

	public NoticeDAO() {}
	
	public static NoticeDAO getInstance() {
		return new NoticeDAO();
	}
	
	// 알림 생성
	public int newNotice(NoticeVO noVO) {
		int result = 0 ;
		try {
			getConnect();
			
			String sql = "insert into P_notice(id, msg, read) values(?, ?, ?)";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, noVO.getId());
			prs.setString(2, noVO.getMsg());
			prs.setString(3, "1");
			
			result = prs.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {disConnect();}
		return result; 
	}
	
	public int newNotice(String id, String msg) {
		int result = 0 ;
		try {
			getConnect();
			
			String sql = "insert into P_notice(id, msg, read) values(?, ?, 1)";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, id);
			prs.setString(2, msg);
			
			result = prs.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {disConnect();}
		return result; 
	}
	
	// 알림 읽기
	public List<NoticeVO> getAllNotice(String id) {
		try {
			getConnect();
			
			String sql = "select id, msg, read, to_char(inputDate, 'YYYY-MM-DD HH24:MI:SS') from P_NOTICE where id = ? order by inputdate desc";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, id);
						
			rs = prs.executeQuery();
			
			while(rs.next()) {
				NoticeVO vo= new NoticeVO(rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4));
				notice.add(vo);
			}		
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		
		return notice;
	}
}
