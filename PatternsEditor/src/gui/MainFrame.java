package gui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import mainEngine.Controller;


@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	private JPanel templatePanel;
	private Controller controller;
	private ArrayList<JTextPane> fields = new ArrayList<JTextPane>(); 
    private JFileChooser fileChooser = new JFileChooser();
    private int patternPotition;
	
	public MainFrame(String title){
		super(title);
		controller = new Controller();
		
		/*
		 * Set layout manager
		 */
		setLayout(new BorderLayout());
		
		/*
		 * Create Swing component
		 */
		//Menu bar
		JMenuBar menuBar = new JMenuBar();
		//File Menu
		JMenu menuFile = new JMenu("File");
		JMenuItem fileItem1 = new JMenuItem("New pattern language");
		JMenuItem fileItem2 = new JMenuItem("Remove pattern");
		final JMenuItem fileItem3 = new JMenuItem("Save to text file");
		final JMenuItem fileItem4 = new JMenuItem("Save latex decorated to text file");
		final JMenuItem fileItem5 = new JMenuItem("Load from text file");
		final JMenuItem fileItem6 = new JMenuItem("Load from latex decorated text file");
		JMenuItem fileItem7 = new JMenuItem("Exit");
		
		
		JMenu menuEdit = new JMenu("Edit");
		JMenuItem editItem1 = new JMenuItem("Edit content");
		JMenuItem editItem3 = new JMenuItem("Exit");
		
		//Sub menu1
		JMenu subMenu1 = new JMenu("Add new pattern");
		JMenuItem subMenu1Item1 = new JMenuItem("MicroPattern");
		JMenuItem subMenu1Item2 = new JMenuItem("Inductive Mini-Pattern");
		JMenuItem subMenu1Item3 = new JMenuItem("Deductive Mini-Pattern");
		JMenuItem subMenu1Item4 = new JMenuItem("Gang of four Pattern");
		JMenuItem subMenu1Item5 = new JMenuItem("System of Patterns Template");	
		
		menuFile.add(fileItem1);
		menuFile.add(subMenu1);
		menuFile.add(fileItem2);
		menuFile.add(fileItem3);
		menuFile.add(fileItem4);
		menuFile.add(fileItem5);
		menuFile.add(fileItem6);
		menuFile.add(fileItem7);

		menuEdit.add(editItem1);
		menuEdit.add(editItem3);
		
		subMenu1.add(subMenu1Item1);
		subMenu1.add(subMenu1Item2);
		subMenu1.add(subMenu1Item3);
		subMenu1.add(subMenu1Item4);
		subMenu1.add(subMenu1Item5);
		
		
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		
		
		//Template Panel
		
		templatePanel = new JPanel();
		final JButton okButton = new JButton("OK");
		final JButton editButton = new JButton("OK");
		templatePanel.setLayout(new BoxLayout(templatePanel, BoxLayout.Y_AXIS));//put labels vertically
		
		/*
		 * Add Swing components to content pane
		 */
		Container container = getContentPane();
		container.add(menuBar, BorderLayout.PAGE_START);
		container.add(templatePanel,BorderLayout.CENTER);
		
		
		/*
		 * Add behavior
		 */
		
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.setFields(fields, controller.getPattern());
				templatePanel.removeAll();//clears template Panel
				fields.clear();//clears all fields
				JOptionPane.showMessageDialog(null, "The pattern has been added.");
				revalidate();
				repaint();
			}
		});
		
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.setFields(fields, controller.getPattern(patternPotition));
				templatePanel.removeAll();//clears template Panel
				fields.clear();//clears all fields
				JOptionPane.showMessageDialog(null, "The pattern has been edited.");
				revalidate();
				repaint();
			}
		});
		
		fileItem1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String title = JOptionPane.showInputDialog("Give me the title of the pattern language.");
				try{
					if(title.equals("")){
						title = "Pattern language";
						JOptionPane.showMessageDialog(null, "Default name given to your project.");
						JOptionPane.showMessageDialog(null, "Add pattern form file menu");
						controller.createPatternLanguage(title); //set the title of pattern language
					}else{
						JOptionPane.showMessageDialog(null, "Add pattern form file menu");
						controller.createPatternLanguage(title); //set the title of pattern language
					}
				}catch(Exception e1){//cancel or x halding
	                return;
	            }
			}
		});
		
		fileItem2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String patternName = JOptionPane.showInputDialog("Give me the name of the pattern you want to remove.");
				try{
					if(controller.removePattern(patternName)){
						JOptionPane.showMessageDialog(null, "The pattern " + patternName +"  has been removed.");
					}else{
						JOptionPane.showMessageDialog(null, "There is no pattern with name " + patternName + ". No pattern has been removed.");
					}
				}catch(Exception e1){ //cancel or x halding
					return;
				}
			}
		});
		
		fileItem3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String fileName = "/";
				fileName += JOptionPane.showInputDialog("Give text file name.") + ".txt";
				try{
					String path = createGUISave(fileItem3);
					if(path == null) return;
					controller.savePatternLanguage(path, fileName);
				}catch(Exception e1){
					return;
				}
			}	
			
		});
	
		fileItem4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String fileName = "/";
				fileName += JOptionPane.showInputDialog("Give text file name.") + ".txt";
				try{
					String path = createGUISave(fileItem4);
					if(path == null) return;
					controller.saveDecoratedPatternLanguage(path, fileName);
				}catch(Exception e1){
					return;
				}
			}	
			
		});
		
		fileItem5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					String path = createGUILoad(fileItem5);
					if(path == null) return;
					controller.loadPatternLanguage(path);
				}catch(Exception e1){
					return;
				}
			}	
			
		});
		
		fileItem6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					String path = createGUILoad(fileItem6);
					if(path == null) return;
					controller.loadDecoratedPatternLanguage(path);
				}catch(Exception e1){
					return;
				}
			}	
			
		});
		
		editItem1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String patternName = JOptionPane.showInputDialog("Give me the name of the pattern you want to edit.");
				try{
					int patternPosition = controller.getPatternPotition(patternName);
					if(patternPosition < 0){
						JOptionPane.showMessageDialog(null, "There is no pattern with name " + patternName + ".");
					}else{
						patternPotition = patternPosition;
						ArrayList<String> labels = controller.getLabels(patternPosition);
						ArrayList<String> contents = controller.getContents(patternPosition);
						createGUIEdit(labels, contents, editButton);
					}
				}catch(Exception e1){
					return;
				}
			}
		});		
		
		subMenu1Item1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ArrayList<String> labels = controller.addPattern("MicroPattern"); //takes the labels from MicroPattern
				createGUIElements(labels, okButton);//sends them to be created to gui
			}
		});
		
		subMenu1Item2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ArrayList<String> labels = controller.addPattern("Inductive-Mini-Pattern");
				createGUIElements(labels, okButton);
			}
		});
		
		subMenu1Item3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ArrayList<String> labels = controller.addPattern("Deductive-Mini-Pattern");
				createGUIElements(labels, okButton);
			}
		});
		
		subMenu1Item4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ArrayList<String> labels = controller.addPattern("Gang-of-four-Pattern");
				createGUIElements(labels, okButton);
			}
		});
	
		subMenu1Item5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ArrayList<String> labels = controller.addPattern("System-of-Patterns-Template");
				createGUIElements(labels, okButton);
			}
		});
	
	}
	
	private void createGUIEdit(ArrayList<String> labels, ArrayList<String> contents, JButton button) {
		for (int i = 0; i < labels.size(); i++) {
			JPanel panel = new JPanel(), panelAbove = new JPanel(), panelBellow = new JPanel(), panelSide = new JPanel();
			panel.setLayout(new BorderLayout());
			JLabel guiLabel = new JLabel(labels.get(i));
			panel.add(guiLabel, BorderLayout.WEST);
			JTextPane field = new JTextPane();
			field.setText(contents.get(i));
			fields.add(field);
			panel.add(field, BorderLayout.CENTER);
			panel.add(panelAbove, BorderLayout.NORTH);
			panel.add(panelBellow, BorderLayout.SOUTH);
			panel.add(panelSide, BorderLayout.EAST);
			templatePanel.add(panel);
		}
		templatePanel.add(button, BorderLayout.SOUTH);
		revalidate();
		repaint();
		
	}

	private void createGUIElements(ArrayList<String> labels, JButton button){
		for (String label : labels) {
			JPanel panel = new JPanel(), panelAbove = new JPanel(), panelBellow = new JPanel(), panelSide = new JPanel();
			panel.setLayout(new BorderLayout());
			JLabel guiLabel = new JLabel(label);
			panel.add(guiLabel, BorderLayout.WEST);
			JTextPane field = new JTextPane();
			fields.add(field);
			panel.add(field, BorderLayout.CENTER);
			panel.add(panelAbove, BorderLayout.NORTH);
			panel.add(panelBellow, BorderLayout.SOUTH);
			panel.add(panelSide, BorderLayout.EAST);
			templatePanel.add(panel);
		}
		templatePanel.add(button, BorderLayout.SOUTH);
		revalidate();
		repaint();
	}
	
	private String createGUISave(Component component){
		templatePanel.add(fileChooser);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle("Choose diractory to save the file");
		if (fileChooser.showSaveDialog(component) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			revalidate();
			repaint();
			return file.getAbsolutePath();
		}
		
		return null;
	}
	
	private String createGUILoad(Component component){
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		fileChooser.setFileFilter(filter);
		templatePanel.add(fileChooser);
		fileChooser.setDialogTitle("Choose a text file to load");
		if (fileChooser.showOpenDialog(component) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			revalidate();
			repaint();
			return file.getAbsolutePath();
		}	
		return null;
	}
}
