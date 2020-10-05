import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Setting {
		
	// 라벨 속성
	Color bgColor ;
	Color fntColor ;
	Color btnColor;
	Color btnFntColor;
	String setFont ;
	
	Font fnt ;
	
	SetVO vo = new SetVO();
	SetDAO dao = SetDAO.getInstance();

	public Setting() {}
	
	public Setting(String id) {
		vo = dao.getSetting(id);
	
		bgColor = vo.getBg();
		fntColor = vo.getFg();
		btnColor = vo.getBbg();
		btnFntColor = vo.getBfg();
		setFont = vo.getFont();
		fnt = new Font(setFont, 1, 12);
		
	}
	
	public void setLblStyle(JLabel lbl) {
		lbl.setBackground(bgColor);
		lbl.setForeground(fntColor);
		lbl.setOpaque(true);
		lbl.setFont(fnt);
	}
	
	public void setLblStyle(JLabel lbl, int type, int fontSize) {
		setLblStyle(lbl);
		fnt = new Font(setFont, type, fontSize);
		lbl.setFont(fnt);
	}
	
	public void setPaneStyle(JPanel pane) {
		pane.setBackground(bgColor);
		pane.setForeground(fntColor);
		pane.setOpaque(true);
	}
	
	public void setRbStyle(JRadioButton rb) {
		rb.setBackground(bgColor);
		rb.setForeground(fntColor);
		rb.setOpaque(true);
		rb.setFont(fnt);
	}
	
	public void setRbStyle(JRadioButton rb, int type, int fontSize) {
		setRbStyle(rb);
		fnt = new Font(setFont, type, fontSize);
		rb.setFont(fnt);
	}
	
	public void setBtnStyle(JButton btn) {
		btn.setBackground(bgColor);
		btn.setForeground(fntColor);
		btn.setOpaque(true);
		btn.setFont(fnt);
	}
	
	public void setBtnStyle(JButton btn, int type, int fontSize) {
		setBtnStyle(btn);
		fnt = new Font(setFont, type, fontSize);
		btn.setFont(fnt);
	}
}
