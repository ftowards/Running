import java.util.ArrayList;
import java.util.List;

public class EntryDAO extends DBConn{

	List<EntryVO> entry = new ArrayList<EntryVO>();
	public EntryDAO() {}
	
	public static EntryDAO getInstance() {
		return new EntryDAO();
	}
	
	public List<EntryVO> entryList(int no) {
		try {
			getConnect();
			String sql = "select id, present from p_entry where meeting_no = ?";
			prs = con.prepareStatement(sql);
			
			prs.setInt(1, no);
			
			rs  =prs.executeQuery();
			
			while(rs.next()) {
				EntryVO vo = new EntryVO(no, rs.getString(1), rs.getString(2));
				entry.add(vo);
			}
			
		} catch(Exception e ) {
			e.printStackTrace();
		} finally {disConnect();}
		return entry;
	}
	
	public int inMeeting(String id, int meetingNo, String host) {
		int result = 0;
		try {
			getConnect();
			
			String sql = "insert into P_entry(meeting_no, id, present) values(?,?,1)";
			prs = con.prepareStatement(sql);
			
			prs.setString(2,  id);
			prs.setInt(1,  meetingNo);
			
			result = prs.executeUpdate();
			
			if (result == 1 ) {
				sql = "insert into p_notice(id, msg) values(?, ?)";
				prs = con.prepareStatement(sql);
				
				String msg = id + "님이 " + meetingNo +"번 모임의 참가하셨습니다."; 
				prs.setString(1, host);
				prs.setString(2,msg);
				
				result = prs.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect();}
		return result;
	}
	public int outMeeting(String id, int meetingNo, String host) {
		int result = 0;
		try {
			getConnect();
			
			String sql = "delete from P_entry where id = ? and meeting_no = ?";
			prs = con.prepareStatement(sql);
			
			prs.setString(1,  id);
			prs.setInt(2,  meetingNo);
			
			result = prs.executeUpdate();
			
			if (result == 1 ) {
				sql = "insert into p_notice(id, msg) values(?, ?)";
				prs = con.prepareStatement(sql);
				
				String msg = id + "님이 " + meetingNo +"번 모임의 참가 신청을 취소하셨습니다."; 
				prs.setString(1, host);
				prs.setString(2,msg);
				
				result = prs.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect();}
		return result;
	}
	
	public int outByHost(String id, int meetingNo) {
		int result = 0;
		try {
			getConnect();
			
			String sql = "delete from P_entry where id = ? and meeting_no = ?";
			prs = con.prepareStatement(sql);
			
			prs.setString(1,  id);
			prs.setInt(2,  meetingNo);
			
			result = prs.executeUpdate();
			
			if (result == 1 ) {
				sql = "insert into p_notice(id, msg) values(?, ?)";
				prs = con.prepareStatement(sql);
				
				String msg = "주최자가 "+ meetingNo +"번 모임의 참가 신청을 취소하셨습니다."; 
				prs.setString(1, id);
				prs.setString(2,msg);
				
				result = prs.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect();}
		return result;
	}
}
