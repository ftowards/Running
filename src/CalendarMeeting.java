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
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class CalendarMeeting extends JPanel implements ActionListener, ItemListener{
	
	JPanel northPane = new JPanel();
		JButton leftYear = new JButton("◁");
		DefaultComboBoxModel<String> yearCB = new DefaultComboBoxModel<String>();
		JComboBox<String> yearTf = new JComboBox<String>(yearCB);
		JButton rightYear = new JButton("▷");
		JLabel yearLbl = new JLabel("년  ");
		JButton leftMonth = new JButton("◁");
		DefaultComboBoxModel<String> monthCB = new DefaultComboBoxModel<String>();
		JComboBox<String> monthTf = new JComboBox<String>(monthCB);
		JButton rightMonth = new JButton("▷");
		JLabel monthLbl = new JLabel("월");
		
	JPanel calPane = new JPanel(new GridLayout(0,7));
	
	JPanel eastPane = new JPanel();
		JLabel txt1 = new JLabel(" 참가 모임 수");
		JLabel txt2 = new JLabel(" 참가 모임 수");
		JLabel txt3 = new JLabel(" 참가 모임 수");
		
		
	String id ;
	Setting set ; 
	
	Calendar cal = Calendar.getInstance();
	int year ; 
	int month ;
	MeetingDAO meetDAO = MeetingDAO.getInstance(); 
	
	public CalendarMeeting() {}
	
	public CalendarMeeting(String id, Setting set) {
		this.id = id;
		this.set = set;
		
		setLayout(new BorderLayout());
		
		add("North", northPane);	set.setPaneStyle(northPane);
			northPane.add(leftYear);		set.setBtnStyle(leftYear);
			northPane.add(yearTf);				
			northPane.add(yearLbl);		set.setLblStyle(yearLbl);
			northPane.add(rightYear);		set.setBtnStyle(rightYear);
			
			northPane.add(leftMonth);		set.setBtnStyle(leftMonth);
			northPane.add(monthTf);
			northPane.add(monthLbl);		set.setLblStyle(monthLbl);
			northPane.add(rightMonth);	set.setBtnStyle(rightMonth);
			
			
		
		add(calPane);
			calPane.setBackground(Color.lightGray);
			calPane.setOpaque(true);
		
		setCB();
		setTfFirst();
		
		setCalendar();
		
		yearTf.addItemListener(this);
		monthTf.addItemListener(this);
		leftYear.addActionListener(this);
		rightYear.addActionListener(this);
		leftMonth.addActionListener(this);
		rightMonth.addActionListener(this);
		
		
		setSize(600,520);
		setVisible(true);
	}
	
	public void setCB() {
		for (int y = 2010 ; y <= 2030 ; y++) {
			yearCB.addElement(String.valueOf(y));
		}
		
		for (int m = 1 ; m <= 12 ; m++) {
			if (m < 10) 
				monthCB.addElement("0"+m);
			else
				monthCB.addElement(String.valueOf(m));
		}
	}
	
	public void setTfFirst() {
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);

		yearTf.setSelectedItem(String.valueOf(year));
		monthTf.setSelectedIndex(month);
	}
	
	public void setWeekTitle(){
		// 타이틀 세팅 
		String[] week = {"일", "월","화","수","목","금","토"};
		for (int i  = 0 ; i<week.length ; i++) {
			JLabel title = new JLabel(week[i],JLabel.CENTER);
			set.setLblStyle(title, 1, 16);
			if(i==0) {
				title.setForeground(Color.RED);
			} else if(i==6) {
				title.setForeground(Color.BLUE);
			}
			calPane.add(title);
		}
	}
	
	public void setCalendar() {
		setWeekTitle();
		//달력 세팅하기
		// 날짜 구하기
		cal.set(year, month, 1);
		int firstWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		cal.set(year, month+1, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		int lastDay = cal.get(Calendar.DAY_OF_MONTH);
		
		for(int i = 1 ; i < firstWeek ; i++) {
			JLabel blank = new JLabel("  ");
			blank.setBackground(Color.lightGray);
			blank.setOpaque(true);
			calPane.add(blank);
		}
		
		for(int day = 1 ; day <= lastDay ; day++) {
			JPanel dayPane = setDayPane(day);
			dayPane.setName(String.valueOf(day));
			calPane.add(dayPane);
			dayPane.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					JPanel obj = (JPanel)me.getSource();
					String searchDate = obj.getName();
					// 클릭한 일자의 모임 정보 가져오기
					popList(searchDate);
				}
			});
		}
	}
	
	public void popList(String date) {
		MeetingTab tab = new MeetingTab(id, set);
		JFrame pop = new JFrame();
		JPanel btnPane = new JPanel();
			JButton closeBtn = new JButton("닫기");
		
		pop.setLayout(new BorderLayout());
		pop.add(tab);
				
		pop.add("South",btnPane);	set.setPaneStyle(btnPane);
			btnPane.add(closeBtn); 	set.setBtnStyle(closeBtn);
			closeBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pop.dispose();
				}
			});			
					
		tab.upperPane.setVisible(false);
		
		// list 세팅하기
		tab.meetList = meetDAO.searchMeetingDate(getTerms(date));
		tab.setTable();
		tab.sp.setPreferredSize(new Dimension(550,270));
		tab.meetingList.getColumnModel().getColumn(3).setPreferredWidth(100);

		
		pop.setBounds(350,350,550,350);
		pop.setVisible(true);
	}
	
	public String[] getTerms(String date){
		String[] terms = new String[4];
		
		terms[0] = getDateString(Integer.parseInt(date));
		terms[1] = getDateString(Integer.parseInt(date));
		terms[2] = "%%";
		terms[3] = "%%";
		
		return terms;		
	}
	
	
	public JPanel setDayPane(int day) {
		Font fnt1 = new Font("맑은 고딕", 1, 12);
		Font fnt2 = new Font("맑은 고딕", 1, 10);
		JPanel dayPane = new JPanel(new GridLayout(4,2));
			dayPane.setBackground(Color.lightGray);
			dayPane.setBorder(new LineBorder(Color.black));
		JPanel empty = new JPanel(); dayPane.add(empty);
			empty.setOpaque(false);
		JLabel dayLbl = new JLabel(String.valueOf(day),JLabel.CENTER);	
			dayPane.add(dayLbl);
			dayLbl.setFont(fnt1);
		
		HashMap<String, Integer> cnt = meetDAO.calendarSet(getDateString(day));
			
		JLabel onLbl = new JLabel("진행 :", JLabel.CENTER);		
			dayPane.add(onLbl);
			onLbl.setFont(fnt2);
		// 해당 일자의 진행 모임 숫자 설정
		String lblTxt = String.valueOf(cnt.get("1")==null ? 0 : cnt.get("1"))+" 개";
			JLabel onNum = new JLabel(lblTxt, JLabel.CENTER);	
			dayPane.add(onNum);
			onNum.setFont(fnt2);
	
		JLabel comLbl = new JLabel("완료 : ",JLabel.CENTER);	
			dayPane.add(comLbl);
			comLbl.setFont(fnt2);
		// 해당 일자의 완료 모임 숫자 설정
		String lblTxt2 = String.valueOf(cnt.get("2")==null ? 0 : cnt.get("2"))+" 개";	
			JLabel comNum = new JLabel(lblTxt2, JLabel.CENTER);
			dayPane.add(comNum);
			comNum.setFont(fnt2);

		JLabel canLbl = new JLabel("취소 : ",JLabel.CENTER);		
			dayPane.add(canLbl);
			canLbl.setFont(fnt2);
		// 해당 일자의 캔슬 된 모임 일자 설정
		String lblTxt3 = String.valueOf(cnt.get("3")==null ? 0 : cnt.get("3"))+" 개";	
			JLabel canNum = new JLabel(lblTxt3, JLabel.CENTER);				
			dayPane.add(canNum);
			canNum.setFont(fnt2);
		
		return dayPane;
	}
	
	public String getDateString(int day){
		String date ;
		
		if(day<10) {
			date = String.valueOf(yearTf.getSelectedItem())+String.valueOf(monthTf.getSelectedItem())+"0"+String.valueOf(day);
		} else {
			date = String.valueOf(yearTf.getSelectedItem())+String.valueOf(monthTf.getSelectedItem())+String.valueOf(day);
		}
		
		return date;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if(obj == leftYear) {
			int idx = yearTf.getSelectedIndex();
			if(idx <= 0) {
				yearTf.setSelectedIndex(0);
			} else {
				yearTf.setSelectedIndex(idx-1);
			}
		} else if(obj == rightYear) {
			int idx = yearTf.getSelectedIndex();
			if(idx >= yearCB.getSize()-1) {
				yearTf.setSelectedIndex(yearCB.getSize()-1);
			} else {
				yearTf.setSelectedIndex(idx+1);
			}
		} else if(obj == leftMonth) {
			int idx = monthTf.getSelectedIndex();
			if(idx <= 0) {
				int idxYear = yearTf.getSelectedIndex();
				if(idxYear > 0) {
					monthTf.setSelectedIndex(11);
					yearTf.setSelectedIndex(idxYear-1);
				}
			} else {
				monthTf.setSelectedIndex(idx-1);
			}
		} else if(obj == rightMonth) {
			int idx = monthTf.getSelectedIndex();
			if(idx >= 11) {
				int idxYear = yearTf.getSelectedIndex();
				if(idxYear < yearCB.getSize()-1) {
					monthTf.setSelectedIndex(0);
					yearTf.setSelectedIndex(idxYear+1);
				}
			} else {
				monthTf.setSelectedIndex(idx+1);
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		year = Integer.parseInt(String.valueOf(yearTf.getSelectedItem()));
		month = monthTf.getSelectedIndex();

		calPane.removeAll();
		setCalendar();
		calPane.updateUI();
	}

}
