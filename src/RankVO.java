
public class RankVO {
	
	private String rank;
	private String id;
	private String cnt;
	private String mem;

	public RankVO(String rank, String id, String cnt, String mem) {
		this.rank = rank;
		this.id = id;
		this.cnt = cnt;
		this.mem = mem;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public String getMem() {
		return mem;
	}

	public void setMem(String mem) {
		this.mem = mem;
	}
	
}
