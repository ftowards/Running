
public class MemberVO {
	
	private String id ;
	private String password ;
	private String userName ;
	private String tel ;
	private double height ;
	private double weight ;

	public MemberVO() {
	}
	
	public MemberVO(String id, String password, String userName, String tel, double height, double weight ) {
		this.id = id;
		this.password = password;
		this.userName = userName;
		this.tel = tel;
		this.height = height;
		this.weight = weight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	

}
