

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {

	JPanel back = new JPanel();
	JLabel backScreen = new JLabel();
	JPanel menu = new JPanel();
		JPanel lblBar = new JPanel(new GridLayout(2,1));
			JPanel lblB = new JPanel(new GridBagLayout());
				JLabel idLbl = new JLabel("아이디");
			JPanel lblB2 = new JPanel(new GridBagLayout());
				JLabel pwLbl = new JLabel("비밀번호");
		JPanel tfBar = new JPanel(new GridLayout(2,1));
			JPanel blank1 = new JPanel(new GridBagLayout());
				JTextField idTf = new JTextField(10);
			JPanel blank2 = new JPanel(new GridBagLayout());
				JPasswordField pwTf = new JPasswordField(10);
		JPanel btnBar = new JPanel(new GridLayout(2,1));
			JButton loginBtn = new JButton("들어가기");
			JButton joinBtn = new JButton("함께하기");

	Image img ;
	Font fnt = new Font("맑은 고딕", 1, 12);
	
	String id;
	ImageIcon icon;
	
	Setting set = new Setting("admin");
	
	public Login() {		
		setLayout(new BorderLayout());
		setTitle("함께 달리기");

		
		icon = new ImageIcon(getClass().getClassLoader().getResource("login.gif"));
		backScreen.setIcon(icon);

		back.setPreferredSize(new Dimension(600,400));
		back.add(backScreen);

		JPanel btn1 = new JPanel(new GridBagLayout());
		JPanel btn2 = new JPanel(new GridBagLayout());
		
		menu.setBackground(Color.white);
		menu.setPreferredSize(new Dimension(600,140));
		
		lblB.add(idLbl); lblB2.add(pwLbl);
		lblBar.add(lblB); lblBar.add(lblB2);
		lblBar.setPreferredSize(new Dimension(80,140));
		
		blank1.add(idTf); tfBar.add(blank1); blank2.add(pwTf); tfBar.add(blank2);
			tfBar.setPreferredSize(new Dimension(150,140));
		
		btnBar.setPreferredSize(new Dimension(150,140));
			loginBtn.setPreferredSize(new Dimension(100,25));
			joinBtn.setPreferredSize(new Dimension(100,25));
			loginBtn.setFont(fnt);
			joinBtn.setFont(fnt);
		
			btn1.add(loginBtn);
			btn1.setBackground(Color.white); btn1.setOpaque(true);
			btnBar.add(btn1);	
			btn2.add(joinBtn);
			btn2.setBackground(Color.white); btn2.setOpaque(true);
			btnBar.add(btn2);
		
		menu.add(lblBar);
		menu.add(tfBar);
		menu.add(btnBar);
		
		// 환결 설정
		set.setPaneStyle(back);		set.setPaneStyle(blank1);	set.setPaneStyle(blank2);
		set.setPaneStyle(lblB); 		set.setPaneStyle(lblB2);			
		set.setPaneStyle(btn1);		set.setPaneStyle(btn2);		set.setPaneStyle(btnBar);	set.setPaneStyle(menu);
		
		set.setBtnStyle(joinBtn);		set.setBtnStyle(loginBtn);
		
		set.setLblStyle(idLbl);	set.setLblStyle(pwLbl);
		
		add(back);
		add("South",menu);
		
		setBounds(100,100, 600,600);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		
		joinBtn.addActionListener(this);
		loginBtn.addActionListener(this);
		idTf.addActionListener(this);
		pwTf.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		
		if (obj == loginBtn || obj == idTf || obj == pwTf) {
			// 아이디 비밀번호 정합성 체크
			try { 
				// DAO 생성 후 DB 에서 로그인 체크
				if ( loginCheck())  { 
					// 로그인 성공
					// 메인 화면 열기  >> 로그인된 아이디 정보 넘겨주기
					// 로그인창 닫기
					JOptionPane.showMessageDialog(this, "로그인하였습니다.");
					dispose();
					new MainScreen(idTf.getText());
				} else {
					JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 다시 입력하세요");
					tfClear();
				}
			} catch(NullPointerException ne) {
				JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력해주세요.");
				tfClear();
			}
		} else if (obj == joinBtn) { 
			// 회원 가입 일 때 회원가입창 띄우기
			new MemberInfo();
		}
	}
	
	// 로그인 확인
	public boolean loginCheck() {
		MemberDAO mDAO = MemberDAO.getInstance();
		List<MemberVO> memberList = mDAO.getAllRecordL();
		
		for (int i = 0; i < memberList.size(); i++ ) {
			MemberVO vo = memberList.get(i);
			if(idTf.getText().equals(vo.getId()) && getPw(pwTf.getPassword()).equals(vo.getPassword())) {
				return true; 
			} 
		}
		return false;
	}
	
	public void tfClear() {
		idTf.setText("");
		pwTf.setText("");
	}
	public String getPw(char[] password) {
		String pw ="" ; 
		for (int i = 0 ; i < password.length ; i++) {
			pw += password[i];
		}
		return pw;
	}
}
