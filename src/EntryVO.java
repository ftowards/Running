
public class EntryVO {
	private int meetingNo ;
	private String id;
	private String present;

	public EntryVO() {
	}
	
	public EntryVO(int meetingNo, String id, String present) {
		this.meetingNo = meetingNo;
		this.id = id;
		this.present = present ;
	}

	public String getPresent() {
		return present;
	}

	public void setPresent(String present) {
		this.present = present;
	}

	public int getMeetingNo() {
		return meetingNo;
	}

	public void setMeetingNo(int meetingNo) {
		this.meetingNo = meetingNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
