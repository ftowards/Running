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
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class RecordTab extends JPanel implements ActionListener, ItemListener{
	String id ;

	// 상단 검색 패널
	JPanel searchBorder = new JPanel(new BorderLayout());
	JPanel searchPane = new JPanel(new GridLayout(3,1));
		JPanel datePane = new JPanel();
		
		JPanel datePane2 = new JPanel();
			DefaultComboBoxModel<Integer> yc = new DefaultComboBoxModel<Integer>();
			DefaultComboBoxModel<Integer> mc = new DefaultComboBoxModel<Integer>();

			DefaultComboBoxModel<Integer> yc2 = new DefaultComboBoxModel<Integer>();
			DefaultComboBoxModel<Integer> mc2 = new DefaultComboBoxModel<Integer>();

			DefaultComboBoxModel<Integer> dc1 = new DefaultComboBoxModel<Integer>();
			DefaultComboBoxModel<Integer> dc2 = new DefaultComboBoxModel<Integer>();
			
			// 검색 시작일
			JComboBox<Integer> yy1 = new JComboBox<Integer>(yc);
				JLabel year = new JLabel("년");
			JComboBox<Integer> mm1 = new JComboBox<Integer>(mc);
				JLabel month = new JLabel("월");
			JComboBox<Integer> dd1 = new JComboBox<Integer>(dc1);
				JLabel day = new JLabel("일");
				JLabel from = new JLabel("부터");
			
			// 검색 종료일
			JComboBox<Integer> yy2 = new JComboBox<Integer>(yc2);
				JLabel year2 = new JLabel("년");
			JComboBox<Integer> mm2 = new JComboBox<Integer>(mc2);
				JLabel month2 = new JLabel("월");
			JComboBox<Integer> dd2 = new JComboBox<Integer>(dc2);
				JLabel day2 = new JLabel("일");
				JLabel to = new JLabel("까지");
		
		JPanel btnPane1 = new JPanel(new BorderLayout());
			JButton searchBtn = new JButton("검색");
			
		JPanel btnPane2 = new JPanel();
			JLabel start = new JLabel("시작일부터");
			JButton search3Month = new JButton("3개월");
			JButton searchMonth = new JButton("1개월");
			JButton searchWeek = new JButton("1주일");
	
	
	// Record Table
	JTabbedPane test = new JTabbedPane();
	Object[][] emptySet ;
	String[] titleRec = {"No.", "일자", "소요시간", "거리 (km)", "칼로리 (kcal)"};
	DefaultTableModel dtm = new DefaultTableModel(emptySet, titleRec);
	JTable recordTable = new JTable(dtm);
	JScrollPane sp = new JScrollPane(recordTable);

	List<RecordVO> recordList = new ArrayList<RecordVO>();
	RecordDAO recDAO = RecordDAO.getInstance();
	Calendar cal = Calendar.getInstance();
	
	Setting set ;
	
	public RecordTab() {}
	
	public RecordTab(String id, Setting set) {
		this.id = id;
		this.set= set;
		
		setLayout(new BorderLayout());
		
		searchPane.add(datePane);
		datePane.add(yy1); datePane.add(year);
		datePane.add(mm1); datePane.add(month);
		datePane.add(dd1); datePane.add(day); datePane.add(from);
			set.setPaneStyle(datePane); 
			set.setLblStyle(year); set.setLblStyle(month); set.setLblStyle(day); set.setLblStyle(from);
		
		comboBoxSet(); 	dayComboSet(yy1, mm1, dc1);
		
		searchPane.add(datePane2);
		datePane2.add(yy2); datePane2.add(year2);
		datePane2.add(mm2); datePane2.add(month2);
		datePane2.add(dd2); datePane2.add(day2); datePane2.add(to);
			set.setPaneStyle(datePane2); 
			set.setLblStyle(year2); set.setLblStyle(month2); set.setLblStyle(day2); set.setLblStyle(to);
	
		dayComboSet(yy2, mm2, dc2);
		
		searchPane.add(btnPane2);
			btnPane2.add(start);
			btnPane2.add(searchWeek);
			btnPane2.add(searchMonth);
			btnPane2.add(search3Month);
			set.setPaneStyle(btnPane2); 
			set.setLblStyle(start); 
			set.setBtnStyle(searchWeek); set.setBtnStyle(searchMonth); set.setBtnStyle(search3Month);
			
		searchBorder.add(searchPane);
		
		searchBorder.add("East", btnPane1);
			btnPane1.add(searchBtn);
			set.setBtnStyle(searchBtn);
			JLabel marginN = new JLabel(" "); set.setLblStyle(marginN);
			btnPane1.add("North", marginN);
			JLabel marginS = new JLabel(" "); set.setLblStyle(marginS);
			btnPane1.add("South", marginS);
			JLabel marginW = new JLabel(" "); set.setLblStyle(marginW);
			btnPane1.add("West", marginW);
			JLabel marginE = new JLabel(" "); set.setLblStyle(marginE);
			btnPane1.add("East", marginE);
		
		add("North", searchBorder);
		add(sp);				
		sp.setPreferredSize(new Dimension(550,280));
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(JLabel.CENTER);
		DefaultTableCellRenderer rightAli = new DefaultTableCellRenderer();
		rightAli.setHorizontalAlignment(JLabel.RIGHT);
		recordTable.getColumnModel().getColumn(0).setPreferredWidth(80);
		recordTable.getColumnModel().getColumn(0).setCellRenderer(center);
		recordTable.getColumnModel().getColumn(1).setPreferredWidth(160);
		recordTable.getColumnModel().getColumn(1).setCellRenderer(center);
		recordTable.getColumnModel().getColumn(2).setPreferredWidth(120);
		recordTable.getColumnModel().getColumn(2).setCellRenderer(center);
		recordTable.getColumnModel().getColumn(3).setPreferredWidth(80);
		recordTable.getColumnModel().getColumn(3).setCellRenderer(rightAli);
		recordTable.getColumnModel().getColumn(4).setPreferredWidth(80);
		recordTable.getColumnModel().getColumn(4).setCellRenderer(rightAli);
		
		String month =  String.valueOf(cal.get(Calendar.MONTH)+1).length() == 1 ? "0"+String.valueOf(cal.get(Calendar.MONTH)+1) :String.valueOf(cal.get(Calendar.MONTH)+1);
		showRecord(recDAO.showAllRecord(id, month));
		searchBorder.setBorder(new CompoundBorder(new MatteBorder(5, 5, 5, 5, set.bgColor), new LineBorder(set.fntColor, 2)));
		setVisible(true);

		yy1.addItemListener(this);
		mm1.addItemListener(this);
		yy2.addItemListener(this);
		mm2.addItemListener(this);
		searchWeek.addActionListener(this);
		searchMonth.addActionListener(this);
		search3Month.addActionListener(this);
		searchBtn.addActionListener(this);
		
		recordTable.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) {
				int mouse = me.getButton();
				int row = recordTable.getSelectedRow();
				
				if(mouse == MouseEvent.BUTTON1) {
					int recordNo = (int) dtm.getValueAt(row, 0);
					new RecordInfo(id, recordNo, set);
				}
			}
		});
	}
	
	public void showRecord(List<RecordVO> recordList) {
		dtm.setRowCount(0);
		RecordVO vo ;
		
		for (int i = 0 ; i < recordList.size() ; i ++ ) {
			vo = recordList.get(i);
			String date = vo.getDate().substring(0,4) +"-"+vo.getDate().substring(4,6) +"-"+vo.getDate().substring(6);
			String time = vo.getTime().substring(0,2) +":"+vo.getTime().substring(2,4) +":"+vo.getTime().substring(4);
			
			Object[] element = { vo.getRecordNo(), date, time, vo.getDistance(), vo.getCal()};
			dtm.addRow(element);		
		}
	}
	
	public void comboBoxSet() {
		for (int y = 2020 ; y < 2030 ; y++) {
			yc.addElement(y); yc2.addElement(y);
		}
		
		for (int m = 1 ; m <= 12 ; m++) {
			mc.addElement(m); mc2.addElement(m);
		}
		
		yc.setSelectedItem(cal.get(Calendar.YEAR));
		mc.setSelectedItem(cal.get(Calendar.MONTH)+1);
		yc2.setSelectedItem(cal.get(Calendar.YEAR));
		mc2.setSelectedItem(cal.get(Calendar.MONTH)+1);
	}
	
	public void dayComboSet(JComboBox<Integer> combo1, JComboBox<Integer> combo2, DefaultComboBoxModel<Integer> model) {
		cal.set(Integer.parseInt(String.valueOf(combo1.getSelectedItem())),(Integer.parseInt(String.valueOf(combo2.getSelectedItem()))),1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		
		int lastDay = cal.get(Calendar.DAY_OF_MONTH);
		model.removeAllElements();
		for (int d = 1 ; d <= lastDay; d++) {
			model.addElement(d);
		}		
		
	}
	
	public void getStartDate(Calendar temp) {
		temp.set(Calendar.YEAR, Integer.parseInt(String.valueOf(yy1.getSelectedItem())));
		temp.set(Calendar.MONTH, (Integer.parseInt(String.valueOf(mm1.getSelectedItem())))-1);
		temp.set(Calendar.DAY_OF_MONTH, Integer.parseInt(String.valueOf(dd1.getSelectedItem())));
	}
	
	public void setEndDate(Calendar temp) {
		yy2.setSelectedItem(temp.get(Calendar.YEAR));
		mm2.setSelectedItem(temp.get(Calendar.MONTH)+1);
		dayComboSet(yy2,mm2,dc2);
		dd2.setSelectedItem(temp.get(Calendar.DAY_OF_MONTH));
	}
	
	// DB와 통신할 때 사용할 날짜 구하기
	public String getDate(JComboBox<Integer> yy,JComboBox<Integer> mm,JComboBox<Integer> dd) {
		String year = String.valueOf(yy.getSelectedItem());
		String month = (String.valueOf(mm.getSelectedItem()).length() == 1 ? "0"+String.valueOf(mm.getSelectedItem()) : String.valueOf(mm.getSelectedItem()));  
		String day = (String.valueOf(dd.getSelectedItem()).length() == 1 ? "0"+String.valueOf(dd.getSelectedItem()) : String.valueOf(dd.getSelectedItem()));
		String date = year+month+day;
		return date;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		Calendar temp = Calendar.getInstance();
		if (obj == searchWeek) { // 일주일 후 버튼일 때
			getStartDate(temp);
			temp.add(Calendar.DAY_OF_MONTH, 7);
			setEndDate(temp);
		} else if (obj == searchMonth) { // 1개월
			getStartDate(temp);
			temp.add(Calendar.MONTH, 1);
			setEndDate(temp);
		} else if (obj == search3Month) { // 3개월
			getStartDate(temp);
			temp.add(Calendar.MONTH, 3);
			setEndDate(temp);
		} else if (obj == searchBtn) { // 검색 버튼 액션
			// 검색 범위 설정하기
			
			String startDate = getDate(yy1,mm1, dd1);
			String endDate = getDate(yy2,mm2, dd2);
			
			// dao 보내기
			// 테이블에 데이터 세팅하기
			showRecord(recDAO.showSearchedRecord(id, startDate, endDate));
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object obj = e.getSource();
		
		if (obj == yy1 || obj == mm1) {
			dayComboSet(yy1, mm1, dc1);
		} else if (obj == yy2 || obj == mm2) {
			dayComboSet(yy1, mm1, dc1);
		}
	}
}
