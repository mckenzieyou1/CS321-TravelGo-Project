import javax.swing.*;
import java.awt.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoadPage {
    private final JFrame frame;
    //private final ButtonPanel buttonSelection;
    private JButton button;
    private JLabel label;
    

    public LoadPage() {
        //this.buttonSelection = new ButtonPanel();
        this.frame = createAndShowGUI();
    }

    private JFrame createAndShowGUI(){
        JFrame frame = new JFrame("TravelGo");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setJMenuBar(createMenuBar());
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
			@Override
			 public void windowClosing(WindowEvent event) {
				shutdown();
			}
		});
        frame.add(goButton());
        frame.pack();
        frame.setSize(400, 700);
		//frame.setLocationByPlatform(true);
		frame.setVisible(true);
        return frame;
    }
    private JPanel goButton(){
        JPanel panel = new JPanel();
        //FlowLayout layout = new FlowLayout();
        //panel.setLayout(new BorderLayout());
        ImageIcon image = new ImageIcon("go.png");
        JTextArea text = new JTextArea("Welcome to TravelGo! Select Go to continue:");
        text.setFont(new Font("Serif", Font.BOLD, 18));
        text.setEditable(false);
        panel.add(text);
        //panel.setLayout(layout);
        button = new JButton(image);
        button.addActionListener(e ->buttonClicked());
        button.setFocusable(false);
        panel.add(button);
        return panel;
    }

    private void buttonClicked(){
        new travelappFrame();
        frame.dispose();
    }

    private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		JMenuItem instructionsItem = new JMenuItem("Instructions...");
		instructionsItem.addActionListener(event -> new InstructionsDialog()); //event -> new InstructionsDialog(this)
		helpMenu.add(instructionsItem);
		
		JMenuItem aboutItem = new JMenuItem("About...");
		aboutItem.addActionListener(event -> new AboutDialogue()); //new AboutDialog(this)
		helpMenu.add(aboutItem);
		
		return menuBar;
    }

    private void shutdown() {
		frame.dispose();
		System.exit(0);
	}

}