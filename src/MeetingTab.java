import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MeetingTab extends JPanel implements ItemListener, ActionListener {
	
	// 레이아웃
	
	// 상단 패널 - 검색 패널
	JPanel upperPane = new JPanel (new BorderLayout());
		// 센터
		JPanel upperCenter = new JPanel(new BorderLayout()); // 보더 테두리 세팅
			JPanel upperRadioPane = new JPanel(new GridLayout(2,1));
				ButtonGroup search = new ButtonGroup();
					JRadioButton date = new JRadioButton("날짜 검색");
					JRadioButton time = new JRadioButton("시간 검색");
			JPanel upperComboPane = new JPanel(new GridLayout(2,1));
				// 날짜 지정 그룹
				JPanel dateGroup = new JPanel();
					DefaultComboBoxModel<String> yc1 = new DefaultComboBoxModel<String>();
					JComboBox<String> yyCb1= new JComboBox<String>(yc1);
						JLabel txt1 = new JLabel("년");
					DefaultComboBoxModel<String> mc1= new DefaultComboBoxModel<String>();
					JComboBox<String> mmCb1 = new JComboBox<String>(mc1);
						JLabel txt2 = new JLabel("월");
					DefaultComboBoxModel<String> dc1 = new DefaultComboBoxModel<String>();
					JComboBox<String> ddCb1 = new JComboBox<String>(dc1);
						JLabel txt3 = new JLabel("일~");
						
					DefaultComboBoxModel<String> yc2 = new DefaultComboBoxModel<String>();
					JComboBox<String> yyCb2= new JComboBox<String>(yc2);
						JLabel txt4 = new JLabel("년");
					DefaultComboBoxModel<String> mc2 = new DefaultComboBoxModel<String>();
					JComboBox<String> mmCb2 = new JComboBox<String>(mc2);
						JLabel txt5 = new JLabel("월");
					DefaultComboBoxModel<String> dc2 = new DefaultComboBoxModel<String>();
					JComboBox<String> ddCb2 = new JComboBox<String>(dc2);
					JTextField ddTf = new JTextField(3);
						JLabel txt6 = new JLabel("일");
				
				// 시간 지정 그룹
				JPanel timeGroup = new JPanel();
					DefaultComboBoxModel<String> hc1 = new DefaultComboBoxModel<String>();
					JComboBox<String> hhCb1 = new JComboBox<String>(hc1);
						JLabel txt7 = new JLabel("시");
					DefaultComboBoxModel<String> mic1 = new DefaultComboBoxModel<String>();
					JComboBox<String> miCb1 = new JComboBox<String>(mic1);
						JLabel txt8 = new JLabel("분 ~ ");
					DefaultComboBoxModel<String> hc2 = new DefaultComboBoxModel<String>();
					JComboBox<String> hhCb2 = new JComboBox<String>(hc2);
						JLabel txt9 = new JLabel("시");
					DefaultComboBoxModel<String> mic2 = new DefaultComboBoxModel<String>();
					JComboBox<String> miCb2 = new JComboBox<String>(mic2);
						JLabel txt0 = new JLabel("분");
						
			JPanel centerSouth = new JPanel();
				JLabel hostLbl = new JLabel("주최자");
				JTextField hostTf = new JTextField(6);
				JLabel stateLbl = new JLabel("상태");
				String status[] = {"전체", "진행 중", "완료", "취소"};
				JComboBox<String> stateCb = new JComboBox<String>(status);				
				
	 	// East 검색 버튼
		JPanel upperEast = new JPanel(new BorderLayout());
			JPanel btnPane = new JPanel(new GridLayout(2,1,2,5));
			JButton searchBtn = new JButton("모임 검색");
			JButton searchMeInBtn = new JButton("참가내역 검색");
		

	// 하단 - ScrollPane
	// JTable
	String title[] = {"No.", "날짜", "시간", "장소", "주최자", "가능인원", "참가인원", "상태"};
	DefaultTableModel dtm = new DefaultTableModel(title,0);
	JTable meetingList = new JTable(dtm);
	JScrollPane sp = new JScrollPane(meetingList);
	
	//	입력  			수정
//private int meetingNo;   / 생성 때 시퀀스   인에이블 
//private String date; 			입력 연월일별도로  // 수정 시 기존 참가자 알람
//private String time;           콤보박스 2종 24 / 10분 단위
//private String location;      텍스트 박스
//private String id;				생성자 id
//private int nop ;				    생성자 포함 2인 이상  // 현재 참가자 이하로 수정 불가능
//private String code;			4자리 코드
//private String state;			생성 시 1 : 가능 // 2: 완료 // 3: 취소

	
	// 필드
	String id ;
	List<MeetingVO> meetList = new ArrayList<MeetingVO>();
	Calendar cal = Calendar.getInstance();

	Setting set; 

	public MeetingTab() {}
	
	public MeetingTab(String id, Setting set) {
		this.id = id;
		this.set = set;
		
		// 레이아웃 설정
		// 여백
		setLayout(new BorderLayout());
		
		// 상단 패널 설정
		add(upperPane);
			set.setPaneStyle(upperPane); 
			upperPane.setBorder(new CompoundBorder(new MatteBorder(5,5,5,5, set.bgColor),new LineBorder(set.fntColor,2)));
			upperPane.add("West", upperRadioPane);
				upperRadioPane.add(date); upperRadioPane.add(time);
				search.add(date); search.add(time);
					set.setRbStyle(date); set.setRbStyle(time); set.setPaneStyle(upperRadioPane);
			upperPane.add(upperComboPane);
				set.setPaneStyle(upperComboPane);
				upperComboPane.add(dateGroup);
					dateGroup.add(yyCb1); dateGroup.add(txt1);		set.setLblStyle(txt1);
					dateGroup.add(mmCb1); dateGroup.add(txt2);	set.setLblStyle(txt2);
					dateGroup.add(ddCb1); dateGroup.add(txt3);		set.setLblStyle(txt3);
					dateGroup.add(yyCb2); dateGroup.add(txt4);		set.setLblStyle(txt4);
					dateGroup.add(mmCb2); dateGroup.add(txt5);	set.setLblStyle(txt5);
					dateGroup.add(ddCb2); dateGroup.add(txt6);		set.setLblStyle(txt6);
						set.setPaneStyle(dateGroup);
				upperComboPane.add(timeGroup);
					timeGroup.add(hhCb1); timeGroup.add(txt7);		set.setLblStyle(txt7);
					timeGroup.add(miCb1); timeGroup.add(txt8);		set.setLblStyle(txt8);
					timeGroup.add(hhCb2); timeGroup.add(txt9);		set.setLblStyle(txt9);
					timeGroup.add(miCb2); timeGroup.add(txt0);		set.setLblStyle(txt0);
						set.setPaneStyle(timeGroup);
			upperPane.add("South", centerSouth);
				set.setPaneStyle(centerSouth);
				centerSouth.add(hostLbl) ; centerSouth.add(hostTf);
					set.setLblStyle(hostLbl);
				centerSouth.add(stateLbl); centerSouth.add(stateCb);
					set.setLblStyle(stateLbl);
			
		upperPane.add("East",upperEast);
			upperEast.setBorder(new MatteBorder(5,5,5,5,set.bgColor));  
			upperEast.add(btnPane);
				btnPane.add(searchBtn); btnPane.add(searchMeInBtn);
					set.setPaneStyle(btnPane); set.setBtnStyle(searchBtn); set.setBtnStyle(searchMeInBtn);
			
		
		add("South",sp);
		upperPane.setPreferredSize(new Dimension(550,150));
		sp.setPreferredSize(new Dimension(550,280));
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);
		meetingList.getColumnModel().getColumn(0).setPreferredWidth(30);
		meetingList.getColumnModel().getColumn(0).setCellRenderer(center);
		meetingList.getColumnModel().getColumn(1).setPreferredWidth(80);
		meetingList.getColumnModel().getColumn(1).setCellRenderer(center);
		meetingList.getColumnModel().getColumn(2).setPreferredWidth(40);
		meetingList.getColumnModel().getColumn(2).setCellRenderer(center);
		meetingList.getColumnModel().getColumn(3).setPreferredWidth(180);
		meetingList.getColumnModel().getColumn(4).setPreferredWidth(70);
		meetingList.getColumnModel().getColumn(4).setCellRenderer(center);
		meetingList.getColumnModel().getColumn(5).setPreferredWidth(50);
		meetingList.getColumnModel().getColumn(5).setCellRenderer(center);
		meetingList.getColumnModel().getColumn(6).setPreferredWidth(50);
		meetingList.getColumnModel().getColumn(6).setCellRenderer(center);
		meetingList.getColumnModel().getColumn(7).setPreferredWidth(50);
		meetingList.getColumnModel().getColumn(7).setCellRenderer(center);
		
		setDefault();
		
		yyCb1.addItemListener(this);
		mmCb1.addItemListener(this);
		yyCb2.addItemListener(this);
		mmCb2.addItemListener(this);
		searchBtn.addActionListener(this);
		searchMeInBtn.addActionListener(this);
		meetingList.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) {
				if(me.getButton() == 1) {
					int row = meetingList.getSelectedRow();
					new MeetingInfo(id, Integer.parseInt(String.valueOf(dtm.getValueAt(row, 0))), set);
				}
			}
		});
		
		setSize(600,520);
		setVisible(true);
	}
	
	public void setComboList() {
		// 콤보 박스 아이템 세팅
		for (int i = 2019 ; i <= 2030 ; i++) {
			yc1.addElement(String.valueOf(i));
			yc2.addElement(String.valueOf(i));
		}
		
		for (int m = 1 ; m <=12 ; m++ ) {
			if ( m< 10) {
				mc1.addElement("0"+m); mc2.addElement("0"+m); }
			else {
				mc1.addElement(String.valueOf(m)); mc2.addElement(String.valueOf(m)); }
		}
		
		for (int h = 0 ; h <= 23 ; h++) {
			if ( h< 10) {
				hc1.addElement("0"+h); hc2.addElement("0"+h); }
			else {
				hc1.addElement(String.valueOf(h));	hc2.addElement(String.valueOf(h));}
		}
		
		for (int mi = 0 ; mi <=50 ; mi+=5) {
			if (mi < 10) {
				mic1.addElement("0"+mi);	mic2.addElement("0"+mi);}  
			else {
				mic1.addElement(String.valueOf(mi)); mic2.addElement(String.valueOf(mi)); } 
		} 
	}
	
	public void setDayComboBoxModel(JComboBox<String>combo1, JComboBox<String> combo2, DefaultComboBoxModel<String> model) {
		try {
			Calendar temp = Calendar.getInstance();
			int year = Integer.parseInt(String.valueOf(combo1.getSelectedItem()));
			int month = Integer.parseInt(String.valueOf(combo2.getSelectedItem()));
			temp.set(year, month,1);
			temp.add(Calendar.DAY_OF_MONTH, -1);
			
			int lastDay = temp.get(Calendar.DAY_OF_MONTH);
			for (int d = 1 ; d <= lastDay; d++) {
				if (d < 10) 
					model.addElement("0"+String.valueOf(d));
				else
					model.addElement(String.valueOf(d));
			}		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setDefault() {
		setComboList();
		
		yyCb1.setSelectedItem(String.valueOf(cal.get(Calendar.YEAR)));
		mmCb1.setSelectedIndex(cal.get(Calendar.MONTH));
		setDayComboBoxModel(yyCb1, mmCb1, dc1);
		ddCb1.setSelectedIndex(cal.get(Calendar.DAY_OF_MONTH)-1);
		
		yyCb2.setSelectedItem(String.valueOf(cal.get(Calendar.YEAR)));
		mmCb2.setSelectedIndex(cal.get(Calendar.MONTH));
		setDayComboBoxModel(yyCb2, mmCb2, dc2);
		ddCb2.setSelectedIndex(cal.get(Calendar.DAY_OF_MONTH)-1);
		
		hhCb1.setSelectedIndex(0);
		miCb1.setSelectedIndex(0);
		
		hhCb2.setSelectedIndex(0);
		miCb2.setSelectedIndex(0);
		
		date.setSelected(true);
		
	}
	
	public int compareDate() {
		int year1 = Integer.parseInt(String.valueOf(yyCb1.getSelectedItem()));
		int month1 = Integer.parseInt(String.valueOf(mmCb1.getSelectedItem()));
		int day1 = Integer.parseInt(String.valueOf(ddCb1.getSelectedItem()));
		
		int year2 = Integer.parseInt(String.valueOf(yyCb2.getSelectedItem()));
		int month2 = Integer.parseInt(String.valueOf(mmCb2.getSelectedItem()));
		int day2 = Integer.parseInt(String.valueOf(ddCb2.getSelectedItem()));
		
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.set(year1,  month1-1, day1);
		end.set(year2,  month2-1, day2);
		
		return end.compareTo(start); // 0 보다 크거나 같아야 함.
	}
	
	public int compareTime() {
		int hh1 = Integer.parseInt(String.valueOf(hhCb1.getSelectedItem()));
		int mi1 = Integer.parseInt(String.valueOf(miCb1.getSelectedItem()));

		int hh2 = Integer.parseInt(String.valueOf(hhCb2.getSelectedItem()));
		int mi2 = Integer.parseInt(String.valueOf(miCb2.getSelectedItem()));		
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.set(1,1,1,hh1,mi1);
		end.set(1,1,1,hh2,mi2);
		
		return end.compareTo(start); // 0 보다 크거나 같아야 함.		
	}
	
	public String[] getSearchDateTerms(){
		String[] terms = new String[4];
		
		terms[0] = getDateInput(yyCb1, yc1, mmCb1, mc1, ddCb1, dc1);
		terms[1] =getDateInput(yyCb2, yc2, mmCb2, mc2, ddCb2, dc2);
		terms[2] = "%"+hostTf.getText()+"%";
		String status = stateCb.getSelectedIndex() == 0 ? "" : String.valueOf(stateCb.getSelectedIndex()) ;
		terms[3] = "%"+status+"%";
		
		return terms;
	}
	
	public String[] getSearchTimeTerms(){
		String[] terms = new String[4];
		
		terms[0] = getTimeInput(hhCb1, hc1, miCb1, mic1);
		terms[1] = getTimeInput(hhCb2, hc2, miCb2, mic2);
		terms[2] = "%"+hostTf.getText()+"%";
		String status = stateCb.getSelectedIndex() == 0 ? "" : String.valueOf(stateCb.getSelectedIndex()) ; 
		terms[3] = "%"+status+"%";
		
		return terms;
	}
		
	public String getDateInput(JComboBox<String> cb1, DefaultComboBoxModel<String> m1, JComboBox<String> cb2, DefaultComboBoxModel<String> m2, JComboBox<String> cb3,DefaultComboBoxModel<String> m3) {
		String year = m1.getElementAt(cb1.getSelectedIndex());
		String month = m2.getElementAt(cb2.getSelectedIndex());
		String day = m3.getElementAt(cb3.getSelectedIndex());
		
		return year+month+day;
	}
	
	public String getTimeInput(JComboBox<String> cb1, DefaultComboBoxModel<String> m1, JComboBox<String> cb2, DefaultComboBoxModel<String> m2) {
		String hour = m1.getElementAt(cb1.getSelectedIndex());
		String min = m2.getElementAt(cb2.getSelectedIndex());
		
		return hour+min;
	}		
	
	public void setTable() {
		dtm.setRowCount(0);
		for (int i  =0 ; i < meetList.size(); i++) {
			MeetingVO vo = meetList.get(i);
			
			Object data[] = {vo.getMeetingNo(), vo.getDate(), vo.getTime(), vo.getLocation(), vo.getId(), 
									vo.getNop(), vo.getEntry(), vo.getState()};
			
			dtm.addRow(data);
		}
	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object event = e.getSource();
		
		if(date.isSelected()) {
			if( compareDate() >= 0 ) {
				if(event == searchBtn) {
					MeetingDAO meetDAO = MeetingDAO.getInstance();
					meetList = meetDAO.searchMeetingDate(getSearchDateTerms());
					setTable();
				} else if(event == searchMeInBtn) {
					MeetingDAO meetDAO = MeetingDAO.getInstance();
					meetList = meetDAO.searchMeetingDate(getSearchDateTerms(), id);
					setTable();
				}
			} else {
				JOptionPane.showMessageDialog(this, "검색 종료일은 시작일보다 빠를 수 없습니다.");
			}
		} else {
			if( compareTime() >= 0 ) {
				if( event == searchBtn) {
					MeetingDAO meetDAO = MeetingDAO.getInstance();
					meetList = meetDAO.searchMeetingTime(getSearchTimeTerms());
					setTable();
				} else if( event == searchMeInBtn) {
					MeetingDAO meetDAO = MeetingDAO.getInstance();
					meetList = meetDAO.searchMeetingTime(getSearchTimeTerms(),id);
					setTable();
				}
			} else {
				JOptionPane.showMessageDialog(this, "검색 종료 시간은 시작 시간보다 빠를 수 없습니다.");
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		try {
			Object obj = e.getSource();
			if (obj == yyCb1 || obj == mmCb1) {
				dc1.removeAllElements();
				setDayComboBoxModel(yyCb1, mmCb1, dc1);
			} else if (obj == yyCb2 || obj == mmCb2) {
				dc2.removeAllElements();
				setDayComboBoxModel(yyCb2, mmCb2, dc2);
			}
		} catch(Exception ee) {
			ee.printStackTrace();
		}		
	}
	
}
