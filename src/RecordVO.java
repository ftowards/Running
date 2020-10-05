
public class RecordVO {
	private int recordNo;
	private String date;
	private String time;
	private double distance; // km
	private int cal ; 
	private String id ;
	
	public RecordVO() {}
	
	public RecordVO(int recordNo, String date, String time, double distance, int cal, String id) {
		this.recordNo = recordNo;
		this.date = date;
		this.time = time;
		this.distance = distance;
		this.cal = cal;
		this.id = id;
	}
	
	

	public int getCal() {
		return cal;
	}

	public void setCal(int cal) {
		this.cal = cal;
	}

	public int getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
