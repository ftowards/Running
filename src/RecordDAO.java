import java.util.ArrayList;
import java.util.List;

public class RecordDAO extends DBConn{

	public RecordDAO() {}
	
	public static RecordDAO getInstance() {
		return new RecordDAO();
	}
	
	// 레코드 전체 리스트
	public List<RecordVO> showAllRecord(String id) {
		List<RecordVO> recordList = new ArrayList<RecordVO>();
		try {
			getConnect();
			
			String sql = "select record_no, to_char(mmdd, 'yyyymmdd'), to_char(time, 'hh24miss'), km, cal "
						   + "from P_record where id = ? order by 1";
			prs = con.prepareStatement(sql);
			prs.setString(1,id);
			
			rs = prs.executeQuery();
			while (rs.next()) {
				RecordVO vo = new RecordVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), id);
				recordList.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return recordList;
	}
	
	public List<RecordVO> showAllRecord(String id, String mm) {
		List<RecordVO> recordList = new ArrayList<RecordVO>();
		try {
			getConnect();
			
			String sql = "select record_no, to_char(mmdd, 'yyyymmdd'), to_char(time, 'hh24miss'), km, cal "
						   + "from P_record where id = ? and to_char(mmdd,'mm') =? order by 1";
			prs = con.prepareStatement(sql);
			prs.setString(1,id);
			prs.setString(2,mm);
			
			rs = prs.executeQuery();
			while (rs.next()) {
				RecordVO vo = new RecordVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), id);
				recordList.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return recordList;
	}
	
	// 레코드 기간 검색
	public List<RecordVO> showSearchedRecord(String id,String startDate, String endDate) {
		List<RecordVO> recordList = new ArrayList<RecordVO>();
		try {
			getConnect();
			
			String sql = "select record_no, to_char(mmdd, 'yyyymmdd'), to_char(time, 'hh24miss'), km, cal "
						   + "from P_record where id = ? and mmdd between to_date(?, 'yyyymmdd') and to_date(?, 'yyyymmdd')";
			prs = con.prepareStatement(sql);
			prs.setString(1,id);
			prs.setString(2,startDate);
			prs.setString(3,endDate);
			
			
			rs = prs.executeQuery();
			while (rs.next()) {
				RecordVO vo = new RecordVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), id);
				recordList.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return recordList;
	}
	
	
	// 레코드 입력
	public int createRec(RecordVO recVO) {
		int result = 0;
		try {
			getConnect();
			
			String sql = "insert into P_record(record_no, mmdd, time, km, cal, id) values "+
						   "(seq_record.nextval, to_date(?, 'YYYYMMDD'), to_date(?,'hh24miss'), ?, ?, ?)";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, recVO.getDate());
			prs.setString(2, recVO.getTime());
			prs.setDouble(3, recVO.getDistance());
			prs.setInt(4,  recVO.getCal());
			prs.setString(5, recVO.getId());
			
			result = prs.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }		
		return result;
	}
	
	public int createRecByMeeting(String id, String date, String time, int meetNo) {
		int result = 0;
		try {
			getConnect();
			
			String sql = "insert into P_record(record_no, mmdd, time, id) values "+
						   "(seq_record.nextval, to_date(?, 'YYYYMMDD'), to_date(?,'hh24mi'), ?)";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, date);
			prs.setString(2, time);
			prs.setString(3, id);
			
			result = prs.executeUpdate();
			
			if (result == 1 ) {
				sql = "insert into p_notice(id, msg) values(?, ?)";
				prs = con.prepareStatement(sql);
				
				String msg = meetNo + "번 모임이 완료되어 기록이 생성되었습니다. 세부 사항을 수정하세요.";
				prs.setString(1, id);
				prs.setString(2,  msg);
				
				result = prs.executeUpdate();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }		
		return result;
	}
	
	// 수정 테이블용 데이터 가져오기
	public RecordVO getRecord(int no) {
		RecordVO vo = new RecordVO();
		try {
			getConnect();
			
			String sql = "select to_char(mmdd, 'YYYYMMDD'), to_char(time, 'hh24miss'), km, cal "
						+ "from p_record where record_no = ?";
			prs = con.prepareStatement(sql);
			
			prs.setInt(1, no);
			rs = prs.executeQuery();
			
			rs.next();
			
			vo.setDate(rs.getString(1));
			vo.setTime(rs.getString(2));
			vo.setDistance(rs.getDouble(3));
			vo.setCal(rs.getInt(4));
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return vo;
	}
	
	// 레코드 수정
	public int updateRecord(RecordVO recVO) {
		int result = 0;
		try {
			getConnect();
			
			String sql = "update P_record set time = to_date(?,'hh24miss'), km = ?, cal = ? where record_no = ?";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, recVO.getTime());
			prs.setDouble(2, recVO.getDistance());
			prs.setInt(3,  recVO.getCal());
			prs.setInt(4, recVO.getRecordNo());
			
			result = prs.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }		
		return result;
	}
	
	// 레코드 삭제
	public int deleteRecord(int no) {
		int result = 0;
		try {
			getConnect();
			
			String sql = "delete from P_record where record_no = ?";
			prs = con.prepareStatement(sql);
			
			prs.setInt(1, no);
			
			result = prs.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return result ;
	}
	
	// 월간 기록 : 거리 + 칼로리

	public double[] getPersonalMonthRecord(String id, String  month) {
		double[] result = new double[3] ;
		try {
			getConnect();
			
			String sql = "select sum(km), sum(cal) from P_record where id = ? and to_char(mmdd, 'mm') = ?";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, id);
			prs.setString(2, month);

			rs = prs.executeQuery();
			rs.next();
			
			result[0] = rs.getDouble(1);
			result[2] = rs.getDouble(2);
			
			sql = "select count(meeting_no) from p_meeting where meeting_no in (select meeting_no from p_entry where id =?) "
					+ "and state = '2' and to_char(mmdd, 'mm') = ?";
			prs=con.prepareStatement(sql);
			
			prs.setString(1, id);
			prs.setString(2, month);
			
			rs = prs.executeQuery();
			rs.next();
			result[1] = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return result; 
	}	
}
