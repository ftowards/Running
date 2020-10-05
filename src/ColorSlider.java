import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorSlider extends JPanel implements ChangeListener{

	JPanel panel = new JPanel(new BorderLayout());
		JPanel colorBoard = new JPanel();
		
		JPanel sliderBoard = new JPanel(new BorderLayout());
			JPanel lblPane = new JPanel(new GridLayout(3,1));
				JLabel lblR = new JLabel("R",JLabel.CENTER);
				JLabel lblG = new JLabel("G",JLabel.CENTER);
				JLabel lblB = new JLabel("B",JLabel.CENTER);
			JPanel sliderPane = new JPanel(new GridLayout(3,1));
				JSlider sliderR = new JSlider(JSlider.HORIZONTAL,0,255,0 );
				JSlider sliderG = new JSlider(JSlider.HORIZONTAL,0,255,0 );
				JSlider sliderB = new JSlider(JSlider.HORIZONTAL,0,255,0 );
		
	String id ;
	Setting set ;
	Color color ;
	
	int r =0; 		int g =0;		int b =0;
	
	public ColorSlider() {}
	
	public ColorSlider(String id ) {
		this.id = id;
		set = new Setting(id);
		color = new Color(r,g,b);
		
		setLayout(new BorderLayout());

		add("West", colorBoard);
			colorBoard.setPreferredSize(new Dimension(40,20));
			colorBoard.setBackground(color);
		
		add("Center", sliderBoard);						set.setPaneStyle(sliderBoard);
			sliderBoard.add("West", lblPane);		set.setPaneStyle(lblPane);
				lblPane.add(lblR);							set.setLblStyle(lblR,1,10);
				lblPane.add(lblG);							set.setLblStyle(lblG,1,10);
				lblPane.add(lblB);							set.setLblStyle(lblB,1,10);
			sliderBoard.add(sliderPane);				set.setPaneStyle(sliderPane);
				sliderPane.add(sliderR);					sliderR.setBackground(set.bgColor);
				sliderPane.add(sliderG);					sliderG.setBackground(set.bgColor);
				sliderPane.add(sliderB);					sliderB.setBackground(set.bgColor);
				
		setSize(60,40);
		setVisible(true);
		
		sliderR.addChangeListener(this);
		sliderG.addChangeListener(this);
		sliderB.addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		r = sliderR.getValue();
		g = sliderG.getValue();
		b = sliderB.getValue();
		
		color = new Color(r, g, b);
		colorBoard.setBackground(color);
	}
	
	public static void main(String args[]) {
		new ColorSlider("ftowards");
	}
}
