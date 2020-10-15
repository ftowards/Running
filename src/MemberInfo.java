

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.Document;

public class MemberInfo extends JFrame implements ActionListener {
	
	// ���� 
	// ���̵� / ��й�ȣ *2 / �̸� / ����ó / ����cm / ü��kg
	
	JPanel center = new JPanel(new BorderLayout());
		JPanel inputPane = new JPanel(new GridLayout(7,2));
			JLabel idLbl = new JLabel("���̵�* (10�� �̳�)", JLabel.CENTER);
			JPanel tf1 = new JPanel();
				JTextField idTf = new JTextField(8);
			JLabel pwLbl = new JLabel("��й�ȣ* (10�� �̳�)", JLabel.CENTER);
			JPanel tf2 = new JPanel();
				JPasswordField pwTf = new JPasswordField(8);
			JLabel pwReLbl = new JLabel("��й�ȣ Ȯ��*", JLabel.CENTER);
			JPanel tf3 = new JPanel();
				JPasswordField pwReTf = new JPasswordField(8);	
			JLabel nameLbl = new JLabel("�̸�* (5�� �̳�)", JLabel.CENTER);
			JPanel tf4 = new JPanel();
				JTextField nameTf = new JTextField(8);
			JLabel telLbl = new JLabel("����ó*", JLabel.CENTER);
			JPanel tf5 = new JPanel();
				JTextField tel1Tf = new JTextField(3);
				JTextField tel2Tf = new JTextField(3);
				JTextField tel3Tf = new JTextField(3);
				JLabel dummy1 = new JLabel("-"); 		
				JLabel dummy2 = new JLabel("-");
			JLabel heightLbl = new JLabel("����(cm)", JLabel.CENTER);
			JPanel tf6 = new JPanel();
				JTextField heightTf = new JTextField("",5);
			JLabel weightLbl = new JLabel("ü��(kg)", JLabel.CENTER);
			JPanel tf7 = new JPanel();
				JTextField weightTf = new JTextField("",5);
		JLabel caution = new JLabel("       * �ʼ��׸��Դϴ�.");
		
	JPanel east = new JPanel(new GridLayout(7,1));
		JPanel blank1 = new JPanel();
			JButton check1 = new JButton("�ߺ�Ȯ��");
			JButton changeId = new JButton("����");
		JPanel blank2 = new JPanel();
		JPanel blank3 = new JPanel();
		JPanel blank4 = new JPanel();
		JPanel blank5 = new JPanel();
			JButton check2 = new JButton("�ߺ�Ȯ��");
			JButton changeTel = new JButton("����ó ����");
		JPanel blank6 = new JPanel();
		JPanel blank7 = new JPanel();
			
	// �ϴ� ��� ��ư
	JPanel btnPane = new JPanel();
		JButton okBtn = new JButton("Ȯ��");
		JButton closeBtn = new JButton("�ݱ�");
		JButton editBtn = new JButton("����");
		JButton delBtn = new JButton("Ż��");
		JButton clearBtn = new JButton("�ʱ�ȭ");
		
	Font fnt = new Font("���� ���", 1, 12);
	Font fnt2 = new Font("���� ���", 0, 12);
	Font fnt3 = new Font("���� ���", 1, 10);
	
	// �ߺ� üũ	
	Boolean checkPoint1 = false; // ���̵� �ߺ� üũ
	Boolean checkPoint2 = false; // ����ó �ߺ� üũ
	
	Boolean mode = false;//
	
	// ����
	List<MemberVO> memberList = new ArrayList<MemberVO>();
	MemberDAO mDAO = MemberDAO.getInstance();
	
	String id ; // ���� �г� ���� �� ���
	String telRegistered;
	Setting set;
	SetVO setVO ;

	public MemberInfo() {
		super("ȸ�� ����");
		
		set = new Setting("admin");
		setStyle();
			
		okBtn.addActionListener(this);
		closeBtn.addActionListener(this);
		check1.addActionListener(this);
		check2.addActionListener(this);
		clearBtn.addActionListener(this);
		editBtn.addActionListener(this);
		delBtn.addActionListener(this);
		changeTel.addActionListener(this);
		changeId.addActionListener(this);
		
		setBounds(300,300,400,300);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
	}
	
