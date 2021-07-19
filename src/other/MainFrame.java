package other;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	private JFrame frame; 
	
	public MainFrame() {
		frame = new JFrame();
	}
	
	public void makeFrame() {
		frame.setBounds(300, 200, 500, 800);
		
		JPanel panel = new JPanel(null);
		panel.setBounds(0, 0, 500, 800);
		frame.add(panel);
		
		
		
		frame.setVisible(true);
	}
	
	public JFrame getFrame() {
		
		
		return this.frame;
	}
	
}
