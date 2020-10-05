
public class NoticeVO {
	private String id;
	private String msg;
	private String read;
	private String inputDate;

	public NoticeVO() {
	}
	
	public NoticeVO(String id, String msg, String read, String inputDate) {
		this.id = id;
		this.msg = msg;
		this.read = read;
		this.inputDate = inputDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
}
