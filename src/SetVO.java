import java.awt.Color;

public class SetVO {
	
	String id ;
	int bgR ;
	int bgG ;
	int bgB ;
	int fgR ;
	int fgG ;
	int fgB ;
	int bbgR ;
	int bbgG ;
	int bbgB ;
	int bfgR ;
	int bfgG ;
	int bfgB ;
	String font ;
	String bFont ;

	public String getbFont() {
		return bFont;
	}

	public void setbFont(String bFont) {
		this.bFont = bFont;
	}

	public SetVO() {	}
	
	public SetVO(String id, int r, int g, int b, int r2, int g2, int b2, int r3, int g3, int b3, int r4, int g4, int b4, String font, String bFont) {
		this.id = id;
		this.font = font;
		this.bFont = bFont;
		
		this.bgR = r; 		this.bgG = g;		this.bgB = b;
		this.fgR = r2; 		this.fgG = g2;		this.fgB = b2;
		this.bbgR = r3; 		this.bbgG = g3;		this.bbgB = b3;
		this.bfgR = r4; 		this.bfgG = g4;		this.bfgB = b4;
	}
	
	public void setBg(Color c) {
		bgR = c.getRed();
		bgG = c.getGreen();
		bgB = c.getBlue();
	}
	
	public Color getBg() {
		return new Color(bgR, bgG, bgB);
	}
	
	public void setFg(Color c) {
		fgR = c.getRed();
		fgG = c.getGreen();
		fgB = c.getBlue();
	}
	
	public Color getFg() {
		return new Color(fgR, fgG, fgB);
	}

	public void setBbg(Color c) {
		bbgR = c.getRed();
		bbgG = c.getGreen();
		bbgB = c.getBlue();
	}
	
	public Color getBbg() {
		return new Color(bbgR, bbgG, bbgB);
	}

	public void setBfg(Color c) {
		bfgR = c.getRed();
		bfgG = c.getGreen();
		bfgB = c.getBlue();
	}
	
	public Color getBfg() {
		return new Color(bfgR, bfgG, bfgB);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public int getBgR() {
		return bgR;
	}

	public void setBgR(int bgR) {
		this.bgR = bgR;
	}

	public int getBgG() {
		return bgG;
	}

	public void setBgG(int bgG) {
		this.bgG = bgG;
	}

	public int getBgB() {
		return bgB;
	}

	public void setBgB(int bgB) {
		this.bgB = bgB;
	}

	public int getFgR() {
		return fgR;
	}

	public void setFgR(int fgR) {
		this.fgR = fgR;
	}

	public int getFgG() {
		return fgG;
	}

	public void setFgG(int fgG) {
		this.fgG = fgG;
	}

	public int getFgB() {
		return fgB;
	}

	public void setFgB(int fgB) {
		this.fgB = fgB;
	}

	public int getBbgR() {
		return bbgR;
	}

	public void setBbgR(int bbgR) {
		this.bbgR = bbgR;
	}

	public int getBbgG() {
		return bbgG;
	}

	public void setBbgG(int bbgG) {
		this.bbgG = bbgG;
	}

	public int getBbgB() {
		return bbgB;
	}

	public void setBbgB(int bbgB) {
		this.bbgB = bbgB;
	}

	public int getBfgR() {
		return bfgR;
	}

	public void setBfgR(int bfgR) {
		this.bfgR = bfgR;
	}

	public int getBfgG() {
		return bfgG;
	}

	public void setBfgG(int bfgG) {
		this.bfgG = bfgG;
	}

	public int getBfgB() {
		return bfgB;
	}

	public void setBfgB(int bfgB) {
		this.bfgB = bfgB;
	}	
}
