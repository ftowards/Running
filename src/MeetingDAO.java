import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MeetingDAO extends DBConn{

	EntryDAO entryDAO = EntryDAO.getInstance();
	ResultSet rs ;
	
	public MeetingDAO() {	
	}
	
	public static MeetingDAO getInstance() {
		return new MeetingDAO();
	}
	
//	private int meetingNo;
//	private String date;
//	private String time;
//	private String location;
//	private String id;
//	private int nop ;
//	private String code;
//	private String state;
	
	// 미팅 입력
	public int createMeeting(MeetingVO vo) {
		int result = 0;
		try {
			getConnect();
			
			String sql = "insert into P_meeting(meeting_no, mmdd, hhmm, location, id, nop, code, state) values"+
						   	  "(seq_meeting.nextval, to_date(?, 'YYYYMMDD'), to_date(?,'hh24mi'), ?, ?, ?, ?, 1)";
						   	  
			prs = con.prepareStatement(sql);
			
			prs.setString(1, vo.getDate());
			prs.setString(2, vo.getTime());
			prs.setString(3, vo.getLocation());
			prs.setString(4,  vo.getId());
			prs.setInt(5, vo.getNop());
			prs.setString(6, vo.getCode());
			
			result = prs.executeUpdate();
			
			if (result == 1) {
				sql = "select meeting_no, id from p_meeting order BY 1 DESC";
				prs= con.prepareStatement(sql);
				
				rs = prs.executeQuery();
				rs.next();
				
				sql = "insert into p_entry(meeting_no, id, present) values (?, ?, 1)";
				prs=con.prepareStatement(sql);
				prs.setInt(1,rs.getInt(1));
				prs.setString(2,  rs.getString(2));
				
				result = prs.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }		
		return result;
	}
	
	public int editMeeting(MeetingVO vo) {
		int result = 0;
		try {
			getConnect();
			
			String sql = "update P_meeting set mmdd = to_date(?, 'YYYYMMDD'), hhmm = to_date(?,'hh24mi'), "+
							  "location =?, nop = ?, code = ? where meeting_no = ?";
						   	  
			prs = con.prepareStatement(sql);
			
			prs.setString(1, vo.getDate());
			prs.setString(2, vo.getTime());
			prs.setString(3, vo.getLocation());
			prs.setInt(4, vo.getNop());
			prs.setString(5, vo.getCode());
			prs.setInt(6,  vo.getMeetingNo());
			
			result = prs.executeUpdate();
			
			if (result == 1) {
				sql = "select id from p_entry where id ^= ? and meeting_no = ?" ;
				prs= con.prepareStatement(sql);
				
				prs.setString(1, vo.getId());
				prs.setInt(2, vo.getMeetingNo());
				
				rs = prs.executeQuery();
				
				while (rs.next()) {
					sql = "insert into p_notice(id, msg) values (?, ?)";
					prs=con.prepareStatement(sql);
					
					String msg = "호스트가 " + vo.getMeetingNo() + "번 모임의 설정을 변경하였습니다."; 
					prs.setString(1,rs.getString(1));
					prs.setString(2, msg);
					
					result = prs.executeUpdate();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return result;
	}
	
	public int cancelMeeting(int no, String host) {
		int result = 0;
		try {
			getConnect();
			
			String sql = "select id from p_entry where meeting_no = ?";
			prs = con.prepareStatement(sql);
			
			prs.setInt(1, no);
			
			rs = prs.executeQuery();
			
			// 참석자 예정자가 있을 경우 취소 공지
			while(rs.next()) {
				sql = "insert into p_notice(id, msg) values (?, ?)";
				prs=con.prepareStatement(sql);
				
				String msg = "호스트가 " + no + "번 모임을 취소하였습니다."; 
				prs.setString(1,rs.getString(1));
				prs.setString(2, msg);	
				
				prs.executeUpdate();
			}
			
			// 엔트리 목록 삭제
//			sql = "delete from p_entry where meeting_no = ?";
//			prs=con.prepareStatement(sql);
//			prs.setInt(1,  no);
//			prs.executeUpdate();
			
			// 모임 레코드 상태 취소로 전환
			sql = "update p_meeting set state = '3' where meeting_no = ?";
			prs=con.prepareStatement(sql);
			prs.setInt(1, no);
			result = prs.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }		
		return result;
	}
	
	// 수정 테이블용 데이터 가져오기
	public MeetingVO getMeeting(int no) {
		MeetingVO vo = new MeetingVO();
		try {
			getConnect();
			
			String sql = "select to_char(mmdd, 'YYYYMMDD'), to_char(hhmm, 'hh24mi'), location, id, nop, code, state "
						+ "from p_meeting where meeting_no = ?";
			prs = con.prepareStatement(sql);
			
			prs.setInt(1, no);
			rs = prs.executeQuery();
			
			rs.next();
			
			vo.setDate(rs.getString(1));
			vo.setTime(rs.getString(2));
			vo.setLocation(rs.getString(3));
			vo.setId(rs.getString(4));
			vo.setNop(rs.getInt(5));
			vo.setCode(rs.getString(6));
			vo.setState(rs.getString(7));
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return vo;
	}
	
	public List<MeetingVO> searchMeetingDate(String[] terms) {
		List<MeetingVO> list = new ArrayList<MeetingVO>();
		try {
			getConnect();
			
			String sql = "select a.meeting_no, to_char(mmdd, 'YYYY-MM-DD'), to_char(hhmm, 'hh24:mi'), location, a.id, nop, code, decode(state, 1, '진행 중', 2,'완료',3,'취소'), count(b.id) " + 
							  "from (select * from p_meeting " + 
							  "where mmdd between to_date(?, 'YYYYMMDD') AND TO_DATE(?, 'YYYYMMDD') AND id like ? and state like ?) a " + 
							  "join p_entry b on a.meeting_no = b.meeting_no " + 
							  "group by a.meeting_no, mmdd, hhmm, location, a.id, nop, code, state order By a.meeting_no";

			prs = con.prepareStatement(sql);
			
			prs.setString(1,  terms[0]);
			prs.setString(2,  terms[1]);
			prs.setString(3,  terms[2]);
			prs.setString(4,  terms[3]);
			
			rs = prs.executeQuery();

			while(rs.next()) {
				MeetingVO vo = new MeetingVO();
				vo.setMeetingNo(rs.getInt(1));
				vo.setDate(rs.getString(2));
				vo.setTime(rs.getString(3));
				vo.setLocation(rs.getString(4));
				vo.setId(rs.getString(5));
				vo.setNop(rs.getInt(6));
				vo.setCode(rs.getString(7));
				vo.setState(rs.getString(8));
				vo.setEntry(rs.getInt(9));

				list.add(vo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return list;
	}
	
	public List<MeetingVO> searchMeetingDate(String[] terms, String id) {
		List<MeetingVO> list = new ArrayList<MeetingVO>();
		try {
			getConnect();
			
			String sql = "select a.meeting_no, to_char(mmdd, 'YYYY-MM-DD'), to_char(hhmm, 'hh24:mi'), location, a.id, nop, code, decode(state, 1, '진행 중', 2,'완료',3,'취소'), count(b.id) " + 
							  "from (select * from p_meeting " + 
							  "where mmdd between to_date(?, 'YYYYMMDD') AND TO_DATE(?, 'YYYYMMDD') AND id like ? and state like ? and meeting_no in (select meeting_no from p_entry where id = ?)) a " + 
							  "join p_entry b on a.meeting_no = b.meeting_no " + 
							  "group by a.meeting_no, mmdd, hhmm, location, a.id, nop, code, state order By a.meeting_no";

			prs = con.prepareStatement(sql);
			
			prs.setString(1,  terms[0]);
			prs.setString(2,  terms[1]);
			prs.setString(3,  terms[2]);
			prs.setString(4,  terms[3]);
			prs.setString(5, id);
			
			rs = prs.executeQuery();

			while(rs.next()) {
				MeetingVO vo = new MeetingVO();
				vo.setMeetingNo(rs.getInt(1));
				vo.setDate(rs.getString(2));
				vo.setTime(rs.getString(3));
				vo.setLocation(rs.getString(4));
				vo.setId(rs.getString(5));
				vo.setNop(rs.getInt(6));
				vo.setCode(rs.getString(7));
				vo.setState(rs.getString(8));
				vo.setEntry(rs.getInt(9));

				list.add(vo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return list;
	}
	
	public List<MeetingVO> searchMeetingTime(String[] terms) {
		List<MeetingVO> list = new ArrayList<MeetingVO>();
		try {
			getConnect();
			
			String sql = "select a.meeting_no, to_char(mmdd, 'YYYY-MM-DD'), to_char(hhmm, 'hh24:mi'), location, a.id, nop, code, decode(state, 1, '진행 중', 2,'완료',3,'취소'), count(b.id) " + 
							  "from (select * from p_meeting " + 
							  "where hhmm between to_date(?, 'hh24mi') AND TO_DATE(?, 'hh24mi') AND id like ? and state like ?) a " + 
							  "join p_entry b on a.meeting_no = b.meeting_no " + 
							  "group by a.meeting_no, mmdd, hhmm, location, a.id, nop, code, state order By a.meeting_no";

			prs = con.prepareStatement(sql);
			
			prs.setString(1,  terms[0]);
			prs.setString(2,  terms[1]);
			prs.setString(3,  terms[2]);
			prs.setString(4,  terms[3]);
			
			rs = prs.executeQuery();

			while(rs.next()) {
				MeetingVO vo = new MeetingVO();
				vo.setMeetingNo(rs.getInt(1));
				vo.setDate(rs.getString(2));
				vo.setTime(rs.getString(3));
				vo.setLocation(rs.getString(4));
				vo.setId(rs.getString(5));
				vo.setNop(rs.getInt(6));
				vo.setCode(rs.getString(7));
				vo.setState(rs.getString(8));
				vo.setEntry(rs.getInt(9));

				list.add(vo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return list;
	}
	
	public List<MeetingVO> searchMeetingTime(String[] terms, String id) {
		List<MeetingVO> list = new ArrayList<MeetingVO>();
		try {
			getConnect();
			
			String sql = "select a.meeting_no, to_char(mmdd, 'YYYY-MM-DD'), to_char(hhmm, 'hh24:mi'), location, a.id, nop, code, decode(state, 1, '진행 중', 2,'완료',3,'취소'), count(b.id) " + 
							  "from (select * from p_meeting " + 
							  "where hhmm between to_date(?, 'hh24mi') AND TO_DATE(?, 'hh24mi') AND id like ? and state like ? and meeting_no in (select meeting_no from p_entry where id = ?)) a " + 
							  "join p_entry b on a.meeting_no = b.meeting_no " + 
							  "group by a.meeting_no, mmdd, hhmm, location, a.id, nop, code, state order By a.meeting_no";

			prs = con.prepareStatement(sql);
			
			prs.setString(1,  terms[0]);
			prs.setString(2,  terms[1]);
			prs.setString(3,  terms[2]);
			prs.setString(4,  terms[3]);
			prs.setString(5, id);
			
			rs = prs.executeQuery();

			while(rs.next()) {
				MeetingVO vo = new MeetingVO();
				vo.setMeetingNo(rs.getInt(1));
				vo.setDate(rs.getString(2));
				vo.setTime(rs.getString(3));
				vo.setLocation(rs.getString(4));
				vo.setId(rs.getString(5));
				vo.setNop(rs.getInt(6));
				vo.setCode(rs.getString(7));
				vo.setState(rs.getString(8));
				vo.setEntry(rs.getInt(9));

				list.add(vo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return list;
	}
	
	public void statusChange(int status, int no) {
		try {
			getConnect();
			
			String sql = "update p_meeting set state = ? where meeting_no = ?";
			prs = con.prepareStatement(sql);
			
			prs.setInt(1,  status);
			prs.setInt(2, no);
			
			prs.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect();}
	}
	
	// 달력에 세팅할 모임 숫자 가져오기
	public HashMap<String, Integer> calendarSet(String date) {
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		try {
			getConnect();
			
			String sql = "select state, count(meeting_no) from p_meeting where to_char(mmdd, 'YYYYMMDD') = ? group by state order by 1";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, date);
			rs = prs.executeQuery();
			
			while (rs.next()) {
				result.put(rs.getString(1), rs.getInt(2));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect();}
		return result;
	}
	
	// 월간 랭킹 구하기
	public HashMap<String, RankVO> monthlyRank(String id, String date){
		HashMap<String, RankVO> result = new HashMap<String, RankVO>();
		int rank = 0;
		try {
			getConnect();
			String sql = "select rank() over(order by cnt desc), id, cnt, member from(	select c.id, count(c.meeting_no) as cnt, sum(d.num) as member from p_meeting c "+
							"join (select a.meeting_no as meeting_no, count(b.id) as num from p_meeting a join p_entry b on a.meeting_no = b.meeting_no where a.state ='2' group by a.meeting_no) d "+
							"on c.meeting_no = d.meeting_no where to_char(c.mmdd, 'YYYYMM') = ? group by c.id)";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, date);
			
			rs = prs.executeQuery();
			
			while (rs.next()) {
				RankVO vo = new RankVO(String.valueOf(rs.getInt(1)) , rs.getString(2),String.valueOf(rs.getInt(3)), String.valueOf(rs.getInt(4)));
				
				rank++;
				result.put(String.valueOf(rank), vo);
				if(rank == 5) {break;}
			}
			
			// 모임 주최 횟수 별 랭킹 / 내 정보
			sql =	"select * from (select rank() over(order by cnt desc), id, cnt, member from(select c.id, count(c.meeting_no) as cnt, sum(d.num) as member from p_meeting c "+
					"join (select a.meeting_no as meeting_no, count(b.id) as num from p_meeting a join p_entry b on a.meeting_no = b.meeting_no where state ='2' group by a.meeting_no) d " +
					"on c.meeting_no = d.meeting_no where to_char(c.mmdd, 'YYYYMM') = ? group by c.id)) where id = ?";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, date);
			prs.setString(2, id);
			
			rs = prs.executeQuery();
			rs.next();
			RankVO vo = new RankVO(String.valueOf(rs.getInt(1)) , rs.getString(2),String.valueOf(rs.getInt(3)), String.valueOf(rs.getInt(4)));
			
			result.put(String.valueOf(6), vo);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {disConnect();}
		return result;
	}
	
	// 월간 랭킹 구하기2
	public HashMap<String, RankVO> monthlyRank2(String id, String date){
		HashMap<String, RankVO> result = new HashMap<String, RankVO>();
		int rank = 0;
		try {
			getConnect();
			String sql = "select rank() over(order by count(a.meeting_no) desc) as rank, a.id, count(a.meeting_no) from p_entry a " + 
							"join p_meeting b on a.meeting_no = b.meeting_no " + 
							"where to_char(b.mmdd, 'YYYYMM') = ? and b.state = '2' group by a.id";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, date);
			
			rs = prs.executeQuery();
			
			while (rs.next()) {
				RankVO vo = new RankVO(String.valueOf(rs.getInt(1)) , rs.getString(2),String.valueOf(rs.getInt(3)), "");
				
				rank++;
				result.put(String.valueOf(rank), vo);
				
				if(rank == 5) {break;}
			}
			
			// 모임 참가 횟수별 랭킹 / 내 정보
			sql =	"select * from(select rank() over(order by count(a.meeting_no) desc) as rank, a.id, count(a.meeting_no) from p_entry a " + 
					"join p_meeting b on a.meeting_no = b.meeting_no " + 
					"where to_char(b.mmdd, 'YYYYMM') = ? and b.state = '2' group by a.id) where id = ?";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, date);
			prs.setString(2, id);
			
			rs = prs.executeQuery();
			
			rs.next();
			RankVO vo = new RankVO(String.valueOf(rs.getInt(1)) , rs.getString(2),String.valueOf(rs.getInt(3)), "");
			
			result.put(String.valueOf(6), vo);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {disConnect();}
		return result;
	}
}
