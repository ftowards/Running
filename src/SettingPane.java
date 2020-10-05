import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingPane extends JFrame implements ActionListener, ItemListener, ChangeListener {
	
	// 레이아웃
	JPanel selectPane = new JPanel(new GridLayout(1,2));
	JPanel upperPane = new JPanel(new BorderLayout());
		JPanel upperLblPane = new JPanel(new GridLayout(5,1));
			JLabel bgLbl = new JLabel("배경색 ", JLabel.RIGHT);
			JLabel dummy1= new JLabel();
			JLabel fgLbl = new JLabel("글자색 ", JLabel.RIGHT);
			JLabel dummy2= new JLabel();
			JLabel fntLbl = new JLabel(" 글자 폰트 ", JLabel.RIGHT);
		JPanel upperCbPane = new JPanel(new GridLayout(5,1));
			JPanel blank1 = new JPanel(new GridBagLayout());
				DefaultComboBoxModel<String> bgC = new DefaultComboBoxModel<String>();
				JComboBox<String> bgCb = new JComboBox<String>(bgC);
			JPanel blank2 = new JPanel();
				ColorSlider bgCs ;
	
			JPanel blank3 = new JPanel(new GridBagLayout());
				DefaultComboBoxModel<String> fgC = new DefaultComboBoxModel<String>();
				JComboBox<String> fgCb = new JComboBox<String>(fgC);
					
			JPanel blank4 = new JPanel();
				ColorSlider fgCs ;
			
			JPanel blank5= new JPanel(new GridBagLayout());
				DefaultComboBoxModel<String> fntC = new DefaultComboBoxModel<String>();
				JComboBox<String> fntCb = new JComboBox<String>(fntC);
				
	JPanel downPane = new JPanel(new BorderLayout());
	JPanel downLblPane = new JPanel(new GridLayout(5,1));
		JLabel bbgLbl = new JLabel("버튼색 ", JLabel.RIGHT);
		JLabel dummy3= new JLabel();
		JLabel bfgLbl = new JLabel("버튼 글자색 ", JLabel.RIGHT);
		JLabel dummy4= new JLabel();
		JLabel bfntLbl = new JLabel(" 버튼 폰트 ", JLabel.RIGHT);
	JPanel downCbPane = new JPanel(new GridLayout(5,1));
		JPanel blank6 = new JPanel(new GridBagLayout());
			DefaultComboBoxModel<String> bbgC = new DefaultComboBoxModel<String>();
			JComboBox<String> bbgCb = new JComboBox<String>(bbgC);
		JPanel blank7 = new JPanel();
			ColorSlider bbgCs ;

		JPanel blank8 = new JPanel(new GridBagLayout());
			DefaultComboBoxModel<String> bfgC = new DefaultComboBoxModel<String>();
			JComboBox<String> bfgCb = new JComboBox<String>(bfgC);
				
		JPanel blank9 = new JPanel();
			ColorSlider bfgCs ;
		
		JPanel blank10= new JPanel(new GridBagLayout());
			DefaultComboBoxModel<String> bfntC = new DefaultComboBoxModel<String>();
			JComboBox<String> bfntCb = new JComboBox<String>(bfntC);
			
	JPanel exPane = new JPanel(new BorderLayout());
		JPanel blank11 = new JPanel();
			JPanel examBox = new JPanel();
				JLabel examLbl = new JLabel("Test 글자" , JLabel.CENTER);
				JButton examBtn = new JButton("Test 버튼");
			JPanel btnPane = new JPanel();
				JButton applyBtn = new JButton("적용");
				JButton defaultBtn = new JButton("초기화");
				JButton closeBtn = new JButton("닫기");
				

	String id ;
	Setting set ;
	
	TreeMap<String, Color> map1 = new TreeMap<String, Color>();
	TreeMap<String, Color> map2 = new TreeMap<String, Color>();
	TreeMap<String, Color> map3 = new TreeMap<String, Color>();
	TreeMap<String, Color> map4 = new TreeMap<String, Color>();
	
	Object obj ;
	
	// 색 조합 1
	Color bg ;
	Color fg ;
	String font;
	
	// 색 조합 2
	Color bbg ;
	Color bfg ;
	String bFont;
	
	MainScreen ms ;

	public SettingPane() {}
	
	public SettingPane(String id, Setting set) {
		this.id = id;
		setTitle("환경 설정");
		this.set = new Setting(id);
		
		setLayout(new BorderLayout());
		
		add(selectPane);
		selectPane.add(upperPane);	selectPane.add(downPane);
		
		upperPane.setBorder(new CompoundBorder(new MatteBorder(5,5,5,5,set.bgColor), new LineBorder(set.fntColor, 2)));
		set.setPaneStyle(upperPane);
			upperPane.add("West", upperLblPane);
			set.setPaneStyle(upperLblPane);
				upperLblPane.add(bgLbl);		set.setLblStyle(bgLbl);
				upperLblPane.add(dummy1); set.setLblStyle(dummy1);
				upperLblPane.add(fgLbl);		set.setLblStyle(fgLbl);
				upperLblPane.add(dummy2); set.setLblStyle(dummy2);			
				upperLblPane.add(fntLbl);		set.setLblStyle(fntLbl);
		
			upperPane.add(upperCbPane);
			set.setPaneStyle(upperCbPane);
				upperCbPane.add(blank1);
					blank1.add(bgCb);
						blank1.add(bgCb);	set.setPaneStyle(blank1);
				upperCbPane.add(blank2);
					bgCs = new ColorSlider(id);
					blank2.add(bgCs);		set.setPaneStyle(blank2);
						bgCs.setVisible(false);	dummy1.setVisible(false);
						bgCs.setPreferredSize(new Dimension(130,40));

					
				upperCbPane.add(blank3);
					blank3.add(fgCb);	set.setPaneStyle(blank3);
				upperCbPane.add(blank4);
					fgCs = new ColorSlider(id);
					blank4.add(fgCs);		set.setPaneStyle(blank4);
						fgCs.setVisible(false); 	dummy2.setVisible(false);
						fgCs.setPreferredSize(new Dimension(130,40));
						
				upperCbPane.add(blank5);	
					blank5.add(fntCb);		set.setPaneStyle(blank5); 	


		downPane.setBorder(new CompoundBorder(new MatteBorder(5,5,5,5,set.bgColor), new LineBorder(set.fntColor, 2)));
		set.setPaneStyle(downPane);
			downPane.add("West", downLblPane);
			set.setPaneStyle(downLblPane);
				downLblPane.add(bbgLbl);		set.setLblStyle(bbgLbl);
				downLblPane.add(dummy3); set.setLblStyle(dummy3);
				downLblPane.add(bfgLbl);		set.setLblStyle(bfgLbl);
				downLblPane.add(dummy4); set.setLblStyle(dummy4);			
				downLblPane.add(bfntLbl);		set.setLblStyle(bfntLbl);
		
			downPane.add(downCbPane);
			set.setPaneStyle(downCbPane);
				downCbPane.add(blank6);
					blank6.add(bbgCb);
						blank6.add(bbgCb);	set.setPaneStyle(blank6);
				downCbPane.add(blank7);
					bbgCs = new ColorSlider(id);
					blank7.add(bbgCs);		set.setPaneStyle(blank7);
						bbgCs.setVisible(false);	dummy3.setVisible(false);
						bbgCs.setPreferredSize(new Dimension(130,40));

					
				downCbPane.add(blank8);
					blank8.add(bfgCb);	set.setPaneStyle(blank8);
				downCbPane.add(blank9);
					bfgCs = new ColorSlider(id);
					blank9.add(bfgCs);		set.setPaneStyle(blank9);
						bfgCs.setVisible(false); 	dummy4.setVisible(false);
						bfgCs.setPreferredSize(new Dimension(130,40));
						
				downCbPane.add(blank10);	
					blank10.add(bfntCb);		set.setPaneStyle(blank10); 	

			add("South",exPane);
			set.setPaneStyle(exPane);
				exPane.add(blank11); set.setPaneStyle(blank11);
					blank11.add(examBox);
						examBox.add(examLbl);		examBox.add(examBtn);
				exPane.add("South", btnPane);
					set.setPaneStyle(btnPane);
					btnPane.add(applyBtn);	set.setBtnStyle(applyBtn);
					btnPane.add(defaultBtn); set.setBtnStyle(defaultBtn);
					btnPane.add(closeBtn);	set.setBtnStyle(closeBtn);
			
		setCollection();
		setCbModel();
		
		setExamLbl();		setExamBtn();
		
		bgCb.addItemListener(this);
		fgCb.addItemListener(this);
		fntCb.addItemListener(this);
		bgCs.sliderR.addChangeListener(this);	bgCs.sliderG.addChangeListener(this);		bgCs.sliderB.addChangeListener(this);
		fgCs.sliderR.addChangeListener(this);		fgCs.sliderG.addChangeListener(this);			fgCs.sliderB.addChangeListener(this);	
		
		bbgCb.addItemListener(this);
		bfgCb.addItemListener(this);
		bfntCb.addItemListener(this);
		bbgCs.sliderR.addChangeListener(this);	bbgCs.sliderG.addChangeListener(this);		bbgCs.sliderB.addChangeListener(this);
		bfgCs.sliderR.addChangeListener(this);	bfgCs.sliderG.addChangeListener(this);		bfgCs.sliderB.addChangeListener(this);
		
		applyBtn.addActionListener(this);
		closeBtn.addActionListener(this);
		defaultBtn.addActionListener(this);
			
		setBounds(300,300,400,400);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void setCbModel() {
		Set<String> set1 = map1.keySet();
		Iterator<String> iter1 = set1.iterator();
		while(iter1.hasNext()) {
			bgC.addElement(iter1.next());
		}
		
		Set<String> set2 = map2.keySet();
		Iterator<String> iter2 = set2.iterator();
		while(iter2.hasNext()) {
			fgC.addElement(iter2.next());
		}
		
		String[] font = {"고딕", "굴림", "궁서","맑은 고딕","바탕" };
		
		for (int i = 0 ; i < font.length ; i++) {
			fntC.addElement(font[i]);
			bfntC.addElement(font[i]);
		}
		
		Set<String> set3 = map3.keySet();
		Iterator<String> iter3 = set3.iterator();
		while(iter3.hasNext()) {
			bbgC.addElement(iter3.next());
		}

		Set<String> set4 = map4.keySet();
		Iterator<String> iter4 = set4.iterator();
		while(iter4.hasNext()) {
			bfgC.addElement(iter4.next());
		}
	}
	
	public void setCollection() {
		map1.put("White", Color.white);		map1.put("Black", Color.BLACK);		map1.put("Red", Color.RED);
		map1.put("Green", Color.GREEN);		map1.put("Blue", Color.BLUE);		map1.put("Gray", Color.gray);
		map1.put("기타", new Color(0,0,0));
		
		map2.put("White", Color.white);		map2.put("Black", Color.BLACK);		map2.put("Red", Color.RED);
		map2.put("Green", Color.GREEN);		map2.put("Blue", Color.BLUE);		map2.put("Gray", Color.gray);
		map2.put("기타", new Color(0,0,0));

		map3.put("White", Color.white);		map3.put("Black", Color.BLACK);		map3.put("Red", Color.RED);
		map3.put("Green", Color.GREEN);		map3.put("Blue", Color.BLUE);		map3.put("Gray", Color.gray);
		map3.put("기타", new Color(0,0,0));

		map4.put("White", Color.white);		map4.put("Black", Color.BLACK);		map4.put("Red", Color.RED);
		map4.put("Green", Color.GREEN);		map4.put("Blue", Color.BLUE);		map4.put("Gray", Color.gray);
		map4.put("기타", new Color(0,0,0));
	}
	
	public void setExamLbl() {
		if (bgCb.getSelectedIndex() == 6)
			bg = bgCs.color;
		else 
			bg = map1.get(String.valueOf(bgCb.getSelectedItem()));
		
		if (fgCb.getSelectedIndex() == 6)
			fg = fgCs.color;
		else
			fg = map2.get(String.valueOf(fgCb.getSelectedItem()));
		
		font = String.valueOf(fntCb.getSelectedItem());
		
		examBox.setBorder(new CompoundBorder(new MatteBorder(3,3,3,3,bg), new LineBorder(fg,2)));
		examBox.setBackground(bg);
		examLbl.setBackground(bg);
		examLbl.setForeground(fg);	examLbl.setOpaque(true);
		examLbl.setFont(new Font(font, 1, 12));
	}
	
	public void setExamBtn() {
		if (bbgCb.getSelectedIndex() == 6)
			bbg = bbgCs.color;
		else 
			bbg = map3.get(String.valueOf(bbgCb.getSelectedItem()));
		
		if (bfgCb.getSelectedIndex() == 6)
			bfg = bfgCs.color;
		else
			bfg = map4.get(String.valueOf(bfgCb.getSelectedItem()));
		
		bFont = String.valueOf(bfntCb.getSelectedItem());
		
		examBtn.setBackground(bbg);
		examBtn.setForeground(bfg);	
		examBtn.setFont(new Font(bFont, 1, 12));
	}
	
	public SetVO getSetting() {
		SetVO vo = new SetVO();
		
		vo.setId(id);
		vo.setBg(bg);
		vo.setFg(fg);
		vo.setFont(font);
		vo.setbFont(bFont);
		
		return vo;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		obj = e.getSource();
		if (obj == bgCb) {
			if (bgCb.getSelectedIndex() == 6) {
				bgCs.setVisible(true); 
			} else {
				setExamLbl();
				bgCs.setVisible(false); 
			}
		} else if ( obj == fgCb) {
			if (fgCb.getSelectedIndex() == 6) {
				fgCs.setVisible(true);
			} else {
				setExamLbl();
				fgCs.setVisible(false);
			}
		} else if (obj == fntCb) {
			setExamLbl();
		} else if (obj == bbgCb) {
			if (bbgCb.getSelectedIndex() == 6) {
				bbgCs.setVisible(true); 
			} else {
				setExamBtn();
				bbgCs.setVisible(false);
			}
		} else if ( obj == bfgCb) {
			if (bfgCb.getSelectedIndex() == 6) {
				bfgCs.setVisible(true);
			} else {
				setExamBtn();
				bfgCs.setVisible(false);
			}
		} else if (obj == bfntCb) {
			setExamBtn();
		}		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		setExamLbl();
		setExamBtn();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if (obj == closeBtn) {
			dispose();
		} else if (obj == applyBtn) {
			int applyQ = JOptionPane.showConfirmDialog(this, "변경 사항을 적용하시겠습니까?","",JOptionPane.YES_NO_CANCEL_OPTION);
			if (applyQ == JOptionPane.NO_OPTION) {
				dispose();
			} else if ( applyQ == JOptionPane.YES_OPTION) {
				SetVO vo = getSetting();
				SetDAO dao = SetDAO.getInstance();
				int result = dao.modify(vo);
				if(result == 1) {
					JOptionPane.showMessageDialog(this, "변경 사항이 저장되었습니다.\n변경 사항을 적용하시려면 프로그램을 재시작하셔야 합니다.");
					dispose();
				}
			}
		} else if (obj == defaultBtn) {
			int applyQ = JOptionPane.showConfirmDialog(this, "환경 설정을 초기화하시겠습니까?","",JOptionPane.YES_NO_OPTION);
			if ( applyQ == JOptionPane.YES_OPTION) {
				SetDAO dao = SetDAO.getInstance();
				SetVO vo = dao.getSetting("admin");
				vo.setId(id);
				int result = dao.modify(vo);
				if(result == 1) {
					JOptionPane.showMessageDialog(this, "변경 사항이 저장되었습니다.\n변경 사항을 적용하시려면 프로그램을 재시작하셔야 합니다.");
					dispose();
				}
			}
		}
	}
}

