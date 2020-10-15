

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
	
	// 센터 
	// 아이디 / 비밀번호 *2 / 이름 / 연락처 / 신장cm / 체중kg
	
	JPanel center = new JPanel(new BorderLayout());
		JPanel inputPane = new JPanel(new GridLayout(7,2));
			JLabel idLbl = new JLabel("아이디* (10자 이내)", JLabel.CENTER);
			JPanel tf1 = new JPanel();
				JTextField idTf = new JTextField(8);
			JLabel pwLbl = new JLabel("비밀번호* (10자 이내)", JLabel.CENTER);
			JPanel tf2 = new JPanel();
				JPasswordField pwTf = new JPasswordField(8);
			JLabel pwReLbl = new JLabel("비밀번호 확인*", JLabel.CENTER);
			JPanel tf3 = new JPanel();
				JPasswordField pwReTf = new JPasswordField(8);	
			JLabel nameLbl = new JLabel("이름* (5자 이내)", JLabel.CENTER);
			JPanel tf4 = new JPanel();
				JTextField nameTf = new JTextField(8);
			JLabel telLbl = new JLabel("연락처*", JLabel.CENTER);
			JPanel tf5 = new JPanel();
				JTextField tel1Tf = new JTextField(3);
				JTextField tel2Tf = new JTextField(3);
				JTextField tel3Tf = new JTextField(3);
				JLabel dummy1 = new JLabel("-"); 		
				JLabel dummy2 = new JLabel("-");
			JLabel heightLbl = new JLabel("신장(cm)", JLabel.CENTER);
			JPanel tf6 = new JPanel();
				JTextField heightTf = new JTextField("",5);
			JLabel weightLbl = new JLabel("체중(kg)", JLabel.CENTER);
			JPanel tf7 = new JPanel();
				JTextField weightTf = new JTextField("",5);
		JLabel caution = new JLabel("       * 필수항목입니다.");
		
	JPanel east = new JPanel(new GridLayout(7,1));
		JPanel blank1 = new JPanel();
			JButton check1 = new JButton("중복확인");
			JButton changeId = new JButton("변경");
		JPanel blank2 = new JPanel();
		JPanel blank3 = new JPanel();
		JPanel blank4 = new JPanel();
		JPanel blank5 = new JPanel();
			JButton check2 = new JButton("중복확인");
			JButton changeTel = new JButton("연락처 변경");
		JPanel blank6 = new JPanel();
		JPanel blank7 = new JPanel();
			
	// 하단 기능 버튼
	JPanel btnPane = new JPanel();
		JButton okBtn = new JButton("확인");
		JButton closeBtn = new JButton("닫기");
		JButton editBtn = new JButton("수정");
		JButton delBtn = new JButton("탈퇴");
		JButton clearBtn = new JButton("초기화");
		
	Font fnt = new Font("맑은 고딕", 1, 12);
	Font fnt2 = new Font("맑은 고딕", 0, 12);
	Font fnt3 = new Font("맑은 고딕", 1, 10);
	
	// 중복 체크	
	Boolean checkPoint1 = false; // 아이디 중복 체크
	Boolean checkPoint2 = false; // 연락처 중복 체크
	
	Boolean mode = false;//
	
	// 변수
	List<MemberVO> memberList = new ArrayList<MemberVO>();
	MemberDAO mDAO = MemberDAO.getInstance();
	
	String id ; // 수정 패널 적용 시 사용
	String telRegistered;
	Setting set;
	SetVO setVO ;

	public MemberInfo() {
		super("회원 가입");
		
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
		
		setTitle("회원 정보 수정");
		idLbl.setText("아이디");
		idTf.setEditable(false);
		check1.setVisible(false); checkPoint1 = true;
		setTelConfirm();
		
		nameLbl.setText("이름");
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
			System.out.println("1");
			if (checkInfo()) {
				System.out.println("1");
				insertNewMember();
			}			
		} else if (obj == closeBtn) {
			dispose();
		} else if (obj == check1) {
			// 아이디 중복 체크
			if(checkPoint1 == false) {
				checkPoint1 = checkIdUsed();
			}
		} else if (obj == check2) {
			// 연락처 중복 체크
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
			// 회원 정보 수정
			updateMember();
		} else if(obj == changeTel) {
			//연락처 변경
			changeTelInfo();
		} else if(obj == changeId) {
			// 아이디 변경
			changeIdInfo();
		}
	}
	
	// 회원 정보 기입 체크
	public boolean checkInfo() {
		if (!checkPoint1) {
			JOptionPane.showMessageDialog(this, "아이디 중복 체크를 하십시오.");
		} else if (!checkPoint2) {
			JOptionPane.showMessageDialog(this, "연락처 중복 체크를 하십시오.");
		} else if (nameTf.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "이름을 입력 하십시오.");
		} else if (getPw(pwTf.getPassword()).equals("") || getPw(pwReTf.getPassword()).equals("")) {
			JOptionPane.showMessageDialog(this, "비밀번호를 입력 하십시오.");
		} else if (!(getPw(pwTf.getPassword()).equals(getPw(pwReTf.getPassword())))) {				
			JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.");
		} else {
			return true;
		}
		return false;
	}
	
	
	// 아이디 생성
	public void insertNewMember() {
		MemberVO vo = getTfData();
		
		if (mDAO.insertRecord(vo) == 1) {
			JOptionPane.showMessageDialog(this, "회원 가입이 완료되었습니다.");
			NoticeDAO noDAO = NoticeDAO.getInstance();
			noDAO.newNotice(vo.getId(), vo.getId() + "님 가입을 축하합니다.");
			
			SetDAO setDAO = SetDAO.getInstance();
			setVO = setInitialSetting();
			
			setDAO.insert(setVO);
			setTfClear();
		} else {
			JOptionPane.showMessageDialog(this, "회원 가입에 실패하였습니다.");
		}
	}
	
	public void updateMember() {
		MemberVO vo = getTfData();
		
		if ((getTelIn().equals(telRegistered))) {
			if (getPw(pwTf.getPassword()).equals("") && getPw(pwReTf.getPassword()).equals("")) {
				// 비밀번호 빼고 회원정보 수정
				int result = mDAO.updateMem(vo);
				System.out.println("1");
				if (result == 1) {
					JOptionPane.showMessageDialog(this,"회원 정보가 수정되었습니다.");
				}
			} else if (!(getPw(pwTf.getPassword()).equals(getPw(pwReTf.getPassword())))) {				
				JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.");
			} else {
				// 비밀번호 포함해서 수정
				int result = mDAO.updateMem(vo);
				if (result == 1) {
					result = mDAO.updatePw(vo);
					if (result == 1) {
						JOptionPane.showMessageDialog(this,"회원 정보가 수정되었습니다.");	
					}
				}
			}
		} else {
			if (checkPoint2 == false) {
				JOptionPane.showMessageDialog(this,"연락처 중복 체크를 하십시오.");
			} else {
				if (getPw(pwTf.getPassword()).equals("") && getPw(pwReTf.getPassword()).equals("")) {
				// 비밀번호 빼고 회원정보 수정
					int result = mDAO.updateMem(vo);
						if (result == 1) {
							JOptionPane.showMessageDialog(this,"회원 정보가 수정되었습니다.");
						}
				} else if (!(getPw(pwTf.getPassword()).equals(getPw(pwReTf.getPassword())))) {				
					JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.");				
				} else {
					// 비밀번호 포함해서 수정
					int result = mDAO.updateMem(vo);
					if (result == 1) {
						result = mDAO.updatePw(vo);
						if (result == 1) {
							JOptionPane.showMessageDialog(this,"회원 정보가 수정되었습니다.");	
						}
					}
				}
			}
		}
	}
	
	
	// 아이디 삭제
	public void deleteMember(String id) {
		int delQ = JOptionPane.showConfirmDialog(this, "회원에서 탈퇴하시겠습니까?", "", JOptionPane.YES_NO_OPTION);
		if ( delQ == JOptionPane.YES_OPTION) {
			int result = mDAO.dropMember(id);
			if (result == 1) {
				JOptionPane.showMessageDialog(this, "회원 탈퇴가 완료되었습니다.\n감사합니다.");
				dispose();
				System.exit(0);
			}
		}
	}
	
	// 입력되어 있는 정보 수집
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
	
	// 기존에 DB 에 저장된 데이터 TF 에 세팅
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
	
	// 입력 패널 초기화
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
	
	// 아이디 중복 체크
	public boolean checkIdUsed() {
		if (idTf.getText().equals("")) {
			JOptionPane.showMessageDialog(this,"사용할 아이디를 입력하십시오");
			return false;
		} else { 
			memberList = mDAO.getAllRecord();
			for (int i = 0 ; i < memberList.size(); i++) {
				MemberVO vo = memberList.get(i);
				if(vo.getId().equals(idTf.getText())) {
					JOptionPane.showMessageDialog(this, "사용 중인 ID 입니다.");
					return false;				
				}
			}
			JOptionPane.showMessageDialog(this, "사용 가능한 ID 입니다.");
			setIdConfirm();
			return true;
		}
	}
	
	// 연락처 중복 체크
	public boolean checkTelUsed() {
		if (checkPoint2 == false) {
			if (tel1Tf.getText().equals("") || tel2Tf.getText().equals("") || tel3Tf.getText().equals("")) {
				JOptionPane.showMessageDialog(this,"연락처를 입력하십시오");
				return false;
			} else { 				
				memberList = mDAO.getAllRecord();
				for (int i = 0 ; i < memberList.size(); i++) {
					MemberVO vo = memberList.get(i);
					if(vo.getTel().equals(getTelIn())) {
						JOptionPane.showMessageDialog(this, "이미 등록된 연락처 입니다.");
						return false;				
					} 
				}
				JOptionPane.showMessageDialog(this, "등록 가능한 연락처 입니다.");
				setTelConfirm();
				return true;
			}
		}
		return true;
	}
	
	
	// 중복 확인 시
	// 1. 기존 연락처와 같으면, 변경을 취소하시겠습니까? 메세지 출력 후
	// YES >> 원래 번호로 변경 check2 true;
	
	// 2. 연락처 중복 체크 
	public boolean checkTelUsed(String originalTel) {
		if (checkPoint2 == false) {
			if (tel1Tf.getText().equals("") || tel2Tf.getText().equals("") || tel3Tf.getText().equals("")) {
				JOptionPane.showMessageDialog(this,"연락처를 입력하십시오");
				return false;
			} else if(getTelIn().equals(telRegistered)) {
				int q = JOptionPane.showConfirmDialog(this, "현재 등록된 연락처와 같습니다.\n연락처를 유지하시겠습니까?","", JOptionPane.YES_NO_OPTION);
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
						JOptionPane.showMessageDialog(this, "이미 등록된 연락처 입니다.");
						return false;				 
					}
				}
				JOptionPane.showMessageDialog(this, "등록 가능한 연락처 입니다.");
				setTelConfirm();
				return true;
			}
		}
		return true;
	}
	
	// 연락처 변경
	public void changeTelInfo() {
		// 변경 버튼 숨김 후 중복 확인 버튼 비지블
		changeTel.setVisible(false);
		check2.setVisible(true);
		
		// tel TF 비우기 / 기존 연락처 정보 저장
		tel1Tf.setEditable(true);		tel2Tf.setEditable(true);		tel3Tf.setEditable(true);
		tel1Tf.setText("");		tel2Tf.setText("");		tel3Tf.setText("");
		
		mode = true;
		checkPoint2 = false;
	}
	
	public void changeIdInfo() {
		// 중복 체크 후에 다시 변경
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
		vo.setFont("맑은 고딕");
		vo.setbFont("맑은 고딕");
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