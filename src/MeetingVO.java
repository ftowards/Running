
public class MeetingVO {
	private int meetingNo;
	private String date;
	private String time;
	private String location;
	private String id;
	private int nop ;
	private String code;
	private String state;
	private int entry;

	public int getEntry() {
		return entry;
	}

	public void setEntry(int entry) {
		this.entry = entry;
	}

	public MeetingVO() {	}
	
	public MeetingVO(int meetingNo, String date, String time, String location, String id, int nop, String code, String state) {
		this.meetingNo = meetingNo;
		this.date = date;
		this.time = time; 
		this.location = location;
		this.id = id;
		this.nop = nop;
		this.code = code;
		this.state = state;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getMeetingNo() {
		return meetingNo;
	}

	public void setMeetingNo(int meetingNo) {
		this.meetingNo = meetingNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNop() {
		return nop;
	}

	public void setNop(int nop) {
		this.nop = nop;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}
