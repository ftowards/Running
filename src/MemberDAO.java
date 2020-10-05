import java.util.ArrayList;
import java.util.List;

public class MemberDAO extends DBConn{
	List<MemberVO> memberList = new ArrayList<MemberVO>() ;

	public MemberDAO() {	}
	
	public static MemberDAO getInstance() {
		return new MemberDAO();
	}
	
	public List<MemberVO> getAllRecord() {
		try {
			getConnect();
			
			String sql = "select id, password, member_name, tel, height, weight from P_member order by 1";
			prs = con.prepareStatement(sql);
			
			rs = prs.executeQuery();
			
			while(rs.next()) {
				MemberVO vo= new MemberVO(rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getDouble(6));
				memberList.add(vo);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		
		return memberList;
	}
	
	public List<MemberVO> getAllRecordL() {
		try {
			getConnect();
			
			String sql = "select id, password, member_name, tel, height, weight from P_member where status is null order by 1";
			prs = con.prepareStatement(sql);
			
			rs = prs.executeQuery();
			
			while(rs.next()) {
				MemberVO vo= new MemberVO(rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getDouble(6));
				memberList.add(vo);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		
		return memberList;
	}
	
	public MemberVO getMemberInfo(String id) {
		MemberVO vo = new MemberVO();
		try {
			getConnect();
			
			String sql = "select member_name, password, tel, height, weight from P_member where id = ?";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, id);
			rs = prs.executeQuery();
			
			rs.next();
			
			vo.setUserName(rs.getString(1));
			vo.setPassword(rs.getString(2));
			vo.setTel(rs.getString(3));
			vo.setHeight(rs.getDouble(4));
			vo.setWeight(rs.getDouble(5));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return vo;
	}
	
	public int insertRecord(MemberVO vo) {
		int result = 0;
		try {
			getConnect();
			
			String sql = "insert into P_member(id, password, member_name, tel, height, weight) "
					+ "values(?,?,?,?,?,?)";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, vo.getId());
			prs.setString(2, vo.getPassword());
			prs.setString(3, vo.getUserName());
			prs.setString(4, vo.getTel());
			prs.setDouble(5, vo.getHeight());
			prs.setDouble(6, vo.getWeight());
			
			result = prs.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally { disConnect();}
		return result;
	}
	
	public int updateMem(MemberVO vo) {
		int result = 0;
		try {
			getConnect();
			
			String sql = "update P_member set tel = ? , height = ?, weight = ? where id = ?";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, vo.getTel());
			prs.setDouble(2, vo.getHeight());
			prs.setDouble(3,  vo.getWeight());
			prs.setString(4, vo.getId());
			
			result = prs.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return result ;
	}
	
	public int updatePw(MemberVO vo) {
		int result = 0;
		try {
			getConnect();
			
			String sql = "update P_member set password = ? where id = ?";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, vo.getPassword());
			prs.setString(2, vo.getId());
			
			result = prs.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect(); }
		return result ;
	}
	
	public int dropMember(String id) {
		int result = 0;
		try {
			getConnect();
			
			String sql = "update P_member set status = 1 where id = ?";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, id);
			result = prs.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect();}
		return result ;
	}
		
}
