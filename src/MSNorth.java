import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MSNorth extends JPanel {

	// North 개인정보 패널
	JPanel center = new JPanel(new BorderLayout());
		JLabel upperLbl = new JLabel("ftowards 님  /  2020-08-27 ");
		JLabel groundLbl = new JLabel("8월 누적 거리 : 123km / 8월 누적 모임 : 8회 / 8월 소모 칼로리 : 10,525cal");
			
	// east 기능 키
	JPanel east = new JPanel(new BorderLayout());
		JButton logoutBtn = new JButton("로그아웃");
		JButton exitBtn = new JButton("프로그램 종료");
		JButton refreshBtn = new JButton("새로 고침");
		
	String id ; // 로그인 id
	Calendar cal ;
	Font fnt = new Font("맑은 고딕", 1, 15);
	Font fnt2 = new Font("맑은 고딕", 1, 12);
	
	Setting set ;

	public MSNorth() {}
	
	public MSNorth(String id, Setting set) {
		this.id = id;
		
		this.set = set;

		setLayout(new BorderLayout());
		
		center.add("North",upperLbl);
			upperLbl.setPreferredSize(new Dimension(0,50));
		center.add("Center", groundLbl);
		add("Center",center);
		
		setUpperLbl();
		setGroundLbl();
		
			east.add("North",logoutBtn);
				logoutBtn.setPreferredSize(new Dimension(0,50));
			east.add("Center", exitBtn);	
			east.add("West", refreshBtn);	set.setBtnStyle(refreshBtn, 1 ,15);
				
		add("East", east);
		
		set.setPaneStyle(center);
		set.setLblStyle(upperLbl, 1, 15);
		set.setLblStyle(groundLbl, 1, 15);
		
		set.setBtnStyle(exitBtn);	set.setBtnStyle(logoutBtn);
	}
	
	// 상단 라벨 문자열 세팅
	public void setUpperLbl() {
		upperLbl.setText("   " + id + " 님  /  " + setDate());
	}
	
	// 하단 라벨 문자열 세팅
	public void setGroundLbl() {
		String month = (String.valueOf(cal.get(Calendar.MONTH)+1).length()) == 1 ? "0" + String.valueOf(cal.get(Calendar.MONTH)+1) : String.valueOf(cal.get(Calendar.MONTH)+1);

		DecimalFormat format = new DecimalFormat("#,##0");
		// 월간 러닝 거리 / 칼로리
		RecordDAO recDAO = RecordDAO.getInstance();	
		double[] personalMonthData = recDAO.getPersonalMonthRecord(id, month);
		double monthRun = personalMonthData[0];
		int monthMeet = (int) personalMonthData[1];
		String monthCal =format.format(personalMonthData[2]);
		// 월간 모임 횟수
		
		groundLbl.setText("  [" + month + "월 요약] 러닝 거리 : " + monthRun + "km / 모임 참가 : "+monthMeet+"회 / 소모 칼로리 " + monthCal +"kcal" );
	}
	
	// 현재 일자 > 문자열
	public String setDate() {
		cal = Calendar.getInstance();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy. MM. dd");
		return sdf.format(cal.getTime());
	}
}
