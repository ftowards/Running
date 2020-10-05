import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class NoticePane extends JPanel {
	
	String id ;
	
	String[] title = {"알림", "등록일"};
	DefaultTableModel dtm = new DefaultTableModel(title, 0);
	JTable noticeTable = new JTable(dtm);
	JScrollPane sp = new JScrollPane(noticeTable);
	
	List<NoticeVO> notice = new ArrayList<NoticeVO>();
	NoticeDAO nDAO = new NoticeDAO();
	
	public NoticePane() {}
	
	public NoticePane(String id) {
		this.id = id;

		notice = nDAO.getAllNotice(id);
		
		for (int i = 0 ; i < notice.size(); i++) {
			NoticeVO nVO = notice.get(i);
			Object[] data = {nVO.getMsg(),nVO.getInputDate()};
			dtm.addRow(data);
		}
		
		noticeTable.getColumnModel().getColumn(0).setPreferredWidth(350);
		
		setLayout(new BorderLayout());
		add(sp);
		
		sp.setPreferredSize(new Dimension(500,100));
		setVisible(true);
	}
}