	public MemberInfo(String id, Setting set) {
		this();
		this.id = id;
		this.set = new Setting(id);
		setStyle();
		
		setTitle("ȸ�� ���� ����");
		idLbl.setText("���̵�");
		idTf.setEditable(false);
		check1.setVisible(false); checkPoint1 = true;
		setTelConfirm();
		
		nameLbl.setText("�̸�");
		nameTf.setEditable(false);
				
		okBtn.setVisible(false);	clearBtn.setVisible(false);
		editBtn.setVisible(true); 	delBtn.setVisible(true);;

		setData(id);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		
		char[] empty = {} ;
		if (obj == okBtn) {
			if (checkInfo()) {
				insertNewMember();
			}			
		} else if (obj == closeBtn) {
			dispose();
		} else if (obj == check1) {
			// ���̵� �ߺ� üũ
			if(checkPoint1 == false) {
				checkPoint1 = checkIdUsed();
			}
		} else if (obj == check2) {
			// ����ó �ߺ� üũ
			if(checkPoint2 == false) {
				if(mode == false) {
					checkPoint2 = checkTelUsed();
				} else {
					checkPoint2 = checkTelUsed(telRegistered);
				}
			}
		} else if (obj == delBtn) {
			deleteMember(id);
		} else if (obj == clearBtn) {
			setTfClear();
		} else if (obj == editBtn) {
			// ȸ�� ���� ����
			updateMember();
		} else if(obj == changeTel) {
			//����ó ����
			changeTelInfo();
		} else if(obj == changeId) {
			// ���̵� ����
			changeIdInfo();
		}
	}
	
	// ȸ�� ���� ���� üũ
	public boolean checkInfo() {
		if (!checkPoint1) {
			JOptionPane.showMessageDialog(this, "���̵� �ߺ� üũ�� �Ͻʽÿ�.");
		} else if (!checkPoint2) {
			JOptionPane.showMessageDialog(this, "����ó �ߺ� üũ�� �Ͻʽÿ�.");
		} else if (nameTf.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "�̸��� �Է� �Ͻʽÿ�.");
		} else if (getPw(pwTf.getPassword()).equals("") || getPw(pwReTf.getPassword()).equals("")) {
			JOptionPane.showMessageDialog(this, "��й�ȣ�� �Է� �Ͻʽÿ�.");
		} else if (!(getPw(pwTf.getPassword()).equals(getPw(pwReTf.getPassword())))) {				
			JOptionPane.showMessageDialog(this, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
		} else {
			return true;
		}
		return false;
	}
	
	
	// ���̵� ����
	public void insertNewMember() {
		MemberVO vo = getTfData();
		
		if (mDAO.insertRecord(vo) == 1) {
			JOptionPane.showMessageDialog(this, "ȸ�� ������ �Ϸ�Ǿ����ϴ�.");
			NoticeDAO noDAO = NoticeDAO.getInstance();
			noDAO.newNotice(vo.getId(), vo.getId() + "�� ������ �����մϴ�.");
			
			SetDAO setDAO = SetDAO.getInstance();
			setVO = setInitialSetting();
			
			setDAO.insert(setVO);
			setTfClear();
		} else {
			JOptionPane.showMessageDialog(this, "ȸ�� ���Կ� �����Ͽ����ϴ�.");
		}
	}
	
