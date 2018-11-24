package gui;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class GuiMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				JFrame frame = new MainFrame("Gang Of Three App");
				frame.setSize(500,800);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

}
