import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.LineBorder;

public class MonthlyKing extends JPanel{
	
	JPanel board = new JPanel(new BorderLayout());
		JPanel titlePane = new JPanel(new GridBagLayout());
			JLabel title = new JLabel("", JLabel.CENTER);
		JPanel rankingPane = new JPanel(new GridLayout(7,4));
			JPanel blank = new JPanel();
			JLabel idTitle = new JLabel("아이디", JLabel.CENTER);
			JLabel cntTitle = new JLabel("모임 횟수", JLabel.CENTER);
			JLabel memTitle = new JLabel("참가 인원", JLabel.CENTER);
			
	JPanel board2 = new JPanel(new BorderLayout());
		JPanel titlePane2 = new JPanel(new GridBagLayout());
			JLabel title2 = new JLabel("", JLabel.CENTER);
		JPanel rankingPane2 = new JPanel(new GridLayout(7,3));
			JPanel blank2 = new JPanel();
			JLabel idTitle2 = new JLabel("아이디", JLabel.CENTER);
			JLabel cntTitle2 = new JLabel("참가 횟수", JLabel.CENTER);		
	
	JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, board, board2);
			
	Setting set ;
	String id ;	
	HashMap<String, RankVO> rankList = new HashMap<String, RankVO>();
	MeetingDAO meetDAO = MeetingDAO.getInstance();
	
	public MonthlyKing() {}
	
	public MonthlyKing(String id,Setting set) {
		this.id = id;
		this.set = set;
		
		setLayout(new BorderLayout());
		add(pane);
		
		board.add("North",titlePane);
			titlePane.add(title);	title.setText("이달의 모임왕");
			set.setPaneStyle(titlePane); set.setLblStyle(title,1,20);
			titlePane.setPreferredSize(new Dimension(350,60));

		board.add(rankingPane);
			rankingPane.add(blank);	set.setPaneStyle(blank);
			setLbl(rankingPane, idTitle, 13 );			setLbl(rankingPane, cntTitle, 13 );		setLbl(rankingPane, memTitle, 13 );
			
			setBoard();

		board2.add("North",titlePane2);
			titlePane2.add(title2);	title2.setText("이달의 출석왕");
			set.setPaneStyle(titlePane2); set.setLblStyle(title2,1,20);
			titlePane2.setPreferredSize(new Dimension(240,60));

		board2.add(rankingPane2);
			rankingPane2.add(blank2);	set.setPaneStyle(blank2);
			setLbl(rankingPane2, idTitle2, 13 );			setLbl(rankingPane2, cntTitle2, 13 );
			
			setBoard2();
			
		setSize(600,520);
		setVisible(true);
	}
	
	public void setBoard() {
		rankList = meetDAO.monthlyRank(id, getDate());
		
		for (int i=1; i<=6; i++) {
			RankVO vo = rankList.get(String.valueOf(i));
			if (vo!=null) {
				JLabel rankLbl = new JLabel(vo.getRank()+"위", JLabel.CENTER);	
				JLabel idLbl = new JLabel(vo.getId(), JLabel.CENTER);
				JLabel cntLbl = new JLabel(vo.getCnt(), JLabel.CENTER);
				JLabel memLbl = new JLabel(vo.getMem(), JLabel.CENTER);
				
				setLbl(rankingPane, rankLbl, 12 );		setLbl(rankingPane, idLbl, 12 );		setLbl(rankingPane, cntLbl, 12 );			setLbl(rankingPane, memLbl, 12);
				if(i==6) {rankLbl.setText("<html>내 순위<br/>"+vo.getRank()+"위</html>");}
			} else {
				JLabel rankLbl = new JLabel("위", JLabel.CENTER);	
				JLabel idLbl = new JLabel("", JLabel.CENTER);
				JLabel cntLbl = new JLabel("", JLabel.CENTER);
				JLabel memLbl = new JLabel("", JLabel.CENTER);
				setLbl(rankingPane, rankLbl, 12 );		setLbl(rankingPane, idLbl, 12 );		setLbl(rankingPane, cntLbl, 12 );			setLbl(rankingPane, memLbl, 12);
				if(i==6) {rankLbl.setText("<html>내 순위<br/>"+"위</html>"); idLbl.setText(id);}
			}
		}
	}
	
	public void setBoard2() {
		rankList = meetDAO.monthlyRank2(id, getDate());
		
		for (int i=1; i<=6; i++) {
			RankVO vo = rankList.get(String.valueOf(i));
			if (vo!=null) {
				JLabel rankLbl = new JLabel(vo.getRank()+"위", JLabel.CENTER);	
				JLabel idLbl = new JLabel(vo.getId(), JLabel.CENTER);
				JLabel cntLbl = new JLabel(vo.getCnt(), JLabel.CENTER);
				
				setLbl(rankingPane2, rankLbl, 12 );		setLbl(rankingPane2, idLbl, 12 );		setLbl(rankingPane2, cntLbl, 12 );
				if(i==6) {rankLbl.setText("<html>내 순위<br/>"+vo.getRank()+"위</html>");}
			} else {
				JLabel rankLbl = new JLabel("위", JLabel.CENTER);	
				JLabel idLbl = new JLabel("", JLabel.CENTER);
				JLabel cntLbl = new JLabel("", JLabel.CENTER);
				setLbl(rankingPane2, rankLbl, 12 );		setLbl(rankingPane2, idLbl, 12 );		setLbl(rankingPane2, cntLbl, 12 );
				if(i==6) {rankLbl.setText("<html>내 순위<br/>"+"위</html>"); idLbl.setText(id);}
			}
		}
	}
	
	public void setLbl(JPanel pane, JLabel lbl, int fntSize) {
		JPanel blank = new JPanel(new GridBagLayout());
		pane.add(blank);	
		blank.add(lbl);
		set.setLblStyle(lbl, 1, fntSize);
		set.setPaneStyle(blank);
		blank.setBorder(new LineBorder(set.fntColor,1));
	}
	
	public String getDate() {
		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		
		String yy = String.valueOf(year);
		String mm = month < 10 ? "0"+month : String.valueOf(month); 
		
		return yy+mm;
	}
}