	public void updateMember() {
		MemberVO vo = getTfData();
		
		if ((getTelIn().equals(telRegistered))) {
			if (getPw(pwTf.getPassword()).equals("") && getPw(pwReTf.getPassword()).equals("")) {
				// ��й�ȣ ���� ȸ������ ����
				int result = mDAO.updateMem(vo);
				System.out.println("1");
				if (result == 1) {
					JOptionPane.showMessageDialog(this,"ȸ�� ������ �����Ǿ����ϴ�.");
				}
			} else if (!(getPw(pwTf.getPassword()).equals(getPw(pwReTf.getPassword())))) {				
				JOptionPane.showMessageDialog(this, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			} else {
				// ��й�ȣ �����ؼ� ����
				int result = mDAO.updateMem(vo);
				if (result == 1) {
					result = mDAO.updatePw(vo);
					if (result == 1) {
						JOptionPane.showMessageDialog(this,"ȸ�� ������ �����Ǿ����ϴ�.");	
					}
				}
			}
		} else {
			if (checkPoint2 == false) {
				JOptionPane.showMessageDialog(this,"����ó �ߺ� üũ�� �Ͻʽÿ�.");
			} else {
				if (getPw(pwTf.getPassword()).equals("") && getPw(pwReTf.getPassword()).equals("")) {
				// ��й�ȣ ���� ȸ������ ����
					int result = mDAO.updateMem(vo);
						if (result == 1) {
							JOptionPane.showMessageDialog(this,"ȸ�� ������ �����Ǿ����ϴ�.");
						}
				} else if (!(getPw(pwTf.getPassword()).equals(getPw(pwReTf.getPassword())))) {				
					JOptionPane.showMessageDialog(this, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.");				
				} else {
					// ��й�ȣ �����ؼ� ����
					int result = mDAO.updateMem(vo);
					if (result == 1) {
						result = mDAO.updatePw(vo);
						if (result == 1) {
							JOptionPane.showMessageDialog(this,"ȸ�� ������ �����Ǿ����ϴ�.");	
						}
					}
				}
			}
		}
	}
	
	
	// ���̵� ����
	public void deleteMember(String id) {
		int delQ = JOptionPane.showConfirmDialog(this, "ȸ������ Ż���Ͻðڽ��ϱ�?", "", JOptionPane.YES_NO_OPTION);
		if ( delQ == JOptionPane.YES_OPTION) {
			int result = mDAO.dropMember(id);
			if (result == 1) {
				JOptionPane.showMessageDialog(this, "ȸ�� Ż�� �Ϸ�Ǿ����ϴ�.\n�����մϴ�.");
				dispose();
				System.exit(0);
			}
		}
	}
	
	// �ԷµǾ� �ִ� ���� ����
	public MemberVO getTfData() {
		MemberVO vo = new MemberVO();
		vo.setId(idTf.getText());
		vo.setPassword(getPw(pwTf.getPassword()));
		vo.setUserName(nameTf.getText());
		vo.setTel(getTelIn());
		vo.setHeight(Double.parseDouble(heightTf.getText()));
		vo.setWeight(Double.parseDouble(weightTf.getText()));
		
		return vo;
	}
	
	// ������ DB �� ����� ������ TF �� ����
	public void setData(String id) {
		MemberVO vo = mDAO.getMemberInfo(id);
		this.telRegistered = vo.getTel();
		
		idTf.setText(id);
		nameTf.setText(vo.getUserName());
		tel1Tf.setText(vo.getTel().substring(0,3));
		tel2Tf.setText(vo.getTel().substring(4,8));
		tel3Tf.setText(vo.getTel().substring(9));
		heightTf.setText(String.valueOf(vo.getHeight()));		
		weightTf.setText(String.valueOf(vo.getWeight()));
	}
	
	// �Է� �г� �ʱ�ȭ
	public void setTfClear() {
		checkPoint1 = false;
		checkPoint2 = false;
		
		idTf.setText("");
		pwTf.setText("");
		pwReTf.setText("");
		nameTf.setText("");
		tel1Tf.setText(""); tel2Tf.setText(""); tel3Tf.setText("");
		heightTf.setText("");
		weightTf.setText("");
	}
	
	// ���̵� �ߺ� üũ
	public boolean checkIdUsed() {
		if (idTf.getText().equals("")) {
			JOptionPane.showMessageDialog(this,"����� ���̵� �Է��Ͻʽÿ�");
			return false;
		} else { 
			memberList = mDAO.getAllRecord();
			for (int i = 0 ; i < memberList.size(); i++) {
				MemberVO vo = memberList.get(i);
				if(vo.getId().equals(idTf.getText())) {
					JOptionPane.showMessageDialog(this, "��� ���� ID �Դϴ�.");
					return false;				
				}
			}
			JOptionPane.showMessageDialog(this, "��� ������ ID �Դϴ�.");
			setIdConfirm();
			return true;
		}
	}
	
	// ����ó �ߺ� üũ
	public boolean checkTelUsed() {
		if (checkPoint2 == false) {
			if (tel1Tf.getText().equals("") || tel2Tf.getText().equals("") || tel3Tf.getText().equals("")) {
				JOptionPane.showMessageDialog(this,"����ó�� �Է��Ͻʽÿ�");
				return false;
			} else { 				
				memberList = mDAO.getAllRecord();
				for (int i = 0 ; i < memberList.size(); i++) {
					MemberVO vo = memberList.get(i);
					if(vo.getTel().equals(getTelIn())) {
						JOptionPane.showMessageDialog(this, "�̹� ��ϵ� ����ó �Դϴ�.");
						return false;				
					}
				}
			}
		}
		JOptionPane.showMessageDialog(this, "��� ������ ����ó �Դϴ�.");
		setTelConfirm();
		return true;
	}
	
	
	// �ߺ� Ȯ�� ��
	// 1. ���� ����ó�� ������, ������ ����Ͻðڽ��ϱ�? �޼��� ��� ��
	// YES >> ���� ��ȣ�� ���� check2 true;
	
	// 2. ����ó �ߺ� üũ 
	public boolean checkTelUsed(String originalTel) {
		if (checkPoint2 == false) {
			if (tel1Tf.getText().equals("") || tel2Tf.getText().equals("") || tel3Tf.getText().equals("")) {
				JOptionPane.showMessageDialog(this,"����ó�� �Է��Ͻʽÿ�");
				return false;
			} else if(getTelIn().equals(telRegistered)) {
				int q = JOptionPane.showConfirmDialog(this, "���� ��ϵ� ����ó�� �����ϴ�.\n����ó�� �����Ͻðڽ��ϱ�?","", JOptionPane.YES_NO_OPTION);
				if(q==JOptionPane.YES_OPTION) {
					mode = false;
					setTelConfirm();
					return true;
				} else {
					return false;
				}
			} else { 				
				memberList = mDAO.getAllRecord();
				for (int i = 0 ; i < memberList.size(); i++) {
					MemberVO vo = memberList.get(i);
					if(vo.getTel().equals(getTelIn())) {
						JOptionPane.showMessageDialog(this, "�̹� ��ϵ� ����ó �Դϴ�.");
						return false;				
					} else {
						JOptionPane.showMessageDialog(this, "��� ������ ����ó �Դϴ�.");
						setTelConfirm();
						return true;
					}
				}
			}
		}
		return true;
	}
	
	// ����ó ����
	public void changeTelInfo() {
		// ���� ��ư ���� �� �ߺ� Ȯ�� ��ư ������
		changeTel.setVisible(false);
		check2.setVisible(true);
		
		// tel TF ���� / ���� ����ó ���� ����
		tel1Tf.setEditable(true);		tel2Tf.setEditable(true);		tel3Tf.setEditable(true);
		tel1Tf.setText("");		tel2Tf.setText("");		tel3Tf.setText("");
		
		mode = true;
		checkPoint2 = false;
	}
	
	public void changeIdInfo() {
		// �ߺ� üũ �Ŀ� �ٽ� ����
		changeId.setVisible(false);
		check1.setVisible(true);
		
		idTf.setEditable(true);
		idTf.setText("");
		
		checkPoint1 = false;
	}
	
	public void setTelConfirm() {
		check2.setVisible(false); changeTel.setVisible(true);
		tel1Tf.setEditable(false);		tel2Tf.setEditable(false);		tel3Tf.setEditable(false);
	}
	
	public void setIdConfirm() {
		check1.setVisible(false); changeId.setVisible(true);
		idTf.setEditable(false);
	}
	
	public String getTelIn() {
		return tel1Tf.getText()+"-"+tel2Tf.getText() + "-"+tel3Tf.getText();
	}
	
	public void setLbl1(JLabel lbl) {
		inputPane.add(lbl);
		lbl.setFont(fnt);
		lbl.setVerticalAlignment(JLabel.CENTER);
		set.setLblStyle(lbl);
	}
	
	public void setTf1(JPanel pane, JTextField tf) {
		inputPane.add(pane); pane.add(tf);
		tf.setFont(fnt2);
		set.setPaneStyle(pane);
	}
	
	public void setTelTf() {
		tel1Tf.setDocument(new LimitDocument(3));
		tel2Tf.setDocument(new LimitDocument(4));
		tel3Tf.setDocument(new LimitDocument(4));
		
		set.setLblStyle(dummy1);	set.setLblStyle(dummy2);
		tf5.add(tel1Tf); tf5.add(dummy1);	
		tf5.add(tel2Tf); tf5.add(dummy2);
		tf5.add(tel3Tf);	set.setPaneStyle(tf5);
	}
	
	public void setPwf1(JPanel pane, JPasswordField tf) {
		inputPane.add(pane); pane.add(tf);
		tf.setFont(fnt2);
		tf.setAlignmentX(LEFT_ALIGNMENT);
		set.setPaneStyle(pane);
	}
	
	public String getPw(char[] password) {
		String pw ="" ; 
		for (int i = 0 ; i < password.length ; i++) {
			pw += password[i];
		}
		return pw;
	}
	
	public SetVO setInitialSetting() {
		SetVO vo = new SetVO();
			
		vo.setBg(Color.DARK_GRAY);
		vo.setFg(Color.white);
		vo.setBbg(Color.DARK_GRAY);
		vo.setBfg(Color.white);
		vo.setFont("���� ���");
		vo.setbFont("���� ���");
		vo.setId(idTf.getText());
		
		return vo;
	}
	
	public void setStyle() {
		setLayout(new BorderLayout());
		add("Center", center);
			center.add("Center", inputPane);
				set.setPaneStyle(inputPane);
				setLbl1(idLbl); 		setTf1(tf1, idTf);
				idTf.setDocument(new LimitDocument(10));
				setLbl1(pwLbl);		setPwf1(tf2, pwTf);
				pwTf.setDocument(new LimitDocument(10));
				setLbl1(pwReLbl);	setPwf1(tf3, pwReTf);
				pwReTf.setDocument(new LimitDocument(10));
				setLbl1(nameLbl);	setTf1(tf4, nameTf);
				nameTf.setDocument(new LimitDocument(5));
				setLbl1(telLbl);		
				inputPane.add(tf5); setTelTf();
				setLbl1(heightLbl);	setTf1(tf6, heightTf);
				setLbl1(weightLbl);	setTf1(tf7, weightTf);

			center.add("East", east);
				east.add(blank1); 	blank1.add(check1);		blank1.add(changeId);
				east.add(blank2);	east.add(blank3);		east.add(blank4);
				east.add(blank5);	blank5.add(check2); 	blank5.add(changeTel);		
				
				east.add(blank6);
				east.add(blank7);
				
				set.setPaneStyle(blank1);	set.setPaneStyle(blank2);	set.setPaneStyle(blank3);	set.setPaneStyle(blank4);
				set.setPaneStyle(blank5);	set.setPaneStyle(blank6);	set.setPaneStyle(blank7);	set.setPaneStyle(east);
				
				set.setBtnStyle(check1,1,10);
				set.setBtnStyle(changeId,1,10); changeId.setVisible(false);
				set.setBtnStyle(check2,1,10);
				set.setBtnStyle(changeTel,1,9); changeTel.setVisible(false);
				
			center.add("South", caution);
				set.setLblStyle(caution);
				caution.setForeground(Color.RED);
				
		add("South", btnPane);
			btnPane.add(okBtn);			set.setBtnStyle(okBtn);
			btnPane.add(clearBtn);		set.setBtnStyle(clearBtn);
			btnPane.add(editBtn);		set.setBtnStyle(editBtn);
			btnPane.add(delBtn);			set.setBtnStyle(delBtn);
			btnPane.add(closeBtn);		set.setBtnStyle(closeBtn);
			
			editBtn.setVisible(false);		delBtn.setVisible(false);

				set.setPaneStyle(btnPane);
	}
}