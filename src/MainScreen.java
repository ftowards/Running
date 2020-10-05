import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

public class MainScreen extends JFrame implements ActionListener, TreeSelectionListener {
	String id ; // 로그인 된 아이디 받기
	
	JPanel northPane = new JPanel(new BorderLayout());
	
	// West 메뉴바
	JPanel westPane = new JPanel();
		DefaultMutableTreeNode tRec = new DefaultMutableTreeNode("기록");
			DefaultMutableTreeNode recCre = new DefaultMutableTreeNode("입력");
			DefaultMutableTreeNode recSer = new DefaultMutableTreeNode("검색");
			
		DefaultMutableTreeNode tMeet = new DefaultMutableTreeNode("모임");
			DefaultMutableTreeNode meetCre = new DefaultMutableTreeNode("생성");
			DefaultMutableTreeNode meetSer = new DefaultMutableTreeNode("검색");
			DefaultMutableTreeNode meetCalendar = new DefaultMutableTreeNode("달력");
			DefaultMutableTreeNode meetRank = new DefaultMutableTreeNode("순위");
			
		DefaultMutableTreeNode tMem = new DefaultMutableTreeNode("회원 정보");
			DefaultMutableTreeNode memEdit = new DefaultMutableTreeNode("정보 수정");
			DefaultMutableTreeNode memSet = new DefaultMutableTreeNode("환경 설정");

		
		DefaultMutableTreeNode menu = new DefaultMutableTreeNode("메뉴");
		JTree menuTree = new JTree(menu);
	
	// Center 데이터 표시창
	JTabbedPane display = new JTabbedPane();
		
		// Meeting Table
		String[] titleMt = {"일련번호", "일시", "장소", "주최자", "참가인원", "잔여인원", "상태"};
		DefaultTableModel dtmM = new DefaultTableModel(titleMt, 0);
		JTable mtTabel = new JTable(dtmM);
		
		//
	JSplitPane jsp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, westPane, display);
	JSplitPane jsp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, northPane, jsp2);
	
	Font fnt = new Font("맑은 고딕" , 1, 15);
	
	MSNorth inNorthPane; 
	RecordTab recordTab;
	NoticePane noticeTab;
	MeetingTab meetingTab;
	CalendarMeeting meetingCal ;
	MonthlyKing rankTab;
	
	Setting set ;
	
	public MainScreen() {}
	
	public MainScreen(String id) {
		setTitle("함께 달리기");
		this.id = id;
		this.set = new Setting(id);
		
		recordTab = new RecordTab(id, set);
		meetingTab = new MeetingTab(id, set);
		meetingCal = new CalendarMeeting(id, set);
		rankTab = new MonthlyKing(id, set);
		
		try { 
		// 상단 패널 객체 호출
		northPane.setPreferredSize(new Dimension(700,100));
		inNorthPane = new MSNorth(id, set);
		northPane.add(inNorthPane);
		
		setMenu();
		add(jsp1);
		
		// 로그인 시 알림판 출력
		noticeTab = new NoticePane(id);
		display.add("알림", noticeTab);
				
		setBounds(100,100,800,600);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		inNorthPane.logoutBtn.addActionListener(this);
		inNorthPane.exitBtn.addActionListener(this);
		inNorthPane.refreshBtn.addActionListener(this);
		menuTree.addTreeSelectionListener(this);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setMenu() {
		DefaultTreeCellRenderer cr = new DefaultTreeCellRenderer();
		
		menuTree.setCellRenderer(cr);
		
		tRec.add(recCre); tRec.add(recSer);
		tMeet.add(meetCre);  tMeet.add(meetSer);	tMeet.add(meetCalendar);	tMeet.add(meetRank);
		tMem.add(memEdit); tMem.add(memSet);
		
		menu.add(tRec); menu.add(tMeet); menu.add(tMem);
		westPane.add(menuTree);
		westPane.setPreferredSize(new Dimension(130,400));
		
		menuTree.setBackground(set.bgColor);
		menuTree.setForeground(set.fntColor);
		menuTree.setFont(fnt);
		
		set.setPaneStyle(westPane);
		
		menuTree.setCellRenderer(new MyCellRendere());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		
		if (ac.equals("로그아웃")) {
			int logoutQ = JOptionPane.showConfirmDialog(this, "로그아웃 하시겠습니까", "", JOptionPane.YES_NO_OPTION);
			if (logoutQ == JOptionPane.YES_OPTION) {
				new Login();
				dispose();
			}
		} else if (ac.equals("프로그램 종료")) {
			int exitQ = JOptionPane.showConfirmDialog(this, "프로그램을 종료하시겠습니까", "", JOptionPane.YES_NO_OPTION);
			if (exitQ == JOptionPane.YES_OPTION) {
				dispose();
				System.exit(0);
			}
		} else if (ac.equals("새로 고침")) {
			inNorthPane.setGroundLbl();			
		}
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		TreePath tp = e.getPath();
		Object obj = tp.getLastPathComponent();

		if (obj == recCre) {
			// 새 기록 생성
			new RecordInfo(id, set);
		} else if (obj == recSer) {
			addTab(recordTab, "기록 검색");
		} else if (obj == meetCre) {
			// 모임 생성
			new MeetingInfo(id, set);
		} else if (obj == meetSer) {
			// 모임 검색 			
			addTab(meetingTab, "모임 검색");
		} else if (obj == meetCalendar) {
			// 모임 달력	
			addTab(meetingCal, "모임 달력");
		} else if (obj == meetRank) {
			// 순위
			addTab(rankTab, "이달의 순위");
		} else if (obj == memEdit) {
			// 회원 정보 수정
			new MemberInfo(id, set);
		} else if (obj == memSet) {
			//환경 설정
			new SettingPane(id, set);
		}
	}
	
	public void addTab(Component obj , String title) {
		int indexTab = display.indexOfTab(title);
		if (indexTab < 0) {
			// 탭에 기록 검색 패널이 없을 경우 생성
			display.add(title, obj);
			
			// 기록 검색의 인덱스를 검색하여 활성화
			indexTab = display.indexOfTab(title);
			display.setSelectedIndex(indexTab);
		} else { 
			// 이미 생성된 기록 검색 패널이 있을 경우
			display.setSelectedIndex(indexTab);
		}
	}
	
	class MyCellRendere extends DefaultTreeCellRenderer{
		@Override
		public Color getBackgroundNonSelectionColor() {return set.bgColor;}
		@Override
		public Color getBackgroundSelectionColor() {return null;}
		@Override
		public Color getBackground() {return set.bgColor;}
		@Override
		public Color getForeground() {return set.fntColor;}
	}

	public static void main(String args[]) {
		new MainScreen("ftowards");
	}
}
