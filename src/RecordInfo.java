import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
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
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class RecordInfo extends JFrame implements ActionListener, ItemListener{
	String id ;
	
	// 센터 
	// 일련번호(기본 비활성) / 일자(YYYY-MM-DD) / 시간 (HH24:MI:SS) / 거리 / 칼로리 계산(비활성화)
	
	JPanel center = new JPanel(new BorderLayout());
		JPanel lblPane = new JPanel(new GridLayout(5,1));
		
		JPanel inputPane = new JPanel(new GridLayout(5,1));
			JLabel recordNoLbl = new JLabel("일련번호", JLabel.RIGHT);
				JPanel tf1 = new JPanel();
				JTextField recordNoTf = new JTextField(6);
			JLabel dateLbl = new JLabel("일자*", JLabel.RIGHT);
				JPanel tf2 = new JPanel();
				DefaultComboBoxModel<Integer> yc = new DefaultComboBoxModel<Integer>();
				JComboBox<Integer> yyCb = new JComboBox<Integer>(yc);
				JTextField yyTf = new JTextField();
				DefaultComboBoxModel<Integer> mc = new DefaultComboBoxModel<Integer>();
				JComboBox<Integer> mmCb = new JComboBox<Integer>(mc);
				JTextField mmTf = new JTextField();
				DefaultComboBoxModel<Integer> dc = new DefaultComboBoxModel<Integer>();
				JComboBox<Integer> ddCb = new JComboBox<Integer>(dc);
				JTextField ddTf = new JTextField();
			JLabel timeLbl = new JLabel("소요시간*", JLabel.RIGHT);
				JPanel tf3 = new JPanel();
				DefaultComboBoxModel<Integer> hc = new DefaultComboBoxModel<Integer>();
				JComboBox<Integer> hhCb = new JComboBox<Integer>(hc);
				DefaultComboBoxModel<Integer> mic = new DefaultComboBoxModel<Integer>();
				JComboBox<Integer> miCb = new JComboBox<Integer>(mic);
				DefaultComboBoxModel<Integer> sc = new DefaultComboBoxModel<Integer>();
				JComboBox<Integer> ssCb = new JComboBox<Integer>(sc);
									
			JLabel distanceLbl = new JLabel("거리", JLabel.RIGHT);
				JPanel tf4 = new JPanel();
				JTextField distanceTf = new JTextField(4);
			JLabel calLbl = new JLabel("소모 칼로리", JLabel.RIGHT);
				JPanel tf5 = new JPanel();
				JTextField calTf = new JTextField(4);
		JLabel caution = new JLabel("       * 필수항목입니다.");
		
	JPanel east = new JPanel(new BorderLayout());
		JPanel empty = new JPanel();
		JPanel radioCen = new JPanel(new GridLayout(3,1));
			JRadioButton type1 = new JRadioButton("산책(3~6km/h)");
			JRadioButton type2 = new JRadioButton("조깅(6~10km/h)");
			JRadioButton type3 = new JRadioButton("달리기(10km/h 이상");
		JPanel calPane = new JPanel();
			JButton check1 = new JButton("계산");
			
	// 하단 기능 버튼
	JPanel southPane = new JPanel(new BorderLayout());
	JPanel btnPane = new JPanel();
		JButton okBtn = new JButton("입력");
		JButton closeBtn = new JButton("닫기");
		JButton editBtn = new JButton("수정");
		JButton delBtn = new JButton("삭제");
		JButton clearBtn = new JButton("초기화");
		
	Font fnt2 = new Font("맑은 고딕", 0, 12);
	
	// 변수
	RecordDAO recDAO = RecordDAO.getInstance();
	MemberDAO mDAO = MemberDAO.getInstance();
	MemberVO myVO ; 
	RecordVO recVO;
	int recordNo ;
	
	Calendar cal ;
	Setting set ;

	public RecordInfo() {}
	
	public RecordInfo(String id, Setting set) {
		this.id = id;
		
		this.set = new Setting(id);
		myVO = mDAO.getMemberInfo(id);
		setTitle("기록 입력");
		
		setLayout(new BorderLayout());
		add("West", lblPane);
			set.setPaneStyle(lblPane);
		add("Center", center);
			center.add("Center", inputPane);
				set.setPaneStyle(inputPane);
				setLbl1(recordNoLbl); 		setTf1(tf1, recordNoTf);		recordNoTf.setHorizontalAlignment(JLabel.CENTER);
				setLbl1(dateLbl);
				inputPane.add(tf2); setDateTf();
				setLbl1(timeLbl);
				inputPane.add(tf3); setTimeTf();
				distanceTf.setDocument(new LimitDocument(5));
				setLbl1(distanceLbl); setTf1(tf4, distanceTf);	
				JLabel txt1 = new JLabel("km"); set.setLblStyle(txt1);		tf4.add(txt1);
				setLbl1(calLbl); setTf1(tf5, calTf); 
				JLabel txt2 = new JLabel("kcal"); set.setLblStyle(txt2); 	tf5.add(txt2);

		center.add("East", east);
			east.add("North",empty); empty.setPreferredSize(new Dimension(60,20));
				set.setPaneStyle(empty);
			
			east.add(radioCen);
				radioCen.setBorder(new LineBorder(set.fntColor));
				set.setPaneStyle(radioCen);
				radioCen.add(type1); 	set.setRbStyle(type1);
				radioCen.add(type2); set.setRbStyle(type2);
				radioCen.add(type3); set.setRbStyle(type3);
					ButtonGroup type = new ButtonGroup();
					type.add(type1);type.add(type2);type.add(type3);
				
			east.add("South", calPane); calPane.add(check1);
				set.setPaneStyle(calPane); set.setBtnStyle(check1,1,10);

			southPane.add("North", caution);
			southPane.add(btnPane);
				set.setLblStyle(caution,1,12);
				caution.setForeground(Color.RED);
			
		add("South", southPane);
			btnPane.add(okBtn);		set.setBtnStyle(okBtn);
			btnPane.add(clearBtn);	set.setBtnStyle(clearBtn);
			btnPane.add(editBtn);	set.setBtnStyle(editBtn);
			btnPane.add(delBtn);		set.setBtnStyle(delBtn);
			btnPane.add(closeBtn);	set.setBtnStyle(closeBtn);
				set.setPaneStyle(btnPane);
			editBtn.setVisible(false);delBtn.setVisible(false);
			
			JLabel dummyEast = new JLabel("  "); set.setLblStyle(dummyEast);
		add("East", dummyEast);
			
		okBtn.addActionListener(this);
		closeBtn.addActionListener(this);
		check1.addActionListener(this);
		editBtn.addActionListener(this);
		delBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		
		yyCb.addItemListener(this);
		mmCb.addItemListener(this);
		
		setComboBoxModel();
		setDefault();
		
		setBounds(300,300,450,250);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
	}
	
	public RecordInfo(String id, int recordNo, Setting set) {
		this(id, set);
		this.recordNo = recordNo; 
		setTitle("기록 수정");
		
		okBtn.setVisible(false);clearBtn.setVisible(false);
		editBtn.setVisible(true);delBtn.setVisible(true);
		
		yyCb.setVisible(false); mmCb.setVisible(false);ddCb.setVisible(false);
		yyTf.setVisible(true); mmTf.setVisible(true);ddTf.setVisible(true);
		yyTf.setEditable(false);mmTf.setEditable(false);ddTf.setEditable(false);
		
		setData(recordNo);
	}

	public void setComboBoxModel() {
		for (int i = 2019 ; i <= 2030 ; i++)
			yc.addElement(i);
		
		for (int m = 1 ; m <=12 ; m++ ) 
			mc.addElement(m);
		
		for (int h = 0 ; h <= 99 ; h++)
			hc.addElement(h);
		
		for (int mi = 0 ; mi <=59 ; mi++)
			mic.addElement(mi);
		
		for (int ss = 0 ; ss <=59 ; ss++)
			sc.addElement(ss);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
	
		if (obj == okBtn) {
			// 입력			
			createRecord();			
		} else if (obj == closeBtn) {
			dispose();
		} else if (obj == check1) {
			// 칼로리 계산
			calCal();
		} else if (obj == editBtn) {
			// 기록 수정
			updateRecord();
		} else if (obj == delBtn) {
			// 기록 삭제
			int delQ = JOptionPane.showConfirmDialog(this, "해당 기록을 삭제하시겠습니까?","",JOptionPane.YES_NO_OPTION);
			if (delQ == JOptionPane.YES_OPTION) {
				int result = recDAO.deleteRecord(recordNo);
				if (result == 1) {
					JOptionPane.showMessageDialog(this,"기록이 삭제되었습니다.");
					dispose();
				}
			}
		} else if (obj == clearBtn) {
			setTfClear();
		}
	}	
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		try {
			Object obj = e.getSource();
			if (obj == yyCb || obj == mmCb) {
				dc.removeAllElements();
				setDayComboBoxModel(yyCb, mmCb, dc);
			} 
		} catch(Exception ee) {
			ee.printStackTrace();
		}
	}
	
	public void setDayComboBoxModel(JComboBox<Integer>combo1, JComboBox<Integer> combo2, DefaultComboBoxModel<Integer> model) {
		try {
			Calendar temp = Calendar.getInstance();
			int year = combo1.getSelectedIndex()+2019;
			int month = combo2.getSelectedIndex()+1;
			temp.set(year, month,1);
			temp.add(Calendar.DAY_OF_MONTH, -1);
			
			int lastDay = temp.get(Calendar.DAY_OF_MONTH);
			for (int d = 1 ; d <= lastDay; d++) {
				model.addElement(d);
			}		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 날짜 시간 초기값 설정
	public void setDefault() {
		cal = Calendar.getInstance();
		recordNoTf.setEditable(false);
			
		yyCb.setSelectedItem(cal.get(Calendar.YEAR));
		mmCb.setSelectedIndex(cal.get(Calendar.MONTH));
		
		setDayComboBoxModel(yyCb, mmCb, dc);
		ddCb.setSelectedIndex(cal.get(Calendar.DAY_OF_MONTH)-1);
		
		hhCb.setSelectedItem(0);
		miCb.setSelectedItem(0);
		ssCb.setSelectedItem(0);
		
		type2.setSelected(true);
		
		calTf.setEditable(false);
	}
	
	
	// 칼로리 계산
	public void calCal() {
		double kg = myVO.getWeight();
		double time = Double.parseDouble(String.valueOf((miCb.getSelectedItem())))
							+Double.parseDouble(String.valueOf(hhCb.getSelectedItem()))*60
							+(Double.parseDouble(String.valueOf(ssCb.getSelectedItem()))/60);
		double met = 1;
		
		if (time == 0) {
			JOptionPane.showMessageDialog(this, "운동 시간을 입력하세요.");
		} else { 	
			if (type1.isSelected()) met = 2.9;
			else if (type2.isSelected()) met = 4.5;
			else if (type3.isSelected()) met = 7;
			
			int cal = (int) Math.round(time * (met*3.5*kg) / 200);
			
			calTf.setText(String.valueOf(cal));
		}
		// Duration (in minutes)*(MET*3.5*weight in kg)/200
		// 산책 2.9/ 조깅 4.5 달리기 7.0
	}
	
	// 기록 생성 
	public void createRecord() {
		try {
			if (calTf.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "칼로리 계산을 하셔야 합니다.");
			} else {
				getTfData();
				int result = recDAO.createRec(recVO);
				if (result == 1) {
					JOptionPane.showMessageDialog(this, "기록이 저장되었습니다..");
					setTfClear();
				}
			}
		} catch(Exception er) {
			er.printStackTrace();
			JOptionPane.showMessageDialog(this, "거리는 숫자로 입력하셔야 합니다.");
		}
	}
	
	// 기록 수정
	public void updateRecord() {
		getTfData();
		recVO.setRecordNo(recordNo);
		
		int editQ = JOptionPane.showConfirmDialog(this, "해당 기록을 수정하시겠습니까?","",JOptionPane.YES_NO_OPTION);
		if (editQ == JOptionPane.YES_OPTION) {
			int result = recDAO.updateRecord(recVO);
			if (result == 1) {
				JOptionPane.showMessageDialog(this,"기록이 수정되었습니다.");
				setData(recordNo);
			}
		}
	}
	
	public RecordVO getTfData() {
		recVO = new RecordVO();
		recVO.setDate(getDateIn());
		recVO.setTime(getTimeIn());
		recVO.setDistance(Double.parseDouble(distanceTf.getText()));
		recVO.setCal(Integer.parseInt(calTf.getText()));
		recVO.setId(id);
		
		return recVO;
	}
	
	public void setTfClear() {
		setDefault();
		distanceTf.setText("");
		calTf.setText("");
	}
	
	public void setData(int recordNo) {
		recVO = recDAO.getRecord(recordNo);
		
		recordNoTf.setText(String.valueOf(recordNo));
		
		yyCb.setSelectedItem(recVO.getDate().substring(0,4));
		yyTf.setText(recVO.getDate().substring(0,4));
		mmCb.setSelectedItem(recVO.getDate().substring(4,6));
		mmTf.setText(recVO.getDate().substring(4,6));
		ddCb.setSelectedItem(recVO.getDate().substring(6));
		ddTf.setText(recVO.getDate().substring(6));
		
		hhCb.setSelectedIndex(getNumValue(recVO.getTime().substring(0,2)));
		miCb.setSelectedIndex(getNumValue(recVO.getTime().substring(2,4)));
		ssCb.setSelectedIndex(getNumValue(recVO.getTime().substring(4)));
		
		distanceTf.setText(String.valueOf(recVO.getDistance()));
		calTf.setText(String.valueOf(recVO.getCal()));
	}
	
	public void setLbl1(JLabel lbl) {
		lblPane.add(lbl);
		lbl.setHorizontalTextPosition(JLabel.RIGHT);
		set.setLblStyle(lbl);
	}
	
	public void setTf1(JPanel pane, JTextField tf) {
		inputPane.add(pane); pane.add(tf);
		set.setPaneStyle(pane);
		tf.setFont(fnt2);
		tf.setHorizontalAlignment(JLabel.RIGHT);
	}
	
	public void setDateTf() {
		JLabel dummy1 = new JLabel("년 "); 
		JLabel dummy2 = new JLabel("월 ");
		JLabel dummy3 = new JLabel("일");
		
		tf2.add(yyCb); tf2.add(yyTf); tf2.add(dummy1);
		tf2.add(mmCb); tf2.add(mmTf); tf2.add(dummy2);
		tf2.add(ddCb); tf2.add(ddTf); tf2.add(dummy3);
		yyTf.setVisible(false);		mmTf.setVisible(false);		ddTf.setVisible(false);
		set.setLblStyle(dummy1);	set.setLblStyle(dummy2);	set.setLblStyle(dummy3);
		set.setPaneStyle(tf2);
	}
	
	public void setTimeTf() {
		JLabel dummy1 = new JLabel("시간 "); 	set.setLblStyle(dummy1);
		JLabel dummy2 = new JLabel("분 ");		set.setLblStyle(dummy2);
		JLabel dummy3 = new JLabel("초");		set.setLblStyle(dummy3);
		
		tf3.add(hhCb); tf3.add(dummy1);
		tf3.add(miCb); tf3.add(dummy2);
		tf3.add(ssCb); tf3.add(dummy3);	
		set.setPaneStyle(tf3);
	}

	public String getDateIn() {
		String year = String.valueOf(yyCb.getSelectedItem());
		String month = String.valueOf(mmCb.getSelectedItem());
		String day = String.valueOf(ddCb.getSelectedItem());
		
		if (month.length() == 1) 
			month = "0"+month;
		
		if (day.length() == 1)
			day ="0"+day;

		return year+month+day;
	}
	
	public String getTimeIn() {
		String hour = String.valueOf(hhCb.getSelectedItem());
		String minute = String.valueOf(miCb.getSelectedItem());
		String second = String.valueOf(ssCb.getSelectedItem());
		
		if (hour.length() == 1) 
			hour = "0"+hour;
		
		if (minute.length() == 1) 
			minute = "0"+minute;
		
		if (second.length() == 1)
			second ="0"+second;
		
		return hour+minute+second;
	}
	
	public int getNumValue(String sub) {
		int result = 0;
		if (sub.substring(0,1).equals("0")) {
			result = Integer.parseInt(sub.substring(1));
		} else {
			result = Integer.parseInt(sub);
		}
		return result;
	}
}
